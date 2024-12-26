
// Composite - Apartman (Birçok apartman birimi içerir)
import java.util.ArrayList;
import java.util.List;

public class Apartman extends ApartmanBirim {
    private List<ApartmanBirim> birimler = new ArrayList<>();

    public Apartman(String birimAdi, int alan, String sahip) {
        super(birimAdi, alan, sahip);
    }

    @Override
    public void bilgiGoster() {
        System.out.println("Apartman: " + birimAdi + ", Alan: " + alan + " m², Sahip: " + sahip);
        for (ApartmanBirim birim : birimler) {
            birim.bilgiGoster();  // Alt birimleri göster
        }
    }

    // Alt birimleri ekleme
    public void addBirim(ApartmanBirim birim) {
        birimler.add(birim);
    }

    public void removeBirim(ApartmanBirim birim) {
        birimler.remove(birim);
    }

    @Override
    public double hesaplaAidat() {
        double toplamAidat = 0;
        for (ApartmanBirim birim : birimler) {
            toplamAidat += birim.hesaplaAidat();  // Alt birimlerin aidatlarını toplar
        }
        return toplamAidat;
    }
}