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
    private JLabel nameLabel;
    private JLabel nimLabel;
    private JLabel averageLabel;
    private JLabel gradeLabel;
    private JLabel statusLabel;

    /**
     * Creates new form sudentMaster
     */
    public StudentGrades() {
        setTitle("Student Grades");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel name = new JLabel("Nama:");
        name.setBounds(20, 20, 100, 25);
        add(name);

        nameField = new JTextField();
        nameField.setBounds(120, 20, 150, 25);
        add(nameField);

        JLabel nim = new JLabel("NIM:");
        nim.setBounds(20, 60, 100, 25);
        add(nim);

        nimField = new JTextField();
        nimField.setBounds(120, 60, 150, 25);
        add(nimField);

        JLabel task = new JLabel("Nilai Tugas:");
        task.setBounds(20, 100, 100, 25);
        add(task);

        taskField = new JTextField();
        taskField.setBounds(120, 100, 150, 25);
        add(taskField);

        JLabel quiz = new JLabel("Nilai Kuis:");
        quiz.setBounds(20, 140, 100, 25);
        add(quiz);

        quizField = new JTextField();
        quizField.setBounds(120, 140, 150, 25);
        add(quizField);

        JLabel uts = new JLabel("Nilai UTS:");
        uts.setBounds(20, 180, 100, 25);
        add(uts);

        utsField = new JTextField();
        utsField.setBounds(120, 180, 150, 25);
        add(utsField);

        JLabel uas = new JLabel("Nilai UAS:");
        uas.setBounds(20, 220, 100, 25);
        add(uas);

        uasField = new JTextField();
        uasField.setBounds(120, 220, 150, 25);
        add(uasField);

        JLabel nameLabelTitle = new JLabel("Nama :");
        nameLabelTitle.setBounds(300, 20, 100, 25);
        add(nameLabelTitle);

        nameLabel = new JLabel();
        nameLabel.setBounds(350, 20, 100, 25);
        add(nameLabel);

        JLabel nameNIMTitle = new JLabel("NIM :");
        nameNIMTitle.setBounds(300, 60, 100, 25);
        add(nameNIMTitle);

        nimLabel = new JLabel();
        nimLabel.setBounds(350, 60, 100, 25);
        add(nimLabel);

        JLabel averageLabelTitle = new JLabel("Rata - rata :");
        averageLabelTitle.setBounds(300, 100, 100, 25);
        add(averageLabelTitle);

        averageLabel = new JLabel();
        averageLabel.setBounds(380, 100, 100, 25);
        add(averageLabel);

        JLabel gradeLabelTitle = new JLabel("Grade :");
        gradeLabelTitle.setBounds(300, 140, 100, 25);
        add(gradeLabelTitle);

        gradeLabel = new JLabel();
        gradeLabel.setBounds(350, 140, 100, 25);
        add(gradeLabel);

        JLabel statusLabelTitle = new JLabel("Keterangan :");
        statusLabelTitle.setBounds(300, 180, 100, 25);
        add(statusLabelTitle);

        statusLabel = new JLabel();
        statusLabel.setBounds(380, 180, 100, 25);
        add(statusLabel);

        JButton hitungButton = new JButton("HITUNG");
        hitungButton.setBounds(40, 280, 100, 25);
        add(hitungButton);

        JButton resetButton = new JButton("RESET");
        resetButton.setBounds(160, 280, 100, 25);
        add(resetButton);

        JButton simpanButton = new JButton("SIMPAN");
        simpanButton.setBounds(280, 280, 100, 25);
        add(simpanButton);

        hitungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countAverage();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });
    }

    private void countAverage() {
        try {
            String studentName = nameField.getText();
            String studentNIM = nimField.getText();
            int nilaiTask = Integer.parseInt(taskField.getText());
            int nilaiQuiz = Integer.parseInt(quizField.getText());
            int nilaiUTS = Integer.parseInt(utsField.getText());
            int nilaiUAS = Integer.parseInt(uasField.getText());

            float average = (nilaiTask + nilaiQuiz + nilaiUTS + nilaiUAS) / 4.0f;
            averageLabel.setText(String.valueOf(average));

            String grade;
            String keterangan;
            if (average >= 85) {
                grade = "A";
                keterangan = "Dinyatakan Lulus";
            } else if (average >= 70) {
                grade = "B";
                keterangan = "Dinyatakan Lulus";
            } else if (average >= 55) {
                grade = "C";
                keterangan = "Dinyatakan Lulus";
            } else if (average >= 40) {
                grade = "D";
                keterangan = "Dinyatakan Tidak Lulus";
            } else {
                grade = "E";
                keterangan = "Dinyatakan Tidak Lulus";
            }

            gradeLabel.setText(grade);
            statusLabel.setText(keterangan);
            nameLabel.setText(studentName);
            nimLabel.setText(studentNIM);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid. Pastikan semua nilai diisi dengan angka.");
        }
    }

    private void reset() {
        nameField.setText("");
        nimField.setText("");
        taskField.setText("");
        quizField.setText("");
        utsField.setText("");
        uasField.setText("");
        averageLabel.setText("");
        gradeLabel.setText("");
        statusLabel.setText("");
        nameLabel.setText("");
        nimLabel.setText("");
    }

    private void saveData() {
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
