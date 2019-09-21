package com.example.laundryappcustomer;

public class NotificationPOJO {
    String Notification_POJO_Title;


    public String getNotification_POJO_Title() {
        return Notification_POJO_Title;
    }

    public void setNotification_POJO_Title(String notification_POJO_Title) {
        Notification_POJO_Title = notification_POJO_Title;
    }

    public String getNotification_POJO_Body() {
        return Notification_POJO_Body;
    }

    public void setNotification_POJO_Body(String notification_POJO_Body) {
        Notification_POJO_Body = notification_POJO_Body;
    }

    String Notification_POJO_Body;
    public NotificationPOJO()
    {

    }

    public NotificationPOJO(int id,String notification_POJO_Title, String notification_POJO_Body) {
        this.Notification_POJO_Title = notification_POJO_Title;
        this.Notification_POJO_Body = notification_POJO_Body;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;






}
