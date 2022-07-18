import { Location } from '../Location';
import { RestaurantTable } from './RestaurantTable';
import { PriceCategory } from '../types/PriceCategory';
import { RestaurantType } from '../types/RestaurantType';
import { WeekTimeSlot } from '../time/WeekTimeSlot';
import { Comment } from './Comment';

export interface Restaurant {
  id: number;

  name: string;
  restaurantType: RestaurantType;
  priceCategory: PriceCategory;

  averageRating: number;
  linkToWebsite: string;
  distanceToUser: number;
  commentCount: number;

  location: Location;

  layoutId: number;

  comments: Array<Comment>;
  openingTimes: Array<WeekTimeSlot>;
  pictures: Array<String>;
  restaurantTables: Array<RestaurantTable>;
}
