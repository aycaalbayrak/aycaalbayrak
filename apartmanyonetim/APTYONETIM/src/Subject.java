public interface Subject {
    void addObserver(Observer observer);   // Yeni bir gözlemci ekle
    void removeObserver(Observer observer); // Bir gözlemciyi kaldır
    void notifyObservers(String mesaj);   // Tüm gözlemcilere mesaj gönder
}
