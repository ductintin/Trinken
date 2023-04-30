package vn.tp.trinken.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    private int id;
    private int quantity;

    private double price;

    private Date createdAt;

    private Date updatedAt;
    private Products product;


}
