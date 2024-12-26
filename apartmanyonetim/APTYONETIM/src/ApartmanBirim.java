public abstract class ApartmanBirim {
    protected String birimAdi;
    protected int alan;
    protected String sahip;

    public ApartmanBirim(String birimAdi, int alan, String sahip) {
        this.birimAdi = birimAdi;
        this.alan = alan;
        this.sahip = sahip;
    }

    public abstract void bilgiGoster();  // Her birim kendi bilgilerini gösterecek
    public abstract double hesaplaAidat(); // Aidat hesaplama işlemi
}