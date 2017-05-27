package homemgmtsystem.ui;

import homemgmtsystem.db.DBConnection;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ChangePasswordUI extends javax.swing.JFrame {

    DBConnection db;
    DashboardUI dui = new DashboardUI();

    public ChangePasswordUI() {
        initComponents();
        newusernametxtfield.setEditable(false);
        newpasswordtxtfield.setEditable(false);
        currentusernametxtfield.setEditable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

    }

    @SuppressWarnings("unchecked")

    public void reset() {
        currentpasswordtxtfield.setEditable(true);
        currentusernametxtfield.setEditable(false);

        newusernametxtfield.setEditable(false);
        newpasswordtxtfield.setEditable(false);

        currentpasswordtxtfield.setText("");
        newusernametxtfield.setText("");
        newpasswordtxtfield.setText("");

        changebtn.setText("Verify");
    }

    public void oldUnamePassword() {
        db = new DBConnection();
        String username, password;
        username = currentusernametxtfield.getText();
        password = new String(currentpasswordtxtfield.getPassword());

        if (username.length() == 0 && password.length() == 0) {
            JOptionPane.showMessageDialog(null, "Username Or Password Shouldn't Be Empty", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (db.verifyLogin(username, password) == true) {
                newusernametxtfield.setEditable(true);
                newpasswordtxtfield.setEditable(true);
                currentpasswordtxtfield.setEditable(false);
                currentusernametxtfield.setEditable(false);
                changebtn.setText("Change");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Password", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
                currentusernametxtfield.requestFocus(true);
            }
        }
    }

    public void newUnamePassword() {
        String newusername, newpassword;
        newusername = newusernametxtfield.getText();
        newpassword = new String(newpasswordtxtfield.getPassword());
        if (newusername.equalsIgnoreCase("") && newpassword.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Username Or Password Shouldn't Be Empty", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            newusernametxtfield.requestFocus(true);
        } else {
            db.updateLogin(newusername, newpassword, currentusernametxtfield.getText());
            currentusernametxtfield.setText(newusername);
            dispose();
        }
    }

    public void setUsername(String username) {
        currentusernametxtfield.setText(username);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        currentusernametxtfield = new javax.swing.JTextField();
        currentpasswordtxtfield = new javax.swing.JPasswordField();
        changebtn = new javax.swing.JButton();
        cancelbtn = new javax.swing.JButton();
        currentunamelab = new javax.swing.JLabel();
        currrentpwdlab = new javax.swing.JLabel();
        currentunamelab1 = new javax.swing.JLabel();
        currentunamelab2 = new javax.swing.JLabel();
        newpasswordtxtfield = new javax.swing.JPasswordField();
        newusernametxtfield = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Change Username And Password");
        setIconImages(null);
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel3.setBackground(javax.swing.UIManager.getDefaults().getColor("controlDkShadow"));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setFocusCycleRoot(true);
        jPanel2.setFocusTraversalPolicyProvider(true);
        jPanel2.setOpaque(false);

        currentusernametxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentusernametxtfieldActionPerformed(evt);
            }
        });
        currentusernametxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                currentusernametxtfieldKeyPressed(evt);
            }
        });

        currentpasswordtxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                currentpasswordtxtfieldKeyPressed(evt);
            }
        });

        changebtn.setText("Verify");
        changebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changebtnActionPerformed(evt);
            }
        });

        cancelbtn.setText("Cancel");
        cancelbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelbtnActionPerformed(evt);
            }
        });

        currentunamelab.setBackground(java.awt.Color.red);
        currentunamelab.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        currentunamelab.setForeground(new java.awt.Color(255, 255, 255));
        currentunamelab.setText("Current Username");

        currrentpwdlab.setBackground(new java.awt.Color(255, 255, 255));
        currrentpwdlab.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        currrentpwdlab.setForeground(new java.awt.Color(255, 255, 255));
        currrentpwdlab.setText("Current Password");

        currentunamelab1.setBackground(java.awt.Color.red);
        currentunamelab1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        currentunamelab1.setForeground(new java.awt.Color(255, 255, 255));
        currentunamelab1.setText("New Username");

        currentunamelab2.setBackground(java.awt.Color.red);
        currentunamelab2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        currentunamelab2.setForeground(new java.awt.Color(255, 255, 255));
        currentunamelab2.setText("New Password");

        newpasswordtxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newpasswordtxtfieldKeyPressed(evt);
            }
        });

        newusernametxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newusernametxtfieldActionPerformed(evt);
            }
        });
        newusernametxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newusernametxtfieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(currentunamelab)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                        .addComponent(currentusernametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(currrentpwdlab)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(currentpasswordtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(currentunamelab1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(newusernametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(currentunamelab2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(changebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(cancelbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(newpasswordtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentunamelab)
                    .addComponent(currentusernametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currrentpwdlab, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentpasswordtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentunamelab1)
                    .addComponent(newusernametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currentunamelab2)
                    .addComponent(newpasswordtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(changebtn)
                    .addComponent(cancelbtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setIconImage(new ImageIcon(getClass().getResource("home.png")).getImage());
    }//GEN-LAST:event_formWindowOpened

    private void cancelbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelbtnActionPerformed
        dispose();
        UserProfileUI uui = new UserProfileUI();
        uui.visible();
    }//GEN-LAST:event_cancelbtnActionPerformed

    private void changebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changebtnActionPerformed
        if (changebtn.getText().equalsIgnoreCase("verify")) {
            oldUnamePassword();
        } else {
            newUnamePassword();
            reset();
        }
    }//GEN-LAST:event_changebtnActionPerformed

    private void currentpasswordtxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_currentpasswordtxtfieldKeyPressed
        // TODO add your handling code here
        int keycode = evt.getKeyCode();
        if (evt.getSource() == currentpasswordtxtfield) {
            if (keycode == KeyEvent.VK_ENTER) {
                oldUnamePassword();
                newusernametxtfield.requestFocus(true);
            }
        }

    }//GEN-LAST:event_currentpasswordtxtfieldKeyPressed

    private void currentusernametxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentusernametxtfieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_currentusernametxtfieldActionPerformed

    private void newpasswordtxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newpasswordtxtfieldKeyPressed
        // TODO add your handling code here:
        int keycode = evt.getKeyCode();
        if (evt.getSource() == newpasswordtxtfield) {
            if (keycode == KeyEvent.VK_ENTER) {
                newUnamePassword();
                reset();
                currentusernametxtfield.requestFocus(true);
            }
        }


    }//GEN-LAST:event_newpasswordtxtfieldKeyPressed

    private void newusernametxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newusernametxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newusernametxtfieldActionPerformed

    private void currentusernametxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_currentusernametxtfieldKeyPressed
        // TODO add your handling code here:
        int keycode = evt.getKeyCode();
        if (evt.getSource() == currentusernametxtfield) {
            if (keycode == KeyEvent.VK_ENTER) {
                currentpasswordtxtfield.requestFocus(true);
            }
        }
    }//GEN-LAST:event_currentusernametxtfieldKeyPressed

    private void newusernametxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newusernametxtfieldKeyPressed
        // TODO add your handling code here:
        int keycode = evt.getKeyCode();
        if (evt.getSource() == newusernametxtfield) {
            if (keycode == KeyEvent.VK_ENTER) {
                newpasswordtxtfield.requestFocus(true);
            }
        }

    }//GEN-LAST:event_newusernametxtfieldKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        dispose();
        UserProfileUI uui = new UserProfileUI();
        uui.visible();
    }//GEN-LAST:event_formWindowClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelbtn;
    private javax.swing.JButton changebtn;
    private javax.swing.JPasswordField currentpasswordtxtfield;
    private javax.swing.JLabel currentunamelab;
    private javax.swing.JLabel currentunamelab1;
    private javax.swing.JLabel currentunamelab2;
    private javax.swing.JTextField currentusernametxtfield;
    private javax.swing.JLabel currrentpwdlab;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField newpasswordtxtfield;
    private javax.swing.JTextField newusernametxtfield;
    // End of variables declaration//GEN-END:variables
}
