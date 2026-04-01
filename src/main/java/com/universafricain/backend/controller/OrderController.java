package com.universafricain.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universafricain.backend.dto.OrderRequestDTO;
import com.universafricain.backend.dto.OrderResponseDTO;
import com.universafricain.backend.model.Order;
import com.universafricain.backend.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Route publique — passer une commande
    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO dto) {
        return ResponseEntity.ok(orderService.create(dto));
    }

    // Routes admin protégées
    @GetMapping("/admin/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PutMapping("/admin/orders/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam Order.Status status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }
}