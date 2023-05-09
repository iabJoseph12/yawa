package com.bacante.yawa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bacante.yawa.IntegrationTest;
import com.bacante.yawa.domain.Activity;
import com.bacante.yawa.domain.enumeration.ActivityType;
import com.bacante.yawa.repository.ActivityRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ActivityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ActivityResourceIT {

    private static final ActivityType DEFAULT_ACTIVITY_TYPE = ActivityType.STRENGTH;
    private static final ActivityType UPDATED_ACTIVITY_TYPE = ActivityType.CARDIO;

    private static final String DEFAULT_ACTIVITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORK_WEIGHT = 1;
    private static final Integer UPDATED_WORK_WEIGHT = 2;

    private static final Integer DEFAULT_WORK_REPETITIONS = 1;
    private static final Integer UPDATED_WORK_REPETITIONS = 2;

    private static final Integer DEFAULT_WORK_SETS = 1;
    private static final Integer UPDATED_WORK_SETS = 2;

    private static final String DEFAULT_WORK_SET_INTENSITY = "AAAAAAAAAA";
    private static final String UPDATED_WORK_SET_INTENSITY = "BBBBBBBBBB";

    private static final Float DEFAULT_WORK_INTENSITY = 1F;
    private static final Float UPDATED_WORK_INTENSITY = 2F;

    private static final Float DEFAULT_WORK_DURATION = 1F;
    private static final Float UPDATED_WORK_DURATION = 2F;

    private static final Float DEFAULT_WORK_DISTANCE = 1F;
    private static final Float UPDATED_WORK_DISTANCE = 2F;

    private static final String ENTITY_API_URL = "/api/activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MockMvc restActivityMockMvc;

    private Activity activity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createEntity() {
        Activity activity = new Activity()
            .activityType(DEFAULT_ACTIVITY_TYPE)
            .activityName(DEFAULT_ACTIVITY_NAME)
            .workWeight(DEFAULT_WORK_WEIGHT)
            .workRepetitions(DEFAULT_WORK_REPETITIONS)
            .workSets(DEFAULT_WORK_SETS)
            .workSetIntensity(DEFAULT_WORK_SET_INTENSITY)
            .workIntensity(DEFAULT_WORK_INTENSITY)
            .workDuration(DEFAULT_WORK_DURATION)
            .workDistance(DEFAULT_WORK_DISTANCE);
        return activity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createUpdatedEntity() {
        Activity activity = new Activity()
            .activityType(UPDATED_ACTIVITY_TYPE)
            .activityName(UPDATED_ACTIVITY_NAME)
            .workWeight(UPDATED_WORK_WEIGHT)
            .workRepetitions(UPDATED_WORK_REPETITIONS)
            .workSets(UPDATED_WORK_SETS)
            .workSetIntensity(UPDATED_WORK_SET_INTENSITY)
            .workIntensity(UPDATED_WORK_INTENSITY)
            .workDuration(UPDATED_WORK_DURATION)
            .workDistance(UPDATED_WORK_DISTANCE);
        return activity;
    }

    @BeforeEach
    public void initTest() {
        activityRepository.deleteAll();
        activity = createEntity();
    }

    @Test
    void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();
        // Create the Activity
        restActivityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityType()).isEqualTo(DEFAULT_ACTIVITY_TYPE);
        assertThat(testActivity.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);
        assertThat(testActivity.getWorkWeight()).isEqualTo(DEFAULT_WORK_WEIGHT);
        assertThat(testActivity.getWorkRepetitions()).isEqualTo(DEFAULT_WORK_REPETITIONS);
        assertThat(testActivity.getWorkSets()).isEqualTo(DEFAULT_WORK_SETS);
        assertThat(testActivity.getWorkSetIntensity()).isEqualTo(DEFAULT_WORK_SET_INTENSITY);
        assertThat(testActivity.getWorkIntensity()).isEqualTo(DEFAULT_WORK_INTENSITY);
        assertThat(testActivity.getWorkDuration()).isEqualTo(DEFAULT_WORK_DURATION);
        assertThat(testActivity.getWorkDistance()).isEqualTo(DEFAULT_WORK_DISTANCE);
    }

    @Test
    void createActivityWithExistingId() throws Exception {
        // Create the Activity with an existing ID
        activity.setId("existing_id");

        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.save(activity);

        // Get all the activityList
        restActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId())))
            .andExpect(jsonPath("$.[*].activityType").value(hasItem(DEFAULT_ACTIVITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME)))
            .andExpect(jsonPath("$.[*].workWeight").value(hasItem(DEFAULT_WORK_WEIGHT)))
            .andExpect(jsonPath("$.[*].workRepetitions").value(hasItem(DEFAULT_WORK_REPETITIONS)))
            .andExpect(jsonPath("$.[*].workSets").value(hasItem(DEFAULT_WORK_SETS)))
            .andExpect(jsonPath("$.[*].workSetIntensity").value(hasItem(DEFAULT_WORK_SET_INTENSITY)))
            .andExpect(jsonPath("$.[*].workIntensity").value(hasItem(DEFAULT_WORK_INTENSITY.doubleValue())))
            .andExpect(jsonPath("$.[*].workDuration").value(hasItem(DEFAULT_WORK_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].workDistance").value(hasItem(DEFAULT_WORK_DISTANCE.doubleValue())));
    }

    @Test
    void getActivity() throws Exception {
        // Initialize the database
        activityRepository.save(activity);

        // Get the activity
        restActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId()))
            .andExpect(jsonPath("$.activityType").value(DEFAULT_ACTIVITY_TYPE.toString()))
            .andExpect(jsonPath("$.activityName").value(DEFAULT_ACTIVITY_NAME))
            .andExpect(jsonPath("$.workWeight").value(DEFAULT_WORK_WEIGHT))
            .andExpect(jsonPath("$.workRepetitions").value(DEFAULT_WORK_REPETITIONS))
            .andExpect(jsonPath("$.workSets").value(DEFAULT_WORK_SETS))
            .andExpect(jsonPath("$.workSetIntensity").value(DEFAULT_WORK_SET_INTENSITY))
            .andExpect(jsonPath("$.workIntensity").value(DEFAULT_WORK_INTENSITY.doubleValue()))
            .andExpect(jsonPath("$.workDuration").value(DEFAULT_WORK_DURATION.doubleValue()))
            .andExpect(jsonPath("$.workDistance").value(DEFAULT_WORK_DISTANCE.doubleValue()));
    }

    @Test
    void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingActivity() throws Exception {
        // Initialize the database
        activityRepository.save(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findById(activity.getId()).get();
        updatedActivity
            .activityType(UPDATED_ACTIVITY_TYPE)
            .activityName(UPDATED_ACTIVITY_NAME)
            .workWeight(UPDATED_WORK_WEIGHT)
            .workRepetitions(UPDATED_WORK_REPETITIONS)
            .workSets(UPDATED_WORK_SETS)
            .workSetIntensity(UPDATED_WORK_SET_INTENSITY)
            .workIntensity(UPDATED_WORK_INTENSITY)
            .workDuration(UPDATED_WORK_DURATION)
            .workDistance(UPDATED_WORK_DISTANCE);

        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedActivity.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivity.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);
        assertThat(testActivity.getWorkWeight()).isEqualTo(UPDATED_WORK_WEIGHT);
        assertThat(testActivity.getWorkRepetitions()).isEqualTo(UPDATED_WORK_REPETITIONS);
        assertThat(testActivity.getWorkSets()).isEqualTo(UPDATED_WORK_SETS);
        assertThat(testActivity.getWorkSetIntensity()).isEqualTo(UPDATED_WORK_SET_INTENSITY);
        assertThat(testActivity.getWorkIntensity()).isEqualTo(UPDATED_WORK_INTENSITY);
        assertThat(testActivity.getWorkDuration()).isEqualTo(UPDATED_WORK_DURATION);
        assertThat(testActivity.getWorkDistance()).isEqualTo(UPDATED_WORK_DISTANCE);
    }

    @Test
    void putNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activity.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        activityRepository.save(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .activityType(UPDATED_ACTIVITY_TYPE)
            .workRepetitions(UPDATED_WORK_REPETITIONS)
            .workSets(UPDATED_WORK_SETS)
            .workIntensity(UPDATED_WORK_INTENSITY)
            .workDistance(UPDATED_WORK_DISTANCE);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivity.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);
        assertThat(testActivity.getWorkWeight()).isEqualTo(DEFAULT_WORK_WEIGHT);
        assertThat(testActivity.getWorkRepetitions()).isEqualTo(UPDATED_WORK_REPETITIONS);
        assertThat(testActivity.getWorkSets()).isEqualTo(UPDATED_WORK_SETS);
        assertThat(testActivity.getWorkSetIntensity()).isEqualTo(DEFAULT_WORK_SET_INTENSITY);
        assertThat(testActivity.getWorkIntensity()).isEqualTo(UPDATED_WORK_INTENSITY);
        assertThat(testActivity.getWorkDuration()).isEqualTo(DEFAULT_WORK_DURATION);
        assertThat(testActivity.getWorkDistance()).isEqualTo(UPDATED_WORK_DISTANCE);
    }

    @Test
    void fullUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        activityRepository.save(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .activityType(UPDATED_ACTIVITY_TYPE)
            .activityName(UPDATED_ACTIVITY_NAME)
            .workWeight(UPDATED_WORK_WEIGHT)
            .workRepetitions(UPDATED_WORK_REPETITIONS)
            .workSets(UPDATED_WORK_SETS)
            .workSetIntensity(UPDATED_WORK_SET_INTENSITY)
            .workIntensity(UPDATED_WORK_INTENSITY)
            .workDuration(UPDATED_WORK_DURATION)
            .workDistance(UPDATED_WORK_DISTANCE);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityType()).isEqualTo(UPDATED_ACTIVITY_TYPE);
        assertThat(testActivity.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);
        assertThat(testActivity.getWorkWeight()).isEqualTo(UPDATED_WORK_WEIGHT);
        assertThat(testActivity.getWorkRepetitions()).isEqualTo(UPDATED_WORK_REPETITIONS);
        assertThat(testActivity.getWorkSets()).isEqualTo(UPDATED_WORK_SETS);
        assertThat(testActivity.getWorkSetIntensity()).isEqualTo(UPDATED_WORK_SET_INTENSITY);
        assertThat(testActivity.getWorkIntensity()).isEqualTo(UPDATED_WORK_INTENSITY);
        assertThat(testActivity.getWorkDuration()).isEqualTo(UPDATED_WORK_DURATION);
        assertThat(testActivity.getWorkDistance()).isEqualTo(UPDATED_WORK_DISTANCE);
    }

    @Test
    void patchNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();
        activity.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(activity))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.save(activity);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Delete the activity
        restActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, activity.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
