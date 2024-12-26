public class GuvenlikHizmetiDecorator extends FiyatDecorator {
    private double guvenlikUcreti;

    public GuvenlikHizmetiDecorator(AidatHesaplama aidatHesaplama, double guvenlikUcreti) {
        super(aidatHesaplama);
        this.guvenlikUcreti = guvenlikUcreti;
    }

    @Override
    public double hesapla() {
        return super.hesapla() + guvenlikUcreti;
    }
}