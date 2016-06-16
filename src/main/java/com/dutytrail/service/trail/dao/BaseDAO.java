package com.dutytrail.service.trail.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;

@Component
class BaseDAO {

    @Value("${db.driver}") private String driver;
    @Value("${db.url}") private String url;
    @Value("${db.schema}") private String schema;
    @Value("${db.user}") private String user;
    @Value("${db.password}") private String password;

    Connection con;

    @PostConstruct
    public void initDAO(){
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url+schema, user, password);
            con.setAutoCommit(false);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
