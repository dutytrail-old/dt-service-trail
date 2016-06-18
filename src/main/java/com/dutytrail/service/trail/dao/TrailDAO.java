package com.dutytrail.service.trail.dao;

import com.dutytrail.service.trail.entity.Status;
import com.dutytrail.service.trail.entity.Trail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Component
public class TrailDAO extends BaseDAO{

    @Value("${sql.insert.trail}") private String postTrail;
    @Value("${sql.select.trail}") private String getTrail;

    public Long postTrail(Long userId, Long dutyId, Status status){
        PreparedStatement ps = null;

        try {
            ps = super.con.prepareStatement(postTrail);
            ps.setLong(1, userId);
            ps.setLong(2, dutyId);
            ps.setString(3, status.getDescription());

            if(ps.executeUpdate()==1)
                return dutyId;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                super.commitAndCloseAll(ps, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1L;
    }

    public ArrayList<Trail> getTrail(Long dutyId){
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        ArrayList<Trail> trails = new ArrayList<>();

        try {
            ps = super.con.prepareStatement(getTrail);
            ps.setLong(1, dutyId);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                trails.add(new Trail(resultSet.getLong("user_id"), resultSet.getString("status"), resultSet.getTimestamp("change_date")));
            }
            return trails;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                super.commitAndCloseAll(ps, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return trails;
    }
}
