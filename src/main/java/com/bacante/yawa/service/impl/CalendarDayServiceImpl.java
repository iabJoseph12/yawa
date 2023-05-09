package com.bacante.yawa.service.impl;

import com.bacante.yawa.domain.CalendarDay;
import com.bacante.yawa.repository.CalendarDayRepository;
import com.bacante.yawa.service.CalendarDayService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link CalendarDay}.
 */
@Service
public class CalendarDayServiceImpl implements CalendarDayService {

    private final Logger log = LoggerFactory.getLogger(CalendarDayServiceImpl.class);

    private final CalendarDayRepository calendarDayRepository;

    public CalendarDayServiceImpl(CalendarDayRepository calendarDayRepository) {
        this.calendarDayRepository = calendarDayRepository;
    }

    @Override
    public CalendarDay save(CalendarDay calendarDay) {
        log.debug("Request to save CalendarDay : {}", calendarDay);
        return calendarDayRepository.save(calendarDay);
    }

    @Override
    public CalendarDay update(CalendarDay calendarDay) {
        log.debug("Request to update CalendarDay : {}", calendarDay);
        return calendarDayRepository.save(calendarDay);
    }

    @Override
    public Optional<CalendarDay> partialUpdate(CalendarDay calendarDay) {
        log.debug("Request to partially update CalendarDay : {}", calendarDay);

        return calendarDayRepository
            .findById(calendarDay.getId())
            .map(existingCalendarDay -> {
                if (calendarDay.getDate() != null) {
                    existingCalendarDay.setDate(calendarDay.getDate());
                }
                if (calendarDay.getCreatedBy() != null) {
                    existingCalendarDay.setCreatedBy(calendarDay.getCreatedBy());
                }

                return existingCalendarDay;
            })
            .map(calendarDayRepository::save);
    }

    @Override
    public Page<CalendarDay> findAll(Pageable pageable) {
        log.debug("Request to get all CalendarDays");
        return calendarDayRepository.findAll(pageable);
    }

    @Override
    public Optional<CalendarDay> findOne(String id) {
        log.debug("Request to get CalendarDay : {}", id);
        return calendarDayRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete CalendarDay : {}", id);
        calendarDayRepository.deleteById(id);
    }
}
