import { Component, Input, OnInit } from '@angular/core';
import { Reservation } from 'src/app/models/restaurant/Reservation';
import { Restaurant } from 'src/app/models/restaurant/Restaurant';
import { ReservationService } from 'src/app/services/reservation.service';

@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.scss'],
})
export class ReservationCardComponent implements OnInit {
  @Input() reservation!: Reservation;

  constructor(private reservationService: ReservationService) {}

  ngOnInit(): void {}

  deleteReservation(reservationId: number): void {
    this.reservationService.cancelReservation(this.reservation.id).subscribe({
      next: (result) => this.reservationService.requestReservation(),
    });
  }

  confirmReservation(reservationId: number): void {
    this.reservationService.confirmReservation(this.reservation.id).subscribe({
      next: (reservation) => this.reservationService.requestReservation(),
    });
  }
}
