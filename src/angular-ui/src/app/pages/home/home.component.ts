import { Component, OnInit } from '@angular/core';
import { FilterService } from 'src/app/services/filter.service';
import { RestaurantService } from 'src/app/services/restaurant.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor(
    public restaurantService: RestaurantService,
    public filterService: FilterService
  ) {}

  ngOnInit(): void {}
}
