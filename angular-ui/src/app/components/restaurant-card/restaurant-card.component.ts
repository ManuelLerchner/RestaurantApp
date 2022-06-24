import { Component, OnInit } from '@angular/core';
import { Comment } from 'src/app/models/restaurant/Comment';
import { MapService } from 'src/app/services/map.service';
import { Restaurant } from '../../models/restaurant/Restaurant';

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
  subscription: any;

  constructor(private mapService: MapService) {}

  ngOnInit(): void {
    this.subscription = this.mapService.selectedRestaurant$.subscribe(
      (restaurant: Restaurant | null) => {
        if (restaurant) {
          this.restaurant = restaurant;
        }
      }
    );
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
    if(index<0 || index>=this.restaurant.comments.length){
      return "";
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

  ngOnDestroy(): void {
    if(this.subscription){
      this.subscription.unsubscribe();
    }
  }

  close() {
    this.mapService.selectedRestaurant$.next(null);
  }

  getWeekDay(index: number): string{
    switch(index){
      case 0: return "MON";
      case 1: return "TUE";
      case 2: return "WED";
      case 3: return "THU";
      case 4: return "FRI";
      case 5: return "SAT";
      case 6: return "SUN";
      default: return "";
    }
  }
}
