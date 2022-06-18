const express = require("express");
const router = express.Router();
const db = require("../db");

router.get("/", (req, res) => {
  let restaurant_type = req.query.restaurant_type;
  let price_category = req.query.price_category;
  let min_rating = req.query.min_rating;
  let max_distance = req.query.max_distance;
  let time_slot = req.query.time_slot;
  let date = req.query.date;
  let person_count = req.query.person_count;

  console.log(req.query);

  let filteredRestaurants = db.restaurants.filter((restaurant) => {
    return (
      (restaurant_type === undefined ||
        restaurant_type.toLowerCase() === restaurant.type.toLowerCase()) &&
      (price_category === undefined || parseInt(price_category) === restaurant.price_category) &&
      (min_rating === undefined || restaurant.rating >= min_rating) &&
      (max_distance === undefined || restaurant.distance <= max_distance)
    );
  });

  res.json(filteredRestaurants);
});

module.exports = router;
