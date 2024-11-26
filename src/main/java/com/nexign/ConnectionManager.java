package com.nexign;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionManager {
    public static final String URL = PropertiesUtil.get("db.url");
    public static final String USER = PropertiesUtil.get("db.username");
    public static final String PASSWORD = PropertiesUtil.get("db.password");
    private static HikariDataSource dataSource;


    static  {
        initHikariCP();
    }

    private ConnectionManager() {
    }

    private static void initHikariCP() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(URL);
        hikariConfig.setUsername(USER);
        hikariConfig.setPassword(PASSWORD);
        hikariConfig.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(hikariConfig);
    }

    public static Connection get() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
