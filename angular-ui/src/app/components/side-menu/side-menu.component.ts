import { Component, OnInit } from '@angular/core';
import { Restaurant } from 'src/app/models/restaurant/Restaurant';
import { MapService } from 'src/app/services/map.service';
import { RestaurantService } from 'src/app/services/restaurant.service';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss'],
})
export class SideMenuComponent implements OnInit {
  sideMenuExpanded: boolean = true;
  subsciption: any;

  constructor(
    public restaurantService: RestaurantService,
    public mapService: MapService
  ) {}

  ngOnInit(): void {}

  selectRestaurant(restaurant: Restaurant): void {
    this.mapService.selectAndFlyToRestaurant(restaurant, 1.5);
  }

  ngOnDestroy(): void {
    this.subsciption.unsubscribe();
  }
}
