import { IWorkout } from 'app/shared/model/workout.model';
import { ActivityType } from 'app/shared/model/enumerations/activity-type.model';

export interface IActivity {
  id?: string;
  activityType?: ActivityType | null;
  activityName?: string | null;
  workWeight?: number | null;
  workRepetitions?: number | null;
  workSets?: number | null;
  workSetIntensity?: string | null;
  workIntensity?: number | null;
  workDuration?: number | null;
  workDistance?: number | null;
  activity?: IWorkout | null;
}

export const defaultValue: Readonly<IActivity> = {};
