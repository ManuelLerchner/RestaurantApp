import { Component, Input, OnInit } from '@angular/core';
import { Reservation } from 'src/app/models/restaurant/Reservation';
import { Restaurant } from 'src/app/models/restaurant/Restaurant';

@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.scss'],
})
export class ReservationCardComponent implements OnInit {
  @Input() reservation!: Reservation;

  constructor() {}

  ngOnInit(): void {}

  deleteReservation(reservationId: number): void {
    console.log("button deleteReservation clicked reservationId:"+reservationId);
  }

  confirmReservation(reservationId: number): void {
    console.log('button confirmReservation clicked reservationId:'+reservationId);
  }
}
