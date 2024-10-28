package br.com.solidtechsolutions.apipagamentos.services.impls;


import br.com.solidtechsolutions.apipagamentos.models.Product;
import br.com.solidtechsolutions.apipagamentos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setQuotasPurchased(product.getQuotasPurchased());
                    existingProduct.setQuotasTotals(product.getQuotasTotals());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setFeatures(product.getFeatures());
                    existingProduct.setImage(product.getImage());
                    existingProduct.setReviews(product.getReviews());
                    return productRepository.save(existingProduct);
                }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Método para salvar uma lista de produtos
    public List<Product> saveProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    // Método para retornar 4 produtos aleatórios
    public List<Product> getRandomProducts() {
        return productRepository.findRandomProducts();
    }

    // Marca o produto como completamente pago
    public void updateProductAsFullyPaid(Long productId, Long reviewId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Define quotasPurchased igual a quotasTotals (pagamento completo)
        product.setQuotasPurchased(product.getQuotasTotals());

        product.getReviews().stream()
                .filter(review -> Objects.equals(review.getId(), reviewId))
                .findFirst()
                .ifPresent(review -> review.setEnable(true));

        // Atualiza o produto no banco de dados
        productRepository.save(product);
    }

    // Atualiza o produto com um pagamento parcial
    public void updateProductWithPartialPayment(Long productId, String payedQuotes, Long reviewId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Atualiza o valor de quotasPurchased somando o payedQuotes
        product.setQuotasPurchased(String.valueOf(Integer.valueOf(product.getQuotasPurchased()) + (Integer.valueOf(payedQuotes))));

        product.getReviews().stream()
                .filter(review -> Objects.equals(review.getId(), reviewId))
                .findFirst()
                .ifPresent(review -> review.setEnable(true));

        // Atualiza o produto no banco de dados
        productRepository.save(product);
    }
}
