import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './session.reducer';

export const SessionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sessionEntity = useAppSelector(state => state.session.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sessionDetailsHeading">Session</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sessionEntity.id}</dd>
          <dt>
            <span id="sessionNotes">Session Notes</span>
          </dt>
          <dd>{sessionEntity.sessionNotes}</dd>
          <dt>Date</dt>
          <dd>{sessionEntity.date ? sessionEntity.date.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/session" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/session/${sessionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SessionDetail;
