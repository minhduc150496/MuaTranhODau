/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MinhDuc
 */
public class DBUtils implements Serializable {    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // 1. Load driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // 2. Create connection string
        String url = "jdbc:sqlserver://localhost:1433;"
                + "databaseName=TranhDayRoi;"
                + "instanceName=DUCBMSE61791;"
                + "user=sa;password=123456;"
                + "useUnicode=true;"
                + "characterEncoding=UTF-8";
        // 3. Open connection
        Connection con = DriverManager.getConnection(url);
        return con;
    }
}
