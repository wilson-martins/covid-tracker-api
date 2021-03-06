package com.mc855.tracker.service;

import com.mc855.tracker.domain.StatusHistory;
import com.mc855.tracker.domain.reference.HealthState;
import com.mc855.tracker.repository.StatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StatusHistoryBO {

    @Autowired
    private StatusHistoryRepository statusHistoryRepository;

    public List<StatusHistory> getStatusHistoryList() {
        return statusHistoryRepository.findAll();
    }

    public StatusHistory getStatusHistory(Long id) {
        Optional<StatusHistory> statusHistory = statusHistoryRepository.findById(id);

        return statusHistory.orElse(null);
    }

    public void deleteStatusHistory(Long id) {
        statusHistoryRepository.deleteById(id);
    }

    public StatusHistory updateStatusHistory(StatusHistory statusHistory) {
        return statusHistoryRepository.save(statusHistory);
    }

    public Collection<StatusHistory> getStatusHistory(Collection<Long> personIds, Collection<HealthState> healthStates, Date date) {
        if (personIds == null || personIds.isEmpty()
                || healthStates == null || healthStates.isEmpty()
                || date == null) {
            return new ArrayList<>();
        }
        return this.statusHistoryRepository.findAllByPersonIdInAndValueNotInAndStatusDtGreaterThanEqual(personIds, healthStates, date);
    }
}
