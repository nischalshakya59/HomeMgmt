package homemgmtsystem.ui;

import homemgmtsystem.dao.EventMgmtDAO;
import homemgmtsystem.db.DBBck;
import homemgmtsystem.db.DBConnection;
import homemgmtsystem.db.DBRestore;
import homemgmtsystem.dto.BckRestoreDTO;
import homemgmtsystem.dto.EventMgmtDTO;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class EventManager extends javax.swing.JFrame {
    
    Connection con;
    PreparedStatement pstmt;
    DashboardUI dui = new DashboardUI();

    
    public EventManager() {
        initComponents();
        this.setTitle("Event Manager");
        loadDatas();
        setEventid();
        eventidtxtfield.setEnabled(false);
        this.setResizable(false);
        reset();
    }
    
    @SuppressWarnings("unchecked")
    
    public void reset() {
        eventnametxtfield.setText("");
        todaydatetxtfield.setToolTipText("");
        eventdatetxtfield.setToolTipText("");
        ((JTextField) todaydatetxtfield.getDateEditor().getUiComponent()).setText("");
        ((JTextField) eventdatetxtfield.getDateEditor().getUiComponent()).setText("");
        todaydatetxtfield.transferFocus();
        eventinfolab.setText("");
        loadDatas();
        setEventid();
        count();
    }
    
    public void check() {
        
        String eventdate = ((JTextField) eventdatetxtfield.getDateEditor().getUiComponent()).getText();
        String todaydate = ((JTextField) todaydatetxtfield.getDateEditor().getUiComponent()).getText();
        if (eventnametxtfield.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Event Name Should Not Be Empty");
        }
        if (eventdate.equals("")) {
            JOptionPane.showMessageDialog(null, "Event Date Should Not Be Empty");
        }
        if (todaydate.equals("")) {
            JOptionPane.showMessageDialog(null, "Date Should Not Be Empty");
        }
    }
    
    public void DeleteMethodUsingArray() {
        try {
            con = new DBConnection().getConnection();
            int[] rows = eventtbl.getSelectedRows();
            
            int clicked = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Delete The Event", "Home Management System", JOptionPane.YES_NO_OPTION);
            if (clicked == 0) {
                for (int i = 0; i < rows.length; i++) {
                    String eventid = Integer.toString((int) eventtbl.getValueAt(rows[i], 0));
                    String[] strArray = new String[]{eventid};
                    new EventMgmtDAO().deleteRows(strArray);
                }
            }
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public void addEvent() {
        EventMgmtDTO edto = new EventMgmtDTO();
        
        if (diffdate() < 0) {
            JOptionPane.showMessageDialog(null, "Event Date Must Be Greater Than Today Date", "Home Management System", JOptionPane.INFORMATION_MESSAGE);
            
        } else {
            if (eventnametxtfield.getText().length() != 0) {
                edto.setEventid(Integer.parseInt(eventidtxtfield.getText()));
                
                String todaydate = ((JTextField) todaydatetxtfield.getDateEditor().getUiComponent()).getText();
                edto.setTodaydate(todaydate.replaceAll(",", " "));
                
                String eventdate = ((JTextField) eventdatetxtfield.getDateEditor().getUiComponent()).getText();
                edto.setEventdate(eventdate.replaceAll(",", ""));
                
                edto.setDiffdate(diffdate());
                edto.setEventname(eventnametxtfield.getText().substring(0, 1).toUpperCase() + eventnametxtfield.getText().substring(1) );
                EventMgmtDAO edao = new EventMgmtDAO();
                edao.Addevent(edto);
            }
        }
        
        reset();
    }
    
    public void setEventid() {
        EventMgmtDAO EventDAO = new EventMgmtDAO();
        int eventid = EventDAO.eventId() + 1;
        eventidtxtfield.setText(String.valueOf(eventid));
    }
    
    public int diffdate() {
        // this will give difference in date in millisecond.
        long difference = eventdatetxtfield.getDate().getTime() - todaydatetxtfield.getDate().getTime();
        // this will convert the date from millisecond in the no of days left in integer ie 1,2 etc.
        int diffInDays = (int) (difference / 1000 / 60 / 60 / 24);
        return diffInDays;
    }
    
    public void seteventname() {
        int row = eventtbl.getSelectedRowCount();
        if (row < 2) {
            int selectedrow = eventtbl.getSelectedRow();
            String eventdate = String.valueOf(eventtbl.getValueAt(selectedrow, 2));
            String eventname = String.valueOf(eventtbl.getValueAt(selectedrow, 3));
            //int remainingdays = ((int) (eventtbl.getValueAt(selectedrow, 4)));
            eventinfolab.setText("You have event named " + eventname + " in " + eventdate + ".");
        } else {
            eventinfolab.setText("");
        }
    }
    
    public void count(){
        int count = eventtbl.getRowCount();
        noofrecordlab.setText("No Of Records :- " +count);
    }
    
    public void checknoofdays() {
        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("E");
            SimpleDateFormat sft = new SimpleDateFormat("yyyy.MM.dd");
            String today = ft.format(dNow);
            System.out.println("simple date format " + today);
            System.out.println("date today " + sft.format(dNow));
            
            FileInputStream fin = new FileInputStream("d:\\day.txt");
            DataInputStream din = new DataInputStream(fin);
            
            String readtodayfromfile = din.readLine();
            System.out.println("readtoday" + readtodayfromfile);
            
            if (today.equalsIgnoreCase(readtodayfromfile)) {
                
                new EventMgmtDAO().countdays();
                System.out.println("this");
                FileOutputStream fout = new FileOutputStream("d:\\day.txt");
                DataOutputStream dout = new DataOutputStream(fout);
                
                if (readtodayfromfile.equalsIgnoreCase("sun")) {
                    dout.writeBytes("mon");
                }
                if (readtodayfromfile.equalsIgnoreCase("mon")) {
                    dout.writeBytes("tue");
                }
                if (readtodayfromfile.equalsIgnoreCase("tue")) {
                    dout.writeBytes("wed");
                }
                if (readtodayfromfile.equalsIgnoreCase("wed")) {
                    
                    dout.writeBytes("thu");
                }
                if (readtodayfromfile.equalsIgnoreCase("thu")) {
                    dout.writeBytes("fri");
                }
                if (readtodayfromfile.equalsIgnoreCase("fri")) {
                    dout.writeBytes("sat");
                }
                if (readtodayfromfile.equalsIgnoreCase("sat")) {
                    dout.writeBytes("sun");
                }
                
            } else {
                System.out.println("not this");
            }
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        eventidtxtfield = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        addeventbtn = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        savebtn = new javax.swing.JButton();
        openbtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        eventnametxtfield = new javax.swing.JTextField();
        todaydatetxtfield = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        eventdatetxtfield = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        eventtbl = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        eventinfolab = new javax.swing.JLabel();
        noofrecordlab = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Category Frame"); // NOI18N
        setType(java.awt.Window.Type.POPUP);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Event Id :-");

        eventidtxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eventidtxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventidtxtfieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Date :-");

        addeventbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/add.png"))); // NOI18N
        addeventbtn.setText("Add");
        addeventbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addeventbtnActionPerformed(evt);
            }
        });

        deletebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/bin-3.png"))); // NOI18N
        deletebtn.setText("Delete");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });

        savebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/exporttoexcel.png"))); // NOI18N
        savebtn.setText("Save");
        savebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebtnActionPerformed(evt);
            }
        });

        openbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homemgmtsystem/images/importfromexcel.png"))); // NOI18N
        openbtn.setText("Open");
        openbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbtnActionPerformed(evt);
            }
        });

        jLabel3.setText("Event Date :-");

        eventnametxtfield.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eventnametxtfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventnametxtfieldMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eventnametxtfieldMouseEntered(evt);
            }
        });
        eventnametxtfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eventnametxtfieldKeyPressed(evt);
            }
        });

        todaydatetxtfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                todaydatetxtfieldMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                todaydatetxtfieldMouseEntered(evt);
            }
        });

        jLabel4.setText("Event Name :-");

        eventdatetxtfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventdatetxtfieldMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                eventdatetxtfieldMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(eventnametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(eventidtxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(todaydatetxtfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(eventdatetxtfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(savebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addeventbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(deletebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(openbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(eventidtxtfield))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(todaydatetxtfield, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eventdatetxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eventnametxtfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addeventbtn)
                    .addComponent(deletebtn))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(savebtn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(openbtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addeventbtn, deletebtn, eventdatetxtfield, eventidtxtfield, eventnametxtfield, openbtn, savebtn, todaydatetxtfield});

        eventtbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        eventtbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventtblMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                eventtblMouseReleased(evt);
            }
        });
        eventtbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eventtblKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(eventtbl);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        eventinfolab.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eventinfolab.setForeground(java.awt.Color.darkGray);

        noofrecordlab.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        noofrecordlab.setText("No Of Records :-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(eventinfolab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(noofrecordlab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(eventinfolab, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(noofrecordlab, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addeventbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addeventbtnActionPerformed
        // TODO add your handling code here:
        check();
        addEvent();
    }//GEN-LAST:event_addeventbtnActionPerformed

    private void eventidtxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventidtxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eventidtxtfieldActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        DeleteMethodUsingArray();
    }//GEN-LAST:event_deletebtnActionPerformed
    

    private void eventtblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eventtblKeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        int[] rows = eventtbl.getSelectedRows();
        if (evt.getSource() == eventtbl && rows.length != 0) {
            if (key == KeyEvent.VK_DELETE) {
                DeleteMethodUsingArray();
            }
        }
    }//GEN-LAST:event_eventtblKeyPressed

    private void eventtblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventtblMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_eventtblMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setIconImage(new ImageIcon(getClass().getResource("home.png")).getImage());
        
        checknoofdays();
        loadDatas();
        

    }//GEN-LAST:event_formWindowOpened

    private void savebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebtnActionPerformed
        // TODO add your handling code here:
        DBBck dbbck = new DBBck();
        BckRestoreDTO brd = new BckRestoreDTO();
        JFileChooserSave jfcs = new JFileChooserSave(brd);
        dbbck.eventmgmtbck(brd);
        reset();

    }//GEN-LAST:event_savebtnActionPerformed

    private void openbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openbtnActionPerformed
        // TODO add your handling code here:
        DBRestore dbrest = new DBRestore();
        BckRestoreDTO brd = new BckRestoreDTO();
        JFileChooserOpen jfco = new JFileChooserOpen();
        jfco.open(brd);
        dbrest.eventmgmttblRestore(brd);
        loadDatas();
        reset();
    }//GEN-LAST:event_openbtnActionPerformed

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        DashboardUI dui = new DashboardUI();
        dui.visible();
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void eventnametxtfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eventnametxtfieldKeyPressed
        // TODO add your handling code here:
        int key = evt.getKeyCode();
        if (evt.getSource() == eventnametxtfield) {
            if (key == KeyEvent.VK_ENTER) {
                check();
                addEvent();
                reset();
            }
        }
    }//GEN-LAST:event_eventnametxtfieldKeyPressed

    private void eventtblMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventtblMouseReleased
        // TODO add your handling code here:
        seteventname();
    }//GEN-LAST:event_eventtblMouseReleased

    private void todaydatetxtfieldMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_todaydatetxtfieldMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_todaydatetxtfieldMouseEntered

    private void eventdatetxtfieldMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventdatetxtfieldMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_eventdatetxtfieldMouseEntered

    private void eventnametxtfieldMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventnametxtfieldMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_eventnametxtfieldMouseEntered

    private void todaydatetxtfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_todaydatetxtfieldMouseClicked
        // TODO add your handling code here:
        eventinfolab.setText("");
    }//GEN-LAST:event_todaydatetxtfieldMouseClicked

    private void eventdatetxtfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventdatetxtfieldMouseClicked
        // TODO add your handling code here:
        eventinfolab.setText("");
    }//GEN-LAST:event_eventdatetxtfieldMouseClicked

    private void eventnametxtfieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventnametxtfieldMouseClicked
        // TODO add your handling code here:
       eventinfolab.setText("");
    }//GEN-LAST:event_eventnametxtfieldMouseClicked
    
    public void loadDatas() {
        try {
            EventMgmtDAO edao = new EventMgmtDAO();
            eventtbl.setModel(edao.buildTableModel(edao.getQueryResult()));
            for (int c = 0; c < eventtbl.getColumnCount(); c++) {
                Class<?> col_class = eventtbl.getColumnClass(c);
                eventtbl.setDefaultEditor(col_class, null);
            }
            eventtbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            eventtbl.getColumnModel().getColumn(0).setPreferredWidth(60);
            eventtbl.getColumnModel().getColumn(1).setPreferredWidth(90);
            eventtbl.getColumnModel().getColumn(2).setPreferredWidth(90);
            eventtbl.getColumnModel().getColumn(3).setPreferredWidth(90);
            //eventtbl.getColumnModel().getColumn(4).setPreferredWidth(70);
            eventtbl.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Home Management System", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
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
            java.util.logging.Logger.getLogger(EventManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EventManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EventManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EventManager.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EventManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addeventbtn;
    private javax.swing.JButton deletebtn;
    private com.toedter.calendar.JDateChooser eventdatetxtfield;
    private javax.swing.JTextField eventidtxtfield;
    private javax.swing.JLabel eventinfolab;
    private javax.swing.JTextField eventnametxtfield;
    private javax.swing.JTable eventtbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel noofrecordlab;
    private javax.swing.JButton openbtn;
    private javax.swing.JButton savebtn;
    private com.toedter.calendar.JDateChooser todaydatetxtfield;
    // End of variables declaration//GEN-END:variables
}
