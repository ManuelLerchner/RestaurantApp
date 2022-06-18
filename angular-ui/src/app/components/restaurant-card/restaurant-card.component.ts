import { Component, Input, OnInit } from '@angular/core';
import { Restaurant } from '../../models/Restaurant';
import { Comment } from 'src/app/models/Comment';
import { COMMENTS } from 'src/app/mockdata/Comments';
import { ThisReceiver } from '@angular/compiler';

@Component({
  selector: 'app-restaurant-card',
  templateUrl: './restaurant-card.component.html',
  styleUrls: ['./restaurant-card.component.scss'],
})
export class RestaurantCardComponent implements OnInit {
  @Input() restaurant!: Restaurant;
  comments!: Comment[];
  showComments: boolean = false;
  currentImageIndex: number = 0;
  currentCommentIndex: number = -1;

  constructor() {
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
    this.mapService.selectedRestaurantObservable = of(null);
  }
}
