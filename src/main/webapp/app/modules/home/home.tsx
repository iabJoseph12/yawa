import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import Calendar from 'react-calendar';
import { Row, Col, Alert, Button } from 'reactstrap';
import { useState } from 'react';
import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const [date, setDate] = useState(new Date());

  const highlightToday = ({ date, view }) => {
    if (view === 'month' && date.getDate() === new Date().getDate()) {
      return 'today';
    }
  };

  return (
    <Row>
      <Col md="9">
        {account?.login ? (
          <div>
            <h2>Dashboard</h2>
            <Alert color="success">You are logged in as user &quot;{account.login}&quot;.</Alert>
            <Button color="primary" outline className="btn-long">
              <Link to="/calendar-day/new">Start your workout</Link>
            </Button>
            <br />
            <Button color="primary" outline className="btn-long">
              Create a routine
            </Button>
            <br />
            <Button color="primary" outline className="btn-long">
              Write in your food diary
            </Button>
            <br />
            <br />
            <Calendar value={date} tileClassName={highlightToday} />
          </div>
        ) : (
          <div>
            <h2>Welcome to Yet Another Workout App!</h2>
            <Alert color="warning">
              If you want to
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                sign in
              </Link>
              , you can try the default accounts:
              <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;) <br />- User (login=&quot;user&quot; and
              password=&quot;user&quot;).
            </Alert>

            <Alert color="warning">
              You don&apos;t have an account yet?&nbsp;
              <Link to="/account/register" className="alert-link">
                Register a new account
              </Link>
            </Alert>
          </div>
        )}
        <p>If you have any question on JHipster:</p>
      </Col>
    </Row>
  );
};

export default Home;
