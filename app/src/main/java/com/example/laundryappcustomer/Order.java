package com.example.laundryappcustomer;


public class Order {
    private String Comments;
    private String Status;
    private String Weight="0";
    private String OrderID;
    private String Price;
    private String Service;
    private String timestamp;
    private String PaymentStatus;
    private String Merchant;


    public String getMerchantComments() {
        return merchantComments;
    }

    public void setMerchantComments(String merchantComments) {
        this.merchantComments = merchantComments;
    }

    private String merchantComments;

    public Order(String comments, String status, String weight, String orderID,String price,String service,String paymentStatus,String merchant,String timestamp,String merchantComments) {
        Comments = comments;
        Status = status;
        Weight = weight;
        OrderID = orderID;
        Price = price;
        Service = service;
        PaymentStatus=paymentStatus;
        Merchant=merchant;
        this.timestamp=timestamp;
        this.merchantComments=merchantComments;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public String getMerchant() {
        return Merchant;
    }

    public void setMerchant(String merchant) {
        Merchant = merchant;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Order() {
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
