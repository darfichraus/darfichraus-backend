package de.darfichraus.service;

import de.darfichraus.dto.NearGeoData;
import de.darfichraus.entity.GeoData;
import de.darfichraus.repository.GeoDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoDataService {

    final GeoDataRepository geoDataRepository;

    public GeoDataService(GeoDataRepository geoDataRepository) {
        this.geoDataRepository = geoDataRepository;
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
}
