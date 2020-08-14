package com.mc855.tracker.repository;

import com.mc855.tracker.domain.StatusHistory;
import com.mc855.tracker.domain.reference.HealthState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
    Collection<StatusHistory> findAllByPersonIdInAndValueInAndStatusDtGreaterThanEqual(Collection<Long> personIds, Collection<HealthState> healthStates, Date date);
}
