package vn.tp.trinken.Model;

import com.google.gson.annotations.SerializedName;

public class Payment_Methods {
    @SerializedName("payment_method_id")
    private int payment_method_id;
    @SerializedName("payment_method_name")
    private String payment_method_name;

    public Payment_Methods(int payment_method_id, String payment_method_name) {
        this.payment_method_id = payment_method_id;
        this.payment_method_name = payment_method_name;
    }

    public Payment_Methods() {
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getPayment_method_name() {
        return payment_method_name;
    }

    public void setPayment_method_name(String payment_method_name) {
        this.payment_method_name = payment_method_name;
    }
}
