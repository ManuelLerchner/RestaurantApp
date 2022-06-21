import { Location } from '../Location';
import { RestaurantTable } from './RestaurantTable';
import { PriceCategory } from '../types/PriceCategory';
import { RestaurantType } from '../types/RestaurantType';
import { WeekTimeSlot } from '../time/WeekTimeSlot';

export interface Restaurant {
  id: number;

  name: string;
  restaurantType: RestaurantType;
  priceCategory: PriceCategory;

  averageRating: number;
  linkToWebsite: string;
  distanceToUser: number;

  location: Location;

  layoutId: number;

  comments: Array<Comment>;
  openingTimes: Array<WeekTimeSlot>;
  pictures: Array<String>;
  restaurantTables: Array<RestaurantTable>;
}
