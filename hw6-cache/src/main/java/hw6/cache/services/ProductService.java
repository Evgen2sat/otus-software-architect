package hw6.cache.services;

import hw6.cache.dto.ProductDto;
import hw6.cache.repositories.ProductRepository;
import io.micronaut.http.HttpResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class ProductService {
    @Inject
    private ProductRepository productRepository;

    public HttpResponse getProduct(long id) {
        ProductDto productDto = null;
        try {
            productDto = productRepository.getProduct(id);
            if(productDto == null) {
                return HttpResponse.badRequest("product not found");
            }
        } catch (SQLException e) {
            return HttpResponse.serverError(e);
        }

        return HttpResponse.ok(productDto);
    }

    public HttpResponse createProduct(ProductDto data) {
        ProductDto productDto = null;
        try {
            productDto = productRepository.createProduct(data);
        } catch (SQLException e) {
            return HttpResponse.serverError(e);
        }

        return HttpResponse.ok(productDto);
    }

    public HttpResponse updateProduct(long id, ProductDto data) {
        ProductDto productDto = null;
        try {
            productDto = productRepository.updateProduct(id, data);
        } catch (SQLException e) {
            return HttpResponse.serverError(e);
        }

        return HttpResponse.ok(productDto);
    }

    public HttpResponse deleteProduct(long id) {
        try {
            productRepository.deleteProduct(id);
        } catch (SQLException e) {
            return HttpResponse.serverError(e);
        }

        return HttpResponse.ok();
    }

    public HttpResponse getProducts(String name, String color, Float price) {
        List<ProductDto> products = null;
        try {
            products = productRepository.getProducts(name, color, price);
        } catch (SQLException e) {
            return HttpResponse.serverError(e);
        }

        return HttpResponse.ok(products);
    }
}
