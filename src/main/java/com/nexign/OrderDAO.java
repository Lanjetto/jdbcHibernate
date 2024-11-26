package com.nexign;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDAO {
    private static final OrderDAO INSTANCE  = new OrderDAO();

    public static OrderDAO getInstance() {
        return INSTANCE;
    }

    private static final String deleteSql = """
            delete from orders
            where orderId = ?
            """;

    private static final String saveSql = """
            insert into orders (orderName, cost)
            values (?, ?)
            """;

    private OrderDAO() {
    }

    public Order save(Order order) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(saveSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,order.getOrderName());
            statement.setDouble(2,order.getCost());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setOderId(generatedKeys.getLong("orderId"));
            }
            return order;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(deleteSql)) {
            statement.setLong(1, id);

            return statement.executeUpdate() >0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
