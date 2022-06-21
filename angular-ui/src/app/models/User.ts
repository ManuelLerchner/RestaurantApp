import { Reservation } from './restaurant/Reservation';

export interface User {
  id: number;
  name: string;
  email: string;
  hashedPassword: string;
  authToken: string;
  comments: Array<Comment>;
  reservations: Array<Reservation>;
}
