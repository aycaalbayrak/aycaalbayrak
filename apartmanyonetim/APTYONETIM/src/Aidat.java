import java.time.LocalDate;

public class Aidat {
    private int id;
    private String birimAdi;   // Apartman birimi adı (Daire, Dükkan vs.)
    private double tutar;      // Aidat tutarı
    private LocalDate odemeTarihi; // Aidat ödeme tarihi

    // Constructor
    public Aidat(int id, String birimAdi, double tutar, LocalDate odemeTarihi) {
        this.id = id;
        this.birimAdi = birimAdi;
        this.tutar = tutar;
        this.odemeTarihi = odemeTarihi;
    }

    // Getter ve Setter metotları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirimAdi() {
        return birimAdi;
    }

    public void setBirimAdi(String birimAdi) {
        this.birimAdi = birimAdi;
    }

    public double getTutar() {
        return tutar;
    }

    public void setTutar(double tutar) {
        this.tutar = tutar;
    }

    public LocalDate getOdemeTarihi() {
        return odemeTarihi;
    }

    public void setOdemeTarihi(LocalDate odemeTarihi) {
        this.odemeTarihi = odemeTarihi;
    }
}
