public class TemelAidatHesaplama implements AidatHesaplama {
    private int alan;  // Metrekare cinsinden apartmanın alanı
    private double birimFiyat;  // Metrekare başına ücret

    public TemelAidatHesaplama(int alan, double birimFiyat) {
        this.alan = alan;
        this.birimFiyat = birimFiyat;
    }

    @Override
    public double hesapla() {
        return alan * birimFiyat; // Aidat, alan x birim fiyat şeklinde hesaplanır
    }
}
