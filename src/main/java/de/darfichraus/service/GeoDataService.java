package de.darfichraus.service;

import de.darfichraus.dto.NearGeoData;
import de.darfichraus.entity.GeoData;
import de.darfichraus.entity.Mapping;
import de.darfichraus.model.CityInformation;
import de.darfichraus.model.Location;
import de.darfichraus.model.LocationResponse;
import de.darfichraus.model.LocationType;
import de.darfichraus.repository.GeoDataRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeoDataService {

    private final GeoDataRepository geoDataRepository;
    private final MappingService mappingService;


    public GeoDataService(GeoDataRepository geoDataRepository,
                          MappingService mappingService) {
        this.geoDataRepository = geoDataRepository;
        this.mappingService = mappingService;
    }

    public List<GeoData> getAllStates() {
        return geoDataRepository.findAllStates();
    }

    public List<GeoData> getAdminAreasForState(final String state) {
        return geoDataRepository.findAllAdminAreasForState(state);
    }

    public List<GeoData> getRegionalAreasForAdminArea(final String admin) {
        return geoDataRepository.findAllRegionalAreasForAdminArea(admin);
    }

    public List<GeoData> getGeoDataNearLocation(NearGeoData nearGeoData) {
        return geoDataRepository.findByGeometryNear(nearGeoData.getPoint().getX(), nearGeoData.getPoint().getY(), nearGeoData.getDistanceInMeters());
    }

    public de.darfichraus.model.LocationResponse findLocationsByZip(String zip) {

        List<de.darfichraus.model.Location> locations = new ArrayList<>();

        Mapping zipInformation = new Mapping();
        try {
            zipInformation = this.findMappingByZip(zip);
        } catch (EntityNotFoundException e) {
            return new LocationResponse();
        }


        String city = zipInformation.getCity();
        String county = zipInformation.getCounty().replace("Landkreis ", "").replace("Kreis ", "");
        String state = zipInformation.getState();

        locations.addAll(resolveCityToGeoDataLocation(city, city));
        if (county.isEmpty()) {
            county = city;
        }

        String[] countyParts = county.split(" ");
        locations.addAll(resolveCityToGeoDataLocation(countyParts[0], city));


        locations.add(getState(state));

        locations = locations.stream().distinct().collect(Collectors.toList());

        de.darfichraus.model.LocationResponse response = new LocationResponse();
        response.setCity(this.transformMappingToCityInformation(zipInformation));
        response.setHierarchy(locations);

        return response;
    }

    private List<Location> resolveCityToGeoDataLocation(String cityOrCounty, String city) {
        List<Location> locations = new ArrayList<>();


        List<GeoData> geoDataForCity = this.resolveCity(cityOrCounty + ".*");

        if (geoDataForCity.isEmpty()) {
            return locations;
        }

        geoDataForCity.forEach(geo -> {
            Location location = new Location();
            location.setGeoId(geo.getId());
            if (geo.getProperties().containsKey("ENGTYPE_3")) {
                if (geo.getProperties().get("ENGTYPE_3").equals("Urban district")) {
                    if (!cityOrCounty.equalsIgnoreCase(city)) {
                        return;
                    }
                    location.setLocationType(LocationType.CITY);
                } else {
                    location.setLocationType(LocationType.COUNTY);
                }
                location.setName(cityOrCounty);
                locations.add(location);
            }
        });

        String administrativeArea = geoDataForCity.get(0).getProperties().get("NAME_2").toString();
        Location adminLocation = this.getAdministrativeArea(administrativeArea);

        locations.add(adminLocation);

        return locations;
    }

    private Location getState(String state) {

        Location locationForState = new Location();

        GeoData geoDataState = this.resolveState(state);

        if (geoDataState == null) {
            return locationForState;
        }

        locationForState.setName(state);
        locationForState.setGeoId(geoDataState.getId());
        locationForState.setLocationType(LocationType.STATE);

        return locationForState;
    }

    private Location getAdministrativeArea(String area) {

        Location locationForAdminArea = new Location();

        GeoData geoDataState = this.resolveAdministrativeArea(area);

        if (geoDataState == null) {
            return null;
        }

        locationForAdminArea.setName(area);
        locationForAdminArea.setGeoId(geoDataState.getId());
        locationForAdminArea.setLocationType(LocationType.ADMINISTRATIVE_AREA);

        return locationForAdminArea;
    }


    private Mapping findMappingByZip(String zip) {

        List<Mapping> zipInformation = new ArrayList<>();
        try {
            zipInformation = this.mappingService.getMappingForZip(zip).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(zip + "_is_unknown");
        }

        return zipInformation.get(0);
    }

    private List<GeoData> resolveCity(String city) {
        return this.geoDataRepository.findAllCitiesByPropertiesRegex(city);
    }

    private GeoData resolveState(String state) {
        return this.geoDataRepository.findState(state);
    }

    private GeoData resolveAdministrativeArea(String area) {
        return this.geoDataRepository.findAdministrationArea(area);
    }

    private de.darfichraus.model.CityInformation transformMappingToCityInformation(Mapping mapping) {

        de.darfichraus.model.CityInformation cityInformation = new CityInformation();

        cityInformation.setCity(mapping.getCity());
        cityInformation.setZip(mapping.getZip());
        cityInformation.setCounty(mapping.getCounty());
        cityInformation.setState(mapping.getState());
        cityInformation.setCountry(mapping.getCountry());

        return cityInformation;
    }


    public de.darfichraus.model.ZipSearchResponse findLocationsByZipPart(String zip) {

        List<CityInformation> cityInformation = this.mappingService.findAllByZipStartingWith(zip);

        de.darfichraus.model.ZipSearchResponse zipSearchResponse = new de.darfichraus.model.ZipSearchResponse();
        zipSearchResponse.setZipPart(zip);
        zipSearchResponse.setCount(cityInformation.size());
        zipSearchResponse.setCities(cityInformation);

        return zipSearchResponse;
    }
}
