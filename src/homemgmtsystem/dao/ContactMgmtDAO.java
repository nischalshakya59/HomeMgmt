package homemgmtsystem.dao;

import homemgmtsystem.db.DBConnection;
import homemgmtsystem.dto.ContactDTO;
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

public class ContactMgmtDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    Statement stmt;
    boolean flag = false;

    String mappedColumnNames[] = {
        "Contact Id",
        "First Name",
        "Last Name",
        "Relation",
        "Address",
        "Contact No",
        "Group"};

    public ContactMgmtDAO() {
        try {
            con = new DBConnection().getConnection();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public int getContactid() {
        int contactid = 0;
        try {
            String query = " SELECT contactid FROM contactmgmttbl\n"
                    + "ORDER BY contactid DESC\n"
                    + "LIMIT 1 ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                contactid = rs.getInt("contactid");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return contactid;
    }

    public void insertRecord(ContactDTO cdto) {
        if (cdto.getContactgroup().equalsIgnoreCase("Select Group")) {
            JOptionPane.showMessageDialog(null, "Please Select Group", "Home Management System" , JOptionPane.INFORMATION_MESSAGE);

        } else {
            try {
                String sql = "INSERT INTO contactmgmttbl VALUES(?,?,?,?,?,?,?)";
                pstmt = (PreparedStatement) con.prepareStatement(sql);
                pstmt.setInt(1, cdto.getContactid());
                pstmt.setString(2, cdto.getContactfname());
                pstmt.setString(3, cdto.getContactlname());
                pstmt.setString(4, cdto.getContactrelation());
                pstmt.setString(5, cdto.getContactaddress());
                pstmt.setString(6, cdto.getContactno());
                pstmt.setString(7, cdto.getContactgroup());

                if (pstmt.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(null, "One Record Inserted", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    public boolean updateUser(ContactDTO cdto) {
        if (cdto.getContactgroup().equalsIgnoreCase("Select Group")) {
            JOptionPane.showMessageDialog(null, "Please Select The Group", "Home Management System", JOptionPane.INFORMATION_MESSAGE);

        } else {
            try {
                String sql = "UPDATE contactmgmttbl SET contactid=?, contactfname=?, contactlname=?, contactrelation=?,"
                        + "contactaddress=?, contactno=?, contactgroup=?  WHERE contactid=?";
                pstmt = (PreparedStatement) con.prepareStatement(sql);

                pstmt.setInt(1, cdto.getContactid());
                pstmt.setString(2, cdto.getContactfname());
                pstmt.setString(3, cdto.getContactlname());
                pstmt.setString(4, cdto.getContactrelation());
                pstmt.setString(5, cdto.getContactaddress());
                pstmt.setString(6, cdto.getContactno());
                pstmt.setString(7, cdto.getContactgroup());

                pstmt.setInt(8, cdto.getContactid());

                if (pstmt.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(null, "One Record Updated", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                    boolean flag = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return flag;
    }

    public ContactDTO editUser(JTable table) {
        ContactDTO userEdit = new ContactDTO();
        userEdit.setContactid((int) table.getValueAt(table.getSelectedRow(), 0));
        userEdit.setContactfname((String) table.getValueAt(table.getSelectedRow(), 1));
        userEdit.setContactlname((String) table.getValueAt(table.getSelectedRow(), 2));
        userEdit.setContactrelation(((String) table.getValueAt(table.getSelectedRow(), 3)));
        userEdit.setContactaddress((String) table.getValueAt(table.getSelectedRow(), 4));
        userEdit.setContactno((String) table.getValueAt(table.getSelectedRow(), 5));
        userEdit.setContactgroup((String) table.getValueAt(table.getSelectedRow(), 6));

        return userEdit;
    }

    public void deleteRows(String[] value) throws SQLException {
        try {

            String query = "DELETE FROM contactmgmttbl WHERE contactid = ?";
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
        String query = "SELECT *FROM contactmgmttbl";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }

    public ResultSet search(String contactid, String firstname, String lastname, String relation, String contactno, String address, String group) {
        if (contactid != null) {
            String query = " SELECT *from contactmgmttbl WHERE contactid = '" + contactid + "' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (firstname != null) {
            String query = " SELECT *FROM contactmgmttbl WHERE contactfname = '" + firstname + "' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (lastname != null) {

            String query = " SELECT *FROM contactmgmttbl WHERE contactlname = '" + lastname + "' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (relation != null) {
            String query = " SELECT *FROM contactmgmttbl WHERE contactrelation = '" + relation + "' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (contactno != null) {
            String query = " SELECT *FROM contactmgmttbl WHERE contactno = '" + contactno + "' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (address != null) {
            String query = " SELECT *FROM contactmgmttbl WHERE contactaddress = '" + address + "' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (group != null) {
            String query = " SELECT *FROM contactmgmttbl WHERE contactgroup = '" + group + "' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No Record For The Inputted Value", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

        return rs;
    }

    public ResultSet getQueryResultQuickSearch(String search) {

        String query = "SELECT *FROM contactmgmttbl WHERE contactid LIKE '" + search + "%' OR contactfname LIKE '" + search + "%' OR contactlname LIKE '" + search + "%'"
                + "OR contactrelation LIKE '" + search + "' OR contactno LIKE '" + search + "%' OR contactgroup LIKE '" + search + "%'  "
                + "OR contactaddress LIKE '" + search + "%' ";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }

}
