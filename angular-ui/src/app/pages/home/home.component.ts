import { Component, OnInit } from '@angular/core';
import { Restaurant } from 'src/app/models/Restaurant';
import { LatLng } from 'leaflet';
import { MapService } from 'src/app/services/map.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  canPlacePersonMarker: boolean = false;

  currentRestaurant: Restaurant | null = null;

  constructor(private mapService: MapService) {}

  ngOnInit(): void {
    this.mapService
      .getSelectedRestaurant()
      .subscribe((restaurant: Restaurant | null) => {
        this.currentRestaurant = restaurant;
      });

    this.log();
  }

  log() {
    this.mapService
      .getPersonMarkerLocation()
      .subscribe((location: LatLng | null) => {
        console.log('user-marker:', location);
      });

    this.mapService
      .getSelectedRestaurant()
      .subscribe((restaurant: Restaurant | null) => {
        console.log('selected-restaurant:', restaurant);
      });
  }
}
