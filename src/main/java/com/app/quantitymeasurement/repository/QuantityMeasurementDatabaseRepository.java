package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.util.*;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private ConnectionPool pool;

    public QuantityMeasurementDatabaseRepository(ConnectionPool pool) {
        this.pool = pool;
        initSchema();
    }

    private void initSchema() {

        try {

            Connection conn = pool.getConnection();

            Statement st = conn.createStatement();

            st.execute("CREATE TABLE IF NOT EXISTS quantity_measurement(" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "measurement_type VARCHAR(50)," +
                    "operation_type VARCHAR(50)," +
                    "value DOUBLE)");

            pool.release(conn);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(QuantityMeasurementEntity e) {

        String sql = "INSERT INTO quantity_measurement VALUES(NULL,?,?,?)";

        Connection conn = pool.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getMeasurementType());
            ps.setString(2, e.getOperationType());
            ps.setDouble(3, e.getValue());

            ps.executeUpdate();

        } catch (Exception ex) {
            throw new DatabaseException("Save failed", ex);
        }

        pool.release(conn);
    }

    public List<QuantityMeasurementEntity> getAllMeasurements() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        Connection conn = pool.getConnection();

        try {

            ResultSet rs =
                    conn.createStatement()
                            .executeQuery("SELECT * FROM quantity_measurement");

            while (rs.next()) {

                QuantityMeasurementEntity e =
                        new QuantityMeasurementEntity();

                e.setMeasurementType(rs.getString(2));
                e.setOperationType(rs.getString(3));
                e.setValue(rs.getDouble(4));

                list.add(e);
            }

        } catch (Exception ex) {
            throw new DatabaseException("Fetch failed", ex);
        }

        pool.release(conn);
        return list;
    }

    public int getTotalCount() {

        Connection conn = pool.getConnection();

        try {

            ResultSet rs =
                    conn.createStatement()
                            .executeQuery("SELECT COUNT(*) FROM quantity_measurement");

            rs.next();
            int count = rs.getInt(1);

            pool.release(conn);
            return count;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {

        Connection conn = pool.getConnection();

        try {
            conn.createStatement()
                    .execute("DELETE FROM quantity_measurement");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        pool.release(conn);
    }
}