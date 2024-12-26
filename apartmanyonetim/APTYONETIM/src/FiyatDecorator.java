public class FiyatDecorator implements AidatHesaplama {
    protected AidatHesaplama aidatHesaplama;

    public FiyatDecorator(AidatHesaplama aidatHesaplama) {
        this.aidatHesaplama = aidatHesaplama;
    }

    @Override
    public double hesapla() {
        return aidatHesaplama.hesapla();
    }
}