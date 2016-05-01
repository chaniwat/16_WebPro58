package com.alumnisystem.database;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;

/**
 * Created by meranote on 4/28/2016 AD.
 */
public class MysqlDBCP extends BasicDataSource {

//    private static final String DB_URL = "localhost";
//    private static final String DB_USERNAME = "root";
//    private static final String DB_PASSWORD = "mysql";
//    private static final String DB_PORT = "3306";
//    private static final String DB_SCHEMA = "it_16";

    private static final String DB_URL = "database.it.kmitl.ac.th";
    private static final String DB_USERNAME = "it_16";
    private static final String DB_PASSWORD = "csQgCzmQ";
    private static final String DB_PORT = "3306";
    private static final String DB_SCHEMA = "it_16";

    public MysqlDBCP() throws SQLException {
        /*
            Initialize Datasource
         */
        this.setDriver(new com.mysql.jdbc.Driver());
        this.setUrl("jdbc:mysql://" + DB_URL + ":" + DB_PORT + "/" + DB_SCHEMA + "?characterEncoding=utf8");
        this.setUsername(DB_USERNAME);
        this.setPassword(DB_PASSWORD);
        this.setMaxTotal(50);
        this.setMaxWaitMillis(10000);

        this.setInitialSize(1);
    }

}
