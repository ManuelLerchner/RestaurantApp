import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, combineLatest, Observable, Subscription } from 'rxjs';
import {
  debounceTime,
  distinctUntilChanged,
  map,
  throttleTime,
} from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { TableState } from '../models/restaurant/TableState';
import { FilterService } from './filter.service';

@Injectable({
  providedIn: 'root',
})
export class TableService {
  public timeSlot$ = new BehaviorSubject<[number, number] | null>(null);
  public restaurantId$ = new BehaviorSubject<number | null>(null);
  public numberOfPersons$ = new BehaviorSubject<number | null>(null);
  public selectedDate$ = new BehaviorSubject<string | null>(null);

  private parameters!: { [key: string]: any };

  constructor(private http: HttpClient) {
    combineLatest([
      this.timeSlot$,
      this.restaurantId$,
      this.numberOfPersons$,
      this.selectedDate$,
    ])
      .pipe(throttleTime(25))
      .pipe(
        map((tableData: any[]) => {
          return {
            timeSlot: tableData[0],
            restaurantId: tableData[1],
            numberOfPersons: tableData[2],
            selectedDate: tableData[3],
          };
        })
      )
      .subscribe((filterParams: { [key: string]: any }) => {
        this.parameters = filterParams;
        this.requestTableStates();
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

  public requestTableStates(): Observable<TableState[]> {
    return this.http.get<TableState[]>(
      `${environment.apiUrl}/restaurants/getSuitableTables`,
      {
        params: this.createQueryParams(this.parameters),
      }
    );
  }
}
