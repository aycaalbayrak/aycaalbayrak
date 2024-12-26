import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KullaniciVeritabani {
    private Connection connection;

    public KullaniciVeritabani() {
        this.connection = VeritabaniBaglantisi.getInstance().getConnection();
    }

    // Kullanıcıyı veritabanına ekler
    public void kullaniciEkle(Kullanici kullanici) {
        String sql = "INSERT INTO Kullanici (ad, soyad, email, sifre, durum) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, kullanici.getAd());
            stmt.setString(2, kullanici.getSoyad());
            stmt.setString(3, kullanici.getEmail());
            stmt.setString(4, kullanici.getSifre());
            stmt.setString(5, kullanici.durum.getClass().getSimpleName());  // Durumun sınıf adını kaydediyoruz
            stmt.executeUpdate();
            System.out.println("Kullanıcı başarıyla eklendi.");
        } catch (SQLException e) {
            System.err.println("Kullanıcı eklenirken hata oluştu: " + e.getMessage());
        }
    }

    // Kullanıcıyı ID'sine göre siler
    public void kullaniciSil(int kullaniciId) {
        String sql = "DELETE FROM Kullanici WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, kullaniciId);
            stmt.executeUpdate();
            System.out.println("Kullanıcı başarıyla silindi.");
        } catch (SQLException e) {
            System.err.println("Kullanıcı silinirken hata oluştu: " + e.getMessage());
        }
    }

    // Kullanıcıyı ID'sine göre günceller
    public void kullaniciGuncelle(Kullanici kullanici) {
        String sql = "UPDATE Kullanici SET ad = ?, soyad = ?, email = ?, sifre = ?, durum = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, kullanici.getAd());
            stmt.setString(2, kullanici.getSoyad());
            stmt.setString(3, kullanici.getEmail());
            stmt.setString(4, kullanici.getSifre());
            stmt.setString(5, kullanici.durum.getClass().getSimpleName());
            stmt.setInt(6, kullanici.getId());  // Kullanıcı ID'sine göre güncelleme yapılır
            stmt.executeUpdate();
            System.out.println("Kullanıcı başarıyla güncellendi.");
        } catch (SQLException e) {
            System.err.println("Kullanıcı güncellenirken hata oluştu: " + e.getMessage());
        }
    }

    // Kullanıcıyı email adresine göre sorgular
    public Kullanici kullaniciSorgula(String email) {
        String sql = "SELECT * FROM Kullanici WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Kullanıcıyı sonuçlardan alıp döndürüyoruz
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String sifre = rs.getString("sifre");
                String durum = rs.getString("durum");

                Kullanici kullanici = EntityFactory.createKullanici(durum, ad,soyad, email, sifre);
                return kullanici;
            } else {
                System.out.println("Kullanıcı bulunamadı.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Kullanıcı sorgulanırken hata oluştu: " + e.getMessage());
            return null;
        }
    }
}