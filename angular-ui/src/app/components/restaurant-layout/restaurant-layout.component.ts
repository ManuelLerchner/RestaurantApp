import { ChangeContext, Options } from '@angular-slider/ngx-slider';
import { Component, OnInit } from '@angular/core';
import { TABLESTATES } from 'src/app/mockdata/Tables';
import { TableState } from 'src/app/models/TableState';
import { TableService } from 'src/app/services/table.service';

@Component({
  selector: 'app-restaurant-layout',
  templateUrl: './restaurant-layout.component.html',
  styleUrls: ['./restaurant-layout.component.scss'],
})
export class RestaurantLayoutComponent implements OnInit {
  tableStates: TableState[] = TABLESTATES;
  restaurantId: string = '4235';

  startHour: number = 19;
  endHour: number = 23;

  startTime: string = this.formatTime(19);
  endTime: string = this.formatTime(23);

  hourSelectorOptions: Options = {
    floor: 10,
    ceil: 24,
    step: 0.25,
    minRange: 2,
    pushRange: true,
    translate: (value: number): string => {
      let hour: string | number = Math.floor(value);
      let minutes: string | number = Math.floor((value - hour) * 60);
      if (hour < 10) hour = '0' + hour;
      if (minutes < 10) minutes = '0' + minutes;
      return `${hour}:${minutes}`;
    },
  };

  constructor(private tableService: TableService) {}

  ngOnInit(): void {
    this.tableService.restaurantId$.next(this.restaurantId);
    this.tableService.getTableStates().subscribe({
      next: (tableStates) => (this.tableStates = tableStates),
    });

    this.tableService.timeSlotObserver$.subscribe((time) => {
      if (time) {
        let [startTime, endTime] = time;
        this.startTime = this.formatTime(startTime);
        this.endTime = this.formatTime(endTime);
      }
    });
  }

  private formatTime(time: number) {
    console.log(time)
    const hour = Math.round(time).toString().padStart(2, '0');
    const minutes = Math.round(60 * (time - Math.floor(time)))
      .toString()
      .padStart(2, '0');
    return `${hour}:${minutes}`;
  }

  getTableColor(id: number): string {
    return this.tableStates.find((table) => table.id === id)?.reserved
      ? 'red'
      : 'green';
  }

  setTimeSlot(window: ChangeContext) {
    this.tableService.timeSlotObserver$.next([window.value, window.highValue!]);
  }
}
