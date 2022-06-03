import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Restaurant } from 'src/app/models/Restaurant';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss'],
})
export class SideMenuComponent implements OnInit {
  @Input() restaurants!: Restaurant[];
  @Input() canPlacePersonMarker!: boolean;
  @Output() canPlaceLocationMarkerEvent = new EventEmitter<boolean>();

  sideMenuExpanded: boolean = true;

  constructor() {}

  ngOnInit(): void {}
}
