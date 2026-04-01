package com.universafricain.backend.dto;

import java.util.List;

import com.universafricain.backend.model.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Order.ContactMethod contactMethod;
    private List<OrderItemDTO> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDTO {
        private Long productId;
        private Integer quantity;
    }
}