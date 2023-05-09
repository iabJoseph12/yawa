import dayjs from 'dayjs';
import { ISession } from 'app/shared/model/session.model';

export interface ICalendarDay {
  id?: string;
  date?: string | null;
  createdBy?: string | null;
  sessions?: ISession[] | null;
}

export const defaultValue: Readonly<ICalendarDay> = {};
