import java.sql.Connection;

public class main {
    public static void main(String[] args) {
        try {
            Connection conn = VeritabaniBaglantisi.getInstance().getConnection();
            if (conn != null) {
                System.out.println("Bağlantı başarıyla kuruldu!");
            } else {
                System.out.println("Bağlantı kurulamadı!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
