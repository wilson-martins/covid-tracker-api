package com.mc855.tracker.repository;

import com.mc855.tracker.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
