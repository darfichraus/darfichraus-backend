package de.darfichraus.service.situationAdvisor;

import de.darfichraus.model.SituationMessage;
import de.darfichraus.model.SituationMessagesBySituationResponse;
import de.darfichraus.repository.situationAdvisor.SituationMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;

@Service
public class SituationMessageService {

    private final SituationMessageRepository situationMessageRepository;
    private final SituationCategoryService situationCategoryService;
    private final SituationReferenceService situationReferenceService;
    private final SituationMessageTypeService situationMessageTypeService;
    private final SituationService situationService;

    @Autowired
    public SituationMessageService(SituationMessageRepository situationMessageRepository,
                                   SituationCategoryService situationCategoryService,
                                   SituationReferenceService situationReferenceService,
                                   SituationMessageTypeService situationMessageTypeService,
                                   SituationService situationService) {
        this.situationMessageRepository = situationMessageRepository;
        this.situationCategoryService = situationCategoryService;
        this.situationMessageTypeService = situationMessageTypeService;
        this.situationReferenceService = situationReferenceService;
        this.situationService = situationService;
    }

    public boolean deleteById(String id) {
        if (!this.situationMessageRepository.existsById(id)) {
            return false;
        }

        this.situationMessageRepository.deleteById(id);

        return true;
    }

    public de.darfichraus.model.SituationMessage findById(String id) {
        return this.situationMessageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " not found"));
    }

    public de.darfichraus.model.SituationMessage save(SituationMessage situationMessage) {
        return this.situationMessageRepository.save(situationMessage);
    }

    public List<SituationMessage> findAll(OffsetDateTime lastRequest) {

        List<SituationMessage> messages;
        if (lastRequest == null) {
            messages = this.situationMessageRepository.findAll();
        } else {
            messages = this.situationMessageRepository.findAllByModifiedAfter(lastRequest.toInstant());
        }

        return messages;
    }

    public List<de.darfichraus.model.SituationMessage> findAllBySituationId(String id) {
        return this.situationMessageRepository.findAllBySituationId(id);
    }

    public List<SituationMessage> findAllByCategoryId(String id) {
        return this.situationMessageRepository.findAllByAffectedCategories(id);
    }

    public List<de.darfichraus.model.SituationMessageResponse> resolveAll(OffsetDateTime lastRequest) {

        List<SituationMessage> messages = this.findAll(lastRequest);
        return resolveAllMessage(messages);
    }

    public de.darfichraus.model.SituationMessageResponse resolveOne(String id) {
        return this.resolve(this.findById(id));
    }


    private List<de.darfichraus.model.SituationMessageResponse> resolveAllMessage(List<SituationMessage> messages) {

        List<de.darfichraus.model.SituationMessageResponse> resolved = new ArrayList<>();

        messages.forEach(message -> {
            try {
                resolved.add(this.resolve(message));
            } catch (Exception e) {
                // ToDo: Logging
            }
        });

        return resolved;
    }

    /**
     * Resolves all internal ids to the respective object
     * @param situationMessage to resolve
     * @return a full resolved SituationMessage
     */
    private de.darfichraus.model.SituationMessageResponse resolve(SituationMessage situationMessage) {

        de.darfichraus.model.SituationMessageResponse response = this.createFromSituationMessage(situationMessage);

        // Resolve situation
        try {
            de.darfichraus.model.Situation situation = this.situationService.getById(situationMessage.getSituationId());
            response.setSituation(situation);
        } catch (EntityNotFoundException enfe) {
            // ToDo: Loggin
        }

        // Resolve MessageType
        try {
            de.darfichraus.model.SituationMessageType situationMessageType = this.situationMessageTypeService.findById(
                    situationMessage.getMessageTypeId()
            );
            response.setMessageType(situationMessageType);
        } catch (EntityNotFoundException enfe) {
            // ToDo: Loggin
        }

        // Resolve References
        List<de.darfichraus.model.SituationReference> references = this.situationReferenceService.resolveIds(
                situationMessage.getDocuments()
        );
        response.setDocuments(references);

        // Resolve Categories
        List<de.darfichraus.model.SituationCategory> categories = this.situationCategoryService.resolveIds(
                situationMessage.getAffectedCategories()
        );
        response.setAffectedCategories(categories);

        return response;
    }


    public de.darfichraus.model.SituationMessagesBySituationResponse resolveAllBySituationId(String id) {

        List<SituationMessage> messages = this.situationMessageRepository.findAllBySituationId(id);
        de.darfichraus.model.Situation situation = this.situationService.getById(id);
        de.darfichraus.model.SituationMessagesBySituationResponse situationMessagesBySituationResponse = new SituationMessagesBySituationResponse();

        situationMessagesBySituationResponse.setSituation(situation);
        situationMessagesBySituationResponse.setSituationMessages(this.resolveAllMessage(messages));

        return situationMessagesBySituationResponse;
    }

    private de.darfichraus.model.SituationMessageResponse createFromSituationMessage(SituationMessage message) {

        de.darfichraus.model.SituationMessageResponse situationMessageResponse = new de.darfichraus.model.SituationMessageResponse();

        situationMessageResponse.setId(message.getId());
        situationMessageResponse.setMessage(message.getMessage());
        situationMessageResponse.setTitle(message.getTitle());
        situationMessageResponse.setExcerpt(message.getExcerpt());
        situationMessageResponse.setIcon(message.getIcon());
        situationMessageResponse.setSeverity(message.getSeverity());
        situationMessageResponse.setStatus(message.getStatus());
        situationMessageResponse.setVersion(message.getVersion());
        situationMessageResponse.setModified(message.getModified());
        situationMessageResponse.setAreaOfEffect(message.getAreaOfEffect());
        return situationMessageResponse;
    }
}
