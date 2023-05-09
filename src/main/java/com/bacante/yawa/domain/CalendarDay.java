package com.bacante.yawa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A CalendarDay.
 */
@Document(collection = "calendar_day")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalendarDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date")
    private LocalDate date;

    @Field("created_by")
    private String createdBy;

    @DBRef
    @Field("session")
    @JsonIgnoreProperties(value = { "workouts", "date" }, allowSetters = true)
    private Set<Session> sessions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public CalendarDay id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public CalendarDay date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public CalendarDay createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Session> getSessions() {
        return this.sessions;
    }

    public void setSessions(Set<Session> sessions) {
        if (this.sessions != null) {
            this.sessions.forEach(i -> i.setDate(null));
        }
        if (sessions != null) {
            sessions.forEach(i -> i.setDate(this));
        }
        this.sessions = sessions;
    }

    public CalendarDay sessions(Set<Session> sessions) {
        this.setSessions(sessions);
        return this;
    }

    public CalendarDay addSession(Session session) {
        this.sessions.add(session);
        session.setDate(this);
        return this;
    }

    public CalendarDay removeSession(Session session) {
        this.sessions.remove(session);
        session.setDate(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalendarDay)) {
            return false;
        }
        return id != null && id.equals(((CalendarDay) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalendarDay{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
