import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MapRestaurant } from '../models/restaurant/MapRestaurant';
import { Restaurant } from '../models/restaurant/Restaurant';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RestaurantService {
  public restaurants$ = new BehaviorSubject<MapRestaurant[]>([]);
  public selectedRestaurant$ = new BehaviorSubject<Restaurant | null>(null);
  public showRestaurant: boolean = false;

  constructor(private http: HttpClient) {}

  async loadFullRestaurant(id: number) {
    var selectedRestaurant = await this.http
      .get<Restaurant>(`${environment.apiUrl}/restaurants/${id}`)
      .toPromise();

    this.selectedRestaurant$.next(selectedRestaurant);
  }
}
