import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, combineLatest, Observable } from 'rxjs';
import { map, throttleTime } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { ReserveTableDialogData } from '../models/restaurant/ReserveTableDialogData';
import { TableState } from '../models/restaurant/TableState';

@Injectable({
  providedIn: 'root',
})
export class TableService {
  public timeSlot$ = new BehaviorSubject<[number, number] | null>(null);
  public restaurantId$ = new BehaviorSubject<number | null>(null);
  public numberOfPersons$ = new BehaviorSubject<number | null>(null);
  public selectedDate$ = new BehaviorSubject<string | null>(null);
  public tableStates$ = new BehaviorSubject<boolean[] | null>(null);

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
            date: tableData[3],
          };
        })
      )
      .subscribe((filterParams: { [key: string]: any }) => {
        this.parameters = filterParams;
        this.requestTableStates().subscribe({
          next: (tableStates) => this.tableStates$.next(tableStates),
        });
      });
  }

  private createQueryParams(filterParams: any) {
    let queryParams = new HttpParams();

    Object.entries(filterParams).forEach(([key, value]: [string, any]) => {
      if (value) {
        console.log(key, value);
        queryParams = queryParams.append(key, value);
      }
    });
    console.log(queryParams.toString);
    return queryParams;
  }

  public requestTableStates(): Observable<boolean[]> {
    return this.http.get<boolean[]>(
      `${environment.apiUrl}/restaurants/getSuitableTables`,
      {
        params: this.createQueryParams(this.parameters),
      }
    );
  }

  public reserveTable(params: ReserveTableDialogData): Observable<any> {
    return this.http.post<any>(
      `${environment.apiUrl}/restaurants/getSuitableTables`,
      {
        params: this.createQueryParams(params),
      }
    );
  }
}
