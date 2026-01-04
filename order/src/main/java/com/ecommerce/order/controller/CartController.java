package com.ecommerce.order.controller;

import com.example.ecom_app.dto.CartItemRequest;
import com.example.ecom_app.model.CartItem;
import com.example.ecom_app.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-ID") String userID){
        return ResponseEntity.ok(cartService.getCart(userID));
    }

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userID,
                                          @RequestBody CartItemRequest cartItemRequest){
        if(!cartService.addToCart(userID, cartItemRequest)){
            return ResponseEntity.badRequest().body("Product not found or User not found or Product out of Stock");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId,
                                               @PathVariable Long productId){
        boolean result = cartService.deleteItemFromCart(userId, productId);
        return result ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
