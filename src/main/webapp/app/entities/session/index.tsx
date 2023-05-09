import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Session from './session';
import SessionDetail from './session-detail';
import SessionUpdate from './session-update';
import SessionDeleteDialog from './session-delete-dialog';

const SessionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Session />} />
    <Route path="new" element={<SessionUpdate />} />
    <Route path=":id">
      <Route index element={<SessionDetail />} />
      <Route path="edit" element={<SessionUpdate />} />
      <Route path="delete" element={<SessionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SessionRoutes;
