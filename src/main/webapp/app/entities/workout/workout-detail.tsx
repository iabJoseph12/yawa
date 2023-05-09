import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './workout.reducer';

export const WorkoutDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const workoutEntity = useAppSelector(state => state.workout.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="workoutDetailsHeading">Workout</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{workoutEntity.id}</dd>
          <dt>
            <span id="routineName">Routine Name</span>
          </dt>
          <dd>{workoutEntity.routineName}</dd>
          <dt>Sessionid</dt>
          <dd>{workoutEntity.sessionid ? workoutEntity.sessionid.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/workout" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/workout/${workoutEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WorkoutDetail;
