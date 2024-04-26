package alexDavid.service.CartService;

import alexDavid.models.Cart;
import alexDavid.models.Product;
import alexDavid.models.WishList;
import alexDavid.repository.CartRepository;
import alexDavid.repository.ProductRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;


    @Override
    public List<Cart> getListByUser(String email){
        return this.cartRepository.findByUser_Email(email);
    }

    @Override
    public void cleanCart(String email){
        this.cartRepository.deleteByUser_Email(email);
    }


    @Override
    public void addProduct(Cart cart){
        this.cartRepository.save(cart);
    }

    @Override
    public Long getCountByClient(String email){
        return this.cartRepository.countByUser_Email(email);
    }

    @Override
    public double getTotalPriceByEmail(String email) {
        List<Cart> cartItems = getListByUser(email);
        return cartItems.stream()
                .mapToDouble(cart -> productRepository.findById(cart.getProductId()).get().getFinal_price() * cart.getQuantity())
                .sum();
    }

    @Override
    public void updateProductQuantity(String user_email, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUser_EmailAndProductId(user_email, productId);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    @Override
    public boolean isProductInCart(Cart cart) {
        return cartRepository.findByUser_EmailAndProductId(cart.getUser().getEmail(), cart.getProductId()) != null;
    }

    @Override
    public void removeProduct(String email, Long productId) {
        cartRepository.deleteByUser_EmailAndProductId(email, productId);
    }
}
