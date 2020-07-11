package com.mc855.tracker.service;

import com.mc855.tracker.domain.Disease;
import com.mc855.tracker.domain.Person;
import com.mc855.tracker.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiseaseBO {

    @Autowired
    private DiseaseRepository diseaseRepository;

    public List<Disease> getDiseases() {
        return diseaseRepository.findAll();
    }

    public Disease getDisease(Long id) {
        Optional<Disease> disease = diseaseRepository.findById(id);

        return disease.orElse(null);
    }

    public void deleteDisease(Long id) {
        diseaseRepository.deleteById(id);
    }

    public Disease updateDisease(Disease disease) {
        return diseaseRepository.save(disease);
    }
}
