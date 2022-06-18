import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, combineLatest } from 'rxjs';
import { debounceTime, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { RESTAURANTS } from '../mockdata/Restaurants';
import { Restaurant } from '../models/Restaurant';
import { RestaurantService } from './restaurant.service';

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
    combineLatest([
      this.restaurantTypeObserver,
      this.priceCategoryObserver,
      this.minRatingObserver,
      this.maxDistanceObserver,
      this.timeSlotObserver,
      this.dateObserver,
      this.personCountObserver,
    ])
      .pipe(debounceTime(50))
      .pipe(
        map((filterData: any[]) => {
          return {
            restaurant_type: filterData[0],
            price_category: filterData[1],
            min_rating: filterData[2],
            max_distance: filterData[3],
            time_slot: filterData[4],
            date: filterData[5],
            person_count: filterData[6],
          };
        })
      )
      .subscribe((filterParams: { [key: string]: any }) => {
        this.requestFilteredData(filterParams);
      });
  }

  private createQueryParams(filterParams: { [key: string]: any }) {
    let queryParams = new HttpParams();

    filterParams.forEach((value: any, key: any) => {
      if (value !== null) queryParams = queryParams.append(key, value);
    });

    return queryParams;
  }

  private async requestFilteredData(filterParams: { [key: string]: any }) {
    try {
      let filteredRestaurants = await this.http
        .get<Restaurant[]>(`${environment.apiUrl}/restaurants`, {
          params: this.createQueryParams(filterParams),
        })
        .toPromise();

      this.restaurantService.updateRestaurants(filteredRestaurants);
    } catch (error: any) {
      console.error(
        '%cFetching new data failed, returning mockdata!\nMaybe the server is not running yet?',
        'color:yellow;font-family:system-ui;font-size:2rem;-webkit-text-stroke: 1px black;font-weight:bold'
      );
      this.restaurantService.updateRestaurants(RESTAURANTS);
    }
  }
}
