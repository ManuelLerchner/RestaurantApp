import { Injectable } from '@angular/core';
import { RESTAURANTS } from '../mockdata/Restaurants';
import { Restaurant } from '../models/Restaurant';

@Injectable({
  providedIn: 'root',
})
export class RestaurantService {
  constructor() {}

  getRestaurants(): Restaurant[] {
    return RESTAURANTS;
  }
}
