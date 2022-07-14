import { Component, OnInit } from '@angular/core';
import { MapRestaurant } from 'src/app/models/restaurant/MapRestaurant';
import { FilterService } from 'src/app/services/filter.service';
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
    public mapService: MapService,
    public filterService: FilterService
  ) {}

  ngOnInit(): void {}

  selectRestaurant(restaurant: MapRestaurant): void {
    this.mapService.selectAndFlyToRestaurant(restaurant, 1.5);
  }

  ngOnDestroy(): void {
    if (this.subsciption) {
      this.subsciption.unsubscribe();
    }
  }
}
