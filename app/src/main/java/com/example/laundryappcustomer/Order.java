package com.example.laundryappcustomer;


public class Order {
    private String Comments;
    private String Status;
    private String Weight="0";
    private String OrderID;

    public Order(String comments, String status, String weight, String orderID) {
        Comments = comments;
        Status = status;
        Weight = weight;
        OrderID = orderID;
    }
    public String getPrice()
    {
        return "Free for now";
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
