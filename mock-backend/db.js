const users = [
  { username: "Manuel", email: "manuel@gmail.com", password: "123", authToken: "secret" },
  { username: "Julian", email: "julian@gmail.com", password: "123", authToken: "secret" },
];

function randomLocation() {
  return {
    latitude: 48.135125 + (Math.random() - 0.5) / 10,
    longitude: 11.581981 + (Math.random() - 0.5) / 10,
  }; // default: munich center
}

const RESTAURANTS = [
  {
    id: 1,
    name: "Test Restaurant",
    price_category: 1,
    rating: 1,
    type: "italian",
    number_of_reviews: 1,
    distance: 1,
    location: randomLocation(),
    images: [
      "https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123",
      "https://www.collinsdictionary.com/images/full/restaurant_135621509_1000.jpg?version=4.0.257",
      "https://media0.faz.net/ppmedia/aktuell/rhein-main/1358939109/1.7957160/width610x580/leichte-vorspeise-eine.jpg",
      "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Leuchtturm_Pellworm_%28Hochformat%29.jpg/640px-Leuchtturm_Pellworm_%28Hochformat%29.jpg",
    ],
  },
  {
    id: 2,
    name: "Test Restaurant 2",
    price_category: 2,
    rating: 2,
    type: "chinese",
    number_of_reviews: 2,
    distance: 2,
    location: randomLocation(),
    images: [
      "https://images.adsttc.com/media/images/5e4c/1025/6ee6/7e0b/9d00/0877/slideshow/feature_-_Main_hall_1.jpg?1582043123",
    ],
  },
];

module.exports = {
  users: users,
  restaurants: RESTAURANTS,
};
