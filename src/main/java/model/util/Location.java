package model.util;

public class Location {

    private double longitude;
    private double latitude;
    private String streetName;
    private int houseNumber;
    private String town;
    private int postalCode;

    public Location() {
        this.longitude = 0;
        this.latitude = 0;
    }

    public Location(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getDistanceTo(Location otherLocation) {
        double earthRadiusKm = 6371;

        double dLat = degreesToRadians(otherLocation.getLatitude() - this.latitude);
        double dLon = degreesToRadians(otherLocation.getLongitude() - this.longitude);

        double lat1 = degreesToRadians(this.latitude);
        double lat2 = degreesToRadians(otherLocation.getLatitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (double) Math.round(earthRadiusKm * c * 100.0)/100.0;
    }


    private double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getTown() {
        return town;
    }

    public int getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                ", town='" + town + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }

    public static void main(String[] args) {
        Location loc1 = new Location(48.1403436, 11.5674647);
        Location loc2 = new Location (48.1391615, 11.5734747);
        System.out.println(loc1.getDistanceTo(loc2));
    }
}
