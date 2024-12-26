import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AidatVeritabani {

    private VeritabaniBaglantisi veritabaniBaglantisi;

    public AidatVeritabani() {
        // Veritabanı bağlantısı nesnesini alıyoruz
        this.veritabaniBaglantisi = VeritabaniBaglantisi.getInstance();
    }

    // Aidat ekleme
    public boolean aidatEkle(Aidat aidat) {
        String query = "INSERT INTO Aidatlar (birimAdi, tutar, odemeTarihi) VALUES (?, ?, ?)";
        try (Connection conn = veritabaniBaglantisi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, aidat.getBirimAdi());
            stmt.setDouble(2, aidat.getTutar());
            stmt.setDate(3, Date.valueOf(aidat.getOdemeTarihi()));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



// Aidatları listeleme
    public List<Aidat> aidatlariListele() {
        List<Aidat> aidatlar = new ArrayList<>();
        String query = "SELECT * FROM Aidatlar";

        try (Connection conn = veritabaniBaglantisi.getConnection(); // Veritabanı bağlantısını alıyoruz
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String birimAdi = rs.getString("birimAdi");
                double tutar = rs.getDouble("tutar");
                LocalDate odemeTarihi = rs.getDate("odemeTarihi").toLocalDate(); 

                Aidat aidat = new Aidat(id, birimAdi, tutar, odemeTarihi);
                aidatlar.add(aidat);
            }

            // Eğer liste boşsa, kullanıcıya mesaj gösteriyoruz
            if (aidatlar.isEmpty()) {
                System.out.println("Hiç aidat kaydı bulunamadı.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aidatlar;
    }

    // Aidat güncelleme
    public void aidatGuncelle(Aidat aidat) {
        String query = "UPDATE Aidatlar SET birimAdi = ?, tutar = ?, odemeTarihi = ? WHERE id = ?";

        try (Connection conn = veritabaniBaglantisi.getConnection(); // Veritabanı bağlantısını alıyoruz
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, aidat.getBirimAdi());
            stmt.setDouble(2, aidat.getTutar());
            stmt.setDate(3, Date.valueOf(aidat.getOdemeTarihi()));
            stmt.setInt(4, aidat.getId());

            stmt.executeUpdate();
            System.out.println("Aidat başarıyla güncellendi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Aidat silme
    public void aidatSil(int id) {
        String query = "DELETE FROM Aidatlar WHERE id = ?";

        try (Connection conn = veritabaniBaglantisi.getConnection(); // Veritabanı bağlantısını alıyoruz
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Aidat başarıyla silindi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
