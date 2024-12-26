import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPanel extends JFrame {
    private JTable userTable;
    private DefaultTableModel userModel;
    private JButton addUserButton, deleteUserButton, sendNotificationButton, addFeeButton, logoutButton, updateUserButton;

    // Veritabanı bağlantısını merkezi hale getirdik
    private Connection conn;

    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(700, 600);  // Panel boyutunu arttırdık
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Ekranın ortasında açılacak şekilde ayarladık.

        // Panel düzeni
        setLayout(new BorderLayout());

        // Kullanıcı tablosu
        userTable = new JTable();
        userModel = new DefaultTableModel();
        userTable.setModel(userModel);
        userModel.addColumn("ID");
        userModel.addColumn("Adı");
        userModel.addColumn("Soyadı");
        userModel.addColumn("E-posta");
        userModel.addColumn("Şifre");
        userModel.addColumn("Rol");
        userModel.addColumn("Aidat Durumu"); // Yeni Kolon

        JScrollPane scrollPane = new JScrollPane(userTable);
        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  // Tablonun sütunlarını otomatik boyutlandırma
        add(scrollPane, BorderLayout.CENTER);

        // Butonlar ve panel düzeni
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));  // 3 satır, 2 sütun düzeni ile butonları düzenledik
        add(panel, BorderLayout.SOUTH);

        addUserButton = new JButton("Kullanıcı Ekle");
        deleteUserButton = new JButton("Kullanıcı Sil");
        sendNotificationButton = new JButton("Bildirim Gönder");
        addFeeButton = new JButton("Aidat Ekle");
        logoutButton = new JButton("Çıkış Yap");  // Çıkış yap butonu
        updateUserButton = new JButton("Kullanıcı Güncelle"); // Kullanıcı güncelleme butonu

        panel.add(addUserButton);
        panel.add(deleteUserButton);
        panel.add(sendNotificationButton);
        panel.add(addFeeButton);
        panel.add(updateUserButton);  // Kullanıcı güncelleme butonunu ekledik
        panel.add(logoutButton);  // Çıkış yap butonunu ekledik

        // Buton Aksiyonları
        addUserButton.addActionListener(e -> openSignUpScreen()); // Kullanıcı Ekle butonuna basıldığında yönlendirme
        deleteUserButton.addActionListener(e -> deleteUser());
        sendNotificationButton.addActionListener(e -> sendNotification());
        addFeeButton.addActionListener(e -> addFee());
        logoutButton.addActionListener(e -> logout()); // Çıkış yap butonuna tıklandığında çıkış işlemi
        updateUserButton.addActionListener(e -> updateUser()); // Kullanıcı Güncelle butonuna tıklanıldığında kullanıcı güncelleme işlemi

        // Veritabanı bağlantısını kuruyoruz
        establishDatabaseConnection();

        // Veritabanından kullanıcıları yükle
        loadUsersFromDatabase();
    }

    private void openSignUpScreen() {
        // "Kullanıcı Ekle" butonuna tıklanıldığında Üye Ol ekranını açıyoruz
        new UyeOlEkrani();
        this.setVisible(false); // AdminPanel'i gizle
    }

    private void establishDatabaseConnection() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;databaseName=APTYONETIMSISTEMI;encrypt=true;trustServerCertificate=true;",
                    "ayca", "ayca123a");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Veritabanı bağlantısı hatası: " + ex.getMessage());
        }
    }

    private void loadUsersFromDatabase() {
        try {
            String query = "SELECT k.id, k.adi, k.soyadi, k.eposta, k.sifre, k.rol, a.durum AS aidat_durumu " +
                    "FROM kullanici k " +
                    "LEFT JOIN aidat a ON k.id = a.kullanici_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            userModel.setRowCount(0);  // Tabloyu temizle

            while (rs.next()) {
                int id = rs.getInt("id");
                String adi = rs.getString("adi");
                String soyadi = rs.getString("soyadi");
                String eposta = rs.getString("eposta");
                String sifre = rs.getString("sifre");
                String rol = rs.getString("rol");
                String aidatDurumu = rs.getString("aidat_durumu");

                userModel.addRow(new Object[]{id, adi, soyadi, eposta, sifre, rol, aidatDurumu});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + ex.getMessage());
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) userModel.getValueAt(selectedRow, 0);
            try {
                String query = "DELETE FROM kullanici WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, userId);
                stmt.executeUpdate();
                loadUsersFromDatabase();
                JOptionPane.showMessageDialog(this, "Kullanıcı başarıyla silindi.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Kullanıcı silinirken hata oluştu: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir kullanıcı seçin.");
        }
    }

    private void sendNotification() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) userModel.getValueAt(selectedRow, 0);
            String message = JOptionPane.showInputDialog("Bildirim Mesajı:");
            if (message != null && !message.trim().isEmpty()) {
                try {
                    String query = "INSERT INTO bildirimler (kullanici_id, mesaj, tarih) VALUES (?, ?, GETDATE())";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, userId);
                    stmt.setString(2, message);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Bildirim başarıyla gönderildi.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Bildirim gönderilirken hata oluştu: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir mesaj girin.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen bir kullanıcı seçin.");
        }
    }

    private void addFee() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) userModel.getValueAt(selectedRow, 0);

            // Kullanıcıdan aidat tutarını alıyoruz
            String amount = JOptionPane.showInputDialog("Aidat Tutarı:");
            if (amount == null || amount.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Geçerli bir aidat tutarı girin.");
                return;
            }

            // Güvenlik ve Temizlik ücretlerini eklemek için checkbox'lar
            JCheckBox securityCheckBox = new JCheckBox("Güvenlik Ücreti Ekle");
            JCheckBox cleaningCheckBox = new JCheckBox("Temizlik Ücreti Ekle");

            // Varsayılan olarak 50'yi güvenlik ve temizlik ücreti olarak belirliyoruz
            double securityFee = 50;
            double cleaningFee = 50;

            // Dialog penceresi oluşturuluyor
            JPanel panel = new JPanel();
            panel.add(securityCheckBox);
            panel.add(cleaningCheckBox);

            int option = JOptionPane.showConfirmDialog(this, panel, "Aidat ve Ekstra Ücretler", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                // Aidat tutarını ve ekstra ücretleri hesaplıyoruz
                double aidatAmount = Double.parseDouble(amount);
                if (securityCheckBox.isSelected()) {
                    aidatAmount += securityFee;  // Güvenlik ücretini ekliyoruz
                }
                if (cleaningCheckBox.isSelected()) {
                    aidatAmount += cleaningFee;  // Temizlik ücretini ekliyoruz
                }

                // Veritabanına eklemek için gerekli işlemi yapıyoruz
                try {
                    String query = "INSERT INTO aidat (miktar, durum, odeme_tarihi, kullanici_id, guvenlik_ucreti, temizlik_ucreti) " +
                            "VALUES (?, 'Bekliyor', GETDATE(), ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setDouble(1, aidatAmount); // Güncellenmiş aidat tutarını ekliyoruz
                    stmt.setInt(2, userId);
                    stmt.setDouble(3, securityCheckBox.isSelected() ? securityFee : 0);
                    stmt.setDouble(4, cleaningCheckBox.isSelected() ? cleaningFee : 0);

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Aidat başarıyla eklendi.");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Aidat eklenirken hata oluştu: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen bir kullanıcı seçin.");
        }
    }

    private void logout() {
        // AdminPanel'i gizleyip Giriş Ekranına yönlendiriyoruz
        this.setVisible(false); // AdminPanel'i gizle
        new GirisEkrani(); // Giriş ekranını aç
    }

    private void updateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) userModel.getValueAt(selectedRow, 0);
            String currentFirstName = (String) userModel.getValueAt(selectedRow, 1);
            String currentLastName = (String) userModel.getValueAt(selectedRow, 2);
            String currentEmail = (String) userModel.getValueAt(selectedRow, 3);
            String currentPassword = (String) userModel.getValueAt(selectedRow, 4);  // Şifreyi düz metin olarak alıyoruz
            String currentRole = (String) userModel.getValueAt(selectedRow, 5);

            // Güncelleme ekranı için JTextField'lar
            JTextField firstNameField = new JTextField(currentFirstName, 20);
            JTextField lastNameField = new JTextField(currentLastName, 20);
            JTextField emailField = new JTextField(currentEmail, 20);
            JTextField passwordField = new JTextField(currentPassword, 20);  // Şifreyi JTextField olarak değiştiriyoruz
            JTextField roleField = new JTextField(currentRole, 20);

            // Formu oluştur
            JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
            panel.add(new JLabel("Adı:"));
            panel.add(firstNameField);
            panel.add(new JLabel("Soyadı:"));
            panel.add(lastNameField);
            panel.add(new JLabel("E-posta:"));
            panel.add(emailField);
            panel.add(new JLabel("Şifre:"));
            panel.add(passwordField);  // Şifreyi göstermek için JTextField kullanıyoruz
            panel.add(new JLabel("Rol:"));
            panel.add(roleField);

            int option = JOptionPane.showConfirmDialog(this, panel, "Kullanıcı Güncelle", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                // Güncellenen bilgileri al
                String updatedFirstName = firstNameField.getText();
                String updatedLastName = lastNameField.getText();
                String updatedEmail = emailField.getText();
                String updatedPassword = passwordField.getText();  // Şifreyi JTextField'dan alıyoruz
                String updatedRole = roleField.getText();

                try {
                    // Veritabanı güncelleme sorgusu
                    String query = "UPDATE kullanici SET adi = ?, soyadi = ?, eposta = ?, sifre = ?, rol = ? WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, updatedFirstName);
                    stmt.setString(2, updatedLastName);
                    stmt.setString(3, updatedEmail);
                    stmt.setString(4, updatedPassword);  // Şifreyi düz metin olarak güncelliyoruz
                    stmt.setString(5, updatedRole);
                    stmt.setInt(6, userId);

                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Kullanıcı başarıyla güncellendi.");
                        loadUsersFromDatabase(); // Güncellenen veriyi tabloya yansıt
                    } else {
                        JOptionPane.showMessageDialog(this, "Kullanıcı güncellenemedi.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Güncellenirken hata oluştu: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen güncellemek için bir kullanıcı seçin.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminPanel adminPanel = new AdminPanel();
            adminPanel.setVisible(true);
        });
    }
}
