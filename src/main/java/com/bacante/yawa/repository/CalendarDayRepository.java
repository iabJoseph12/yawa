package com.bacante.yawa.repository;

import com.bacante.yawa.domain.CalendarDay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CalendarDay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarDayRepository extends MongoRepository<CalendarDay, String> {}
