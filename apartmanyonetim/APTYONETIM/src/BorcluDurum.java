
public class BorcluDurum implements KullaniciDurumu {
    private AidatHatirlatici hatirlatici;

    public BorcluDurum(AidatHatirlatici hatirlatici) {
        this.hatirlatici = hatirlatici;
    }

    @Override
    public void durumGuncelle(Kullanici kullanici) {
        System.out.println(kullanici.getAd() + " Borçlu durumuna geçti.Aidat ödemesi gerekiyor!");
        hatirlatici.notifyObservers(kullanici.getAd() + " aidat ödemeli.");
    }
}

