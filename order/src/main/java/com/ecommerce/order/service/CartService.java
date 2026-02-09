package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CartService {

    private CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public boolean addToCart(String userID, CartItemRequest cartItemRequest) {
//        Optional<Product> productOptional = productRepository.findById(cartItemRequest.getProductID());
//        if (productOptional.isEmpty()){
//            return false;
//        }
//
//        Product product = productOptional.get();
//        if (product.getStockQuantity() < cartItemRequest.getQuantity()){
//            return false;
//        }
//
//        Optional<User> userOptional = userRepository.findById(Long.valueOf(userID));
//        if (userOptional.isEmpty()){
//            return false;
//        }
//        User user = userOptional.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userID, cartItemRequest.getProductID());

        if (existingCartItem != null){
            existingCartItem.setQuantity(cartItemRequest.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        }else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userID);
            cartItem.setProductId(cartItemRequest.getProductID());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (cartItem != null){
            cartItemRepository.delete(cartItem);
            return true;
        }

        return false;
    }

    public List<CartItem> getCart(String userID) {
        return cartItemRepository.findByUserId(userID);
    }

    public void clearCart(String userID) {
        cartItemRepository.deleteByUserId(userID);
    }
}
