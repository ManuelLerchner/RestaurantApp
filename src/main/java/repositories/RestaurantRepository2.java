package repositories;

import model.RestaurantFilter;
import model.restaurant.*;
import model.util.Location;
import model.util.WeekTimeSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository2 {

    private final String DATABASE_SOURCE = "jdbc:sqlite:src/main/resources/test.db";


    public boolean isExistingId(int restaurantId) {
        try (
                Connection conn = DriverManager.getConnection(DATABASE_SOURCE);
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery("select restaurantID from restaurants WHERE restaurantID=" + restaurantId + ";")
                ) {
            if(rs.next()) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DetailedRestaurant getDetailedRestaurantById(int restaurantId) {
        return null; // TODO
    }

    public void save(DetailedRestaurant restaurant) {
        try (
                Connection conn = DriverManager.getConnection(DATABASE_SOURCE);
                PreparedStatement prepareStatement = conn.prepareStatement("insert into restaurants values (?, ?, ?, ?, ?, ?, ?)")
        ) {
            // ID is auto-generated
            prepareStatement.setString(2, restaurant.getName());
            prepareStatement.setString(3, restaurant.getLinkToWebsite());
            prepareStatement.setDouble(4, restaurant.getAverageRating());
            prepareStatement.setInt(5, Converter.convertPriceCategoryToInt(restaurant.getPriceCategory()));
            prepareStatement.setInt(6, Converter.convertRestaurantTypeToInt(restaurant.getRestaurantType()));
            // TODO Location erstellen und Id einfügen
            int locationId = 42;
            prepareStatement.setInt(7, locationId);

            // TODO pictures, openingTimes, comments auch noch hinzufügen
            prepareStatement.addBatch();

            conn.setAutoCommit(false);
            prepareStatement.executeBatch();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<SimpleRestaurant> filterRestaurant(RestaurantFilter filter) {
        try (
                Connection conn = DriverManager.getConnection(DATABASE_SOURCE);
                Statement stat = conn.createStatement();
                ResultSet rs = stat.executeQuery("SELECT * FROM restaurants WHERE (" + convertToConditionString(filter) +");");
        ) {
            List<SimpleRestaurant> restaurants = new ArrayList<>();
            while (rs.next()) {
                int restaurantID = rs.getInt("restaurantID");
                String name = rs.getString("name");
                String linkToWebsite = rs.getString("linkToWebsite");
                double averageRating = rs.getDouble("averageRating");
                PriceCategory priceCategory = Converter.convertIntToPriceCategory(rs.getInt("priceCategory"));
                RestaurantType restaurantType = Converter.convertIntToRestaurantType(rs.getInt("restaurantType"));
                int locationID = rs.getInt("locationID");
                Statement statement = conn.createStatement();
                ResultSet rsLocation = statement.executeQuery("select * from locations WHERE locationID="+ locationID +";");
                double latitude = rsLocation.getDouble("latitude");
                double longitude = rsLocation.getDouble("longitude");
                rsLocation.close();
                Location location = new Location(latitude, longitude);
                // TODO Entfernung filtern + Zeitslot + Anzahl auf number limitieren -> nur hier möglich, da noch auf Datenbank zugegriffen werden kann und bei der Reduktion auf SimpleRestaurant nicht mehr alle notwendigen Daten verfügbar sind
                SimpleRestaurant restaurant = new SimpleRestaurant(restaurantID, name, linkToWebsite, averageRating, priceCategory, restaurantType, location);
                restaurants.add(restaurant);
            }

            return restaurants;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertToConditionString(RestaurantFilter filter) {
        // TODO nach default Werten filtern und diese Entfernen, evtl. mittels StringBuilder condition String erstellen
        return ("averageRating>=" + filter.minRating()) + " AND " + ("restaurantType=" + Converter.convertRestaurantTypeToInt(filter.restaurantType())) + " AND " + ("priceCategory=" + Converter.convertPriceCategoryToInt(filter.priceCategory()));
    }

    public static void main(String[] args) {
        RestaurantRepository2 restaurantRepository2 = new RestaurantRepository2();
        System.out.println(restaurantRepository2.isExistingId(10));
        // restaurantRepository2.save(new DetailedRestaurant(0, "SomeName", "SomeLink", 4.2, PriceCategory.COSTLY, RestaurantType.INDIAN, new Location(0,0), new ArrayList<>(), new WeekTimeSlot[7], new ArrayList<>()));
        RestaurantFilter filter = new RestaurantFilter(50, 4, RestaurantType.ITALIAN, PriceCategory.CHEAP, null, 1000, null);
        List<SimpleRestaurant> list = restaurantRepository2.filterRestaurant(filter);
        for (SimpleRestaurant restaurant : list) {
            System.out.println(restaurant);
        }
    }
}
