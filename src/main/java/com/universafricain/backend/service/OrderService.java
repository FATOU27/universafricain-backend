package com.universafricain.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.universafricain.backend.dto.OrderRequestDTO;
import com.universafricain.backend.dto.OrderResponseDTO;
import com.universafricain.backend.model.Order;
import com.universafricain.backend.model.OrderItem;
import com.universafricain.backend.model.Product;
import com.universafricain.backend.repository.OrderRepository;
import com.universafricain.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // Créer une commande
    public OrderResponseDTO create(OrderRequestDTO dto) {

        // Construire les items + calculer le total
        List<OrderItem> items = dto.getItems().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produit introuvable"));
            return OrderItem.builder()
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();
        }).collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Construire la commande
        Order order = Order.builder()
                .customerName(dto.getCustomerName())
                .customerEmail(dto.getCustomerEmail())
                .customerPhone(dto.getCustomerPhone())
                .contactMethod(dto.getContactMethod())
                .status(Order.Status.PENDING)
                .totalAmount(total)
                .items(items)
                .build();

        // Lier les items à la commande
        items.forEach(i -> i.setOrder(order));

        return toDTO(orderRepository.save(order));
    }

    // Toutes les commandes (admin)
    public List<OrderResponseDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Changer le statut (admin)
    public OrderResponseDTO updateStatus(Long id, Order.Status status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));
        order.setStatus(status);
        return toDTO(orderRepository.save(order));
    }

    // Mapper Order → OrderResponseDTO
    private OrderResponseDTO toDTO(Order o) {
        List<OrderResponseDTO.ItemDTO> itemDTOs = o.getItems().stream()
                .map(i -> OrderResponseDTO.ItemDTO.builder()
                        .productName(i.getProduct().getName())
                        .imageUrl(i.getProduct().getImageUrl())
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .id(o.getId())
                .createdAt(o.getCreatedAt())
                .status(o.getStatus())
                .contactMethod(o.getContactMethod())
                .totalAmount(o.getTotalAmount())
                .customerName(o.getCustomerName())
                .customerEmail(o.getCustomerEmail())
                .customerPhone(o.getCustomerPhone())
                .items(itemDTOs)
                .build();
    }
}