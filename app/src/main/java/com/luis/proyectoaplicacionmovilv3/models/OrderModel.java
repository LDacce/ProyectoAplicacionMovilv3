package com.luis.proyectoaplicacionmovilv3.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class OrderModel implements Serializable {
    private String createDate;

    @Nullable()
    private String updateDate;
    private String id;
    private String orderNumber;
    private long pieces;
    private String consultantCode;
    private String consultantName;
    private String consultantPhone;
    private String address;
    private String latitude;
    private String longitude;
    private CompanyModel company;
    private List<OrderEventModel> orderevents;

    public String getCreateDate() { return createDate; }
    public void setCreateDate(String value) { this.createDate = value; }

    public String getUpdateDate() { return updateDate; }
    public void setUpdateDate(String value) { this.updateDate = value; }

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

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

    public List<OrderEventModel> getOrderevents() { return orderevents; }
    public void setOrderevents(List<OrderEventModel> value) { this.orderevents = value; }
}

