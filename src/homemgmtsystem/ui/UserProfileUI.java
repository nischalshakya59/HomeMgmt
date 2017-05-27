package homemgmtsystem.ui;

import homemgmtsystem.dao.UserDAO;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class UserProfileUI extends javax.swing.JFrame {

    ResultSet rs;

    public UserProfileUI() {
        initComponents();
        this.setTitle("UserProfile");
        this.setResizable(false);
        this.pack();
    }

    public static boolean enable = true;

    public void visible() {
        enable = true;
    }

    public void setUserInfo(String userinfofname, String userinfolname, String userinfocontact, String userinfousername, String usertype) {
        userinfonamelab.setText(userinfousername + "." + "Here Is Your Profile Information");
        namelab.setText("Name :- " + userinfofname + " " + userinfolname);
        contactlab.setText("Contact :- " + userinfocontact);
        usernamelab.setText(userinfousername);
        usertypelab.setText("Usertype :- " + usertype);
    }

    public void userinfo(String username) {
        try {
            rs = new UserDAO().getUserProfile(username);
            while (rs.next()) {
                String userinfofname = rs.getString(1);
                String userinfolname = rs.getString(2);
                String userinfocontact = rs.getString(3);
                String userinfousername = rs.getString(4);
                String usertype = rs.getString(6);
                //System.out.println(userinfofname);
                //System.out.println(userinfolname);
                userinfonamelab.setText(userinfousername.substring(0, 1).toUpperCase() + userinfousername.substring(1) + " Here Is Your Profile Information");
                namelab.setText("Name :- " + userinfofname + " " + userinfolname);
                contactlab.setText("Contact :- " + userinfocontact);
                usernamelab.setText(userinfousername);
                usertypelab.setText("Usertype :- " + usertype);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userinfomainlab = new javax.swing.JPanel();
        userinfonamelab = new javax.swing.JLabel();
        namelab = new javax.swing.JLabel();
        contactlab = new javax.swing.JLabel();
        usertypelab = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        passwordlab = new javax.swing.JLabel();
        usernampasswordbtn = new javax.swing.JButton();
        usernamelab = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        userinfonamelab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        namelab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        namelab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        namelab.setText("Name :-");

        contactlab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        contactlab.setText("Contact :-");

        usertypelab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        usertypelab.setText("UserType :-");

        username.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        username.setText("Username :-");

        passwordlab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        passwordlab.setText("Password :- ************");

        usernampasswordbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/key-2.png"))); // NOI18N
        usernampasswordbtn.setText("Change Username And Password");
        usernampasswordbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernampasswordbtnActionPerformed(evt);
            }
        });

        usernamelab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        usernamelab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout userinfomainlabLayout = new javax.swing.GroupLayout(userinfomainlab);
        userinfomainlab.setLayout(userinfomainlabLayout);
        userinfomainlabLayout.setHorizontalGroup(
            userinfomainlabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userinfomainlabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userinfomainlabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userinfonamelab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(userinfomainlabLayout.createSequentialGroup()
                        .addGroup(userinfomainlabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(namelab, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactlab, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usertypelab, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(userinfomainlabLayout.createSequentialGroup()
                                .addGroup(userinfomainlabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(passwordlab, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(userinfomainlabLayout.createSequentialGroup()
                                        .addComponent(username)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(usernamelab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(usernampasswordbtn)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        userinfomainlabLayout.setVerticalGroup(
            userinfomainlabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userinfomainlabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userinfonamelab, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(namelab, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(contactlab, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(usertypelab, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(userinfomainlabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernamelab))
                .addGap(18, 18, 18)
                .addGroup(userinfomainlabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordlab, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernampasswordbtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        userinfomainlabLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {passwordlab, usernamelab, usernampasswordbtn});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userinfomainlab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userinfomainlab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernampasswordbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernampasswordbtnActionPerformed
        if (enable == true) {
            try {
                dispose();
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                ChangePasswordUI cui = new ChangePasswordUI();
                cui.setLocationRelativeTo(null);
                cui.setVisible(true);
                cui.pack();
                cui.setUsername(usernamelab.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        enable = false;
    }//GEN-LAST:event_usernampasswordbtnActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setIconImage(new ImageIcon(getClass().getResource("home.png")).getImage());
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        new DashboardUI().visible();
    }//GEN-LAST:event_formWindowClosed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserProfileUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserProfileUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserProfileUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserProfileUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserProfileUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contactlab;
    private javax.swing.JLabel namelab;
    private javax.swing.JLabel passwordlab;
    private javax.swing.JPanel userinfomainlab;
    private javax.swing.JLabel userinfonamelab;
    private javax.swing.JLabel username;
    private javax.swing.JLabel usernamelab;
    private javax.swing.JButton usernampasswordbtn;
    private javax.swing.JLabel usertypelab;
    // End of variables declaration//GEN-END:variables
}
