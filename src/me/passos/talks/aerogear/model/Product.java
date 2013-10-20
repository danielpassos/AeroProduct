package me.passos.talks.aerogear.model;

import org.jboss.aerogear.android.RecordId;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    @RecordId
    private Long id;
    private String name;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

}
