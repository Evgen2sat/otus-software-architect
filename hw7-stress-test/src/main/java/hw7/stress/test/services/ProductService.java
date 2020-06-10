package hw7.stress.test.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hw7.stress.test.dto.ProductDto;
import hw7.stress.test.repositories.ProductRepository;
import io.micronaut.http.HttpResponse;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class ProductService {

    private Jedis jedis;
    private Type itemsListType;

    public ProductService() {
        if (Integer.valueOf(System.getenv("CACHE_ENABLED")) == 1) {
            this.jedis = new Jedis(System.getenv("REDIS_HOST"), Integer.valueOf(System.getenv("REDIS_PORT")));
            this.itemsListType = new TypeToken<List<ProductDto>>() {
            }.getType();
        }
    }

    @Inject
    private ProductRepository productRepository;

    public HttpResponse getProduct(long id) {
        ProductDto productDto = null;
        try {
            productDto = productRepository.getProduct(id);
            if (productDto == null) {
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

        if (jedis != null) {
            //проверим в кэше
            products = getProductsFromCache(name, color, price);

            if (products != null && !products.isEmpty()) {
                return HttpResponse.ok(products);
            }
        }

        try {
            products = productRepository.getProducts(name, color, price);

            if(jedis != null) {
                addProductsToCache(products, name, color, price);
            }
        } catch (SQLException e) {
            return HttpResponse.serverError(e);
        }

        return HttpResponse.ok(products);
    }

    public HttpResponse cache(String name, String color, Float price) {
        List<ProductDto> products = null;

        if (jedis != null) {
            //проверим в кэше
            products = getProductsFromCache(name, color, price);

            if (products != null && !products.isEmpty()) {
                return HttpResponse.ok(products);
            }
        }

        return HttpResponse.ok(products);
    }

    private List<ProductDto> getProductsFromCache(String name, String color, Float price) {
        String cacheValue = jedis.get(getCacheKey(name, color, price));

        return new Gson().fromJson(cacheValue, itemsListType);
    }

    private void addProductsToCache(List<ProductDto> products, String name, String color, Float price) {
        //если есть результаты
        if (products != null && !products.isEmpty()) {
            //то добавим в кэш
            String cacheValue = new Gson().toJson(products);
            String cacheKey = getCacheKey(name, color, price);
            jedis.set(cacheKey, cacheValue, new SetParams().ex(60));
        }
    }

    private String getCacheKey(String name, String color, Float price) {
        StringBuilder key = new StringBuilder();

        if (name != null && !name.isEmpty()) {
            key.append("#")
                    .append("name:")
                    .append(name)
                    .append("#");
        }

        if (color != null && !color.isEmpty()) {
            key.append("#")
                    .append("color:")
                    .append(color)
                    .append("#");
        }

        if (price != null) {
            key.append("#")
                    .append("price:")
                    .append(price)
                    .append("#");
        }

        return key.toString();
    }
}
