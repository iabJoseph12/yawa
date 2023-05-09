package com.bacante.yawa.service.impl;

import com.bacante.yawa.domain.Activity;
import com.bacante.yawa.repository.ActivityRepository;
import com.bacante.yawa.service.ActivityService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Activity}.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity save(Activity activity) {
        log.debug("Request to save Activity : {}", activity);
        return activityRepository.save(activity);
    }

    @Override
    public Activity update(Activity activity) {
        log.debug("Request to update Activity : {}", activity);
        return activityRepository.save(activity);
    }

    @Override
    public Optional<Activity> partialUpdate(Activity activity) {
        log.debug("Request to partially update Activity : {}", activity);

        return activityRepository
            .findById(activity.getId())
            .map(existingActivity -> {
                if (activity.getActivityType() != null) {
                    existingActivity.setActivityType(activity.getActivityType());
                }
                if (activity.getActivityName() != null) {
                    existingActivity.setActivityName(activity.getActivityName());
                }
                if (activity.getWorkWeight() != null) {
                    existingActivity.setWorkWeight(activity.getWorkWeight());
                }
                if (activity.getWorkRepetitions() != null) {
                    existingActivity.setWorkRepetitions(activity.getWorkRepetitions());
                }
                if (activity.getWorkSets() != null) {
                    existingActivity.setWorkSets(activity.getWorkSets());
                }
                if (activity.getWorkSetIntensity() != null) {
                    existingActivity.setWorkSetIntensity(activity.getWorkSetIntensity());
                }
                if (activity.getWorkIntensity() != null) {
                    existingActivity.setWorkIntensity(activity.getWorkIntensity());
                }
                if (activity.getWorkDuration() != null) {
                    existingActivity.setWorkDuration(activity.getWorkDuration());
                }
                if (activity.getWorkDistance() != null) {
                    existingActivity.setWorkDistance(activity.getWorkDistance());
                }

                return existingActivity;
            })
            .map(activityRepository::save);
    }

    @Override
    public Page<Activity> findAll(Pageable pageable) {
        log.debug("Request to get all Activities");
        return activityRepository.findAll(pageable);
    }

    @Override
    public Optional<Activity> findOne(String id) {
        log.debug("Request to get Activity : {}", id);
        return activityRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Activity : {}", id);
        activityRepository.deleteById(id);
    }
}
