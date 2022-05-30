import {LatLngExpression} from "leaflet";

export interface Restaurant {
  id: number;
  name: string;

  price_category: number;
  rating: number;
  type: string;

  distance:number;
  position: LatLngExpression;
  number_of_reviews: number;

  images: string[];
}
