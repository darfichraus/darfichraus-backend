package de.darfichraus.service.situationAdvisor;

import de.darfichraus.model.SituationMessage;
import de.darfichraus.repository.situationAdvisor.SituationMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SituationMessageService {

    private final SituationMessageRepository situationMessageRepository;

    @Autowired
    public SituationMessageService(SituationMessageRepository situationMessageRepository) {
        this.situationMessageRepository = situationMessageRepository;
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
}
