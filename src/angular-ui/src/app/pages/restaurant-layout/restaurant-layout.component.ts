import { ChangeContext, Options } from '@angular-slider/ngx-slider';
import { Component, OnInit } from '@angular/core';
import { Restaurant } from 'src/app/models/restaurant/Restaurant';
import { ReserveTableDialogData } from 'src/app/models/restaurant/ReserveTableDialogData';
import { TableService } from 'src/app/services/table.service';
import { Location } from '@angular/common';
import { ReserveTableComponent } from 'src/app/components/reserve-table/reserve-table.component';
import { MatDialog } from '@angular/material/dialog';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { FormControl } from '@angular/forms';
import * as moment from 'moment';
import { RestaurantService } from 'src/app/services/restaurant.service';

@Component({
  selector: 'app-restaurant-layout',
  templateUrl: './restaurant-layout.component.html',
  styleUrls: ['./restaurant-layout.component.scss'],
})
export class RestaurantLayoutComponent implements OnInit {
  tableStates!: boolean[];
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
    private restaurantService: RestaurantService,
    private location: Location,
    private reserveDialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.restaurantService.selectedRestaurant$.subscribe((restaurant) => {
      if (restaurant) {
        this.restaurant = restaurant;
      }
    });
    if (this.restaurant) {
      this.tableService.restaurantId$.next(this.restaurant.id);
    }

    this.tableService.tableStates$.subscribe({
      next: (tableStates) => {
        if (tableStates) {
          this.tableStates = tableStates;
        }
      },
    });

    this.tableService.selectedDate$.next(moment().format('YYYY-MM-DD'));
    this.tableService.numberOfPersons$.next(2);
    this.tableService.timeSlot$.next([this.startHour, this.endHour]);

    this.shuffleImagesPeriodically();
  }

  tableFree(tableId: number): boolean {
    if (!this.tableStates || tableId >= this.tableStates.length) {
      return false;
    }
    return this.tableStates[tableId];
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
    this.tableService.selectedDate$.next(date.value.format('YYYY-MM-DD'));
  }

  reserveTable(tableId: number): void {
    if (tableId >= this.tableStates.length) {
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

  showLayout(index: number) {
    if (
      (!this.restaurant && index == 0) ||
      (this.restaurant.layoutId > 2 && index == 0)
    ) {
      return true;
    }
    return this.restaurant.layoutId==index;
  }
}
