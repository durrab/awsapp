package com.h2app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.h2app.model.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
