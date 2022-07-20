import { RestaurantTable } from './RestaurantTable';
import { DateTimeSlot } from '../time/DateTimeSlot';
import { User } from '../User';
import { Restaurant } from './Restaurant';

export interface Reservation {
  id: number;
  user: User;
  dateTimeSlot: DateTimeSlot;
  restaurantTable: RestaurantTable;
  restaurant: Restaurant;
  confirmed: boolean;
}
