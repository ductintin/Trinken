package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Orders implements Serializable {

    @SerializedName("order_id")
    private int order_id;
    @SerializedName("order_date")
    private Date order_date;
    @SerializedName("total_amount")
    private int total_amount;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("payment_method_id")
    private int payment_method_id;
    @SerializedName("status_id")
    private int status_id;

    private Date cancelAt;

    private Date createdAt;

    private Date updatedAt;

    private Date completeAt;

    private Date deliveryAt;

    public Orders(int order_id, Date order_date, int total_amount, int user_id, int payment_method_id, int status_id, Date cancelAt, Date createdAt, Date updatedAt, Date completeAt, Date deliveryAt) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.total_amount = total_amount;
        this.user_id = user_id;
        this.payment_method_id = payment_method_id;
        this.status_id = status_id;
        this.cancelAt = cancelAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.completeAt = completeAt;
        this.deliveryAt = deliveryAt;
    }

    public Date getCancelAt() {
        return cancelAt;
    }

    public void setCancelAt(Date cancelAt) {
        this.cancelAt = cancelAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCompleteAt() {
        return completeAt;
    }

    public void setCompleteAt(Date completeAt) {
        this.completeAt = completeAt;
    }

    public Date getDeliveryAt() {
        return deliveryAt;
    }

    public void setDeliveryAt(Date deliveryAt) {
        this.deliveryAt = deliveryAt;
    }

    public Orders() {
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public Orders(int order_id, Date order_date, int total_amount, int user_id, int payment_method_id, int status_id) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.total_amount = total_amount;
        this.user_id = user_id;
        this.payment_method_id = payment_method_id;
        this.status_id = status_id;
    }
}
