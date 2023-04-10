package vn.tp.trinken.Model;

import java.io.Serializable;
import java.util.Date;

public class Assets implements Serializable {
    private int id;

    private String path;

    private String type;

    private Date deleteAt;

    private Date createdAt;

    private Date updatedAt;

    private Products product_id;
}
