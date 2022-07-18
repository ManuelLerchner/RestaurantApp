import { Location } from '../Location';
import { RestaurantTable } from './RestaurantTable';
import { PriceCategory } from '../types/PriceCategory';
import { RestaurantType } from '../types/RestaurantType';
import { WeekTimeSlot } from '../time/WeekTimeSlot';
import { Comment } from './Comment';

export interface MapRestaurant {
  id: number;

  name: string;
  restaurantType: RestaurantType;
  priceCategory: PriceCategory;

  averageRating: number;

  distanceToUser: number;
  commentCount: number;

  location: Location;
}
