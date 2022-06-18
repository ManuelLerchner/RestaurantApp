import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Restaurant } from '../models/Restaurant';

@Injectable({
  providedIn: 'root',
})
export class RestaurantService {
  private restaurantObserver = new BehaviorSubject<Restaurant[]>([]);
  private oldRestaurants: Restaurant[] = [];

  constructor() {}

  public readonly restaurants: Observable<Restaurant[]> =
    this.restaurantObserver.asObservable();

  public updateRestaurants(restaurants: Restaurant[]) {
    if (restaurants !== this.oldRestaurants) {
      this.restaurantObserver.next(restaurants);
    }
    this.oldRestaurants = restaurants;
  }
}
