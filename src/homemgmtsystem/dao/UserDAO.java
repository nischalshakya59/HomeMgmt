package homemgmtsystem.dao;

import homemgmtsystem.db.DBConnection;
import homemgmtsystem.dto.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    ResultSet rsone;
    boolean flag = false;
    Statement stmt;
    UserDTO udto = new UserDTO();

    // mapping the table of database 
    String mappedColumnNames[] = {
        "User Id",
        "FirstName",
        "LastName",
        "Contact No",
        "Username",
        "Password",
        "Usertype"};

    public UserDAO() {
        try {
            con = new DBConnection().getConnection();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public int getuserid() {
        int userid = 0;
        try {
            String query = " SELECT userinfoid FROM userinfotbl\n"
                    + "ORDER BY userinfoid DESC\n"
                    + "LIMIT 1 ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                userid = rs.getInt("userinfoid");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return userid;
    }

    public int getloginid() {
        int loginid = 0;
        try {
            String query = " SELECT uid FROM logintbl\n"
                    + "ORDER BY uid DESC\n"
                    + "LIMIT 1 ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                loginid = rs.getInt("uid");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return loginid;

    }

    public void insertRecord(UserDTO udto) {

        try {
            String sql = "INSERT INTO userinfotbl VALUES(?,?,?,?,?,?,?)";
            pstmt = (PreparedStatement) con.prepareStatement(sql);
            pstmt.setInt(1, udto.getUserid());
            pstmt.setString(2, udto.getFirstname());
            pstmt.setString(3, udto.getLastname());
            pstmt.setString(4, udto.getContactno());
            pstmt.setString(5, udto.getUsername());
            pstmt.setString(6, udto.getPassword());
            pstmt.setString(7, udto.getUsertype());

            if (pstmt.executeUpdate() == 1) {
                //JOptionPane.showMessageDialog(null, "One Record Inserted");
                adduserlogin(udto.getUserid());
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void adduserlogin(int userid) {
        try {
            String loginsql = "SELECT userinfousername, userinfopassword, userinfousertype FROM userinfotbl WHERE userinfoid = '" + userid + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(loginsql);
            while (rs.next()) {
                String username = rs.getString(1);
                String password = rs.getString(2);
                String usertype = rs.getString(3);

                String adduserlogin = "INSERT INTO logintbl VALUES (?,?,?,?)";
                pstmt = con.prepareStatement(adduserlogin);
                pstmt.setInt(1, getloginid() + 1);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, usertype);

                if (pstmt.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(null, "User Added", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public UserDTO editUser(JTable table) {

        udto.setUserid((int) table.getValueAt(table.getSelectedRow(), 0));
        udto.setFirstname((String) table.getValueAt(table.getSelectedRow(), 1));
        udto.setLastname((String) table.getValueAt(table.getSelectedRow(), 2));
        udto.setContactno(((String) table.getValueAt(table.getSelectedRow(), 3)));
        udto.setUsername((String) table.getValueAt(table.getSelectedRow(), 4));
        udto.setPassword((String) table.getValueAt(table.getSelectedRow(), 5));
        udto.setUsertype((String) table.getValueAt(table.getSelectedRow(), 6));
        //ResultSet rs;
        return udto;
    }

    public boolean updateUser(UserDTO udto) {

        try {
            String sql = "UPDATE userinfotbl SET userinfoid=?, userinfofname=?, userinfolname=?, userinfocontact=?,"
                    + "userinfousername=?, userinfopassword=?, userinfousertype=?  WHERE userinfoid=?";
            pstmt = (PreparedStatement) con.prepareStatement(sql);

            pstmt.setInt(1, udto.getUserid());
            pstmt.setString(2, udto.getFirstname());
            pstmt.setString(3, udto.getLastname());
            pstmt.setString(4, udto.getContactno());
            pstmt.setString(5, udto.getUsername());
            pstmt.setString(6, udto.getPassword());
            pstmt.setString(7, udto.getUsertype());
            pstmt.setInt(8, udto.getUserid());

            if (pstmt.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "One Record Updated", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                updateusertype(udto.getUsertype(), udto.getUsername());
                boolean flag = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return flag;
    }

    public void updateusertype(String usertype, String username) {
        try {
            String sql = "UPDATE logintbl SET usertype=? WHERE username=?";
            pstmt = (PreparedStatement) con.prepareStatement(sql);

            pstmt.setString(1, usertype);
            pstmt.setString(2, username);

            if (pstmt.executeUpdate() == 1) {
                // JOptionPane.showMessageDialog(null, "One Record Updated");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void deleteRows(String[] value) throws SQLException {
        try {
            String query = "  DELETE FROM userinfotbl WHERE userinfoid = ?  ";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, value[0]);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        pstmt.executeUpdate();
    }

    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {

            columnNames.add(mappedColumnNames[column - 1]);
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();

            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    public ResultSet getQueryResult() {
        String query = "SELECT *FROM userinfotbl ";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }

    public ResultSet getUserProfile(String username) {
        String query = "SELECT userinfofname, userinfolname, userinfocontact, userinfousername, userinfopassword,"
                + "userinfousertype FROM userinfotbl WHERE "
                + "userinfousername = '" + username + "'";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }
}
