public class PasifDurum implements KullaniciDurumu {
    @Override
    public void durumGuncelle(Kullanici kullanici) {
        System.out.println(kullanici.getAd() + " artık Pasif durumunda.");
    }
}
