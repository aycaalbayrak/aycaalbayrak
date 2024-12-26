import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

// Rol seçimini kaldırıyoruz
public class UyeOlEkrani {
    private JFrame frame;
    private JTextField nameField, surnameField, emailField;
    private JPasswordField passwordField;

    public UyeOlEkrani() {
        frame = new JFrame("Üye Olma Ekranı");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Font ayarları
        Font font = new Font("Arial", Font.PLAIN, 18);

        // Başlık
        JLabel headerLabel = new JLabel("Yeni Üye Kaydı", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 123, 255));

        // Kullanıcı bilgisi alanları
        JLabel nameLabel = new JLabel("Ad:");
        nameLabel.setFont(font);
        nameField = new JTextField(20);
        nameField.setFont(font);
        nameField.setPreferredSize(new Dimension(350, 40));

        JLabel surnameLabel = new JLabel("Soyad:");
        surnameLabel.setFont(font);
        surnameField = new JTextField(20);
        surnameField.setFont(font);
        surnameField.setPreferredSize(new Dimension(350, 40));

        JLabel emailLabel = new JLabel("E-posta:");
        emailLabel.setFont(font);
        emailField = new JTextField(20);
        emailField.setFont(font);
        emailField.setPreferredSize(new Dimension(350, 40));

        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setFont(font);
        passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        passwordField.setPreferredSize(new Dimension(350, 40));

        // Üye Ol butonu
        JButton signUpButton = new JButton("Üye Ol");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 20));
        signUpButton.setBackground(new Color(0, 123, 255));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setPreferredSize(new Dimension(250, 50));

        // Yeni bir JPanel ekliyoruz
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel düzeni
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        panel.add(headerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(surnameLabel, gbc);
        gbc.gridx = 1;
        panel.add(surnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        panel.add(signUpButton, gbc);

        // Frame'e panel ekliyoruz
        frame.add(panel);
        frame.setLocationRelativeTo(null); // Ortaya yerleştir
        frame.setVisible(true);

        // Üye Ol butonuna tıklama işlemi
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Kullanıcı bilgilerini doğrulama
                if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Tüm alanları doldurduğunuzdan emin olun.");
                    return;
                }

                // E-posta formatı doğrulama
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(frame, "Geçersiz e-posta formatı.");
                    return;
                }

                // Şifre doğrulama (min 6 karakter)
                if (password.length() < 6) {
                    JOptionPane.showMessageDialog(frame, "Şifre en az 6 karakter olmalıdır.");
                    return;
                }

                // Veritabanına kaydetme işlemi
                saveUserToDatabase(name, surname, email, password);

                JOptionPane.showMessageDialog(frame, "Üyelik başarılı.");
                frame.setVisible(false);
                new GirisEkrani(); // Geri dönüyoruz
            }
        });
    }

    private boolean isValidEmail(String email) {
        // E-posta formatı kontrolü
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(regex, email);
    }

    private void saveUserToDatabase(String name, String surname, String email, String password) {
        // Veritabanı bağlantısı için gerekli bilgileri tanımlıyoruz
        String url = "jdbc:sqlserver://localhost:1433;databaseName=APTYONETIMSISTEMI;encrypt=true;trustServerCertificate=true;";
        String user = "ayca";
        String pass = "ayca123a";

        // Veritabanına bağlanma ve kullanıcı ekleme
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Veritabanı bağlantısını oluşturuyoruz
            conn = DriverManager.getConnection(url, user, pass);

            // SQL sorgusu: Yeni bir kullanıcı ekle
            String sql = "INSERT INTO kullanici (adi, soyadi, eposta, sifre, rol) VALUES (?, ?, ?, ?, 'Resident')";

            // PreparedStatement ile sorguyu hazırlıyoruz
            pstmt = conn.prepareStatement(sql);

            // Parametreleri set ediyoruz
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, email);
            pstmt.setString(4, password);

            // Sorguyu çalıştırıyoruz
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Kullanıcı başarıyla kaydedildi!");
            } else {
                System.out.println("Kullanıcı kaydedilemedi.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new UyeOlEkrani();
    }
}
