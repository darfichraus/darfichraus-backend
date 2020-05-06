package de.darfichraus.service.situationAdvisor;

import de.darfichraus.model.SituationMessage;
import de.darfichraus.repository.situationAdvisor.SituationMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
            ZoneId mst =ZoneId.of("Europe/Berlin");
            OffsetDateTime mstOffsetDateTime = OffsetDateTime.now(ZoneId.from(lastRequest));
            ZonedDateTime mstZonedDateTime = mstOffsetDateTime.atZoneSameInstant(mst);
            messages = this.situationMessageRepository.findAllByModifiedAfter(mstZonedDateTime);
        }

        return messages;
    }
}
