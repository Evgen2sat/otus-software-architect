package hw11.sharding.repositories;

import hw11.sharding.database.DatabaseService;
import hw11.sharding.database.QueryParam;
import hw11.sharding.dto.ProductDto;

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
                "SELECT id, name, color, price, category_id\n" +
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
                        "(name, color, price, category_id)\n" +
                        "VALUES\n" +
                        "(?, ?, ?, ?)",
                QueryParam.getString(data.getName()),
                QueryParam.getString(data.getColor()),
                QueryParam.getFloat(data.getPrice()),
                QueryParam.getLong(data.getCategoryId())
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

    public List<ProductDto> getProductsFromCategory(long categoryId) throws SQLException {
        return databaseService.executeSelectQuery(
                "SELECT id, name, color, price, category_id\n" +
                        "FROM products\n" +
                        "WHERE category_id = ?\n" +
                        "   AND deleted = FALSE",
                this::getProductMapper,
                QueryParam.getLong(categoryId)
        );
    }

    private ProductDto getProductsMapper(ResultSet rs) {
        try {
            ProductDto productDto = new ProductDto();
            productDto.setId(rs.getLong("id"));
            productDto.setColor(rs.getString("color"));
            productDto.setName(rs.getString("name"));
            productDto.setPrice(rs.getFloat("price"));
            productDto.setCategoryId(rs.getLong("category_id"));

            return productDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ProductDto getProductMapper(ResultSet rs) {
        try {
            ProductDto productDto = new ProductDto();
            productDto.setId(rs.getLong("id"));
            productDto.setColor(rs.getString("color"));
            productDto.setName(rs.getString("name"));
            productDto.setPrice(rs.getFloat("price"));
            productDto.setCategoryId(rs.getLong("category_id"));

            return productDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
