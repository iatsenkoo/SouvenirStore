package com.task.souvenirstore.repository;

import com.task.souvenirstore.entities.Factory;
import com.task.souvenirstore.entities.Souvenir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import java.util.List;

public interface FactoryRepository extends JpaRepository<Factory, Integer> {
    List<Factory> findByNameAndCountry(String name, String country);
    List<Factory> findBySouvenirsIn(List<Souvenir> souvenirs);
}