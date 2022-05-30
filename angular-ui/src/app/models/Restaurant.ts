import {Location} from "./Location";

export interface Restaurant {
  id: number;
  name: string;

  price_category: number;
  rating: number;
  type: string;

  distance:number;
  location: Location;
  number_of_reviews: number;

  images: string[];
}
