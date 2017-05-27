package homemgmtsystem.dao;

import homemgmtsystem.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ContactGroupDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    Statement stmt;
    boolean flag = false;

    public ContactGroupDAO() {
        try {
            con = new DBConnection().getConnection();

        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void insertRecord(String groupname) {

        try {
            String sql = "INSERT INTO contactgrouptbl (contactgroupname) VALUES(?)";
            pstmt = (PreparedStatement) con.prepareStatement(sql);
            pstmt.setString(1, groupname);
            if (pstmt.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "Contact Group Added", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
