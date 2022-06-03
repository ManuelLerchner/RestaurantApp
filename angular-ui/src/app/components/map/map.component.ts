import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import * as Leaflet from 'leaflet';
import { Restaurant } from 'src/app/models/Restaurant';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit {
  @Input() restaurants!: Restaurant[];
  @Input() canPlacePersonMarker!: boolean;

  @Output() selectedRestaurantEvent = new EventEmitter<Restaurant | null>();
  @Output() personMarkerLocationEvent = new EventEmitter<Leaflet.LatLng>();

  private map: any;
  private centroid: Leaflet.LatLngExpression = [48.135125, 11.581981]; //

  private initMap(): void {
    this.map = Leaflet.map('map', {
      center: this.centroid,
      zoom: 12,
    });

    this.map.on('click', (e: any) => {
      this.selectedRestaurantEvent.emit(null);
    });

    const tiles = Leaflet.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 18,
        minZoom: 10,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );

    var markerIcon = Leaflet.icon({
      iconUrl: 'assets/icons/leaflet_marker_icon.png',
      iconSize: [40, 60],
      iconAnchor: [20, 60], // [0,0] = anchoring at top left
    });

    this.restaurants.forEach((restaurant) => {
      let marker = Leaflet.marker(
        new Leaflet.LatLng(
          restaurant.location.latitude,
          restaurant.location.longitude
        ),
        {
          icon: markerIcon,
        }
      ).addTo(this.map);

      let popup = Leaflet.popup({
        offset: [0, -40],
      })
        .setLatLng(
          Leaflet.latLng(
            restaurant.location.latitude,
            restaurant.location.longitude
          )
        )
        .setContent(
          `<h2>${restaurant.name}</h2>
          <img style="width:280px" src=${restaurant.images[0]}</img>`
        )
        .openOn(this.map);

      marker.bindPopup(popup).closePopup();

      marker.on('click', () => {
        this.selectedRestaurantEvent.emit(restaurant);
      });

      let timer: any;
      marker.on('mouseover', () => {
        timer = setTimeout(() => {
          marker.openPopup();
        }, 500);
      });

      marker.on('mouseout', () => {
        if (timer) {
          clearTimeout(timer);
          marker.closePopup();
        }
      });
    });

    tiles.addTo(this.map);
  }

  constructor() {}

  ngOnInit(): void {
    this.initMap();
    this.prepareUserMarker();
  }

  prepareUserMarker() {
    var userIcon = Leaflet.icon({
      iconUrl: 'assets/icons/leaflet_marker_person.png',
      iconSize: [25, 40],
      iconAnchor: [20, 60],
    });

    let marker: any;
    this.map.on('click', (e: any) => {
      if (this.canPlacePersonMarker) {
        if (marker) this.map.removeLayer(marker);

        this.personMarkerLocationEvent.emit(e.latlng);

        marker = Leaflet.marker(e.latlng, {
          icon: userIcon,
        }).addTo(this.map);
      }
    });
  }
}
