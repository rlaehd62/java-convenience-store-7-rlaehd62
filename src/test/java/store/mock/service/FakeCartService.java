package store.mock.service;

import store.repository.cart.CartRepository;
import store.repository.product.SalesProductRepository;
import store.service.cart.CartService;
import store.service.product.SalesProductService;

public class FakeCartService extends CartService {
    public FakeCartService(SalesProductRepository salesProductRepository, CartRepository cartRepository,
                           SalesProductService salesProductService) {
        super(salesProductRepository, cartRepository, salesProductService);
    }
}
