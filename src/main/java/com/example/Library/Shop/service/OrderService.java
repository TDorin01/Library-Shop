package com.example.Library.Shop.service;

import com.example.Library.Shop.model.dto.SalesStatisticDto;
import com.example.Library.Shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<SalesStatisticDto> getMonthlyStats() {
        List<Object[]> rawStats = orderRepository.getMonthlySalesStats();
        List<SalesStatisticDto> stats = new ArrayList<>();

        for (Object[] row : rawStats) {
            int month = (int) row[0];
            double totalSales = (double) row[1];
            long totalOrders = (long) row[2];

            stats.add(new SalesStatisticDto(month, totalSales, totalOrders));
        }

        return stats;
    }
}
