import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LatLng, Marker } from 'leaflet';
import { BehaviorSubject, combineLatest, Subscription } from 'rxjs';
import { map, throttleTime, debounceTime } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { MapRestaurant } from '../models/restaurant/MapRestaurant';
import { PriceCategory } from '../models/types/PriceCategory';
import { RestaurantType } from '../models/types/RestaurantType';
import { RestaurantService } from './restaurant.service';

@Injectable({
  providedIn: 'root',
})
export class FilterService {
  public hasLoadedRestaurants = false;
  public canRequestNewData = true;

  public restaurantType$ = new BehaviorSubject<RestaurantType | null>(null);
  public priceCategory$ = new BehaviorSubject<PriceCategory | null>(null);
  public minRating$ = new BehaviorSubject<number | null>(null);
  public maxDistance$ = new BehaviorSubject<number | null>(5.0);
  public timeSlot$ = new BehaviorSubject<[number, number] | null>([10.0, 24.0]);
  public date$ = new BehaviorSubject<Date | null>(null);
  public personCount$ = new BehaviorSubject<number | null>(null);
  public personMarker$ = new BehaviorSubject<Marker<any>>(
    new Marker(new LatLng(48.1372264, 11.5755203), {})
  );
  public refresh$ = new BehaviorSubject<boolean>(false);
  private requestSubscription: Subscription;

  constructor(
    private http: HttpClient,
    private restaurantService: RestaurantService
  ) {
    this.requestSubscription = this.createSubscription();

    setInterval(() => {
      if (!this.hasLoadedRestaurants) {
        this.refresh$.next(true);
      }
    }, 2500);
  }

  private createSubscription() {
    return combineLatest([
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
      .pipe(debounceTime(25))
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
        .get<MapRestaurant[]>(`${environment.apiUrl}/restaurants`, {
          params: this.createQueryParams(filterParams),
        })
        .toPromise();

      this.restaurantService.restaurants$.next(filteredRestaurants);
      this.hasLoadedRestaurants = true;
    } catch (error: any) {
      this.hasLoadedRestaurants = false;
    }
  }

  resetFilters() {
    this.requestSubscription.unsubscribe();
    this.restaurantType$.next(null);
    this.priceCategory$.next(null);
    this.minRating$.next(null);
    this.maxDistance$.next(5.0);
    this.timeSlot$.next([10.0, 24.0]);
    this.date$.next(null);
    this.personCount$.next(null);
    this.personMarker$.next(new Marker(new LatLng(48.1372264, 11.5755203), {}));

    this.requestSubscription = this.createSubscription();
    return this.requestSubscription;
  }
}
