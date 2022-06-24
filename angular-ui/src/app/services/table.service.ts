import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { TableState } from '../models/restaurant/TableState';
import { FilterService } from './filter.service';

@Injectable({
  providedIn: 'root',
})
export class TableService {
  public timeSlot$ = new BehaviorSubject<[number, number] | null>(null);
  public restaurantId$ = new BehaviorSubject<number | null>(null);

  constructor(private http: HttpClient, private filterService: FilterService) {
    this.timeSlot$
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe(() => this.getTableStates());
  }

  getTableStates(): Observable<TableState[]> {
    return this.http.get<TableState[]>(
      `${environment.apiUrl}/restaurants/${this.restaurantId$.value}/tables`,
      {
        params: {
          timeSlot: this.timeSlot$.value?.toString() ?? '',
        },
      }
    );
  }
}
