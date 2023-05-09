import calendarDay from 'app/entities/calendar-day/calendar-day.reducer';
import session from 'app/entities/session/session.reducer';
import workout from 'app/entities/workout/workout.reducer';
import activity from 'app/entities/activity/activity.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  calendarDay,
  session,
  workout,
  activity,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
