package com.app.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool {

    private Queue<Connection> pool = new LinkedList<>();

    public ConnectionPool() {

        try {

            Class.forName(ApplicationConfig.getDriver());

            for (int i = 0; i < ApplicationConfig.getPoolSize(); i++) {

                Connection conn = DriverManager.getConnection(
                        ApplicationConfig.getUrl(),
                        ApplicationConfig.getUser(),
                        ApplicationConfig.getPass());

                pool.add(conn);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Connection getConnection() {
        return pool.poll();
    }

    public synchronized void release(Connection conn) {
        pool.add(conn);
    }
}