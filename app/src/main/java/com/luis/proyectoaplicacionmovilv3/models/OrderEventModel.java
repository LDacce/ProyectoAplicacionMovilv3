package com.luis.proyectoaplicacionmovilv3.models;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

public class OrderEventModel {
    private Date createDate;
    private Date updateDate;
    private String id;
    private String observations;
    private String mainImageURL;
    private String referenceImageURL;
    private String longitude;
    private String latitude;
    private EventModel event;
    private UserModel user;

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date value) { this.createDate = value; }

    public Date getUpdateDate() { return updateDate; }
    public void setUpdateDate(Date value) { this.updateDate = value; }

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getObservations() { return observations; }
    public void setObservations(String value) { this.observations = value; }

    public String getMainImageURL() { return mainImageURL; }
    public void setMainImageURL(String value) { this.mainImageURL = value; }

    public String getReferenceImageURL() { return referenceImageURL; }
    public void setReferenceImageURL(String value) { this.referenceImageURL = value; }

    public String getLongitude() { return longitude; }
    public void setLongitude(String value) { this.longitude = value; }

    public String getLatitude() { return latitude; }
    public void setLatitude(String value) { this.latitude = value; }

    public EventModel getEvent() { return event; }
    public void setEvent(EventModel value) { this.event = value; }

    public UserModel getUser() { return user; }
    public void setUser(UserModel value) { this.user = value; }

    public OrderEventModel(String id, String observations, String mainImageURL, String referenceImageURL,
                           String longitude, String latitude, EventModel event, UserModel user,
                           Date createDate, Date updateDate) {
        this.id = id;
        this.observations = observations;
        this.mainImageURL = mainImageURL;
        this.referenceImageURL = referenceImageURL;
        this.longitude = longitude;
        this.latitude = latitude;
        this.event = event;
        this.user = user;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
