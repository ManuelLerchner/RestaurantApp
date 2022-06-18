import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { RESTAURANTS } from '../mockdata/Restaurants';
import { Restaurant } from '../models/Restaurant';
import { RestaurantService } from './restaurant.service';
import { environment } from 'src/environments/environment';
import { merge } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FilterService {
  public canPlaceUserMarker = false;

  public restaurantTypeObserver = new BehaviorSubject<string | null>(null);
  public priceCategoryObserver = new BehaviorSubject<number | null>(null);
  public minRatingObserver = new BehaviorSubject<number | null>(null);
  public maxDistanceObserver = new BehaviorSubject<number | null>(null);
  public timeSlotObserver = new BehaviorSubject<[number, number] | null>(null);
  public dateObserver = new BehaviorSubject<Date | null>(null);
  public personCountObserver = new BehaviorSubject<number | null>(null);

  constructor(
    private http: HttpClient,
    private restaurantService: RestaurantService
  ) {
    merge(
      this.restaurantTypeObserver,
      this.priceCategoryObserver,
      this.minRatingObserver,
      this.maxDistanceObserver,
      this.timeSlotObserver,
      this.dateObserver,
      this.personCountObserver
    )
      .pipe(debounceTime(50))
      .subscribe(() => {
        this.requestFilteredData();
      });
  }

  private collectQueryParameters() {
    let queryParams = new HttpParams();

    let params: (string | any)[] = [
      ['restaurant_type', this.restaurantTypeObserver.value],
      ['price_category', this.priceCategoryObserver.value],
      ['min_rating', this.minRatingObserver.value],
      ['max_distance', this.maxDistanceObserver.value],
      ['time_slot', this.timeSlotObserver.value],
      ['date', this.dateObserver.value],
      ['person_count', this.personCountObserver.value],
    ];

    params.forEach(([key, value]) => {
      if (value) {
        queryParams = queryParams.append(key, value);
      }
    });

    return queryParams;
  }

  private async requestFilteredData() {
    try {
      console.log('requestFilteredData');
      let filteredRestaurants = await this.http
        .get<Restaurant[]>(`${environment.apiUrl}/restaurants`, {
          params: this.collectQueryParameters(),
        })
        .toPromise();

      console.log(filteredRestaurants);

      this.restaurantService.updateRestaurants(filteredRestaurants);
    } catch (error: any) {
      //temporary error message
      console.error(
        '%cFetching new data failed, returning mockdata!\nMaybe the server is not running yet?',
        'color:yellow;font-family:system-ui;font-size:2rem;-webkit-text-stroke: 1px black;font-weight:bold'
      );
      this.restaurantService.updateRestaurants(RESTAURANTS);
    }
  }
}
