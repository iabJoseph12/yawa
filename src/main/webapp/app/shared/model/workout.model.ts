import { IActivity } from 'app/shared/model/activity.model';
import { ISession } from 'app/shared/model/session.model';

export interface IWorkout {
  id?: string;
  routineName?: string | null;
  activities?: IActivity[] | null;
  sessionid?: ISession | null;
}

export const defaultValue: Readonly<IWorkout> = {};
