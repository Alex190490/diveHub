package alexDavid.service;

import alexDavid.models.Category;
import alexDavid.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import alexDavid.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Product> findProductsByName(String name) {
        return productRepository.findProductsByNameContainsIgnoreCase(name);
    }

    @Override
    public List<Product> findProductByCategory(Category category) {
        return productRepository.findProductByCategory(category);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product){
        return productRepository.save(product);
    }

    @Override
    public List<Product> findProductsByTagIgnoreCase(String tag) {
        return productRepository.findProductsByTagIgnoreCase(tag);
    }

    @Override
    public List<Product> findByNameContainsIgnoreCase(String name) {
        return productRepository.findByNameContainsIgnoreCase(name);
    }

    @Override
    public void deleteProductById(Long id) {productRepository.deleteById(id);
    }

    @Override
    public Product update(Long id, Product product) {
        Product productUpdated = this.findById(id);

        productUpdated.setName(product.getName());
        productUpdated.setDescription(product.getDescription());
        productUpdated.setStarting_price(productUpdated.getStarting_price());
        productUpdated.setFinal_price(product.getFinal_price());
        productUpdated.setImage(product.getImage());
        productUpdated.setCategory(product.getCategory());
        productUpdated.setTag(product.getTag());

        return productRepository.save(productUpdated);
    }

  /*
    @Override
    public List<Product> findAllByOrderByFinal_priceDesc() {
        return productRepository.findAllByOrderByFinal_price();
    }

 @Override
    public List<Product> findAllByOrderByFinal_priceDesc() {

        List<Product> products = findAll();
       List<Product> sortedProducts = products.stream()
               .sorted(Comparator.comparing(Product::getFinal_price).reversed())
               .collect(Collectors.toList());
        return sortedProducts;
    }
*/

}
