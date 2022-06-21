import { Injectable } from '@angular/core';
import { icon, LatLng, Map, marker, Marker, popup, tileLayer } from 'leaflet';
import { BehaviorSubject } from 'rxjs';
import { Restaurant } from '../models/restaurant/Restaurant';
import { FilterService } from './filter.service';
import { RestaurantService } from './restaurant.service';

@Injectable({
  providedIn: 'root',
})
export class MapService {
  private _map!: Map;
  private markers: Marker<any>[] = [];

  public selectedRestaurant$ = new BehaviorSubject<Restaurant | null>(null);

  constructor(
    private restaurantService: RestaurantService,
    private filterService: FilterService
  ) {}

  get selectedRestaurant() {
    return this.selectedRestaurant$.getValue();
  }

  get map(): Map {
    return this._map;
  }

  set map(map: Map) {
    this._map = map;

    this.map.on('click', (e: any) => {
      this.selectedRestaurant$.next(null);
    });

    this.initMap();
    this.prepareMovingUserMarker();

    this.restaurantService.restaurants$.subscribe((restaurants) => {
      this.markers.forEach((marker: any) => {
        this.map.removeLayer(marker);
      });
      this.markers = [];

      this.prepareRestaurantBehaiviour(restaurants);
    });
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

  private prepareRestaurantBehaiviour(restaurants: Restaurant[]) {
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
          <img style="width:280px" src=${restaurant.pictures[0]}</img>`
        );

      restaurantMarker.addTo(this.map);
      restaurantPopup.openOn(this.map);
      restaurantMarker.bindPopup(restaurantPopup).closePopup();

      this.markers.push(restaurantMarker);

      //select Restaurant
      restaurantMarker.on('click', () => {
        this.selectAndFlyToRestaurant(restaurant, 0);
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

  prepareMovingUserMarker() {
    this.map.on('click', (e: any) => {
      if (this.filterService.canPlaceUserMarker) {
        const oldMarker = this.filterService.personMarker$.getValue();
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

          this.filterService.personMarker$.next(newMarker);
        });

        this.filterService.personMarker$.next(newMarker);
        this.filterService.canPlaceUserMarker = false;

        newMarker.addTo(this.map);
      }
    });
  }

  public selectAndFlyToRestaurant(
    restaurant: Restaurant,
    durationSeconds: number
  ) {
    this.map.flyTo(
      new LatLng(restaurant.location.latitude, restaurant.location.longitude),
      15,
      {
        animate: true,
        duration: durationSeconds,
      }
    );

    if (durationSeconds > 0) {
      this.selectedRestaurant$.next(null);
    }

    setTimeout(() => {
      this.selectedRestaurant$.next(restaurant);
    }, durationSeconds * 1000);
  }
}
