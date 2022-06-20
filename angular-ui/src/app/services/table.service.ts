import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { TableState } from '../models/TableState';
import { FilterService } from './filter.service';

@Injectable({
  providedIn: 'root',
})
export class TableService {
  public timeSlotObserver$ = new BehaviorSubject<[number, number] | null>(null);
  public restaurantId$ = new BehaviorSubject<string | null>(null);

  constructor(private http: HttpClient, private filterService: FilterService) {
    this.timeSlotObserver$
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe(() => this.getTableStates());
  }

  getTableStates(): Observable<TableState[]> {
    return this.http.get<TableState[]>(
      `${environment.apiUrl}/restaurants/${this.restaurantId$.value}/tables`,
      {
        params: {
          timeSlot: this.timeSlotObserver$.value?.toString() ?? '',
        },
      }
    );
  }
}
