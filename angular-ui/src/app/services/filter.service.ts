import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, interval, timer } from 'rxjs';
import { debounceTime, debounce } from 'rxjs/operators';
import { Restaurant } from '../models/Restaurant';

@Injectable({
  providedIn: 'root',
})
export class FilterService {
  public canPlaceUserMarker = false;

  private selectedRestaurantObserver = new BehaviorSubject<string>('');
  private selectedPriceCategoryObserver = new BehaviorSubject<string>('');
  private selectedStarObserver = new BehaviorSubject<number>(0);
  private selectedMaxDistanceObserver = new BehaviorSubject<number>(0);
  private selectedTimeWindowObserver = new BehaviorSubject<[number, number]>([
    19, 23,
  ]);
  private selectedDateObserver = new BehaviorSubject<Date>(new Date());
  private selectedPersonCountObserver = new BehaviorSubject<number>(0);

  constructor(private http: HttpClient) {
    let observers: BehaviorSubject<any>[] = [
      this.selectedRestaurantObserver,
      this.selectedPriceCategoryObserver,
      this.selectedStarObserver,
      this.selectedMaxDistanceObserver,
      this.selectedTimeWindowObserver,
      this.selectedDateObserver,
      this.selectedPersonCountObserver,
    ];

    this.selectedRestaurantObserver.subscribe(() => {});

    for (let observer of observers) {
      let debouncedRequest = observer.pipe(debounceTime(50));
      debouncedRequest.subscribe(() => {
        this.requestFilteredData();
      });
    }
  }

  set selectedRestaurant(value: string) {
    this.selectedRestaurantObserver.next(value);
  }

  set selectedPriceCategory(value: string) {
    this.selectedPriceCategoryObserver.next(value);
  }

  set selectedStar(value: number) {
    this.selectedStarObserver.next(value);
  }

  set selectedMaxDistance(value: number) {
    this.selectedMaxDistanceObserver.next(value);
  }

  set selectedTimeWindow(value: [number, number]) {
    this.selectedTimeWindowObserver.next(value);
  }

  set selectedDate(value: Date) {
    this.selectedDateObserver.next(value);
  }

  set selectedPersonCount(value: number) {
    this.selectedPersonCountObserver.next(value);
  }

  get selectedRestaurant(): string {
    return this.selectedRestaurantObserver.value;
  }

  get selectedPriceCategory(): string {
    return this.selectedPriceCategoryObserver.value;
  }

  private collectQueryParameters() {
    let queryParams = new HttpParams();

    queryParams = queryParams.append(
      'restaurant',
      this.selectedRestaurantObserver.value
    );
    queryParams = queryParams.append(
      'priceCategory',
      this.selectedPriceCategoryObserver.value
    );
    queryParams = queryParams.append(
      'star',
      this.selectedStarObserver.value.toString()
    );
    queryParams = queryParams.append(
      'maxDistance',
      this.selectedMaxDistanceObserver.value.toString()
    );
    queryParams = queryParams.append(
      'timeWindow',
      this.selectedTimeWindowObserver.value.toString()
    );
    queryParams = queryParams.append(
      'date',
      this.selectedDateObserver.value.toISOString()
    );

    return queryParams;
  }

  private async requestFilteredData() {
    try {
      let filteredRestaurants = this.http
        .get<Restaurant[]>('http://localhost:3000/restaurants', {
          params: this.collectQueryParameters(),
        })
        .toPromise();

      console.log(filteredRestaurants);
    } catch (error: any) {
      console.log(error.statusText);
    }
  }
}
