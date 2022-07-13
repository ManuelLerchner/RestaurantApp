import { Component, OnInit } from '@angular/core';
import { RestaurantFull } from 'src/app/models/restaurant/Restaurant';

@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.scss'],
})
export class ReservationCardComponent implements OnInit {
  reservation: any = {
    id: 2132,
    name: 'Example Restaurant',
    address: 'münchner freiheit 123',
    image: 'https://via.placeholder.com/150',
    date: '2020-01-01',
    personCount: 5,
  };

  constructor() {}

  ngOnInit(): void {}

  deleteReservation(reservationId: number): void {}

  confirmReservation(reservationId: number): void {}
}
