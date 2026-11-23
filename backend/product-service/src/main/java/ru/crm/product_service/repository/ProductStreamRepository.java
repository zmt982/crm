package ru.crm.product_service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.crm.common_lib.dto.ProductDto;

import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class ProductStreamRepository {

    private final NamedParameterJdbcTemplate jdbc;

    private static final String SQL = "SELECT id, name, price, quantity, description FROM products";

    public void streamAll(Consumer<ProductDto> consumer) {
        jdbc.query(
                SQL,
                new MapSqlParameterSource(),
                (RowCallbackHandler) rs -> consumer.accept(new ProductDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getString("description")
                ))
        );
    }

}
