package com.nexign;

import com.nexign.service.HibernateService;

import java.sql.*;

public class Main {

    public static final String URL = PropertiesUtil.get("db.url");
    public static final String USER = PropertiesUtil.get("db.username");
    public static final String PASSWORD = PropertiesUtil.get("db.password");

    public static void main(String[] args) throws SQLException {

        HibernateService hibernateService = new HibernateService();
//        System.out.println(hibernateService.getUser(2L));

//        hibernateService.save();

        hibernateService.getSpecialUser();

//        Main main = new Main();
//        OrderDAO instance = OrderDAO.getInstance();
//        Order order1 = new Order("Блины", 100.);
//        System.out.println(instance.save(order1));

//        main.getOrderById(2L);
//        main.deleteUserById(1L);

//        String createSql = """
//                create table if not exists orders(
//                    orderId serial primary key,
//                    orderName varchar(255) not null,
//                    cost decimal (10,2) not null
//                    );
//                """;
//
//
//
//
//
//        String insertSql = """
//                INSERT INTO orders (orderName, cost) VALUES
//                ('Борщ', 150.00),
//                ('Пельмени', 200.00),
//                ('Оливье', 120.00),
//                ('Котлета по-киевски', 250.00),
//                ('Блины с икрой', 300.00);
//                """;
//
//
    }

    public void getOrderById(Long id) throws SQLException {
        String selectSql = """
                select orderName 
                from orders
                where orderId = ?
                """;

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(selectSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("orderName"));
            }
        }

    }


    public void deleteUserById(Long id) throws SQLException {
        String deleteSql = "DELETE FROM users WHERE id = " + id;
        String deleteOrderSql = "DELETE FROM userorders WHERE userId = " + id;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.addBatch(deleteOrderSql);
            statement.addBatch(deleteSql);
            statement.executeBatch();
            connection.commit();


        } catch (Exception e) {
            if (connection !=null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        }
    }