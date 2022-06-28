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
  private selectedMarker: Marker<any> | null = null;
  public selectedRestaurant$ = new BehaviorSubject<Restaurant | null>(null);

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
  ) {
    this.selectedRestaurant$.subscribe(console.log);
  }

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
        icon: this.normalIcon,
      });

      const restaurantPopup = popup({
        offset: [0, -40],
        autoPan: false,
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

    newMarker.on('drag', (event) => {
      var marker = event.target;
      var position = marker.getLatLng();

      marker.setLatLng(position, {
        draggable: 'true',
      });

      this.filterService.personMarker$.next(newMarker);
    });

    this.filterService.personMarker$.next(newMarker);

    newMarker.addTo(this.map);
  }

  updateIcons(restaurant: Restaurant) {
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
    restaurant: Restaurant,
    durationSeconds: number
  ) {
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
      this.selectedRestaurant$.next(null);
    }

    this.updateIcons(restaurant);

    setTimeout(() => {
      if (restaurant) {
        this.selectedRestaurant$.next(restaurant);
      }
    }, durationSeconds * 1000);
  }
}
