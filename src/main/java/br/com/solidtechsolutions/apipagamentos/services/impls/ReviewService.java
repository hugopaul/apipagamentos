package br.com.solidtechsolutions.apipagamentos.services.impls;


import br.com.solidtechsolutions.apipagamentos.models.Product;
import br.com.solidtechsolutions.apipagamentos.models.Review;
import br.com.solidtechsolutions.apipagamentos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService {


    @Autowired
    private ProductRepository productRepository;

    // Método para adicionar novos reviews a um produto existente
    public Product addReviewToProduct(Long productId, Review newReview) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Define a data e hora atual para o novo review
        newReview.setDataReview(new Date());

        // Adiciona o novo review à lista de reviews do produto
        List<Review> reviews = product.getReviews();
        reviews.add(newReview);
        product.setReviews(reviews);

        // Salva o produto com o novo review
        return productRepository.save(product);
    }
}