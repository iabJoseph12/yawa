package com.bacante.yawa.service;

import com.bacante.yawa.domain.Workout;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Workout}.
 */
public interface WorkoutService {
    /**
     * Save a workout.
     *
     * @param workout the entity to save.
     * @return the persisted entity.
     */
    Workout save(Workout workout);

    /**
     * Updates a workout.
     *
     * @param workout the entity to update.
     * @return the persisted entity.
     */
    Workout update(Workout workout);

    /**
     * Partially updates a workout.
     *
     * @param workout the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Workout> partialUpdate(Workout workout);

    /**
     * Get all the workouts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Workout> findAll(Pageable pageable);

    /**
     * Get the "id" workout.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Workout> findOne(String id);

    /**
     * Delete the "id" workout.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
