package com.shophometech.model;

import com.shophometech.model.enums.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Appliances {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private double price;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int warranty;
    private int discount;
    private String country;
    private String firm;
    private String file;
    private int quantity;
    private String date;

    @OneToMany(mappedBy = "appliances", cascade = CascadeType.ALL)
    private List<Ordering> orderingList;

    @OneToOne(cascade = CascadeType.ALL)
    private Stats stats;

    public Appliances(String name, double price, Category category, int warranty, String country, String firm, int quantity, String date, int discount) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.warranty = warranty;
        this.country = country;
        this.firm = firm;
        this.quantity = quantity;
        this.date = date;
        this.discount = discount;
        this.orderingList = new ArrayList<>();
    }

    public void update(String name, double price, Category category, int warranty, String country, String firm, int quantity, String date, int discount) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.warranty = warranty;
        this.country = country;
        this.firm = firm;
        this.quantity = quantity;
        this.date = date;
        this.discount = discount;
    }

    public void addOrdering(Ordering ordering) {
        orderingList.add(ordering);
        ordering.setAppliances(this);
    }

    public void removeOrdering(Ordering ordering) {
        orderingList.remove(ordering);
        ordering.setAppliances(null);
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
