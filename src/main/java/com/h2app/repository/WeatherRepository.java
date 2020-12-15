package com.h2app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.h2app.model.Weather;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
