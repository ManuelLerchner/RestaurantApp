import { Component, Input, OnInit } from '@angular/core';
import { Restaurant } from 'src/app/models/Restaurant';

@Component({
  selector: 'app-restaurant-list-card',
  templateUrl: './restaurant-list-card.component.html',
  styleUrls: ['./restaurant-list-card.component.scss'],
})
export class RestaurantListCardComponent implements OnInit {
  @Input() restaurant!: Restaurant;

  constructor() {}

  ngOnInit(): void {}

  getRating(rating: number): string {
    return '★'.repeat(rating);	
  }

  getPriceCategory(priceCategory: number): string {
    return '€'.repeat(priceCategory);
  }
}
