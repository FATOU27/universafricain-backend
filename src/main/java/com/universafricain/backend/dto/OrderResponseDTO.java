package com.universafricain.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.universafricain.backend.model.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private Order.Status status;
    private Order.ContactMethod contactMethod;
    private BigDecimal totalAmount;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private List<ItemDTO> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemDTO {
        private String productName;
        private String imageUrl;
        private Integer quantity;
        private BigDecimal unitPrice;
    }
}