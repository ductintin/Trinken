package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;

public class Discounts implements Serializable {
    @SerializedName("id")
    private int discount_id;
    @SerializedName("discount_code")
    private String discount_code;
    @SerializedName("discount_type")
    private String discount_type;
    @SerializedName("discount_value")
    private int discount_value;
    @SerializedName("start_date")
    private Date start_date;
    @SerializedName("end_date")
    private Date end_date;

    private int status;

    public Discounts(int discount_id, String discount_code, String discount_type, int discount_value, Date start_date, Date end_date, int status) {
        this.discount_id = discount_id;
        this.discount_code = discount_code;
        this.discount_type = discount_type;
        this.discount_value = discount_value;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(int discount_id) {
        this.discount_id = discount_id;
    }

    public String getDiscount_code() {
        return discount_code;
    }

    public void setDiscount_code(String discount_code) {
        this.discount_code = discount_code;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public int getDiscount_value() {
        return discount_value;
    }

    public void setDiscount_value(int discount_value) {
        this.discount_value = discount_value;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }


    public Discounts() {
    }
}
