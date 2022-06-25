package com.task.souvenirstore.repository;

import com.task.souvenirstore.entities.Souvenir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SouvenirRepository extends JpaRepository<Souvenir, Integer> {
    List<Souvenir> findByFactoryId(int id);
    List<Souvenir> findByFactoryCountry(String nameCountry);
    List<Souvenir> findByPriceLessThan(double price);
    List<Souvenir> findByFactoryName(String nameFactory);
}