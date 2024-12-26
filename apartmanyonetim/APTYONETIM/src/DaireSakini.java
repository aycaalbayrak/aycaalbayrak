public class DaireSakini extends Kullanici {
    public DaireSakini(String ad, String soyad, String email, String sifre) {
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.sifre = sifre;
    }

    @Override
    public void login() {
        System.out.println(ad + " Daire Sakini sisteme giriş yaptı.");
    }

    @Override
    public void logout() {
        System.out.println(ad + " Daire Sakini sistemden çıkış yaptı.");
    }
}
