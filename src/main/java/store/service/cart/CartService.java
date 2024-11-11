package store.service.cart;

import java.util.List;
import java.util.function.Consumer;
import store.config.constant.ErrorMessage;
import store.config.system.SystemConfig;
import store.exception.ProductException;
import store.model.cart.CartItem;
import store.model.product.SalesProduct;
import store.model.product.SalesType;
import store.repository.cart.CartRepository;
import store.repository.product.SalesProductRepository;
import store.service.product.SalesProductService;

public class CartService {
    private final SalesProductRepository salesProductRepository;
    private final CartRepository cartRepository;
    private final SalesProductService salesProductService;

    public CartService(SalesProductRepository salesProductRepository, CartRepository cartRepository,
                       SalesProductService salesProductService) {
        this.salesProductRepository = salesProductRepository;
        this.cartRepository = cartRepository;
        this.salesProductService = salesProductService;
    }

    public void createCart(String input) {
        String DELIMITER = SystemConfig.DELIMITER.getValue();
        List<String> elements = List.of(input.split(DELIMITER));
        elements.forEach(line -> {
            CartItem item = createCartItem(line);
            cartRepository.save(item);
        });
    }

    public CartItem createCartItem(String itemInput) {
        itemInput = itemInput.substring(1, itemInput.length() - 1);
        String DELIMITER = SystemConfig.BAR.getValue();
        List<String> elements = List.of(itemInput.split(DELIMITER));
        return CartItem.of(elements);
    }

    public void searchForGiveAways(Consumer<CartItem> successHandler) {
        List<CartItem> cartItems = cartRepository.findAll();
        cartItems.forEach(cartItem -> {
            if (!hasGiveAways(cartItem)) {
                return;
            }
            successHandler.accept(cartItem);
        });
    }

    public boolean hasGiveAways(CartItem item) {
        try {
            String name = item.getName();
            SalesProduct salesProduct = salesProductRepository.findSalesProductsByName(name, SalesType.PROMOTIONAL);
            int expected = salesProductService.optimizeQuantityForPromotion(salesProduct, item.getQuantity());
            return item.getQuantity() < expected;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateGiveAways(CartItem item) {
        try {
            String name = item.getName();
            SalesProduct salesProduct = salesProductRepository.findSalesProductsByName(name, SalesType.PROMOTIONAL);
            int expected = salesProductService.optimizeQuantityForPromotion(salesProduct, item.getQuantity());
            item.setQuantity(expected);
        } catch (Exception e) {
            // do nothing.
        }
    }

    public void checkValidityOfCart() {
        List<String> itemNames = salesProductRepository.findAllNames();
        cartRepository.findAll().forEach(cartItem -> {
            if (!itemNames.contains(cartItem.getName())) {
                cartRepository.clear();
                throw new ProductException(ErrorMessage.NO_ITEM_FOUND);
            }
        });
    }
}
