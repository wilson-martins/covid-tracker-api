package com.mc855.tracker.repository;

import com.mc855.tracker.domain.StatusHistory;
import com.mc855.tracker.domain.reference.HealthState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {

    Collection<StatusHistory> findAllByPersonIdInAndValueNotInAndStatusDtGreaterThanEqual(
            @Param("personIds") Collection<Long> personIds,
            @Param("healthStates") Collection<HealthState> healthStates,
            @Param("date") Date date);
}
