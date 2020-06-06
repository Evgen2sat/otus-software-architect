package hw6.cache.controllers;

import hw6.cache.dto.ProductDto;
import hw6.cache.services.ProductService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.inject.Inject;

@Controller("v1/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {
    @Inject
    private ProductService productService;

    @Get("/{id}")
    public HttpResponse getProduct(@PathVariable long id) {
        return productService.getProduct(id);
    }

    @Post
    public HttpResponse createProduct(@Body ProductDto data) {
        return productService.createProduct(data);
    }

    @Put("/{id}")
    public HttpResponse updateProduct(@PathVariable long id, @Body ProductDto data) {
        return productService.updateProduct(id, data);
    }

    @Delete("/{id}")
    public HttpResponse deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }

    @Get
    public HttpResponse getProducts(@Nullable @QueryValue("name") String name,
                                    @Nullable @QueryValue("color") String color,
                                    @Nullable @QueryValue("price") Float price) {
        return productService.getProducts(name, color, price);
    }

    @Get("/cache")
    public HttpResponse cache(@Nullable @QueryValue("name") String name,
                                    @Nullable @QueryValue("color") String color,
                                    @Nullable @QueryValue("price") Float price) {
        return productService.cache(name, color, price);
    }

}
