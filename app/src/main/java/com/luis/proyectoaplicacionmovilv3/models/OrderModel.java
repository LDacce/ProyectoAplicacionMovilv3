package com.luis.proyectoaplicacionmovilv3.models;
import java.io.Serializable;
import java.util.List;
public class OrderModel implements Serializable {
    private String createDate;
    private String updateDate;
    private String id;
    private String orderNumber;
    private int pieces;
    private String consultantCode;
    private String consultantName;
    private String consultantPhone;
    private String address;
    private String latitude;
    private String longitude;
    private CompanyModel company;
    private List<OrderEventModel> orderevents;
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    public int getPieces() {
        return pieces;
    }
    public void setPieces(int pieces) {
        this.pieces = pieces;
    }
    public String getConsultantCode() {
        return consultantCode;
    }
    public void setConsultantCode(String consultantCode) {
        this.consultantCode = consultantCode;
    }
    public String getConsultantName() {
        return consultantName;
    }
    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }
    public String getConsultantPhone() {
        return consultantPhone;
    }
    public void setConsultantPhone(String consultantPhone) {
        this.consultantPhone = consultantPhone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public CompanyModel getCompany() {
        return company;
    }
    public void setCompany(CompanyModel company) {
        this.company = company;
    }
    public List<OrderEventModel> getOrderevents() {
        return orderevents;
    }
    public void setOrderevents(List<OrderEventModel> orderevents) {
        this.orderevents = orderevents;
    }
}