import { ChangeContext, Options } from '@angular-slider/ngx-slider';
import { Component, OnInit } from '@angular/core';

import { FormControl } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { PriceCategory } from 'src/app/models/types/PriceCategory';
import { RestaurantType } from 'src/app/models/types/RestaurantType';
import { FilterService } from 'src/app/services/filter.service';
@Component({
  selector: 'app-restaurant-filters',
  templateUrl: './restaurant-filters.component.html',
  styleUrls: ['./restaurant-filters.component.scss'],
})
export class RestaurantFiltersComponent implements OnInit {
  filterExpanded: boolean = false;
  mouseOverStar = false;

  restaurantTypes: RestaurantType[] = [
    'ITALIAN',
    'CHINESE',
    'INDIAN',
    'JAPANESE',
    'THAI',
    'MEXICAN',
    'GERMAN',
    'ASIAN',
    'VIETNAMESE',
    'GREEK',
    'AMERICAN',
    'KOREAN',
    'TURKISH',
  ];

  priceCategories: PriceCategory[] = ['CHEAP', 'NORMAL', 'COSTLY'];

  selectedStar: number = 1;
  stars: number[] = [5, 4, 3, 2, 1];

  maxDistance: number = 5.0;

  startHour: number = 10.0;
  endHour: number = 24.0;

  distanceOptions: Options = {
    floor: 0.05,
    ceil: 20,
    step: 0.05,
    translate: (value: number): string => {
      if (value < 1) {
        return value * 1000 + 'm';
      } else {
        return value + 'km';
      }
    },
  };

  date = new FormControl();

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

  setRestaurantType(type: RestaurantType | null) {
    var next = type;
    if (type === this.filterService.restaurantType$.value) {
      next = null;
    }
    this.filterService.restaurantType$.next(next);
  }

  setPriceCategory(type: PriceCategory | null) {
    var next = type;
    if (type === this.filterService.priceCategory$.value) {
      next = null;
    }
    this.filterService.priceCategory$.next(next);
  }

  setStarCount(stars: number) {
    this.mouseOverStar = false;
    this.selectedStar = stars;

    this.filterService.minRating$.next(stars);
  }

  setMaxDistance(distance: ChangeContext) {
    this.filterService.maxDistance$.next(distance.value);
  }

  setDate(date: MatDatepickerInputEvent<Date, any>) {
    this.filterService.date$.next(date.value);
  }

  setTimeSlot(window: ChangeContext) {
    this.filterService.timeSlot$.next([window.value, window.highValue!]);
  }

  setPersonCount(count: number) {
    this.filterService.personCount$.next(count);
  }

  formatRestaurantName(restaurantType: RestaurantType): string {
    let lower = restaurantType.toLowerCase();
    return lower.charAt(0).toUpperCase() + lower.slice(1);
  }

  formatPriceCategory(priceCategory: PriceCategory): string {
    switch (priceCategory) {
      case 'CHEAP':
        return '€'.repeat(1);
      case 'NORMAL':
        return '€'.repeat(2);
      case 'COSTLY':
        return '€'.repeat(3);
      default:
        return '-';
    }
  }

  resetFilters() {
    this.filterService.resetFilters();
  }
}
