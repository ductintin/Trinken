package vn.tp.trinken.Dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import vn.tp.trinken.Model.Order_Status;
import vn.tp.trinken.Model.Orders_Items;
import vn.tp.trinken.Model.Payment_Methods;
import vn.tp.trinken.Model.Shipping_Addresses;
import vn.tp.trinken.Model.User;

@Data
public class OrderDto {
    private Date orderDate;

    private double totalAmount;

    private Date cancelAt;

    private Date completeAt;

    private Date deliveryAt;

    private Order_Status orderStatus;

    private User customer;

    private Payment_Methods paymentmethod;

    private Shipping_Addresses shippingAddress;

    private List<Orders_Items> orderItems;
}
