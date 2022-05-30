import {Component, Input, OnInit} from '@angular/core';
import * as Leaflet from 'leaflet';
import { Restaurant } from 'src/app/models/Restaurant'
import {RESTAURANTS} from "../../mockdata/Restaurants";


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {
  @Input() restaurants!: Restaurant[];
  private map: any;
  private centroid: Leaflet.LatLngExpression = [48.135125, 11.581981]; //

  private initMap(): void {
    this.map = Leaflet.map('map', {
      center: this.centroid,
      zoom: 12
    });

    const tiles = Leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 10,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });


    var markerIcon = Leaflet.icon({
      iconUrl: 'assets/icons/leaflet_marker_icon.png',
      iconSize:     [40, 60],
      iconAnchor:   [20, 60], // [0,0] = anchoring at top left

    });

    RESTAURANTS.forEach(restaurant => Leaflet.marker(restaurant.position, {icon: markerIcon}).addTo(this.map));
    tiles.addTo(this.map);

  }

  constructor() { }

  ngOnInit(): void {
    this.initMap();
  }
}
