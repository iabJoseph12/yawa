import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CalendarDay from './calendar-day';
import Session from './session';
import Workout from './workout';
import Activity from './activity';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="calendar-day/*" element={<CalendarDay />} />
        <Route path="session/*" element={<Session />} />
        <Route path="workout/*" element={<Workout />} />
        <Route path="activity/*" element={<Activity />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
