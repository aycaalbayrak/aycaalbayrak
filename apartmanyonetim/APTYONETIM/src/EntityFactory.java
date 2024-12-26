public class EntityFactory {
    // Kullanıcı oluşturma metodu
    public static Kullanici createKullanici(String type, String ad,String soyad, String email, String sifre) {
        switch (type.toUpperCase()) {
            case "YONETICI":
                return new YoneticiKullanici(ad,soyad, email, sifre);
            case "DAIRE_SAKINI":
                return new DaireSakini(ad, soyad, email, sifre);
            default:
                throw new IllegalArgumentException("Geçersiz kullanıcı tipi: " + type);
        }
    }

    // Apartman birimi oluşturma metodu
    public static ApartmanBirim createApartmanBirim(String type, String birimAdi, int alan, String sahip) {
        switch (type.toUpperCase()) {
            case "DAIRE":
                return new Daire(birimAdi, alan, sahip);
            case "DUKKAN":
                return new Dukkan(birimAdi, alan, sahip);
            default:
                throw new IllegalArgumentException("Geçersiz apartman birim tipi: " + type);
        }
    }
}
