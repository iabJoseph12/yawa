import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWorkout } from 'app/shared/model/workout.model';
import { getEntities as getWorkouts } from 'app/entities/workout/workout.reducer';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityType } from 'app/shared/model/enumerations/activity-type.model';
import { getEntity, updateEntity, createEntity, reset } from './activity.reducer';

export const ActivityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const workouts = useAppSelector(state => state.workout.entities);
  const activityEntity = useAppSelector(state => state.activity.entity);
  const loading = useAppSelector(state => state.activity.loading);
  const updating = useAppSelector(state => state.activity.updating);
  const updateSuccess = useAppSelector(state => state.activity.updateSuccess);
  const activityTypeValues = Object.keys(ActivityType);

  const handleClose = () => {
    navigate('/activity');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getWorkouts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...activityEntity,
      ...values,
      activity: workouts.find(it => it.id.toString() === values.activity.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          activityType: 'STRENGTH',
          ...activityEntity,
          activity: activityEntity?.activity?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="yawaApp.activity.home.createOrEditLabel" data-cy="ActivityCreateUpdateHeading">
            Create or edit a Activity
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="activity-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Activity Type" id="activity-activityType" name="activityType" data-cy="activityType" type="select">
                {activityTypeValues.map(activityType => (
                  <option value={activityType} key={activityType}>
                    {activityType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Activity Name" id="activity-activityName" name="activityName" data-cy="activityName" type="text" />
              <ValidatedField label="Work Weight" id="activity-workWeight" name="workWeight" data-cy="workWeight" type="text" />
              <ValidatedField
                label="Work Repetitions"
                id="activity-workRepetitions"
                name="workRepetitions"
                data-cy="workRepetitions"
                type="text"
              />
              <ValidatedField label="Work Sets" id="activity-workSets" name="workSets" data-cy="workSets" type="text" />
              <ValidatedField
                label="Work Set Intensity"
                id="activity-workSetIntensity"
                name="workSetIntensity"
                data-cy="workSetIntensity"
                type="text"
              />
              <ValidatedField label="Work Intensity" id="activity-workIntensity" name="workIntensity" data-cy="workIntensity" type="text" />
              <ValidatedField label="Work Duration" id="activity-workDuration" name="workDuration" data-cy="workDuration" type="text" />
              <ValidatedField label="Work Distance" id="activity-workDistance" name="workDistance" data-cy="workDistance" type="text" />
              <ValidatedField id="activity-activity" name="activity" data-cy="activity" label="Activity" type="select">
                <option value="" key="0" />
                {workouts
                  ? workouts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activity" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ActivityUpdate;
