import { Component, Input, OnInit } from '@angular/core';
import { map } from 'leaflet';

import { Restaurant } from 'src/app/models/Restaurant';
import { MapService } from 'src/app/services/map.service';
import { RestaurantService } from 'src/app/services/restaurant.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit {
  private restaurants!: Restaurant[];
  @Input() canPlacePersonMarker!: boolean;

  constructor(
    private mapService: MapService,
    private restaurantService: RestaurantService
  ) {}

  ngOnInit(): void {
    this.mapService.map = map('map', {
      center: [48.135125, 11.581981], //Munich
      zoom: 12,
    });

    this.restaurants = this.restaurantService.getRestaurants();
  }
}
