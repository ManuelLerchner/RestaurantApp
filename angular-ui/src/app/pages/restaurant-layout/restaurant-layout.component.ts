import { ChangeContext, Options } from '@angular-slider/ngx-slider';
import { Component, OnInit } from '@angular/core';
import { TABLESTATES } from 'src/app/mockdata/Tables';
import { Restaurant } from 'src/app/models/restaurant/Restaurant';
import { ReserveTableDialogData } from 'src/app/models/restaurant/ReserveTableDialogData';
import { TableState } from 'src/app/models/restaurant/TableState';
import { TableService } from 'src/app/services/table.service';
import { MapService } from 'src/app/services/map.service';
import { Location } from '@angular/common';
import { ReserveTableComponent } from 'src/app/components/reserve-table/reserve-table.component';
import { MatDialog } from '@angular/material/dialog';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { FormControl } from '@angular/forms';
import * as moment from 'moment';

@Component({
  selector: 'app-restaurant-layout',
  templateUrl: './restaurant-layout.component.html',
  styleUrls: ['./restaurant-layout.component.scss'],
})
export class RestaurantLayoutComponent implements OnInit {
  tableStates: TableState[] = TABLESTATES;
  restaurant!: Restaurant;
  currentImageIndex: number = 0;
  imageManuallySwitched: boolean = false;

  startHour: number = 19;
  endHour: number = 23;
  date = new FormControl(moment());
  selectedPersons: number = 2;

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

  constructor(
    private tableService: TableService,
    private mapService: MapService,
    private location: Location,
    private reserveDialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.mapService.selectedRestaurant$.subscribe((restaurant) => {
      if (restaurant) {
        this.restaurant = restaurant;
      }
    });

    this.tableService.requestTableStates().subscribe({
      next: (tableStates) => (this.tableStates = tableStates),
    });
    this.tableService.restaurantId$.next(this.restaurant.id);
    this.tableService.selectedDate$.next(moment().format('YYYYMMDD'));
    this.tableService.numberOfPersons$.next(2);
    this.tableService.timeSlot$.next([this.startHour, this.endHour]);

    this.shuffleImagesPeriodically();
  }

  getTableClass(tableId: number): string {
    return this.tableStates.find((table) => table.id === tableId)?.reserved
      ? 'reserved'
      : 'free';
  }

  setTimeSlot(window: ChangeContext) {
    this.tableService.timeSlot$.next([window.value, window.highValue!]);
  }

  getPriceCategory(priceCategory: number): string {
    return '€'.repeat(priceCategory);
  }

  getCommentRating(rating: number) {
    return '★'.repeat(rating) + '☆'.repeat(5 - rating);
  }
  
  changeImage(delta: number) {
    const amountOfImages: number = this.restaurant.pictures.length;
    this.currentImageIndex =
      (this.currentImageIndex + delta + amountOfImages) % amountOfImages;
  }

  changeImageButtonPressed(delta: number) {
    this.imageManuallySwitched = true;
    this.changeImage(delta);
  }

  shuffleImagesPeriodically() {
    setInterval(() => {
      if (!this.imageManuallySwitched) {
        this.changeImage(1);
      }
    }, 3500);
  }

  goBack() {
    this.location.back();
  }

  setDate(date: MatDatepickerInputEvent<any, any>) {
    this.tableService.selectedDate$.next(date.value.format('YYYYMMDD'));
  }

  reserveTable(tableId: number): void {
    if (this.tableStates.find((table) => table.id === tableId)?.reserved) {
      return;
    }

    const tableData: ReserveTableDialogData = {
      tableId,
      persons: this.tableService.numberOfPersons$.value!,
      restaurantId: this.restaurant.id,
      timeSlot: this.tableService.timeSlot$.value!,
      date: this.tableService.selectedDate$.value!,
    };

    this.reserveDialog.open(ReserveTableComponent, { data: tableData });
  }
}
