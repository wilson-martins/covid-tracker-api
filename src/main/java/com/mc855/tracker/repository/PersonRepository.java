package com.mc855.tracker.repository;

import com.mc855.tracker.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Collection<Person> findAllByPersonIdIn(Collection<Long> personIds);

    @Query(value = "select person_id from person", nativeQuery = true)
    List<Long> findAllPersonIds();
}
