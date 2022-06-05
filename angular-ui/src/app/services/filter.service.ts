import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FilterService {
  public canPlaceUserMarker: boolean = false;

  constructor() {}
}
