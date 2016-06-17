package com.dutytrail.service.trail.dao;

import com.dutytrail.service.trail.entity.Status;
import com.dutytrail.service.trail.entity.Trail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Component
public class TrailDAO {

    @Value("${db.driver}") private String driver;
    @Value("${db.url}") private String url;
    @Value("${db.schema}") private String schema;
    @Value("${db.user}") private String user;
    @Value("${db.password}") private String password;

    @Value("${sql.insert.trail}") private String postTrail;
    @Value("${sql.select.trail}") private String getTrail;

    public Long postTrail(Long userId, Long dutyId, Status status){
        PreparedStatement ps = null;
        Connection con = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url+schema, user, password);
            con.setAutoCommit(false);

            ps = con.prepareStatement(postTrail);
            ps.setLong(1, userId);
            ps.setLong(2, dutyId);
            ps.setString(3, status.getDescription());

            if(ps.executeUpdate()==1)
                return dutyId;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert ps != null;
                ps.close();
                con.commit();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1L;
    }

    public ArrayList<Trail> getTrail(Long dutyId){
        PreparedStatement ps = null;
        ArrayList<Trail> trails = new ArrayList<>();
        Connection con = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url+schema, user, password);
            con.setAutoCommit(false);

            ps = con.prepareStatement(getTrail);
            ps.setLong(1, dutyId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                trails.add(new Trail(resultSet.getLong("user_id"), resultSet.getString("status"), resultSet.getTimestamp("change_date")));
            }
            return trails;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert ps != null;
                ps.close();
                con.commit();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return trails;
    }
}
