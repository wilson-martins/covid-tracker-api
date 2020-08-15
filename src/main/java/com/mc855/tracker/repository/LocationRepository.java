package com.mc855.tracker.repository;

import com.mc855.tracker.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByPersonId(Long personId);

    @Query(value = "select l.*\n" +
            "from location l\n" +
            "where (l.short_latitude,\n" +
            "       l.short_longitude,\n" +
            "       year(l.visited_dt),\n" +
            "       month(l.visited_dt),\n" +
            "       day(l.visited_dt),\n" +
            "       hour(l.visited_dt)) in (\n" +
            "          select loc.short_latitude,\n" +
            "                 loc.short_longitude,\n" +
            "                 year(loc.visited_dt),\n" +
            "                 month(loc.visited_dt),\n" +
            "                 day(loc.visited_dt),\n" +
            "                 hour(loc.visited_dt),\n" +
            "                 count(distinct loc.person_id) as c\n" +
            "          from location loc\n" +
            "          where (-1 in (:personIds) or loc.person_id in (:personIds))\n" +
            "            and loc.visited_dt >= :dt\n" +
            "          group by loc.short_latitude, loc.short_longitude, year(loc.visited_dt), month(loc.visited_dt),\n" +
            "                   day(loc.visited_dt),\n" +
            "                   hour(loc.visited_dt)\n" +
            "          having c > 1);", nativeQuery = true)
    List<Location> findRiskLocations(@Param("dt") Date date,
                                     @Param("personIds") Collection<Long> personIds)
}
