package de.darfichraus.service;

import de.darfichraus.entity.HealthCountyInformation;
import de.darfichraus.model.HealthInformationResponse;
import de.darfichraus.model.LocationType;
import de.darfichraus.repository.HealthCountyInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class HealthInformationService {

    private final HealthCountyInformationRepository healthCountyInformationRepository;
    private final MappingService mappingService;
    private final GeoDataService geoDataService;

    @Autowired
    public HealthInformationService(HealthCountyInformationRepository healthCountyInformationRepository,
                                    MappingService mappingService,
                                    GeoDataService geoDataService) {
        this.healthCountyInformationRepository = healthCountyInformationRepository;
        this.mappingService = mappingService;
        this.geoDataService = geoDataService;
    }

    public HealthCountyInformation getByGeometry(String id) {

        HealthCountyInformation healthCountyInformation = this.healthCountyInformationRepository.findByGeometry(id);

        if (healthCountyInformation == null) {
            throw new EntityNotFoundException(id + " not found");
        }

        return healthCountyInformation;
    }

    public List<HealthCountyInformation> getAll() {
        return this.healthCountyInformationRepository.findAll();
    }

    public List<HealthCountyInformation> getByCounty(String county) {

        List<HealthCountyInformation> healthCountyInformation = this.healthCountyInformationRepository.findByCounty(county);

        if (healthCountyInformation == null || healthCountyInformation.isEmpty()) {
            throw new EntityNotFoundException(county + " not found");
        }

        return healthCountyInformation;
    }

    public de.darfichraus.model.HealthInformationResponse getByZip(String zip) {

        List<de.darfichraus.model.Location> locations = new ArrayList<>();
        de.darfichraus.model.LocationResponse locationResponse = this.geoDataService.findLocationsByZip(zip);
        String city = locationResponse.getCity().getCity();

        locations.addAll(locationResponse.getHierarchy().stream().
                filter(location -> (location.getLocationType() == LocationType.COUNTY) || (location.getLocationType() == LocationType.CITY)).
                collect(Collectors.toList())
        );

        List<HealthCountyInformation> healthCountyInformation = new ArrayList<>();
        locations.forEach(location -> {
            List<HealthCountyInformation> hci = this.getByCounty(location.getName());
            healthCountyInformation.addAll(hci);
        });

        de.darfichraus.model.HealthInformationResponse response = new HealthInformationResponse();
        response.setLocation(locationResponse.getCity());
        response.setHealthInformation(healthCountyInformation.stream().filter(distinctByKey(HealthCountyInformation::getId)).collect(Collectors.toList()));

        return response;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
