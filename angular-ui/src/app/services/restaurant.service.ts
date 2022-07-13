import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { RestaurantSmall } from '../models/restaurant/MapRestaurant';
import { RestaurantFull } from '../models/restaurant/Restaurant';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RestaurantService {
  public restaurants$ = new BehaviorSubject<RestaurantSmall[]>([]);
  public selectedRestaurant$ = new BehaviorSubject<RestaurantFull | null>(null);
  public showRestaurant: boolean = false;

  constructor(private http: HttpClient) {}

  async loadFullRestaurant(id: number) {
    console.log('loadFullRestaurant:', id);
    var selectedRestaurant = await this.http
      .get<RestaurantFull>(`${environment.apiUrl}/restaurants/${id}`)
      .toPromise();

    console.log(selectedRestaurant);

    this.selectedRestaurant$.next(selectedRestaurant);
  }
}
