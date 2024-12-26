public class TemizlikHizmetiDecorator extends FiyatDecorator {
    private double temizlikUcreti;

    public TemizlikHizmetiDecorator(AidatHesaplama aidatHesaplama, double temizlikUcreti) {
        super(aidatHesaplama);
        this.temizlikUcreti = temizlikUcreti;
    }

    @Override
    public double hesapla() {
        return super.hesapla() + temizlikUcreti;
    }
}