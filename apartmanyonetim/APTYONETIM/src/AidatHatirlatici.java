import java.util.ArrayList;
import java.util.List;

public class AidatHatirlatici implements Subject {
    private List<Observer> observers;

    public AidatHatirlatici() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);  // Gözlemciyi listeye ekler
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);  // Gözlemciyi listeden çıkarır
    }

    @Override
    public void notifyObservers(String mesaj) {
        for (Observer observer : observers) {
            observer.update(mesaj);  // Her gözlemciye bildirim gönderir
        }
    }

    // Borçlu kullanıcılar için ödeme hatırlatmalarını gönder
    public void borcluKullaniciHatirlatmasi(Kullanici kullanici) {
        String mesaj = kullanici.getAd() + " için ödeme hatırlatması: Aidat ödemesi gerekiyor.";
        notifyObservers(mesaj);  // Gözlemcilere bildirim gönder
    }
}
