package homemgmtsystem.main;

import homemgmtsystem.ui.LoginUI;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class HomeMgmtSystem {

    public void login() {
        try {
            //UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            LoginUI lu = new LoginUI();
            lu.setVisible(true);
            lu.pack();
            lu.setLocationRelativeTo(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new HomeMgmtSystem().login();

    }
}
