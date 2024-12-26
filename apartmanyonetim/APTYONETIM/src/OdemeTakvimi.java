import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OdemeTakvimi {
    private List<LocalDate> odemeTarihler;  // Ödeme tarihleri

    public OdemeTakvimi() {
        this.odemeTarihler = new ArrayList<>();
    }

    public void ekleOdemeTarihi(LocalDate tarih) {
        odemeTarihler.add(tarih);
    }

    public void odemeHatirlatmasiYap(Kullanici kullanici) {
        LocalDate bugun = LocalDate.now();
        for (LocalDate tarih : odemeTarihler) {
            if (tarih.isEqual(bugun)) {
                System.out.println(kullanici.getAd() + " için ödeme hatırlatması: Bugün aidat ödemesi yapılması gerekiyor.");
            }
        }
    }

    public List<LocalDate> getOdemeTarihler() {
        return odemeTarihler;
    }
}
