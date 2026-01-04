package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.model.Product;
import com.ecommerce.order.model.User;
import com.ecommerce.order.repository.CartItemRepository;
import com.ecommerce.order.repository.ProductRepository;
import com.ecommerce.order.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;
    private UserRepository userRepository;

    public CartService(ProductRepository productRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    public boolean addToCart(String userID, CartItemRequest cartItemRequest) {
        Optional<Product> productOptional = productRepository.findById(cartItemRequest.getProductID());
        if (productOptional.isEmpty()){
            return false;
        }

        Product product = productOptional.get();
        if (product.getStockQuantity() < cartItemRequest.getQuantity()){
            return false;
        }

        Optional<User> userOptional = userRepository.findById(Long.valueOf(userID));
        if (userOptional.isEmpty()){
            return false;
        }

        User user = userOptional.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingCartItem != null){
            existingCartItem.setQuantity(cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));

        if (productOptional.isPresent() && userOptional.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOptional.get(), productOptional.get());
            return true;
        }

        return false;
    }

    public List<CartItem> getCart(String userID) {
        return userRepository.findById(Long.valueOf(userID))
                .map(cartItemRepository::findByUser)
                .orElseGet(List::of);
    }

    public void clearCart(String userID) {
        userRepository.findById(Long.valueOf(userID))
                .ifPresent(user -> cartItemRepository.deleteByUser(user));
    }
}
