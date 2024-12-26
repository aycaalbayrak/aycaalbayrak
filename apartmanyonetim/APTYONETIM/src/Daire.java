// Leaf - Daire
public class Daire extends ApartmanBirim {
    public Daire(String birimAdi, int alan, String sahip) {
        super(birimAdi, alan, sahip);
    }

    @Override
    public void bilgiGoster() {
        System.out.println("Daire: " + birimAdi + ", Alan: " + alan + " m², Sahip: " + sahip);
    }

    @Override
    public double hesaplaAidat() {
        // Temel aidat hesaplaması, örneğin: 5 TL/m²
        return alan * 5.0;
    }
}