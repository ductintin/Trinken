package vn.tp.trinken.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SliderModal {

    // string variable for storing
    // title, image url and description.
    private String title;

    private String heading;

    private String imgUrl;

    private int backgroundDrawable;

    private String subTitle;


}

