package com.example.RestAPI.controllers;

import com.example.RestAPI.models.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final List<CartItem> cartItems = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    // Get all items
    @GetMapping
    public List<CartItem> getAllItems() {
        return cartItems;
    }

    // Get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getUserById(@PathVariable Long id) {
        Optional<CartItem> Item = cartItems.stream().filter(u -> u.getId().equals(id)).findFirst();
        return Item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add new item
    @PostMapping("/addcart")
    public ResponseEntity<CartItem> createUser(@RequestBody CartItem cartItem) {
        cartItem.setId(counter.incrementAndGet());
        cartItems.add(cartItem);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    // Delete item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<CartItem> userOptional = cartItems.stream().filter(u -> u.getId().equals(id)).findFirst();
        if (userOptional.isPresent()) {
            cartItems.remove(userOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
