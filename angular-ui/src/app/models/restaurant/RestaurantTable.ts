import { Reservation } from './Reservation';
import { Restaurant } from './Restaurant';

export interface RestaurantTable {
  id: number;

  tableNumber: number;
  capacity: number;

  restaurant: Restaurant;

  reservations: Array<Reservation>;
}
