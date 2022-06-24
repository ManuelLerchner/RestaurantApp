import { ChangeContext, Options } from '@angular-slider/ngx-slider';
import { Component, OnInit } from '@angular/core';
import { TABLESTATES } from 'src/app/mockdata/Tables';
import { Restaurant } from 'src/app/models/restaurant/Restaurant';
import { TableState } from 'src/app/models/restaurant/TableState';
import { TableService } from 'src/app/services/table.service';
import { MapService } from 'src/app/services/map.service';

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
    private mapService: MapService
  ) {}

  ngOnInit(): void {
    this.mapService.selectedRestaurant$.subscribe((restaurant) => {
      console.log('sgho', restaurant);
      if (restaurant) {
        this.restaurant = restaurant;
      }
    });

    this.tableService.restaurantId$.next(this.restaurant?.id ?? 0);
    this.tableService.getTableStates().subscribe({
      next: (tableStates) => (this.tableStates = tableStates),
    });
    
    this.shuffleImagesPeriodically();
  }

  getTableColor(id: number): string {
    return this.tableStates.find((table) => table.id === id)?.reserved
      ? 'red'
      : 'green';
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

  getInitials(name: string) {
    return name[0];
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
}
