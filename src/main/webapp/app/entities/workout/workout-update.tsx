import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISession } from 'app/shared/model/session.model';
import { getEntities as getSessions } from 'app/entities/session/session.reducer';
import { IWorkout } from 'app/shared/model/workout.model';
import { getEntity, updateEntity, createEntity, reset } from './workout.reducer';

export const WorkoutUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sessions = useAppSelector(state => state.session.entities);
  const workoutEntity = useAppSelector(state => state.workout.entity);
  const loading = useAppSelector(state => state.workout.loading);
  const updating = useAppSelector(state => state.workout.updating);
  const updateSuccess = useAppSelector(state => state.workout.updateSuccess);

  const handleClose = () => {
    navigate('/workout');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getSessions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...workoutEntity,
      ...values,
      sessionid: sessions.find(it => it.id.toString() === values.sessionid.toString()),
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
          ...workoutEntity,
          sessionid: workoutEntity?.sessionid?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="yawaApp.workout.home.createOrEditLabel" data-cy="WorkoutCreateUpdateHeading">
            Create or edit a Workout
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="workout-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Routine Name" id="workout-routineName" name="routineName" data-cy="routineName" type="text" />
              <ValidatedField id="workout-sessionid" name="sessionid" data-cy="sessionid" label="Sessionid" type="select">
                <option value="" key="0" />
                {sessions
                  ? sessions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/workout" replace color="info">
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

export default WorkoutUpdate;
