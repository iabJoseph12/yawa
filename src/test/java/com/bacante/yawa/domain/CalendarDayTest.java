package com.bacante.yawa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bacante.yawa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CalendarDayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarDay.class);
        CalendarDay calendarDay1 = new CalendarDay();
        calendarDay1.setId("id1");
        CalendarDay calendarDay2 = new CalendarDay();
        calendarDay2.setId(calendarDay1.getId());
        assertThat(calendarDay1).isEqualTo(calendarDay2);
        calendarDay2.setId("id2");
        assertThat(calendarDay1).isNotEqualTo(calendarDay2);
        calendarDay1.setId(null);
        assertThat(calendarDay1).isNotEqualTo(calendarDay2);
    }
}
