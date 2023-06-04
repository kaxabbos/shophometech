package com.shophometech.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    WASHING_MACHINES("Стиральные машины"),
    PLATES("Плиты"),
    OVENS("Духовки"),
    BLENDER("Блендер"),
    TOASTER("Тостер"),
    TEAPOTS("Чайники"),
    MICROWAVE_OVENS("Микроволновые печи"),
    REFRIGERATORS("Холодильники"),
    DISHWASHERS("Посудомоечные машины"),
    KITCHEN_STOVES("Кухонные плиты");

    private final String name;
}
