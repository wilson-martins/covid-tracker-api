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
            "         join (select loc.short_latitude           short_latitude,\n" +
            "                      loc.short_longitude          short_longitude,\n" +
            "                      year(loc.visited_dt)         year_dt,\n" +
            "                      month(loc.visited_dt)        month_dt,\n" +
            "                      day(loc.visited_dt)          day_dt,\n" +
            "                      hour(loc.visited_dt)         hour_dt,\n" +
            "                      count(distinct person_id) as c\n" +
            "               from location loc\n" +
            "               where (-1 in (:personIds) or loc.person_id in (:personIds))\n" +
            "                 and loc.visited_dt >= :dt\n" +
            "               group by loc.short_latitude, loc.short_longitude, year(loc.visited_dt), month(loc.visited_dt),\n" +
            "                        day(loc.visited_dt), hour(loc.visited_dt)\n" +
            "               having c > 1) q\n" +
            "              on l.short_latitude = q.short_latitude and l.short_longitude = q.short_longitude and\n" +
            "                 year(l.visited_dt) = q.year_dt and month(l.visited_dt) = q.month_dt and\n" +
            "                 day(l.visited_dt) = q.day_dt and hour(l.visited_dt) = q.hour_dt", nativeQuery = true)
    List<Location> findRiskLocations(@Param("dt") Date date,
                                     @Param("personIds") Collection<Long> personIds);
}
