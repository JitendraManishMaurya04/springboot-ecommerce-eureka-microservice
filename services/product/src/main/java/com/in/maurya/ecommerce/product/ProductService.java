package com.in.maurya.ecommerce.product;

import com.in.maurya.ecommerce.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest request) {
        var product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
       //Extract ProductId from the Request List
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        //Returns the products available
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        //Throw Exception in case any product is not available
        if(productIds.size() != storedProducts.size()){
            throw new ProductPurchaseException("One or more products does not exists");
        }
        //Sort the product by productId
        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        //Loop around each product, check if required QTY is available, if not throw exception
        //If Required ATY available then, reset the QTY in DB with newAvailableQty value and also add the product in ProductPurchaseResponse list
        var purchaseProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i=0; i<storedProducts.size(); i++){
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: "+productRequest.productId());
            }
            var newAvailableQty = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQty);
            productRepository.save(product);

            purchaseProducts.add(productMapper.toProductPurchaseReponse(product, productRequest.quantity()));
        }
        return purchaseProducts;
    }

    public ProductResponse findByProductId(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: "+ productId));
    }

    public List<ProductResponse> findAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
