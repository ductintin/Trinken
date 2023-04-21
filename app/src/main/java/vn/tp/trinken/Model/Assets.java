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

    private int product_id;

    public Assets(int id, String path, String type, Date deleteAt, Date createdAt, Date updatedAt, int product_id) {
        this.id = id;
        this.path = path;
        this.type = type;
        this.deleteAt = deleteAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.product_id = product_id;
    }

    public Assets() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
