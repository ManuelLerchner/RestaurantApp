import { Reservation } from './Reservation';
import { RestaurantFull } from './Restaurant';

export interface RestaurantTable {
  id: number;

  tableNumber: number;
  capacity: number;

  restaurant: RestaurantFull;

  reservations: Array<Reservation>;
}
