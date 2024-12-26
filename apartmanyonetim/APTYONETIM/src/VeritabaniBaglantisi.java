import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglantisi {
    private static VeritabaniBaglantisi instance;
    private String baglantiUrl;
    private Connection connection;

    private VeritabaniBaglantisi() {
        this.baglantiUrl = "jdbc:sqlserver://localhost:1433;databaseName=APTYONETIMSISTEMI;encrypt=true;trustServerCertificate=true;";
    }

    public static VeritabaniBaglantisi getInstance() {
        if (instance == null) {
            synchronized (VeritabaniBaglantisi.class) {
                if (instance == null) {
                    instance = new VeritabaniBaglantisi();
                }
            }
        }
        return instance;
    }

    public void baglantiKur() {
        try {
            connection = DriverManager.getConnection(baglantiUrl, "ayca", "ayca123a"); // Gerçek veritabanı kullanıcı adı ve şifreyi kullanın
            System.out.println("Veritabanına bağlanıldı.");
        } catch (SQLException e) {
            System.err.println("Veritabanı bağlantısı sırasında hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (connection == null) {
            System.err.println("Bağlantı henüz kurulmamış.");
            baglantiKur(); // Otomatik olarak bağlantıyı kurmayı deneyebilir.
        }
        return connection;
    }


    public void baglantiKapat() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            }
        } catch (SQLException e) {
            System.err.println("Bağlantı kapatılırken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}