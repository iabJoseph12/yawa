import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Workout from './workout';
import WorkoutDetail from './workout-detail';
import WorkoutUpdate from './workout-update';
import WorkoutDeleteDialog from './workout-delete-dialog';

const WorkoutRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Workout />} />
    <Route path="new" element={<WorkoutUpdate />} />
    <Route path=":id">
      <Route index element={<WorkoutDetail />} />
      <Route path="edit" element={<WorkoutUpdate />} />
      <Route path="delete" element={<WorkoutDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WorkoutRoutes;
