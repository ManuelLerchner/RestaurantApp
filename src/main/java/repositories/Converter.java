package repositories;

import model.restaurant.PriceCategory;
import model.restaurant.RestaurantType;

public class Converter {

    public static PriceCategory convertIntToPriceCategory(int number) {
        return switch (number) {
            case 1 -> PriceCategory.CHEAP;
            case 2 -> PriceCategory.NORMAL;
            case 3 -> PriceCategory.COSTLY;
            default -> PriceCategory.DEFAULT;
        };
    }

    public static RestaurantType convertIntToRestaurantType(int number) {
        return switch (number) {
            case 1 -> RestaurantType.ITALIAN;
            case 2 -> RestaurantType.INDIAN;
            case 3 -> RestaurantType.CHINESE;
            default -> RestaurantType.DEFAULT;
        };
    }

    public static int convertRestaurantTypeToInt(RestaurantType restaurantType) {
        return switch (restaurantType) {
            case ITALIAN -> 1;
            case INDIAN -> 2;
            case CHINESE -> 3;
            default -> 0;
        };
    }

    public static int convertPriceCategoryToInt(PriceCategory priceCategory) {
        return switch (priceCategory) {
            case CHEAP -> 1;
            case NORMAL -> 2;
            case COSTLY -> 3;
            case DEFAULT -> 0;
        };
    }
}
