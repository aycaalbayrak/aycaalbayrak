import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class ResidentPanel extends JFrame {
    private String eposta;
    private JLabel nameLabel, surnameLabel, emailLabel, roleLabel;
    private JPanel profilPanel, aidatPanel, bildirimPanel;

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=APTYONETIMSISTEMI;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "ayca";
    private static final String DB_PASSWORD = "ayca123a";

    private JPanel aidatCheckboxPanel;
    private JButton payButton;
    private JButton logoutButton; // Çıkış yap butonu

    public ResidentPanel(String eposta) {
        this.eposta = eposta;

        setTitle("Resident Paneli");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sekmeler için JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Profil Sekmesi
        profilPanel = new JPanel();
        profilPanel.setLayout(new BoxLayout(profilPanel, BoxLayout.Y_AXIS));
        loadProfilePanel();
        tabbedPane.addTab("Profil", profilPanel);

        // Aidat Sekmesi
        aidatPanel = new JPanel();
        aidatPanel.setLayout(new BoxLayout(aidatPanel, BoxLayout.Y_AXIS));
        loadAidatPanel();
        tabbedPane.addTab("Aidat", aidatPanel);

        // Bildirimler Sekmesi
        bildirimPanel = new JPanel();
        bildirimPanel.setLayout(new BoxLayout(bildirimPanel, BoxLayout.Y_AXIS));
        loadBildirimlerPanel();
        tabbedPane.addTab("Bildirimler", bildirimPanel);

        // Sekmeli paneli frame'e ekle
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Profil sekmesi için panel
    private void loadProfilePanel() {
        JLabel titleLabel = new JLabel("Profil Bilgileriniz");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        profilPanel.add(titleLabel);

        // E-posta etiketi ve kutusu
        addLabeledField(profilPanel, "E-posta", eposta);

        // Ad etiketi ve kutusu
        nameLabel = addLabeledField(profilPanel, "Ad", "");

        // Soyad etiketi ve kutusu
        surnameLabel = addLabeledField(profilPanel, "Soyad", "");

        // Rol etiketi ve kutusu
        roleLabel = addLabeledField(profilPanel, "Rol", "");

        // Profil Bilgileri Çekme
        loadProfileDetails();

        // Çıkış Yap Butonu
        logoutButton = new JButton("Çıkış Yap");
        logoutButton.setPreferredSize(new Dimension(100, 30)); // Boyutunu küçültüyoruz
        logoutButton.addActionListener(e -> {
            // Çıkış işlemi yapılabilir
            JOptionPane.showMessageDialog(this, "Çıkış yapılıyor...");

            // Bu pencereyi kapat
            dispose(); // Uygulamayı kapat

            // GirisEkrani'ni aç
            new GirisEkrani().setVisible(true);  // GirisEkrani sınıfını açıyoruz
        });
        profilPanel.add(logoutButton);
    }

    // Profil detaylarını veritabanından çekme
    private void loadProfileDetails() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT adi, soyadi, rol FROM kullanici WHERE eposta = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, eposta);  // E-posta adresini set et
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String adi = rs.getString("adi");
                String soyadi = rs.getString("soyadi");
                String rol = rs.getString("rol");

                nameLabel.setText(adi);
                surnameLabel.setText(soyadi);
                roleLabel.setText(rol);
            } else {
                JOptionPane.showMessageDialog(this, "Profil bilgileri bulunamadı.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Profil sekmesindeki etiket ve kutuları oluşturma
    private JLabel addLabeledField(JPanel panel, String label, String value) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        fieldPanel.setPreferredSize(new Dimension(400, 40));

        JLabel labelField = new JLabel(label + ": ");
        labelField.setFont(new Font("Arial", Font.BOLD, 14));
        fieldPanel.add(labelField);

        JLabel valueField = new JLabel(value);
        valueField.setFont(new Font("Arial", Font.PLAIN, 14));
        fieldPanel.add(valueField);

        panel.add(fieldPanel);
        return valueField;
    }

    // Aidat sekmesi için panel
    private void loadAidatPanel() {
        JLabel aidatLabel = new JLabel("Aidat Bilgileri");
        aidatLabel.setFont(new Font("Arial", Font.BOLD, 18));
        aidatPanel.add(aidatLabel);

        // Aidat bilgilerini veritabanından alıp ekleyelim
        ArrayList<JCheckBox> aidatCheckboxList = new ArrayList<>();
        ArrayList<Integer> aidatAmounts = new ArrayList<>();
        ArrayList<Integer> aidatIds = new ArrayList<>();

        ArrayList<String> aidatDetails = getAidatDetails(aidatCheckboxList, aidatAmounts, aidatIds);

        // Aidatları checkbox olarak ekliyoruz
        aidatCheckboxPanel = new JPanel();
        aidatCheckboxPanel.setLayout(new BoxLayout(aidatCheckboxPanel, BoxLayout.Y_AXIS));

        for (JCheckBox checkBox : aidatCheckboxList) {
            aidatCheckboxPanel.add(checkBox);
        }
        aidatPanel.add(aidatCheckboxPanel);

        // Ödeme butonu
        payButton = new JButton("Ödeme Yap");
        payButton.addActionListener(e -> {
            // Kullanıcı herhangi bir aidat seçti mi kontrol et
            boolean isAnySelected = false;
            ArrayList<Integer> selectedAidatIds = new ArrayList<>();
            ArrayList<Integer> selectedAidatAmounts = new ArrayList<>();

            for (int i = 0; i < aidatCheckboxList.size(); i++) {
                JCheckBox checkBox = aidatCheckboxList.get(i);
                if (checkBox.isSelected()) {
                    isAnySelected = true;
                    selectedAidatIds.add(aidatIds.get(i));
                    selectedAidatAmounts.add(aidatAmounts.get(i));
                }
            }

            if (!isAnySelected) {
                JOptionPane.showMessageDialog(this, "Lütfen bir aidat seçiniz!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            } else {
                // Seçilen aidatları öde
                for (int i = 0; i < selectedAidatIds.size(); i++) {
                    int selectedAidatId = selectedAidatIds.get(i);
                    int selectedAidatAmount = selectedAidatAmounts.get(i);

                    if (isAidatPaid(selectedAidatId)) {
                        JOptionPane.showMessageDialog(this, "Aidat zaten ödenmiş!", "Hata", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (payAidat(selectedAidatId, selectedAidatAmount)) {
                            JOptionPane.showMessageDialog(this, "Ödeme başarılı!");
                            // Ödeme başarılı olduğunda checkbox'ı "Ödendi" olarak işaretle
                            JCheckBox paidCheckbox = aidatCheckboxList.get(i);
                            paidCheckbox.setEnabled(false);  // Seçilemez yap
                            paidCheckbox.setText("Ödendi: " + paidCheckbox.getText());
                        }
                    }
                }
            }
        });
        aidatPanel.add(payButton);
    }

    // Aidat bilgilerini veritabanından çekme
    private ArrayList<String> getAidatDetails(ArrayList<JCheckBox> aidatCheckboxList, ArrayList<Integer> aidatAmounts, ArrayList<Integer> aidatIds) {
        ArrayList<String> aidatDetails = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT id, miktar, durum FROM aidat WHERE kullanici_id = (SELECT id FROM kullanici WHERE eposta = ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, eposta);  // E-posta adresini set et
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int miktar = rs.getInt("miktar");
                String durum = rs.getString("durum");
                String detail = "Aidat Miktarı: " + miktar + " Durum: " + durum;
                aidatDetails.add(detail);

                // Checkbox'ı oluşturuyoruz
                JCheckBox checkBox = new JCheckBox(detail);
                aidatCheckboxList.add(checkBox);
                aidatAmounts.add(miktar);
                aidatIds.add(id);

                // Eğer aidat ödendiyse, checkbox'ı seçilemez yap
                if ("Ödendi".equals(durum)) {
                    checkBox.setEnabled(false);
                    checkBox.setText("Ödendi: " + detail);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
        return aidatDetails;
    }

    // Aidat ödeme işlemi
    private boolean payAidat(int aidatId, int miktar) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Ödeme yapılacak aidatın durumunu "Ödendi" olarak güncelleyen SQL sorgusu
            String updateQuery = "UPDATE aidat SET durum = 'Ödendi' WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setInt(1, aidatId);  // Aidat ID'sini set ettik

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Aidatın ödenip ödenmediğini kontrol etme
    private boolean isAidatPaid(int aidatId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT durum FROM aidat WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, aidatId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String durum = rs.getString("durum");
                return durum.equals("Ödendi");  // Eğer durum 'Ödendi' ise true döner
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Bildirimler sekmesi için panel
    private void loadBildirimlerPanel() {
        JLabel bildirimLabel = new JLabel("Bildirimler");
        bildirimLabel.setFont(new Font("Arial", Font.BOLD, 18));
        bildirimPanel.add(bildirimLabel);

        // Bildirimleri veritabanından alıp ekleyelim
        ArrayList<String> bildirimList = getBildirimler();
        for (String bildirim : bildirimList) {
            JLabel bildirimMessageLabel = new JLabel(bildirim);
            bildirimPanel.add(bildirimMessageLabel);
        }
    }

    // Bildirimleri veritabanından çekme
    private ArrayList<String> getBildirimler() {
        ArrayList<String> bildirimList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT mesaj FROM bildirimler WHERE kullanici_id = (SELECT id FROM kullanici WHERE eposta = ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, eposta);  // E-posta adresini set et
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String mesaj = rs.getString("mesaj");
                bildirimList.add(mesaj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
        return bildirimList;
    }

    public static void main(String[] args) {
        String eposta = "test@example.com"; // Burada örnek bir e-posta adresi kullanılabilir
        new ResidentPanel(eposta).setVisible(true);
    }
}