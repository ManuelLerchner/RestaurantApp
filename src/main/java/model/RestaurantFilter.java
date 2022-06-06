package model;

import model.restaurant.PriceCategory;
import model.restaurant.RestaurantType;
import model.util.DateTimeSlot;
import model.util.Location;

public record RestaurantFilter(int number, int minRating, RestaurantType restaurantType, PriceCategory priceCategory,
                               Location location, int maxDistance, DateTimeSlot desiredTimeSlot) {
}
