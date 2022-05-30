import {Component, Input, OnInit} from '@angular/core';
import * as Leaflet from 'leaflet';
import { Restaurant } from 'src/app/models/Restaurant'
import {RESTAURANTS} from "../../mockdata/Restaurants";
import {LatLng} from "leaflet";


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




    RESTAURANTS.forEach(restaurant => {
      const marker = Leaflet.marker(
        new LatLng(restaurant.location.latitude, restaurant.location.longitude),
        {icon: new Leaflet.DivIcon(
            {
                className: 'marker-restaurant-' + restaurant.id,
                //iconSize:     [40, 60],
                iconAnchor:   [20, 60], // [0,0] = anchoring at top left
                html:
                  '<img ' +
                  'class=markerIcon '+
                  'style="height:60px; width: 40px" ' +
                  'src="assets/icons/leaflet_marker_icon.png" ' +
                  'alt="Marker"/>' +
                '<span ' +
                  'class=markerIcon.subLabel ' +
                  'style="' +
                  'position: absolute; ' +
                  'width: fit-content; ' +
                  'height: fit-content; ' +
                  'font-size: 12pt; ' +
                  'left: 160%; ' +
                  'transform: translate(-50%, -90%); ' +
                  'line-height: 13pt; ' +
                  'text-align: center;' +
                  'text-shadow: .5px .5px 0  #212529"' +
                  '>' +
                  restaurant.name +
                  '</span>'
            }),
              title: restaurant.name
        });
      marker.addTo(this.map);
    });

    tiles.addTo(this.map);

  }

  constructor() { }

  ngOnInit(): void {
    this.initMap();
  }
}
