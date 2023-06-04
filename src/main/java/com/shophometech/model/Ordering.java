package com.shophometech.model;

import com.shophometech.controller.main.Main;
import com.shophometech.model.enums.OrderingStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Ordering {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Appliances appliances;

    private int quantity;
    private double sum;
    @Enumerated(EnumType.STRING)
    private OrderingStatus orderingStatus;

    public Ordering() {
        this.quantity = 0;
        this.sum = 0;
        orderingStatus = OrderingStatus.ISSUED;
    }

    public void addAppliancesAndUser(Appliances appliances, User user) {
        appliances.addOrdering(this);
        user.addOrdering(this);
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
        this.sum += Main.round(quantity * appliances.getPrice(), 2);
    }

    public double calculating() {
        return sum - (sum * ((double) appliances.getDiscount() / 100));
    }


}
