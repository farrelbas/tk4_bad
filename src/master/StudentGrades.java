/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master;

/**
 *
 * @author JIC
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class StudentGrades extends javax.swing.JFrame {

    private JTextField nameField;
    private JTextField nimField;
    private JTextField taskField;
    private JTextField quizField;
    private JTextField utsField;
    private JTextField uasField;
    private JLabel averageLabel;
    private JLabel gradeLabel;
    private JLabel statusLabel;

    /**
     * Creates new form sudentMaster
     */
    public StudentGrades() {
        setTitle("Student Grades");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Nama:");
        nameLabel.setBounds(20, 20, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 20, 150, 25);
        add(nameField);

        JLabel nimLabel = new JLabel("NIM:");
        nimLabel.setBounds(20, 60, 100, 25);
        add(nimLabel);

        nimField = new JTextField();
        nimField.setBounds(120, 60, 150, 25);
        add(nimField);

        JLabel taskLabel = new JLabel("Nilai Tugas:");
        taskLabel.setBounds(20, 100, 100, 25);
        add(taskLabel);

        taskField = new JTextField();
        taskField.setBounds(120, 100, 150, 25);
        add(taskField);

        JLabel quizLabel = new JLabel("Nilai Kuis:");
        quizLabel.setBounds(20, 140, 100, 25);
        add(quizLabel);

        quizField = new JTextField();
        quizField.setBounds(120, 140, 150, 25);
        add(quizField);

        JLabel utsLabel = new JLabel("Nilai UTS:");
        utsLabel.setBounds(20, 180, 100, 25);
        add(utsLabel);

        utsField = new JTextField();
        utsField.setBounds(120, 180, 150, 25);
        add(utsField);

        JLabel uasLabel = new JLabel("Nilai UAS:");
        uasLabel.setBounds(20, 220, 100, 25);
        add(uasLabel);

        uasField = new JTextField();
        uasField.setBounds(120, 220, 150, 25);
        add(uasField);

        JButton hitungButton = new JButton("HITUNG");
        hitungButton.setBounds(300, 20, 100, 25);
        add(hitungButton);

        JButton resetButton = new JButton("RESET");
        resetButton.setBounds(300, 60, 100, 25);
        add(resetButton);

        JButton simpanButton = new JButton("SIMPAN");
        simpanButton.setBounds(300, 100, 100, 25);
        add(simpanButton);

        JLabel averageLabelTitle = new JLabel("Rerata:");
        averageLabelTitle.setBounds(300, 140, 100, 25);
        add(averageLabelTitle);

        averageLabel = new JLabel();
        averageLabel.setBounds(300, 160, 100, 25);
        add(averageLabel);

        JLabel gradeLabelTitle = new JLabel("Grade:");
        gradeLabelTitle.setBounds(300, 180, 100, 25);
        add(gradeLabelTitle);

        gradeLabel = new JLabel();
        gradeLabel.setBounds(300, 200, 100, 25);
        add(gradeLabel);

        JLabel statusLabelTitle = new JLabel("Keterangan:");
        statusLabelTitle.setBounds(300, 220, 100, 25);
        add(statusLabelTitle);

        statusLabel = new JLabel();
        statusLabel.setBounds(300, 240, 100, 25);
        add(statusLabel);

        hitungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungRerataDanGrade();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanData();
            }
        });
    }

    private void hitungRerataDanGrade() {
        try {
            int nilaiTask = Integer.parseInt(taskField.getText());
            int nilaiQuiz = Integer.parseInt(quizField.getText());
            int nilaiUTS = Integer.parseInt(utsField.getText());
            int nilaiUAS = Integer.parseInt(uasField.getText());

            float rerata = (nilaiTask + nilaiQuiz + nilaiUTS + nilaiUAS) / 4.0f;
            averageLabel.setText(String.valueOf(rerata));

            String grade;
            String keterangan;
            if (rerata >= 85) {
                grade = "A";
                keterangan = "Dinyatakan Lulus";
            } else if (rerata >= 70) {
                grade = "B";
                keterangan = "Dinyatakan Lulus";
            } else if (rerata >= 55) {
                grade = "C";
                keterangan = "Dinyatakan Lulus";
            } else if (rerata >= 40) {
                grade = "D";
                keterangan = "Dinyatakan Tidak Lulus";
            } else {
                grade = "E";
                keterangan = "Dinyatakan Tidak Lulus";
            }

            gradeLabel.setText(grade);
            statusLabel.setText(keterangan);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid. Pastikan semua nilai diisi dengan angka.");
        }
    }

    private void resetFields() {
        nameField.setText("");
        nimField.setText("");
        taskField.setText("");
        quizField.setText("");
        utsField.setText("");
        uasField.setText("");
        averageLabel.setText("");
        gradeLabel.setText("");
        statusLabel.setText("");
    }

    private void simpanData() {
        try (Connection conn = connection.database.getConnection()) {
            String sql = "INSERT INTO tb_student (student_name, student_nim, student_task, student_quiz, student_uts, student_uas, grade, average_grade, status, student_inserted_at, student_last_updated_at, student_softdel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, nimField.getText());
            stmt.setInt(3, Integer.parseInt(taskField.getText()));
            stmt.setInt(4, Integer.parseInt(quizField.getText()));
            stmt.setInt(5, Integer.parseInt(utsField.getText()));
            stmt.setInt(6, Integer.parseInt(uasField.getText()));
            stmt.setString(7, gradeLabel.getText());
            stmt.setFloat(8, Float.parseFloat(averageLabel.getText()));
            stmt.setString(9, statusLabel.getText());
            stmt.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(12, 0);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");

        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data. " + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(StudentGrades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentGrades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentGrades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentGrades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentGrades().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
