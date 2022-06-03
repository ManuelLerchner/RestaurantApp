import { Component, OnInit } from '@angular/core';
import { Restaurant } from 'src/app/models/Restaurant';
import { RESTAURANTS } from './../../mockdata/Restaurants';
import { LatLng } from 'leaflet';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  restaurants: Restaurant[];
  canPlacePersonMarker: boolean = false;

  currentRestaurant: Restaurant | null = null;
  personMarkerLocation: LatLng | null = null;

  constructor() {
    this.restaurants = RESTAURANTS;
  }

  ngOnInit(): void {}

  selectedRestaurantEvent(restaurant: Restaurant | null): void {
    this.currentRestaurant = restaurant;
  }

  personMarkerLocationEvent(canPlacePersonMarker: LatLng): void {
    this.canPlacePersonMarker = false;
    this.personMarkerLocation = canPlacePersonMarker;
    console.log(this.personMarkerLocation);
  }

  handleCanPlaceLocationMarkerEvent(canPlaceLocationMarker: boolean): void {
    this.canPlacePersonMarker = canPlaceLocationMarker;
  }
}
