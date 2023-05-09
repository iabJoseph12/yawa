package com.bacante.yawa.service.impl;

import com.bacante.yawa.domain.Workout;
import com.bacante.yawa.repository.WorkoutRepository;
import com.bacante.yawa.service.WorkoutService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Workout}.
 */
@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final Logger log = LoggerFactory.getLogger(WorkoutServiceImpl.class);

    private final WorkoutRepository workoutRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public Workout save(Workout workout) {
        log.debug("Request to save Workout : {}", workout);
        return workoutRepository.save(workout);
    }

    @Override
    public Workout update(Workout workout) {
        log.debug("Request to update Workout : {}", workout);
        return workoutRepository.save(workout);
    }

    @Override
    public Optional<Workout> partialUpdate(Workout workout) {
        log.debug("Request to partially update Workout : {}", workout);

        return workoutRepository
            .findById(workout.getId())
            .map(existingWorkout -> {
                if (workout.getRoutineName() != null) {
                    existingWorkout.setRoutineName(workout.getRoutineName());
                }

                return existingWorkout;
            })
            .map(workoutRepository::save);
    }

    @Override
    public Page<Workout> findAll(Pageable pageable) {
        log.debug("Request to get all Workouts");
        return workoutRepository.findAll(pageable);
    }

    @Override
    public Optional<Workout> findOne(String id) {
        log.debug("Request to get Workout : {}", id);
        return workoutRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Workout : {}", id);
        workoutRepository.deleteById(id);
    }
}
