// Leaf - Dükkan
public class Dukkan extends ApartmanBirim {
    public Dukkan(String birimAdi, int alan, String sahip) {
        super(birimAdi, alan, sahip);
    }

    @Override
    public void bilgiGoster() {
        System.out.println("Dükkan: " + birimAdi + ", Alan: " + alan + " m², Sahip: " + sahip);
    }

    @Override
    public double hesaplaAidat() {
        // Temel aidat hesaplaması, örneğin: 10 TL/m²
        return alan * 10.0;
    }
}
