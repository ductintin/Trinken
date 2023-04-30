package vn.tp.trinken.Dto;

import lombok.Data;

@Data
public class CartItemDto {
    private int quantity;
    private double price;
    private int productId;
    private int cartId;
}
