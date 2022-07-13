import { WeekDay } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Comment } from 'src/app/models/restaurant/Comment';
import { PriceCategory } from 'src/app/models/types/PriceCategory';
import { RestaurantType } from 'src/app/models/types/RestaurantType';
import { MapService } from 'src/app/services/map.service';
import { Restaurant } from '../../models/restaurant/Restaurant';
import { RestaurantService } from 'src/app/services/restaurant.service';

@Component({
  selector: 'app-restaurant-card',
  templateUrl: './restaurant-card.component.html',
  styleUrls: ['./restaurant-card.component.scss'],
})
export class RestaurantCardComponent implements OnInit {
  restaurant!: Restaurant;
  showComments: boolean = false;
  currentImageIndex: number = 0;
  currentCommentIndex: number = -1;

  constructor(private restaurantService: RestaurantService) {}

  ngOnInit(): void {
    this.restaurantService.selectedRestaurant$.subscribe(
      (restaurant: Restaurant | null) => {
        if (restaurant) {
          this.restaurant = restaurant;
        }
      }
    );
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

  getPriceCategory(priceCategory: number): string {
    return '€'.repeat(priceCategory);
  }

  changeImage(delta: number) {
    const amountOfImages: number = this.restaurant.pictures.length;
    this.currentImageIndex =
      (this.currentImageIndex + delta + amountOfImages) % amountOfImages;
  }

  getCommentRating(rating: number) {
    return '★'.repeat(rating) + '☆'.repeat(5 - rating);
  }

  getInitials(name: string) {
    return name[0];
  }

  getCommentText(index: number) {
    if (index < 0 || index >= this.restaurant.comments.length) {
      return '';
    }

    if (index != this.currentCommentIndex) {
      return this.restaurant.comments[index].text.substring(0, 50) + '...';
    }

    return this.restaurant.comments[index].text;
  }

  changeCurrentComment(newIndex: number) {
    if (newIndex == this.currentCommentIndex) {
      this.currentCommentIndex = -1;
    } else {
      this.currentCommentIndex = newIndex;
    }
  }

  close() {
    this.restaurantService.showRestaurant = false;
  }

  getTimeSlot(index: number): string {
    index = index != 6 ? index + 1 : 0;

    let item = this.restaurant.openingTimes.filter(
      (day) => day.dayOfWeek.toString() == WeekDay[index].toUpperCase()
    )[0];

    if (!item) {
      return 'Geschlossen';
    }

    const { startTime, endTime, ...rest } = item;

    if (!startTime || !endTime) {
      return 'Geschlossen';
    }

    return (
      startTime.substring(0, startTime.lastIndexOf(':')) +
      ' - ' +
      endTime.substring(0, endTime.lastIndexOf(':'))
    );
  }

  getWeekDay(index: number): string {
    switch (index) {
      case 0:
        return 'MON';
      case 1:
        return 'TUE';
      case 2:
        return 'WED';
      case 3:
        return 'THU';
      case 4:
        return 'FRI';
      case 5:
        return 'SAT';
      case 6:
        return 'SUN';
      default:
        return '';
    }
  }
}
