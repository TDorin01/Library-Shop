package com.example.Library.Shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesStatisticDto {
    private int month;
    private double totalSales;
    private long totalOrders;
}
