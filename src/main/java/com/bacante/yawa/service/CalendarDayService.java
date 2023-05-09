package com.bacante.yawa.service;

import com.bacante.yawa.domain.CalendarDay;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CalendarDay}.
 */
public interface CalendarDayService {
    /**
     * Save a calendarDay.
     *
     * @param calendarDay the entity to save.
     * @return the persisted entity.
     */
    CalendarDay save(CalendarDay calendarDay);

    /**
     * Updates a calendarDay.
     *
     * @param calendarDay the entity to update.
     * @return the persisted entity.
     */
    CalendarDay update(CalendarDay calendarDay);

    /**
     * Partially updates a calendarDay.
     *
     * @param calendarDay the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CalendarDay> partialUpdate(CalendarDay calendarDay);

    /**
     * Get all the calendarDays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CalendarDay> findAll(Pageable pageable);

    /**
     * Get the "id" calendarDay.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CalendarDay> findOne(String id);

    /**
     * Delete the "id" calendarDay.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
