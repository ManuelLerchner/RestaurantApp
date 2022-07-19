import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Reservation } from '../models/restaurant/Reservation';
import { User } from '../models/User';
import { AccountService } from './account.service';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  public reservations$ = new BehaviorSubject<Reservation[]>([]);
  constructor(
    private http: HttpClient,
    private accountService: AccountService
  ) {}

  public requestReservation() {
    this.accountService.user$.subscribe(async (user: User) => {
      await this.http
        .get<Reservation[]>(`${environment.apiUrl}/getReservations`, {
          params: {
            authToken: user.authToken,
          },
        })
        .pipe(
          map((reservations: Reservation[]) => {
            this.reservations$.next(reservations);
            return reservations;
          })
        )
        .toPromise();
    });
  }
}
