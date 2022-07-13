import { Component, Input, OnInit } from '@angular/core';
import { MapRestaurant } from 'src/app/models/restaurant/MapRestaurant';
import { PriceCategory } from 'src/app/models/types/PriceCategory';
import { RestaurantType } from 'src/app/models/types/RestaurantType';
import { MapService } from 'src/app/services/map.service';

@Component({
  selector: 'app-restaurant-list-card',
  templateUrl: './restaurant-list-card.component.html',
  styleUrls: ['./restaurant-list-card.component.scss'],
})
export class RestaurantListCardComponent implements OnInit {
  @Input() restaurant!: MapRestaurant;
  @Input() isSelected!: boolean;

  constructor(public mapService: MapService) {}

  ngOnInit(): void {}

  getRating(rating: number): string {
    return '★'.repeat(Math.floor(rating));
  }

  getPriceCategory(priceCategory: PriceCategory): string {
    switch (priceCategory) {
      case 'CHEAP':
        return '€';
      case 'NORMAL':
        return '€€';
      case 'COSTLY':
        return '€€€';
      default:
        return '-';
    }
  }

  getRestaurantType(restaurantType: RestaurantType): string {
    return restaurantType.toLowerCase();
  }
}
