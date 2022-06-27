package application.model.util;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class Location {

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "town_name")
    private String townName;

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public double getDistanceTo(Location otherLocation) {
        if (otherLocation == null || this.longitude == null || this.latitude == null || otherLocation.getLatitude() == null || otherLocation.getLongitude() == null) {
            return Double.MAX_VALUE;
        }

        double earthRadiusKm = 6371;

        double dLat = degreesToRadians(otherLocation.getLatitude() - this.latitude);
        double dLon = degreesToRadians(otherLocation.getLongitude() - this.longitude);

        double lat1 = degreesToRadians(this.latitude);
        double lat2 = degreesToRadians(otherLocation.getLatitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (double) Math.round(earthRadiusKm * c * 100.0) / 100.0;
    }


    private double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }


}