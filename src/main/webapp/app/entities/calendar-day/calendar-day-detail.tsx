import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Card, Button, Row, Col } from 'reactstrap';
import { ValidatedField, ValidatedForm, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './calendar-day.reducer';

export const CalendarDayDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const defaultValues = () => {};

  const saveEntity = values => {
    const entity = {
      ...calendarDayEntity,
      ...values,
    };
  };

  const calendarDayEntity = useAppSelector(state => state.calendarDay.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="calendarDayDetailsHeading">Calendar Day</h2>
        <dl className="jh-entity-details">
          <dd>
            {calendarDayEntity.date ? <TextFormat value={calendarDayEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Card>
          <h2 data-cy="calendarDayDetailsHeading">Session</h2>
          {/* Add condition where if one workout is present, then show different view*/}
          <Card>
            <h3 data-cy="calendarDayDetailsHeading">Add a workout</h3>
            <Button tag={Link} to="/calendar-day" replace color="info" data-cy="entityDetailsBackButton">
              +
            </Button>
            {/* Create an API on backend that will create a workout within a session. Then, add the session to the Calendar Day */}
          </Card>
          {/* <ValidatedForm defaultValues={defaultValues} onSubmit={saveEntity}>
                <ValidatedField name="id" required readOnly id="calendar-day-id" label="ID" validate={{ required: true }} />
                <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/calendar-day" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit">
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Start
                </Button>
          </ValidatedForm> */}
        </Card>
        <Button tag={Link} to="/calendar-day" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/calendar-day/${calendarDayEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CalendarDayDetail;
