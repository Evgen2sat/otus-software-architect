package hw6.cache.repositories;

import hw6.cache.database.DatabaseService;
import hw6.cache.database.QueryParam;
import hw6.cache.dto.ProductDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class ProductRepository {
    @Inject
    private DatabaseService databaseService;

    public ProductDto getProduct(long id) throws SQLException {
        List<ProductDto> products = databaseService.executeSelectQuery(
                "SELECT id, name, color, price\n" +
                        "FROM products\n" +
                        "WHERE id = ?\n" +
                        "   AND deleted = FALSE",
                this::getProductMapper,
                QueryParam.getLong(id)
        );

        if(products != null && !products.isEmpty()) {
            return products.get(0);
        }

        return null;
    }

    public ProductDto createProduct(ProductDto data) throws SQLException {
        long productId = databaseService.executeInsertQueryWithId(
                "INSERT INTO products\n" +
                        "(name, color, price)\n" +
                        "VALUES\n" +
                        "(?, ?, ?)",
                QueryParam.getString(data.getName()),
                QueryParam.getString(data.getColor()),
                QueryParam.getFloat(data.getPrice())
        );

        return getProduct(productId);
    }

    public ProductDto updateProduct(long id, ProductDto data) throws SQLException {
        databaseService.executeInsertQuery(
                "UPDATE products\n" +
                        "SET\n" +
                        "   name = ?,\n" +
                        "   color = ?,\n" +
                        "   price = ?\n" +
                        "WHERE id = ?",
                QueryParam.getString(data.getName()),
                QueryParam.getString(data.getColor()),
                QueryParam.getFloat(data.getPrice()),
                QueryParam.getLong(id)
        );

        return getProduct(id);
    }

    public void deleteProduct(long id) throws SQLException {
        databaseService.executeInsertQuery(
                "UPDATE products\n" +
                        "SET\n" +
                        "   deleted = TRUE\n" +
                        "WHERE id = ?",
                QueryParam.getLong(id)
        );
    }

    public List<ProductDto> getProducts(String name, String color, Float price) throws SQLException {
        StringBuilder query = new StringBuilder();

        query.append("SELECT id, name, color, price")
                .append("\n")
                .append("FROM products")
                .append("\n")
                .append("WHERE")
                .append("\n")
                .append("deleted = FALSE")
                .append("\n");

        if(name != null && !name.isEmpty()) {
            query.append("AND name ilike '%")
                    .append(name)
                    .append("%'")
                    .append("\n");
        }

        if(color != null && !color.isEmpty()) {
            query.append("AND color ilike '%")
                    .append(color)
                    .append("%'")
                    .append("\n");
        }

        if(price != null) {
            query.append("AND price = ")
                    .append(price)
                    .append("\n");
        }

        return databaseService.executeSelectQuery(query.toString(),
                this::getProductsMapper
        );
    }

    public ProductDto getProductsMapper(ResultSet rs) {
        try {
            ProductDto productDto = new ProductDto();
            productDto.setId(rs.getLong("id"));
            productDto.setColor(rs.getString("color"));
            productDto.setName(rs.getString("name"));
            productDto.setPrice(rs.getFloat("price"));

            return productDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ProductDto getProductMapper(ResultSet rs) {
        try {
            ProductDto productDto = new ProductDto();
            productDto.setId(rs.getLong("id"));
            productDto.setColor(rs.getString("color"));
            productDto.setName(rs.getString("name"));
            productDto.setPrice(rs.getFloat("price"));

            return productDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
