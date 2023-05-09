import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivity } from 'app/shared/model/activity.model';
import { getEntities, reset } from './activity.reducer';

export const Activity = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const activityList = useAppSelector(state => state.activity.entities);
  const loading = useAppSelector(state => state.activity.loading);
  const totalItems = useAppSelector(state => state.activity.totalItems);
  const links = useAppSelector(state => state.activity.links);
  const entity = useAppSelector(state => state.activity.entity);
  const updateSuccess = useAppSelector(state => state.activity.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="activity-heading" data-cy="ActivityHeading">
        Activities
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/activity/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Activity
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={activityList ? activityList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {activityList && activityList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('activityType')}>
                    Activity Type <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('activityName')}>
                    Activity Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('workWeight')}>
                    Work Weight <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('workRepetitions')}>
                    Work Repetitions <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('workSets')}>
                    Work Sets <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('workSetIntensity')}>
                    Work Set Intensity <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('workIntensity')}>
                    Work Intensity <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('workDuration')}>
                    Work Duration <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('workDistance')}>
                    Work Distance <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Activity <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {activityList.map((activity, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/activity/${activity.id}`} color="link" size="sm">
                        {activity.id}
                      </Button>
                    </td>
                    <td>{activity.activityType}</td>
                    <td>{activity.activityName}</td>
                    <td>{activity.workWeight}</td>
                    <td>{activity.workRepetitions}</td>
                    <td>{activity.workSets}</td>
                    <td>{activity.workSetIntensity}</td>
                    <td>{activity.workIntensity}</td>
                    <td>{activity.workDuration}</td>
                    <td>{activity.workDistance}</td>
                    <td>{activity.activity ? <Link to={`/workout/${activity.activity.id}`}>{activity.activity.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/activity/${activity.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`/activity/${activity.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`/activity/${activity.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Activities found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Activity;
