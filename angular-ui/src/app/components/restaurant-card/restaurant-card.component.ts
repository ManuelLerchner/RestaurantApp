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
  comments!: Comment[];
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
    if (index != this.currentCommentIndex) {
      return this.comments[index].text.substring(0, 50) + '...';
    }

    return this.comments[index].text;
  }

  changeCurrentComment(newIndex: number) {
    if (newIndex == this.currentCommentIndex) {
      this.currentCommentIndex = -1;
    } else {
      this.currentCommentIndex = newIndex;
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  close() {
    this.mapService.selectedRestaurant$.next(null);
  }
}
