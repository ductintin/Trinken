package vn.tp.trinken.Dto;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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

    public Map<String, RequestBody> product(){
        Map<String, RequestBody> product = new HashMap<>();

        product.put("productName", RequestBody.create(MediaType.parse("text/plain"), productName));
        product.put("description", RequestBody.create(MediaType.parse("text/plain"), description));
        product.put("price", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(price)));
        product.put("brandId", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(brandId)));
        product.put("quantity", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(quantity)));
        for(int id : categoryIds){
            product.put("categoryIds", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id)));
        }
        if (imageFile != null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            product.put("imageFile\"; filename=\"" + imageFile.getName() + "\"", requestBody);
        }
        return product;
    }
}
