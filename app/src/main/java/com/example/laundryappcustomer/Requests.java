package com.example.laundryappcustomer;

public class Requests {
    private String comments;
    private String status;
    private String weight ="0";
    private String orderID;
    private String services;
    private String paymentstatus;
    private String timestamp;
    private String price;
    private String merchantComments;

    public Requests() {
    }

    public Requests(String comments, String status, String weight, String OrderId, String services, String paymentstatus, String price, String timestamp, String merchantComments) {
        this.comments = comments;
        this.status = status;
        this.weight = weight;
        this.orderID = OrderId;
        this.services = services;
        this.paymentstatus = paymentstatus;
        this.price =price;
        this.timestamp=timestamp;
        this.merchantComments=merchantComments;
    }

    public String getMerchantComments() {
        return merchantComments;
    }

    public void setMerchantComments(String merchantComments) {
        this.merchantComments = merchantComments;
    }
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }


    public String getservices() {
        return services;
    }

    public void setService(String services) {
        this.services = services;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
