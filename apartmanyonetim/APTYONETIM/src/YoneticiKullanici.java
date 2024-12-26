public class YoneticiKullanici extends Kullanici {
    public YoneticiKullanici(String ad,String soyad, String email, String sifre) {
        this.ad = ad;
        this.soyad=soyad;
        this.email = email;
        this.sifre = sifre;
    }

    @Override
    public void login() {
        System.out.println(ad + " Yönetici sisteme giriş yaptı.");
    }

    @Override
    public void logout() {
        System.out.println(ad + " Yönetici sistemden çıkış yaptı.");
    }
}
