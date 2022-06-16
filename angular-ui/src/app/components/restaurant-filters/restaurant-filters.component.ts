import { ChangeContext, Options } from '@angular-slider/ngx-slider';
import { Component, OnInit } from '@angular/core';

import { FormControl } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { FilterService } from 'src/app/services/filter.service';
@Component({
  selector: 'app-restaurant-filters',
  templateUrl: './restaurant-filters.component.html',
  styleUrls: ['./restaurant-filters.component.scss'],
})
export class RestaurantFiltersComponent implements OnInit {
  filterExpanded: boolean = false;
  mouseOverStar = false;

  restaurantTypes: string[] = [
    'Italian',
    'Chinese',
    'Japanese',
    'Thai',
    'Mexican',
  ];

  priceCategories: string[] = ['günstig', 'normal', 'teuer'];

  selectedStar: number = 2;
  stars: number[] = [5, 4, 3, 2, 1];

  maxDistance: number = 0.8;

  startHour: number = 19;
  endHour: number = 23;

  distanceOptions: Options = {
    floor: 0,
    ceil: 3,
    step: 0.05,
    translate: (value: number): string => {
      if (value < 1) {
        return value * 1000 + 'm';
      } else {
        return value + 'km';
      }
    },
  };

  date = new FormControl(new Date());

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

  personCount = 2;

  constructor(public filterService: FilterService) {}

  ngOnInit(): void {}

  addClass(star: number) {
    if (this.mouseOverStar) {
      this.selectedStar = star;
    }
  }

  removeClass() {
    if (this.mouseOverStar) {
      this.selectedStar = 0;
    }
  }

  placeLocationMarkerEvent() {
    this.filterService.canPlaceUserMarker =
      !this.filterService.canPlaceUserMarker;
  }

  setRestaurantType(type: string) {
    var next = type;
    if (type === this.filterService.selectedRestaurant) {
      next = '';
    }
    this.filterService.selectedRestaurant = next;
  }

  setPriceCategory(type: string) {
    var next = type;
    if (type === this.filterService.selectedPriceCategory) {
      next = '';
    }
    this.filterService.selectedPriceCategory = next;
  }

  setStarCount(star: number) {
    this.mouseOverStar = false;
    this.selectedStar = star;

    this.filterService.selectedStar = star;
  }

  setMaxDistance(distance: ChangeContext) {
    this.filterService.selectedMaxDistance = distance.value;
  }

  setDate(date: MatDatepickerInputEvent<Date, any>) {
    let newDate = new Date(date.value === null ? new Date() : date.value);
    this.filterService.selectedDate = newDate;
  }

  setTimeWindow(window: ChangeContext) {
    this.filterService.selectedTimeWindow = [window.value, window.highValue!];
  }

  setPersonCount(count: number) {
    this.filterService.selectedPersonCount = count;
  }
}
