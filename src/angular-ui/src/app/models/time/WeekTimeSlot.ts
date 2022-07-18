import { WeekDay } from '@angular/common';

export interface WeekTimeSlot {
  dayOfWeek: WeekDay;

  startTime: string;
  endTime: string;
}
