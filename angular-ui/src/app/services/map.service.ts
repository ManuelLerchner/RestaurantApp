import { Injectable } from '@angular/core';
import {
  marker,
  latLng,
  popup,
  tileLayer,
  LatLngExpression,
  Map,
  icon,
  LatLng,
  Marker,
} from 'leaflet';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { Restaurant } from '../models/Restaurant';
import { FilterService } from './filter.service';
import { RestaurantService } from './restaurant.service';

@Injectable({
  providedIn: 'root',
})
export class MapService {
  private _map!: Map;

  private personMarker = new BehaviorSubject<Marker<any> | null>(null);
  private selectedRestaurant = new Subject<Restaurant | null>();

  constructor(
    private restaurantService: RestaurantService,
    private filterService: FilterService
  ) {}

  get map(): Map {
    return this._map;
  }

  set map(map: Map) {
    this._map = map;

    this.map.on('click', (e: any) => {
      this.selectedRestaurant.next(null);
    });

    this.initMap();
    this.prepareRestaurantBehaiviour();
    this.setUserMarker();
  }

  private initMap(): void {
    const tiles = tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 18,
        minZoom: 10,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );

    tiles.addTo(this.map);
  }

  private prepareRestaurantBehaiviour() {
    const restaurants = this.restaurantService.getRestaurants();

    for (let restaurant of restaurants) {
      const restaurantPosition = new LatLng(
        restaurant.location.latitude,
        restaurant.location.longitude
      );

      const restaurantMarker = marker(restaurantPosition, {
        icon: icon({
          iconUrl: 'assets/icons/leaflet_marker_icon.png',
          iconSize: [40, 60],
          iconAnchor: [20, 60],
        }),
      });

      const restaurantPopup = popup({
        offset: [0, -40],
      })
        .setLatLng(restaurantPosition)
        .setContent(
          `<h2>${restaurant.name}</h2>
          <img style="width:280px" src=${restaurant.images[0]}</img>`
        );

      restaurantMarker.addTo(this.map);
      restaurantPopup.openOn(this.map);
      restaurantMarker.bindPopup(restaurantPopup).closePopup();

      //select Restaurant
      restaurantMarker.on('click', () => {
        this.selectedRestaurant.next(restaurant);
      });

      //Hover Preview
      let hoverTimer: any;
      restaurantMarker
        .on('mouseover', () => {
          hoverTimer = setTimeout(() => {
            restaurantMarker.openPopup();
          }, 500);
        })
        .on('mouseout', () => {
          if (hoverTimer) {
            clearTimeout(hoverTimer);
            restaurantMarker.closePopup();
          }
        });
    }
  }

  setUserMarker() {
    this.map.on('click', (e: any) => {
      if (this.filterService.canPlaceUserMarker) {
        const oldMarker = this.personMarker.getValue();
        if (oldMarker) this.map.removeLayer(oldMarker);

        let newMarker = marker(e.latlng, {
          icon: icon({
            iconUrl: 'assets/icons/leaflet_marker_person.png',
            iconSize: [25, 40],
            iconAnchor: [20, 60],
          }),
          draggable: true,
          autoPan: true,
        });

        newMarker.on('drag', (event) => {
          var marker = event.target;
          var position = marker.getLatLng();

          marker.setLatLng(position, {
            draggable: 'true',
          });

          this.personMarker.next(newMarker);
        });

        this.personMarker.next(newMarker);
        this.filterService.canPlaceUserMarker = false;

        newMarker.addTo(this.map);
      }
    });
  }

  public getPersonMarkerLocation(): Observable<LatLng | null> {
    return this.personMarker.pipe(
      map((marker: Marker<any> | null) => {
        if (marker) {
          return marker.getLatLng();
        } else {
          return null;
        }
      })
    );
  }

  public getSelectedRestaurant(): Observable<Restaurant | null> {
    return this.selectedRestaurant.asObservable();
  }
}
