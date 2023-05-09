import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICalendarDay } from 'app/shared/model/calendar-day.model';
import { getEntities as getCalendarDays } from 'app/entities/calendar-day/calendar-day.reducer';
import { ISession } from 'app/shared/model/session.model';
import { getEntity, updateEntity, createEntity, reset } from './session.reducer';

export const SessionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const calendarDays = useAppSelector(state => state.calendarDay.entities);
  const sessionEntity = useAppSelector(state => state.session.entity);
  const loading = useAppSelector(state => state.session.loading);
  const updating = useAppSelector(state => state.session.updating);
  const updateSuccess = useAppSelector(state => state.session.updateSuccess);

  const handleClose = () => {
    navigate('/session');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCalendarDays({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...sessionEntity,
      ...values,
      date: calendarDays.find(it => it.id.toString() === values.date.toString()),
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
          ...sessionEntity,
          date: sessionEntity?.date?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="yawaApp.session.home.createOrEditLabel" data-cy="SessionCreateUpdateHeading">
            Create or edit a Session
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="session-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Session Notes" id="session-sessionNotes" name="sessionNotes" data-cy="sessionNotes" type="text" />
              <ValidatedField id="session-date" name="date" data-cy="date" label="Date" type="select">
                <option value="" key="0" />
                {calendarDays
                  ? calendarDays.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/session" replace color="info">
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

export default SessionUpdate;
