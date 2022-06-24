import { Component, OnInit } from '@angular/core';
import { FilterService } from 'src/app/services/filter.service';
import { MapService } from 'src/app/services/map.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor(
    public mapService: MapService,
    public filterService: FilterService
  ) {}

  ngOnInit(): void {}
}
