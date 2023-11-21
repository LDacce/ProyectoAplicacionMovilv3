package com.luis.proyectoaplicacionmovilv3.dtos;
import java.util.UUID;

public class CreateOrderEventDto {
    private int eventId;
    private String userId;
    private String orderId;
    private String observations;
    private String mainImageURL;
    private String referenceImageURL;
    private String longitude;
    private String latitude;

    public CreateOrderEventDto(int eventId, String userId, String orderId, String observations, String mainImageURL, String referenceImageURL, String longitude, String latitude) {
        this.eventId = eventId;
        this.userId = userId;
        this.orderId = orderId;
        this.observations = observations;
        this.mainImageURL = mainImageURL;
        this.referenceImageURL = referenceImageURL;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getMainImageURL() {
        return mainImageURL;
    }

    public void setMainImageURL(String mainImageURL) {
        this.mainImageURL = mainImageURL;
    }

    public String getReferenceImageURL() {
        return referenceImageURL;
    }

    public void setReferenceImageURL(String referenceImageURL) {
        this.referenceImageURL = referenceImageURL;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
