package com.dutytrail.service.trail.dao;

import com.dutytrail.service.trail.entity.Status;
import com.dutytrail.service.trail.entity.Trail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Component
public class TrailDAO extends BaseDAO {

    @Value("${sql.insert.trail}") private String postTrail;
    @Value("${sql.select.trail}") private String getTrail;
    @Value("${sql.delete.trail}") private String deleteTrail;

    private PreparedStatement ps = null;
    private ResultSet resultSet = null;

    public Long postTrail(Long userId, Long dutyId, Status status) throws SQLException {

        try {
            ps = super.con.prepareStatement(postTrail);
            ps.setLong(1, userId);
            ps.setLong(2, dutyId);
            ps.setString(3, status.getDescription());

            if(ps.executeUpdate()==1)
                return dutyId;

        } catch (SQLException e) {
            throw e;
        } finally {
            super.commitAndCloseAll(ps, null);
        }
        return -1L;
    }

    public ArrayList<Trail> getTrail(Long dutyId) throws SQLException {
        ArrayList<Trail> trails = new ArrayList<>();

        try {
            ps = super.con.prepareStatement(getTrail);
            ps.setLong(1, dutyId);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                trails.add(new Trail(resultSet.getLong("user_id"), resultSet.getString("status"), resultSet.getTimestamp("change_date")));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            super.commitAndCloseAll(ps, resultSet);
        }
        return trails;
    }

    public Long deleteTrail(Long dutyId) throws SQLException {
        try {
            ps = super.con.prepareStatement(deleteTrail);
            ps.setLong(1, dutyId);

            if(ps.executeUpdate()==1)
                return dutyId;

        } catch (SQLException e) {
            throw e;
        } finally {
            super.commitAndCloseAll(ps, null);
        }
        return -1L;
    }
}
