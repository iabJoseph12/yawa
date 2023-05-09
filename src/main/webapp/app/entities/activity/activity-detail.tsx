import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activity.reducer';

export const ActivityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const activityEntity = useAppSelector(state => state.activity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activityDetailsHeading">Activity</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{activityEntity.id}</dd>
          <dt>
            <span id="activityType">Activity Type</span>
          </dt>
          <dd>{activityEntity.activityType}</dd>
          <dt>
            <span id="activityName">Activity Name</span>
          </dt>
          <dd>{activityEntity.activityName}</dd>
          <dt>
            <span id="workWeight">Work Weight</span>
          </dt>
          <dd>{activityEntity.workWeight}</dd>
          <dt>
            <span id="workRepetitions">Work Repetitions</span>
          </dt>
          <dd>{activityEntity.workRepetitions}</dd>
          <dt>
            <span id="workSets">Work Sets</span>
          </dt>
          <dd>{activityEntity.workSets}</dd>
          <dt>
            <span id="workSetIntensity">Work Set Intensity</span>
          </dt>
          <dd>{activityEntity.workSetIntensity}</dd>
          <dt>
            <span id="workIntensity">Work Intensity</span>
          </dt>
          <dd>{activityEntity.workIntensity}</dd>
          <dt>
            <span id="workDuration">Work Duration</span>
          </dt>
          <dd>{activityEntity.workDuration}</dd>
          <dt>
            <span id="workDistance">Work Distance</span>
          </dt>
          <dd>{activityEntity.workDistance}</dd>
          <dt>Activity</dt>
          <dd>{activityEntity.activity ? activityEntity.activity.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/activity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activity/${activityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivityDetail;
