import { Options } from '@angular-slider/ngx-slider';
import { Component, OnInit } from '@angular/core';

import { FormControl } from '@angular/forms';
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

  constructor() {}

  ngOnInit(): void {}

  countStar(star: number) {
    this.mouseOverStar = false;
    this.selectedStar = star;
    console.log(star);
  }

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
}
