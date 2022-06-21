import { RestaurantTable } from './RestaurantTable';
import { DateTimeSlot } from '../time/DateTimeSlot';
import { User } from '../User';

export interface Reservation {
  id: number;
  user: User;
  dateTimeSlot: DateTimeSlot;
  restaurantTable: RestaurantTable;
}
