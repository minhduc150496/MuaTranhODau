/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sample.painting;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import sample.utils.DBUtils;

/**
 *
 * @author Chuot Bach
 */
public class PaintingDAO implements Serializable{
    
    public boolean insert(String name, String code, String pageURL, int price, String description) 
            throws SQLException, NamingException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.getConnection();
            String sql = "insert into Painting(name, code, pageURL, price, description) values (?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, code);
            stm.setString(3, pageURL);
            stm.setInt(4, price);
            stm.setString(5, description);
            int rows = stm.executeUpdate();
            if (rows>0) {
                return true;
            }
        } finally {
            if (stm!=null) {
                stm.close();
            }
            if (con!=null) {
                con.close();
            }
        }
        return false;
    }
}
