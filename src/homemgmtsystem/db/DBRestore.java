package homemgmtsystem.db;

import homemgmtsystem.dto.BckRestoreDTO;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DBRestore {

    Connection con;
    Statement stmt;
    ResultSet rs;

    public void transactiontblRestore(BckRestoreDTO brd) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();

            String query = "load data local infile '" + directory + "/" + fname + "' \n"
                    + "   replace \n"
                    + "   into table transactiontbl \n"
                    + "   fields terminated by ',' \n"
                    + "   ignore 1 lines";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Restored Sucessfully " + fname, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Restore " + fname, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void particulartblRestore(BckRestoreDTO brd) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();

            String query = "load data local infile '" + directory + "/" + fname + "' \n"
                    + "   replace \n"
                    + "   into table particulartbl \n"
                    + "   fields terminated by ',' \n"
                    + "   ignore 1 lines";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {

                JOptionPane.showMessageDialog(null, "Backup Restored Sucessfully " + fname, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Restore " + fname, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void eventmgmttblRestore(BckRestoreDTO brd) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();

            String query = "load data local infile '" + directory + "/" + fname + "' \n"
                    + "   replace \n"
                    + "   into table eventmgmttbl \n"
                    + "   fields terminated by ',' \n"
                    + "   ignore 1 lines";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {

                JOptionPane.showMessageDialog(null, "Backup Restored Sucessfully " + fname, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Restore " + fname, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void contactmgmttblRestore(BckRestoreDTO brd) {
        try {

            con = new DBConnection().getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String fname = brd.getFilename();
            File directory = brd.getDirectory();

            String query = "load data local infile '" + directory + "/" + fname + "' "
                    + "   replace "
                    + "   into table contactmgmttbl "
                    + "   fields terminated by ',' "
                    + "   ignore 1 lines";

            String query1 = query.replace("\\", "/");

            rs = stmt.executeQuery(query1);
            if (rs != null) {
                JOptionPane.showMessageDialog(null, "Backup Restored Sucessfully " + fname, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed To Restore " + fname, "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
