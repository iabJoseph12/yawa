package com.bacante.yawa.web.rest;

import com.bacante.yawa.domain.CalendarDay;
import com.bacante.yawa.domain.Session;
import com.bacante.yawa.repository.CalendarDayRepository;
import com.bacante.yawa.service.CalendarDayService;
import com.bacante.yawa.service.SessionService;
import com.bacante.yawa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bacante.yawa.domain.CalendarDay}.
 */
@RestController
@RequestMapping("/api")
public class CalendarDayResource {

    private final Logger log = LoggerFactory.getLogger(CalendarDayResource.class);

    private static final String ENTITY_NAME = "calendarDay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalendarDayService calendarDayService;

    private final SessionService sessionService;

    private final CalendarDayRepository calendarDayRepository;

    public CalendarDayResource(
        CalendarDayService calendarDayService,
        CalendarDayRepository calendarDayRepository,
        SessionService sessionService
    ) {
        this.calendarDayService = calendarDayService;
        this.calendarDayRepository = calendarDayRepository;
        this.sessionService = sessionService;
    }

    /**
     *
     * @param calendarDay
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/calendar-days/start")
    public ResponseEntity<CalendarDay> createCalendarDayWithSession(@RequestBody CalendarDay calendarDay) throws URISyntaxException {
        log.debug("REST request to save CalendarDay : {}", calendarDay);
        if (calendarDay.getId() != null) {
            throw new BadRequestAlertException("A new calendarDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        calendarDay.setDate(LocalDate.now());
        // Create a new session
        Session newSession = new Session();
        calendarDay.getSessions().add(newSession);
        CalendarDay result = calendarDayService.save(calendarDay);
        Session sessionResult = sessionService.save(newSession);
        return ResponseEntity
            .created(new URI("/api/calendar-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code POST  /calendar-days} : Create a new calendarDay.
     *
     * @param calendarDay the calendarDay to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calendarDay, or with status {@code 400 (Bad Request)} if the calendarDay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calendar-days")
    public ResponseEntity<CalendarDay> createCalendarDay(@RequestBody CalendarDay calendarDay) throws URISyntaxException {
        log.debug("REST request to save CalendarDay : {}", calendarDay);
        if (calendarDay.getId() != null) {
            throw new BadRequestAlertException("A new calendarDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        calendarDay.setDate(LocalDate.now());
        CalendarDay result = calendarDayService.save(calendarDay);
        return ResponseEntity
            .created(new URI("/api/calendar-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /calendar-days/:id} : Updates an existing calendarDay.
     *
     * @param id the id of the calendarDay to save.
     * @param calendarDay the calendarDay to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendarDay,
     * or with status {@code 400 (Bad Request)} if the calendarDay is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calendarDay couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calendar-days/{id}")
    public ResponseEntity<CalendarDay> updateCalendarDay(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CalendarDay calendarDay
    ) throws URISyntaxException {
        log.debug("REST request to update CalendarDay : {}, {}", id, calendarDay);
        if (calendarDay.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calendarDay.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calendarDayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CalendarDay result = calendarDayService.update(calendarDay);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calendarDay.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /calendar-days/:id} : Partial updates given fields of an existing calendarDay, field will ignore if it is null
     *
     * @param id the id of the calendarDay to save.
     * @param calendarDay the calendarDay to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendarDay,
     * or with status {@code 400 (Bad Request)} if the calendarDay is not valid,
     * or with status {@code 404 (Not Found)} if the calendarDay is not found,
     * or with status {@code 500 (Internal Server Error)} if the calendarDay couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/calendar-days/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CalendarDay> partialUpdateCalendarDay(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody CalendarDay calendarDay
    ) throws URISyntaxException {
        log.debug("REST request to partial update CalendarDay partially : {}, {}", id, calendarDay);
        if (calendarDay.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calendarDay.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calendarDayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CalendarDay> result = calendarDayService.partialUpdate(calendarDay);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calendarDay.getId())
        );
    }

    /**
     * {@code GET  /calendar-days} : get all the calendarDays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calendarDays in body.
     */
    @GetMapping("/calendar-days")
    public ResponseEntity<List<CalendarDay>> getAllCalendarDays(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CalendarDays");
        Page<CalendarDay> page = calendarDayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calendar-days/:id} : get the "id" calendarDay.
     *
     * @param id the id of the calendarDay to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendarDay, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calendar-days/{id}")
    public ResponseEntity<CalendarDay> getCalendarDay(@PathVariable String id) {
        log.debug("REST request to get CalendarDay : {}", id);
        Optional<CalendarDay> calendarDay = calendarDayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendarDay);
    }

    /**
     * {@code DELETE  /calendar-days/:id} : delete the "id" calendarDay.
     *
     * @param id the id of the calendarDay to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calendar-days/{id}")
    public ResponseEntity<Void> deleteCalendarDay(@PathVariable String id) {
        log.debug("REST request to delete CalendarDay : {}", id);
        calendarDayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
