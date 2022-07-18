import { Component, OnInit } from '@angular/core';
import { map } from 'leaflet';
import { MapService } from 'src/app/services/map.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit {
  constructor(private mapService: MapService) {}

  ngOnInit(): void {
    this.mapService.map = map('map', {
      center: [48.135125, 11.581981], //Munich
      zoom: 12,
    });
  }
}
