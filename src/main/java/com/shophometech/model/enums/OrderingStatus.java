package com.shophometech.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderingStatus {
    ISSUED("Оформляется"),
    NOT_CONFIRMED("Не подтверждено"),
    CONFIRMED("Подтверждено"),
    SHIPPED("Отгружено");
    private final String name;
}
