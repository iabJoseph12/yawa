import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICalendarDay } from 'app/shared/model/calendar-day.model';
import { getEntity, updateEntity, createEntity, reset } from './calendar-day.reducer';

export const CalendarDayUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const calendarDayEntity = useAppSelector(state => state.calendarDay.entity);
  const loading = useAppSelector(state => state.calendarDay.loading);
  const updating = useAppSelector(state => state.calendarDay.updating);
  const updateSuccess = useAppSelector(state => state.calendarDay.updateSuccess);

  const handleClose = () => {
    navigate('/calendar-day' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...calendarDayEntity,
      ...values,
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
          ...calendarDayEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="yawaApp.calendarDay.home.createOrEditLabel" data-cy="CalendarDayCreateUpdateHeading">
            Create or edit a Calendar Day
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="calendar-day-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Date" id="calendar-day-date" name="date" data-cy="date" type="date" />
              <ValidatedField label="Created By" id="calendar-day-createdBy" name="createdBy" data-cy="createdBy" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/calendar-day" replace color="info">
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

export default CalendarDayUpdate;
