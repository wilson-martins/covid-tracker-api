package com.mc855.tracker.repository;

import com.mc855.tracker.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByPersonIdAndLatitudeAndLongitude(Long personId, Double latitude, Double longitude);

    List<Location> findAllByPersonId(Long personId);
}
