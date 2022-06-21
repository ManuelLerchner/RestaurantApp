import { User } from '../User';

export interface Comment {
  date: Date;
  headline: string;
  text: string;
  rating: number;
  postedBy: User;
}
