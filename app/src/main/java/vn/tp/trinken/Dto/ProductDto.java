package vn.tp.trinken.Dto;

import java.io.File;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productName;
    private String description;
    private double price;
    private File imageFile;

    private int quantity;
    private int brandId;

    private List<Integer> categoryIds;
    private List<File> imageFiles;
}
