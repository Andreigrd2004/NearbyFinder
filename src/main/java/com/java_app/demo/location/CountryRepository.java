package com.java_app.demo.location;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends  JpaRepository<Country, String> {
    Country findByName(String name);
}
