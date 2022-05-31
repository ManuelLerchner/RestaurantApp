import { Component, OnInit } from '@angular/core';
import { Restaurant } from '../../models/Restaurant';
import { RESTAURANTS } from '../../mockdata/Restaurants';
import { Comment } from 'src/app/models/Comment';
import { COMMENTS } from 'src/app/mockdata/Comments';

@Component({
  selector: 'app-restaurant-card',
  templateUrl: './restaurant-card.component.html',
  styleUrls: ['./restaurant-card.component.scss'],
})
export class RestaurantCardComponent implements OnInit {
  restaurant!: Restaurant;
  comments!: Comment[];
  showComments: boolean = true;
  currentImageIndex: number = 0;

  constructor() {
    this.restaurant = RESTAURANTS[0];
    this.comments = COMMENTS;
  }

  ngOnInit(): void {}

  getPriceCategory(priceCategory: number): string {
    return '€'.repeat(priceCategory);
  }

  changeImage(delta: number) {
    const amountOfImages: number = this.restaurant.images.length;
    this.currentImageIndex =
      (this.currentImageIndex + delta + amountOfImages) % amountOfImages;
  }

  getCommentRating(rating: number) {
    return '★'.repeat(rating) + '☆'.repeat(5 - rating);
  }

  getInitials(name: string){
    return name[0];
  }
}
