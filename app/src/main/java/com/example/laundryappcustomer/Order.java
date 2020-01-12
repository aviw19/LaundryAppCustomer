package com.example.laundryappcustomer;


public class Order {
    private String comments;
    private String status;
    private String weight ="0";
    private String orderID;
    private String price;
    private String services;
    private String timestamp;
    private String paymentstatus;
    private String merchant;
    private String merchantComments;

    public Order(String comments, String status, String weight, String orderID, String price, String services, String paymentstatus, String merchant, String timestamp, String merchantComments) {
        this.comments = comments;
        this.status = status;
        this.weight = weight;
        this.orderID = orderID;
        this.price = price;
        this.services = services;
        this.paymentstatus = paymentstatus;
        this.merchant=merchant;
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

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        merchant = merchant;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getservices() {
        return services;
    }

    public void setservices(String service) {
        this.services = service;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Order() {
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
