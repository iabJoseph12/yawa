package com.bacante.yawa.domain;

import com.bacante.yawa.domain.enumeration.ActivityType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Activity.
 */
@Document(collection = "activity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("activity_type")
    private ActivityType activityType;

    @Field("activity_name")
    private String activityName;

    @Field("work_weight")
    private Integer workWeight;

    @Field("work_repetitions")
    private Integer workRepetitions;

    @Field("work_sets")
    private Integer workSets;

    @Field("work_set_intensity")
    private String workSetIntensity;

    @Field("work_intensity")
    private Float workIntensity;

    @Field("work_duration")
    private Float workDuration;

    @Field("work_distance")
    private Float workDistance;

    @DBRef
    @Field("activity")
    @JsonIgnoreProperties(value = { "activities", "sessionid" }, allowSetters = true)
    private Workout activity;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Activity id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActivityType getActivityType() {
        return this.activityType;
    }

    public Activity activityType(ActivityType activityType) {
        this.setActivityType(activityType);
        return this;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public Activity activityName(String activityName) {
        this.setActivityName(activityName);
        return this;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getWorkWeight() {
        return this.workWeight;
    }

    public Activity workWeight(Integer workWeight) {
        this.setWorkWeight(workWeight);
        return this;
    }

    public void setWorkWeight(Integer workWeight) {
        this.workWeight = workWeight;
    }

    public Integer getWorkRepetitions() {
        return this.workRepetitions;
    }

    public Activity workRepetitions(Integer workRepetitions) {
        this.setWorkRepetitions(workRepetitions);
        return this;
    }

    public void setWorkRepetitions(Integer workRepetitions) {
        this.workRepetitions = workRepetitions;
    }

    public Integer getWorkSets() {
        return this.workSets;
    }

    public Activity workSets(Integer workSets) {
        this.setWorkSets(workSets);
        return this;
    }

    public void setWorkSets(Integer workSets) {
        this.workSets = workSets;
    }

    public String getWorkSetIntensity() {
        return this.workSetIntensity;
    }

    public Activity workSetIntensity(String workSetIntensity) {
        this.setWorkSetIntensity(workSetIntensity);
        return this;
    }

    public void setWorkSetIntensity(String workSetIntensity) {
        this.workSetIntensity = workSetIntensity;
    }

    public Float getWorkIntensity() {
        return this.workIntensity;
    }

    public Activity workIntensity(Float workIntensity) {
        this.setWorkIntensity(workIntensity);
        return this;
    }

    public void setWorkIntensity(Float workIntensity) {
        this.workIntensity = workIntensity;
    }

    public Float getWorkDuration() {
        return this.workDuration;
    }

    public Activity workDuration(Float workDuration) {
        this.setWorkDuration(workDuration);
        return this;
    }

    public void setWorkDuration(Float workDuration) {
        this.workDuration = workDuration;
    }

    public Float getWorkDistance() {
        return this.workDistance;
    }

    public Activity workDistance(Float workDistance) {
        this.setWorkDistance(workDistance);
        return this;
    }

    public void setWorkDistance(Float workDistance) {
        this.workDistance = workDistance;
    }

    public Workout getActivity() {
        return this.activity;
    }

    public void setActivity(Workout workout) {
        this.activity = workout;
    }

    public Activity activity(Workout workout) {
        this.setActivity(workout);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", activityType='" + getActivityType() + "'" +
            ", activityName='" + getActivityName() + "'" +
            ", workWeight=" + getWorkWeight() +
            ", workRepetitions=" + getWorkRepetitions() +
            ", workSets=" + getWorkSets() +
            ", workSetIntensity='" + getWorkSetIntensity() + "'" +
            ", workIntensity=" + getWorkIntensity() +
            ", workDuration=" + getWorkDuration() +
            ", workDistance=" + getWorkDistance() +
            "}";
    }
}
