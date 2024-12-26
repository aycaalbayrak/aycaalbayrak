public class AktifDurum implements KullaniciDurumu {
    @Override
    public void durumGuncelle(Kullanici kullanici) {
        System.out.println(kullanici.getAd() + " artık aktif durumunda.");
    }
}
