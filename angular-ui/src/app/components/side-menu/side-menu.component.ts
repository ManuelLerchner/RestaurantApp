import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Restaurant } from 'src/app/models/Restaurant';
import { MapService } from 'src/app/services/map.service';
import { RestaurantService } from 'src/app/services/restaurant.service';
import { of } from 'rxjs';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss'],
})
export class SideMenuComponent implements OnInit {
  restaurants!: Restaurant[];
  sideMenuExpanded: boolean = true;

  constructor(
    private restaurantService: RestaurantService,
    private mapService: MapService
  ) {}

  ngOnInit(): void {
    this.restaurants = this.restaurantService.getRestaurants();
  }

  selectRestaurant(restaurant: Restaurant): void {
    this.mapService.flyTo(restaurant, 1.5);
  }

  isSelectedRestaurant(currentRestaurant: Restaurant) {
    let selected = false;
    this.mapService.selectedRestaurant.subscribe(
      (restaurant: Restaurant | null) => {
        selected = restaurant === currentRestaurant;
      }
    );
    return selected;
  }
}
