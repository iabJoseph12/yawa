package com.bacante.yawa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Session.
 */
@Document(collection = "session")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("session_notes")
    private String sessionNotes;

    @DBRef
    @Field("workout")
    @JsonIgnoreProperties(value = { "activities", "sessionid" }, allowSetters = true)
    private Set<Workout> workouts = new HashSet<>();

    @DBRef
    @Field("date")
    @JsonIgnoreProperties(value = { "sessions" }, allowSetters = true)
    private CalendarDay date;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Session id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionNotes() {
        return this.sessionNotes;
    }

    public Session sessionNotes(String sessionNotes) {
        this.setSessionNotes(sessionNotes);
        return this;
    }

    public void setSessionNotes(String sessionNotes) {
        this.sessionNotes = sessionNotes;
    }

    public Set<Workout> getWorkouts() {
        return this.workouts;
    }

    public void setWorkouts(Set<Workout> workouts) {
        if (this.workouts != null) {
            this.workouts.forEach(i -> i.setSessionid(null));
        }
        if (workouts != null) {
            workouts.forEach(i -> i.setSessionid(this));
        }
        this.workouts = workouts;
    }

    public Session workouts(Set<Workout> workouts) {
        this.setWorkouts(workouts);
        return this;
    }

    public Session addWorkout(Workout workout) {
        this.workouts.add(workout);
        workout.setSessionid(this);
        return this;
    }

    public Session removeWorkout(Workout workout) {
        this.workouts.remove(workout);
        workout.setSessionid(null);
        return this;
    }

    public CalendarDay getDate() {
        return this.date;
    }

    public void setDate(CalendarDay calendarDay) {
        this.date = calendarDay;
    }

    public Session date(CalendarDay calendarDay) {
        this.setDate(calendarDay);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return id != null && id.equals(((Session) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", sessionNotes='" + getSessionNotes() + "'" +
            "}";
    }
}
