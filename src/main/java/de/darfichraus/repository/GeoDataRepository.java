package de.darfichraus.repository;


import de.darfichraus.entity.GeoData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeoDataRepository extends MongoRepository<GeoData, String> {

    @Query(value = "{ \"properties.TYPE_1\": { $exists: true  }}}")
    List<GeoData> findAllStates();

    @Query(value = "{ \"properties.TYPE_2\": { $exists: true  },\"properties.NAME_1\":\"?0\"}}}")
    List<GeoData> findAllAdminAreasForState(String state);

    @Query(value = "{ \"properties.TYPE_3\": { $exists: true  },\"properties.NAME_2\":\"?0\"}}}")
    List<GeoData> findAllRegionalAreasForAdminArea(String admin);

    @Query(value = "{geometry:{\n" +
            "        $near : {\n" +
            "            $geometry : {\n" +
            "                index : \"Point\" ,\n" +
            "                coordinates : [?0, ?1 ]\n" +
            "            },\n" +
            "            $maxDistance : ?2\n" +
            "        }\n" +
            "    }\n" +
            "}")
    List<GeoData> findByGeometryNear(double x, double y, int distanceInMeters);
}
