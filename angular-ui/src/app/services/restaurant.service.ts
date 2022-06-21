import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Restaurant } from '../models/restaurant/Restaurant';

@Injectable({
  providedIn: 'root',
})
export class RestaurantService {
  public restaurants$ = new BehaviorSubject<Restaurant[]>([]);

  constructor() {}
}
