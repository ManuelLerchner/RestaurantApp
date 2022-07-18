import { Component, OnInit } from '@angular/core';
import { Reservation } from 'src/app/models/restaurant/Reservation';
import { AccountService } from 'src/app/services/account.service';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-my-reservations',
  templateUrl: './my-reservations.component.html',
  styleUrls: ['./my-reservations.component.scss'],
})
export class MyReservationsComponent implements OnInit {
  constructor(public reservationsService: ReservationService) {
    reservationsService.requestReservation();
  }

  ngOnInit(): void {}

  getReservations() {}
}
