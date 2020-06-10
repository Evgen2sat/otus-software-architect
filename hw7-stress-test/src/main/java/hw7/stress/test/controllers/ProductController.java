package hw7.stress.test.controllers;

import hw7.stress.test.dto.ProductDto;
import hw7.stress.test.services.ProductService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.time.Duration;

@Controller("v1/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {
    private MeterRegistry meterRegistry;

    public ProductController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Inject
    private ProductService productService;

    @Get("/{id}")
    public HttpResponse getProduct(@PathVariable long id) {
        return Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "GET")
                .tag("endpoint", "/v1/products")
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(this.meterRegistry)
                .record(() -> productService.getProduct(id));
    }

    @Post
    public HttpResponse createProduct(@Body ProductDto data) {
        return Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "POST")
                .tag("endpoint", "/v1/products")
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(this.meterRegistry)
                .record(() -> productService.createProduct(data));
    }

    @Put("/{id}")
    public HttpResponse updateProduct(@PathVariable long id, @Body ProductDto data) {
        return Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "PUT")
                .tag("endpoint", "/v1/products")
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(this.meterRegistry)
                .record(() -> productService.updateProduct(id, data));
    }

    @Delete("/{id}")
    public HttpResponse deleteProduct(@PathVariable long id) {
        return Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "DELETE")
                .tag("endpoint", "/v1/products")
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(this.meterRegistry)
                .record(() -> productService.deleteProduct(id));
    }

    @Get
    public HttpResponse getProducts(@Nullable @QueryValue("name") String name,
                                    @Nullable @QueryValue("color") String color,
                                    @Nullable @QueryValue("price") Float price) {

        return Timer.builder("app_request_latency")
                .description("Application Request Latency")
                .tag("method", "GET")
                .tag("endpoint", "/v1/products")
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(this.meterRegistry)
                .record(() -> productService.getProducts(name, color, price));
    }

    @Get("/cache")
    public HttpResponse cache(@Nullable @QueryValue("name") String name,
                              @Nullable @QueryValue("color") String color,
                              @Nullable @QueryValue("price") Float price) {
        return productService.cache(name, color, price);
    }

}
