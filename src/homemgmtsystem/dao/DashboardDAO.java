package homemgmtsystem.dao;

import homemgmtsystem.db.DBConnection;
import homemgmtsystem.dto.DashboardDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DashboardDAO {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    ResultSet rsone;
    boolean flag = false;
    Statement stmt;

    // mapping the table of database 
    String mappedColumnNames[] = {
        "Entry Id",
        "Date",
        "Particular Id",
        "Particular",
        "Amount",
        "Remark"};

    public DashboardDAO() {
        try {
            con = new DBConnection().getConnection();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public int getEntryid() {
        int entryid = 0;
        try {
            String query = " SELECT entryid FROM transactiontbl\n"
                    + "ORDER BY entryid DESC\n"
                    + "LIMIT 1 ";

            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                entryid = rs.getInt("entryid");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return entryid;
    }

    public int getCatid(DashboardDTO ddto) {
        int catid = 0;
        String pname = ddto.getParticular();
        try {
            String query = "SELECT pid FROM particulartbl WHERE pname = '" + pname + "'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                catid = rs.getInt("pid");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return catid;
    }

    public void insertRecord(DashboardDTO ddto) {
        if (ddto.getParticular().equalsIgnoreCase("Select Particular")) {
            JOptionPane.showMessageDialog(null, "Please Select The Particular", "Home Management System", JOptionPane.INFORMATION_MESSAGE);

        } else {
            try {
                String sql = "INSERT INTO transactiontbl VALUES(?,?,?,?,?,?)";
                pstmt = (PreparedStatement) con.prepareStatement(sql);
                pstmt.setInt(1, ddto.getEntryid());
                pstmt.setString(2, ddto.getDate());
                pstmt.setInt(3, getCatid(ddto));
                pstmt.setString(4, ddto.getParticular());
                pstmt.setDouble(5, ddto.getAmount());
                pstmt.setString(6, ddto.getRemark());

                if (pstmt.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(null, "One Record Inserted", "Home Management System" , JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public DashboardDTO editUser(JTable table) {
        DashboardDTO userEdit = new DashboardDTO();
        userEdit.setEntryid((int) table.getValueAt(table.getSelectedRow(), 0));
        userEdit.setDate((String) table.getValueAt(table.getSelectedRow(), 1));
        userEdit.setParticular((String) table.getValueAt(table.getSelectedRow(), 3));
        userEdit.setAmount(((double) table.getValueAt(table.getSelectedRow(), 4)));
        userEdit.setRemark((String) table.getValueAt(table.getSelectedRow(), 5));
        //ResultSet rs;
        return userEdit;
    }

    public boolean updateUser(DashboardDTO ddto) {
        if (ddto.getParticular().equalsIgnoreCase("Select Particular")) {
            JOptionPane.showMessageDialog(null, "Please Select The Particular", "Home Management System", JOptionPane.INFORMATION_MESSAGE);

        } else {
            try {
                String sql = "UPDATE transactiontbl SET entryid=?, date=?, pid=?, particular=?,"
                        + "amount=?, remark=?  WHERE entryid=?";
                pstmt = (PreparedStatement) con.prepareStatement(sql);

                pstmt.setInt(1, ddto.getEntryid());
                pstmt.setString(2, ddto.getDate());
                pstmt.setInt(3, getCatid(ddto));
                pstmt.setString(4, ddto.getParticular());
                pstmt.setDouble(5, ddto.getAmount());
                pstmt.setString(6, ddto.getRemark());
                pstmt.setInt(7, ddto.getEntryid());

                if (pstmt.executeUpdate() == 1) {
                    JOptionPane.showMessageDialog(null, "One Record Updated", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                    boolean flag = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        return flag;
    }

    public void deleteRows(String[] value) throws SQLException {
        try {
            //String query = "  DELETE FROM  transactiontbl WHERE entryid = ?  ";

            String reservedquery = "UPDATE transactiontbl SET pid = ? WHERE entryid = ?";
            pstmt = con.prepareStatement(reservedquery);
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setString(2, value[0]);

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
        String query = "SELECT *FROM transactiontbl WHERE pid != 'null'";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return rs;
    }

    public ResultSet getQueryResultQuickSearch(String search) {

        String query = "SELECT *FROM transactiontbl WHERE pid != 'null' AND (entryid LIKE '" + search + "%'" + "OR date LIKE '" + search + "%'"
                + "OR pid LIKE '" + search + "' OR particular LIKE '" + search + "%' OR amount LIKE '" + search + "%'  ) ";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

        return rs;
    }

    public double returntotalwithsearch(String date, String particular, String entryid, String samount) {
        double amount = 0;
        if (date != null) {
            String query = " SELECT amount FROM transactiontbl WHERE date = '" + date + "' "
                    + "AND pid != 'null'";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    amount = rs.getDouble("amount") + amount;

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (particular != null) {
            String query = " SELECT amount FROM transactiontbl WHERE particular = '" + particular + "' "
                    + "AND pid != 'null' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    amount = rs.getDouble("amount") + amount;

                }
            } catch (Exception e) {
               JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (entryid != null) {
            String query = "SELECT amount FROM transactiontbl WHERE entryid = '" + entryid + "' "
                    + " AND pid != 'null' ";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    amount = rs.getDouble("amount") + amount;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (samount != null) {
            String query = "SELECT amount FROM transactiontbl WHERE amount = '" + samount + "'"
                    + "AND pid != 'null'";
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    amount = rs.getDouble("amount") + amount;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return amount;
    }

    public double returntotalWithoutSearch() {
        double amount = 0;
        try {
            String query = "SELECT amount FROM transactiontbl WHERE pid != 'null' ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                amount = rs.getDouble("amount") + amount;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return amount;
    }

    public double returntotalwithquicksearch(String search) {
        double amount = 0;
        try {
            String query = "SELECT SUM(amount) FROM transactiontbl WHERE pid != 'null' AND (entryid LIKE '" + search + "%'" + "OR date LIKE '" + search + "%'"
                    + "OR pid LIKE '" + search + "' OR particular LIKE '" + search + "%' OR amount LIKE '" + search + "%'  ) ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                amount = rs.getDouble(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        return amount;
    }

}
