public abstract class Kullanici implements Observer {
    protected int id;  // Kullanıcının benzersiz ID'si
    protected String ad;  // Kullanıcının adı
    protected String soyad;  // Kullanıcının soyadı
    protected String email;  // Kullanıcının e-posta adresi
    protected String sifre;  // Kullanıcının şifresi
    protected KullaniciDurumu durum;  // Kullanıcının durumu
    protected OdemeTakvimi odemeTakvimi;  // Kullanıcının ödeme takvimi

    // Observer arayüzündeki update metodu
    @Override
    public void update(String mesaj) {
        System.out.println(ad + " için bildirim: " + mesaj);
    }


    public abstract void login();   // Sisteme giriş yapma
    public abstract void logout();  // Sistemden çıkış yapma

    // Durum güncellemesi
    public KullaniciDurumu getDurum() {
        return durum;
    }

    public void setDurum(KullaniciDurumu durum) {
        this.durum = durum;
        this.durum.durumGuncelle(this);
    }

    // ID ile ilgili getter ve setter metodları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Ortak özelliklere erişim metotları
    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    // Ödeme takvimi ile ilgili metodlar
    public void setOdemeTakvimi(OdemeTakvimi odemeTakvimi) {
        this.odemeTakvimi = odemeTakvimi;
    }

    public OdemeTakvimi getOdemeTakvimi() {
        return odemeTakvimi;
    }
}
