import { Injectable } from '@angular/core';
import {
  icon,
  LatLng,
  Map,
  marker,
  Marker,
  popup,
  tileLayer,
  circle,
} from 'leaflet';
import { BehaviorSubject } from 'rxjs';
import { RestaurantSmall } from '../models/restaurant/MapRestaurant';
import { FilterService } from './filter.service';
import { RestaurantService } from './restaurant.service';

@Injectable({
  providedIn: 'root',
})
export class MapService {
  private _map!: Map;
  private markers: Marker<any>[] = [];
  private selectedMarker: Marker<any> | null = null;

  private normalIcon = icon({
    iconUrl: 'assets/icons/leaflet_marker_icon.png',
    shadowUrl: 'assets/icons/leaflet_marker_shadow.png',
    shadowSize: [20, 45],
    shadowAnchor: [1, 43],
    iconSize: [30, 45],
    iconAnchor: [15, 45],
  });

  private selectedIcon = icon({
    iconUrl: 'assets/icons/leaflet_marker_icon_selected.png',
    shadowUrl: 'assets/icons/leaflet_marker_shadow.png',
    shadowSize: [20, 45],
    shadowAnchor: [1, 43],
    iconSize: [30, 45],
    iconAnchor: [15, 45],
  });

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
      this.restaurantService.showRestaurant = false;
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

  private prepareRestaurantBehaiviour(restaurants: RestaurantSmall[]) {
    for (let restaurant of restaurants) {
      const restaurantPosition = new LatLng(
        restaurant.location.latitude,
        restaurant.location.longitude
      );

      const restaurantMarker = marker(restaurantPosition, {
        icon: this.normalIcon,
      });

      const restaurantPopup = popup({
        offset: [0, -40],
        autoPan: false,
      })
        .setLatLng(restaurantPosition)
        .setContent(`<h2>${restaurant.name}</h2>`);

      restaurantMarker.addTo(this.map);
      restaurantPopup.openOn(this.map);
      restaurantMarker.bindPopup(restaurantPopup).closePopup();

      this.markers.push(restaurantMarker);

      //select Restaurant
      restaurantMarker.on('click', () => {
        setTimeout(() => {
          this.selectAndFlyToRestaurant(restaurant, 1);
        }, 10);
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
    const storedMarker = this.filterService.personMarker$.getValue();

    let newMarker = marker(storedMarker.getLatLng(), {
      icon: icon({
        iconUrl: 'assets/icons/leaflet_marker_person.png',
        shadowUrl: 'assets/icons/leaflet_marker_person_shadow.png',
        iconSize: [20, 35],
        shadowSize: [22, 35],
        iconAnchor: [10, 35],
        shadowAnchor: [0, 35],
      }),
      draggable: true,
      autoPan: true,
    });

    let restaurantRadius = circle(
      storedMarker.getLatLng(),
      (this.filterService.maxDistance$.getValue() ?? 0.0) * 1000,
      {
        color: '#444',
        fillColor: '#888',
        fillOpacity: 0.08,
        weight: 2,
      }
    );

    newMarker.on('drag', (event) => {
      var marker = event.target;
      var position = marker.getLatLng();

      marker.setLatLng(position, {
        draggable: 'true',
      });

      this.filterService.personMarker$.next(newMarker);
      restaurantRadius.setLatLng(position);
    });

    this.filterService.personMarker$.next(newMarker);

    this.filterService.maxDistance$.subscribe((maxDistance) => {
      restaurantRadius.setRadius((maxDistance ?? 0.0) * 1000);
    });

    restaurantRadius.addTo(this.map);
    newMarker.addTo(this.map);
  }

  updateIcons(restaurant: RestaurantSmall) {
    if (restaurant) {
      this.markers.find((marker: Marker<any>) => {
        if (
          marker.getLatLng().lat == restaurant.location.latitude &&
          marker.getLatLng().lng == restaurant.location.longitude
        ) {
          if (this.selectedMarker) {
            this.selectedMarker.setIcon(this.normalIcon);
          }

          this.selectedMarker = marker;

          this.selectedMarker.setIcon(this.selectedIcon);
        }
      });
    }
  }

  public selectAndFlyToRestaurant(
    restaurant: RestaurantSmall,
    durationSeconds: number
  ) {
    this.restaurantService.loadFullRestaurant(restaurant.id);
    this.map.flyTo(
      new LatLng(restaurant.location.latitude, restaurant.location.longitude),
      16,
      {
        animate: true,
        duration: durationSeconds,
        noMoveStart: true,
      }
    );

    if (durationSeconds > 0) {
      this.restaurantService.showRestaurant = false;
    }

    this.updateIcons(restaurant);

    setTimeout(() => {
      if (restaurant) {
        this.restaurantService.showRestaurant = true;
      }
    }, durationSeconds * 1000);
  }
}
