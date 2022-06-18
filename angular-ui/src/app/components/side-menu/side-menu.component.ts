import { Component, OnInit } from '@angular/core';
import { Restaurant } from 'src/app/models/Restaurant';
import { MapService } from 'src/app/services/map.service';
import { RestaurantService } from 'src/app/services/restaurant.service';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss'],
})
export class SideMenuComponent implements OnInit {
  sideMenuExpanded: boolean = true;

  constructor(
    public restaurantService: RestaurantService,
    private mapService: MapService
  ) {}

  ngOnInit(): void {}

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
