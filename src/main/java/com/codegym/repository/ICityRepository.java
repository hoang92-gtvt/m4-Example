package com.codegym.repository;

import com.codegym.model.City;
import com.codegym.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICityRepository extends JpaRepository<City, Long> {
    Iterable<City> findAllByCountry(Country country);
}
