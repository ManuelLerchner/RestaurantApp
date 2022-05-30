import {Restaurant} from '../models/Restaurant';
import * as Leaflet from "leaflet";
import {LatLng} from "leaflet";

function randomPosition(latLng?: LatLng): LatLng {
  if (latLng == null) {
    return new Leaflet.LatLng(48.135125 + (Math.random() - .5) / 10, 11.581981 + (Math.random() - .5) / 10); // default: munich center
  } else {
    return new Leaflet.LatLng(latLng.lat + (Math.random() - .5) / 10, latLng.lng + (Math.random() - .5) / 10);
  }
}

export const RESTAURANTS: Restaurant[] = [
  {
    id: 1,
    name: 'Alla Mafia',
    price_category: 1,
    rating: 1,
    type: 'italienisch',
    number_of_reviews: 1,
    distance: 1,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
      'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Leuchtturm_Pellworm_%28Hochformat%29.jpg/640px-Leuchtturm_Pellworm_%28Hochformat%29.jpg'
    ],
  },
  {
    id: 2,
    name: 'El toro',
    price_category: 2,
    rating: 2,
    type: 'spanisch',
    number_of_reviews: 2,
    distance: 2,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
    ],
  },
  {
    id: 3,
    name: 'Pizza Hut',
    price_category: 3,
    rating: 3,
    type: 'pizza',
    number_of_reviews: 3,
    distance: 3,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
    ],
  },
  {
    id: 4,
    name: 'KFC',
    price_category: 4,
    rating: 4,
    type: 'asiatisch',
    number_of_reviews: 3,
    distance: 4,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
    ],
  },
  {
    id: 5,
    name: 'McDonalds',
    price_category: 3,
    rating: 5,
    type: 'asiatisch',
    number_of_reviews: 5,
    distance: 5,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
    ],
  },
  {
    id: 6,
    name: 'Burger King',
    price_category: 2,
    rating: 3,
    type: 'asiatisch',
    number_of_reviews: 6,
    distance: 6,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
    ],
  },
  {
    id: 7,
    name: 'Pizza Hut',
    price_category: 5,
    rating: 4,
    type: 'pizza',
    number_of_reviews: 7,
    distance: 7,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
    ],
  },
  {
    id: 8,
    name: 'KFC',
    price_category: 2,
    rating: 2,
    type: 'asiatisch',
    number_of_reviews: 8,
    distance: 8,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
    ],
  },
  {
    id: 9,
    name: 'McDonalds',
    price_category: 3,
    rating: 1,
    type: 'asiatisch',
    number_of_reviews: 9,
    distance: 9,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
    ],
  },
  {
    id: 10,
    name: 'Burger King',
    price_category: 4,
    rating: 5,
    type: 'asiatisch',
    number_of_reviews: 10,
    distance: 10,
    position: randomPosition(),
    images: [
      'https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123',
      'https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257',
      'https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg',
    ],
  },
];
