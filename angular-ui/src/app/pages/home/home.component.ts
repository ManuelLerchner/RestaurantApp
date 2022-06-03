import { Component, OnInit } from '@angular/core';
import { Restaurant } from 'src/app/models/Restaurant';
import { RESTAURANTS } from './../../mockdata/Restaurants';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  restaurants: Restaurant[];

  currentRestaurant: Restaurant | null = null;

  constructor() {
    this.restaurants = RESTAURANTS;
  }

  ngOnInit(): void {}

  selectedRestaurantEvent(restaurant: Restaurant | null): void {
    this.currentRestaurant = restaurant;
  }
}
