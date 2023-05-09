import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CalendarDay from './calendar-day';
import CalendarDayDetail from './calendar-day-detail';
import CalendarDayUpdate from './calendar-day-update';
import CalendarDayDeleteDialog from './calendar-day-delete-dialog';

const CalendarDayRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CalendarDay />} />
    <Route path="new" element={<CalendarDayUpdate />} />
    <Route path=":id">
      <Route index element={<CalendarDayDetail />} />
      <Route path="edit" element={<CalendarDayUpdate />} />
      <Route path="delete" element={<CalendarDayDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CalendarDayRoutes;
