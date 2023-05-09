package com.bacante.yawa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bacante.yawa.IntegrationTest;
import com.bacante.yawa.domain.CalendarDay;
import com.bacante.yawa.repository.CalendarDayRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CalendarDayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CalendarDayResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/calendar-days";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CalendarDayRepository calendarDayRepository;

    @Autowired
    private MockMvc restCalendarDayMockMvc;

    private CalendarDay calendarDay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarDay createEntity() {
        CalendarDay calendarDay = new CalendarDay().date(DEFAULT_DATE).createdBy(DEFAULT_CREATED_BY);
        return calendarDay;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalendarDay createUpdatedEntity() {
        CalendarDay calendarDay = new CalendarDay().date(UPDATED_DATE).createdBy(UPDATED_CREATED_BY);
        return calendarDay;
    }

    @BeforeEach
    public void initTest() {
        calendarDayRepository.deleteAll();
        calendarDay = createEntity();
    }

    @Test
    void createCalendarDay() throws Exception {
        int databaseSizeBeforeCreate = calendarDayRepository.findAll().size();
        // Create the CalendarDay
        restCalendarDayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarDay))
            )
            .andExpect(status().isCreated());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeCreate + 1);
        CalendarDay testCalendarDay = calendarDayList.get(calendarDayList.size() - 1);
        assertThat(testCalendarDay.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCalendarDay.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    void createCalendarDayWithExistingId() throws Exception {
        // Create the CalendarDay with an existing ID
        calendarDay.setId("existing_id");

        int databaseSizeBeforeCreate = calendarDayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarDayMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarDay))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCalendarDays() throws Exception {
        // Initialize the database
        calendarDayRepository.save(calendarDay);

        // Get all the calendarDayList
        restCalendarDayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarDay.getId())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }

    @Test
    void getCalendarDay() throws Exception {
        // Initialize the database
        calendarDayRepository.save(calendarDay);

        // Get the calendarDay
        restCalendarDayMockMvc
            .perform(get(ENTITY_API_URL_ID, calendarDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendarDay.getId()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    void getNonExistingCalendarDay() throws Exception {
        // Get the calendarDay
        restCalendarDayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCalendarDay() throws Exception {
        // Initialize the database
        calendarDayRepository.save(calendarDay);

        int databaseSizeBeforeUpdate = calendarDayRepository.findAll().size();

        // Update the calendarDay
        CalendarDay updatedCalendarDay = calendarDayRepository.findById(calendarDay.getId()).get();
        updatedCalendarDay.date(UPDATED_DATE).createdBy(UPDATED_CREATED_BY);

        restCalendarDayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCalendarDay.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCalendarDay))
            )
            .andExpect(status().isOk());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeUpdate);
        CalendarDay testCalendarDay = calendarDayList.get(calendarDayList.size() - 1);
        assertThat(testCalendarDay.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCalendarDay.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    void putNonExistingCalendarDay() throws Exception {
        int databaseSizeBeforeUpdate = calendarDayRepository.findAll().size();
        calendarDay.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarDayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calendarDay.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarDay))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCalendarDay() throws Exception {
        int databaseSizeBeforeUpdate = calendarDayRepository.findAll().size();
        calendarDay.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarDayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarDay))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCalendarDay() throws Exception {
        int databaseSizeBeforeUpdate = calendarDayRepository.findAll().size();
        calendarDay.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarDayMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendarDay))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCalendarDayWithPatch() throws Exception {
        // Initialize the database
        calendarDayRepository.save(calendarDay);

        int databaseSizeBeforeUpdate = calendarDayRepository.findAll().size();

        // Update the calendarDay using partial update
        CalendarDay partialUpdatedCalendarDay = new CalendarDay();
        partialUpdatedCalendarDay.setId(calendarDay.getId());

        partialUpdatedCalendarDay.date(UPDATED_DATE).createdBy(UPDATED_CREATED_BY);

        restCalendarDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendarDay.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalendarDay))
            )
            .andExpect(status().isOk());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeUpdate);
        CalendarDay testCalendarDay = calendarDayList.get(calendarDayList.size() - 1);
        assertThat(testCalendarDay.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCalendarDay.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    void fullUpdateCalendarDayWithPatch() throws Exception {
        // Initialize the database
        calendarDayRepository.save(calendarDay);

        int databaseSizeBeforeUpdate = calendarDayRepository.findAll().size();

        // Update the calendarDay using partial update
        CalendarDay partialUpdatedCalendarDay = new CalendarDay();
        partialUpdatedCalendarDay.setId(calendarDay.getId());

        partialUpdatedCalendarDay.date(UPDATED_DATE).createdBy(UPDATED_CREATED_BY);

        restCalendarDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendarDay.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalendarDay))
            )
            .andExpect(status().isOk());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeUpdate);
        CalendarDay testCalendarDay = calendarDayList.get(calendarDayList.size() - 1);
        assertThat(testCalendarDay.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCalendarDay.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    void patchNonExistingCalendarDay() throws Exception {
        int databaseSizeBeforeUpdate = calendarDayRepository.findAll().size();
        calendarDay.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calendarDay.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calendarDay))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCalendarDay() throws Exception {
        int databaseSizeBeforeUpdate = calendarDayRepository.findAll().size();
        calendarDay.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calendarDay))
            )
            .andExpect(status().isBadRequest());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCalendarDay() throws Exception {
        int databaseSizeBeforeUpdate = calendarDayRepository.findAll().size();
        calendarDay.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarDayMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calendarDay))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CalendarDay in the database
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCalendarDay() throws Exception {
        // Initialize the database
        calendarDayRepository.save(calendarDay);

        int databaseSizeBeforeDelete = calendarDayRepository.findAll().size();

        // Delete the calendarDay
        restCalendarDayMockMvc
            .perform(delete(ENTITY_API_URL_ID, calendarDay.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CalendarDay> calendarDayList = calendarDayRepository.findAll();
        assertThat(calendarDayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
