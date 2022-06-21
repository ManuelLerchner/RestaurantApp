import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Marker } from 'leaflet';
import { BehaviorSubject, combineLatest } from 'rxjs';
import { map, throttleTime } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Restaurant } from '../models/restaurant/Restaurant';
import { RestaurantService } from './restaurant.service';

@Injectable({
  providedIn: 'root',
})
export class FilterService {
  public canPlaceUserMarker = false;
  public hasLoadedRestaurants = false;

  public restaurantType$ = new BehaviorSubject<string | null>(null);
  public priceCategory$ = new BehaviorSubject<number | null>(null);
  public minRating$ = new BehaviorSubject<number | null>(null);
  public maxDistance$ = new BehaviorSubject<number | null>(5.0);
  public timeSlot$ = new BehaviorSubject<[number, number] | null>([10.0, 24.0]);
  public date$ = new BehaviorSubject<Date | null>(null);
  public personCount$ = new BehaviorSubject<number | null>(null);
  public personMarker$ = new BehaviorSubject<Marker<any> | null>(null);
  public refresh$ = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient,
    private restaurantService: RestaurantService
  ) {
    combineLatest([
      this.restaurantType$,
      this.priceCategory$,
      this.minRating$,
      this.maxDistance$,
      this.timeSlot$,
      this.date$,
      this.personCount$,
      this.personMarker$,
      this.refresh$,
    ])
      .pipe(throttleTime(100))
      .pipe(
        map((filterData: any[]) => {
          let position = filterData[7]?.getLatLng();
          return {
            restaurantType: filterData[0],
            priceCategory: filterData[1],
            minRating: filterData[2],
            maxDistance: filterData[3],
            timeSlot: filterData[4],
            date: filterData[5],
            capacity: filterData[6],
            userPosition: position ? [position?.lng, position?.lat] : null,
          };
        })
      )
      .subscribe((filterParams: { [key: string]: any }) => {
        this.requestFilteredData(filterParams);
      });

    setInterval(() => {
      if (!this.hasLoadedRestaurants) {
        this.refresh$.next(true);
      }
    }, 2500);
  }

  private createQueryParams(filterParams: any) {
    let queryParams = new HttpParams();

    Object.entries(filterParams).forEach(([key, value]: [string, any]) => {
      if (value) {
        queryParams = queryParams.append(key, value);
      }
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

      this.restaurantService.restaurants$.next(filteredRestaurants);
      this.hasLoadedRestaurants = true;
    } catch (error: any) {
      this.hasLoadedRestaurants = false;
    }
  }
}
