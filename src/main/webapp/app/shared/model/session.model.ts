import { IWorkout } from 'app/shared/model/workout.model';
import { ICalendarDay } from 'app/shared/model/calendar-day.model';

export interface ISession {
  id?: string;
  sessionNotes?: string | null;
  workouts?: IWorkout[] | null;
  date?: ICalendarDay | null;
}

export const defaultValue: Readonly<ISession> = {};
