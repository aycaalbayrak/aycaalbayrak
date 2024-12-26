import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GirisEkrani extends JFrame{
    private JFrame frame;
    private JTextField epostaField;
    private JPasswordField passwordField;

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=APTYONETIMSISTEMI;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "ayca";
    private static final String DB_PASSWORD = "ayca123a";

    public GirisEkrani() {
        frame = new JFrame("Giriş Ekranı");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Font font = new Font("Arial", Font.PLAIN, 14);

        JLabel titleLabel = new JLabel("Apartman Yönetim Sistemi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 123, 255));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(400, 40));
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        panel.add(titleLabel, gbc);

        JLabel epostaLabel = new JLabel("E-posta:");
        epostaLabel.setFont(font);
        epostaField = new JTextField(20);
        epostaField.setFont(font);
        epostaField.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255)));

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(epostaLabel, gbc);
        gbc.gridx = 1;
        panel.add(epostaField, gbc);

        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setFont(font);
        passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255)));

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Giriş Yap");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {
            String eposta = epostaField.getText();
            String password = new String(passwordField.getPassword());

            // E-posta ve şifre kontrolü
            if (eposta.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "E-posta veya şifre boş olamaz.");
                return;
            }

            String rol = login(eposta, password);

            if (rol != null) {
                frame.dispose();  // Giriş başarılı ise giriş ekranını kapat
                // Kullanıcı rolüne göre ilgili paneli aç
                if (rol.equals("Admin")) {
                    AdminPanel adminPanel = new AdminPanel();
                    adminPanel.setVisible(true);
                } else if (rol.equals("Resident")) {
                    ResidentPanel residentPanel = new ResidentPanel(eposta); // Resident paneline yönlendirme
                    residentPanel.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Geçersiz e-posta veya şifre.");
            }
        });

        JButton signUpButton = new JButton("Üye Ol");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setBackground(new Color(220, 53, 69));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);

        signUpButton.addActionListener(e -> {
            new UyeOlEkrani();  // Üye ol ekranı açılır
            frame.dispose();  // Giriş ekranı kapatılır
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        panel.add(loginButton, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 20, 0);
        panel.add(signUpButton, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // E-posta ve şifre kontrolü
    private String login(String eposta, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT rol FROM kullanici WHERE eposta = ? AND sifre = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, eposta);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("rol");  // Kullanıcının rolünü döndür
            } else {
                JOptionPane.showMessageDialog(frame, "E-posta veya şifre yanlış.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static void main(String[] args) {
        new GirisEkrani();
    }
}
