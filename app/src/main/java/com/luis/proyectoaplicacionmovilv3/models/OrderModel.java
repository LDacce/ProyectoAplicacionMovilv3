package com.luis.proyectoaplicacionmovilv3.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public class OrderModel {
    private OffsetDateTime createDate;
    private Object updateDate;
    private UUID id;
    private String orderNumber;
    private long pieces;
    private String consultantCode;
    private String consultantName;
    private String consultantPhone;
    private String address;
    private String latitude;
    private String longitude;
    private CompanyModel company;
    private OrderEventModel[] orderevents;

    public OffsetDateTime getCreateDate() { return createDate; }
    public void setCreateDate(OffsetDateTime value) { this.createDate = value; }

    public Object getUpdateDate() { return updateDate; }
    public void setUpdateDate(Object value) { this.updateDate = value; }

    public UUID getID() { return id; }
    public void setID(UUID value) { this.id = value; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String value) { this.orderNumber = value; }

    public long getPieces() { return pieces; }
    public void setPieces(long value) { this.pieces = value; }

    public String getConsultantCode() { return consultantCode; }
    public void setConsultantCode(String value) { this.consultantCode = value; }

    public String getConsultantName() { return consultantName; }
    public void setConsultantName(String value) { this.consultantName = value; }

    public String getConsultantPhone() { return consultantPhone; }
    public void setConsultantPhone(String value) { this.consultantPhone = value; }

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public String getLatitude() { return latitude; }
    public void setLatitude(String value) { this.latitude = value; }

    public String getLongitude() { return longitude; }
    public void setLongitude(String value) { this.longitude = value; }

    public CompanyModel getCompany() { return company; }
    public void setCompany(CompanyModel value) { this.company = value; }

    public OrderEventModel[] getOrderevents() { return orderevents; }
    public void setOrderevents(OrderEventModel[] value) { this.orderevents = value; }
}

