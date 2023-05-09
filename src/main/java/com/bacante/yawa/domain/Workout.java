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
 * A Workout.
 */
@Document(collection = "workout")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Workout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("routine_name")
    private String routineName;

    @DBRef
    @Field("activity")
    @JsonIgnoreProperties(value = { "activity" }, allowSetters = true)
    private Set<Activity> activities = new HashSet<>();

    @DBRef
    @Field("sessionid")
    @JsonIgnoreProperties(value = { "workouts", "date" }, allowSetters = true)
    private Session sessionid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Workout id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoutineName() {
        return this.routineName;
    }

    public Workout routineName(String routineName) {
        this.setRoutineName(routineName);
        return this;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public Set<Activity> getActivities() {
        return this.activities;
    }

    public void setActivities(Set<Activity> activities) {
        if (this.activities != null) {
            this.activities.forEach(i -> i.setActivity(null));
        }
        if (activities != null) {
            activities.forEach(i -> i.setActivity(this));
        }
        this.activities = activities;
    }

    public Workout activities(Set<Activity> activities) {
        this.setActivities(activities);
        return this;
    }

    public Workout addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setActivity(this);
        return this;
    }

    public Workout removeActivity(Activity activity) {
        this.activities.remove(activity);
        activity.setActivity(null);
        return this;
    }

    public Session getSessionid() {
        return this.sessionid;
    }

    public void setSessionid(Session session) {
        this.sessionid = session;
    }

    public Workout sessionid(Session session) {
        this.setSessionid(session);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Workout)) {
            return false;
        }
        return id != null && id.equals(((Workout) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Workout{" +
            "id=" + getId() +
            ", routineName='" + getRoutineName() + "'" +
            "}";
    }
}
