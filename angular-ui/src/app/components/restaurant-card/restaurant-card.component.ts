import { Component, OnInit } from '@angular/core';
import { Restaurant } from '../../models/Restaurant';
import { RESTAURANTS } from '../../mockdata/Restaurants';

@Component({
  selector: 'app-restaurant-card',
  templateUrl: './restaurant-card.component.html',
  styleUrls: ['./restaurant-card.component.scss']
})
export class RestaurantCardComponent implements OnInit {
  restaurant!: Restaurant;
  currentImageIndex: number = 0;

  constructor() {
    this.restaurant = RESTAURANTS[0];
   }

  ngOnInit(): void {
  }

  getPriceCategory(priceCategory: number): string {
    return '€'.repeat(priceCategory);
  }

}
