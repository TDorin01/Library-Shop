package com.example.Library.Shop.model.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SalesStatisticDto {
    private int month;
    private double totalSales;
    private long totalOrders;

    public SalesStatisticDto(int month, double totalSales, long totalOrders) {
        this.month = month;
        this.totalSales = totalSales;
        this.totalOrders = totalOrders;
    }

}
