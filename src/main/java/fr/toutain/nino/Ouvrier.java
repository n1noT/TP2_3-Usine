package fr.toutain.nino;

public class Ouvrier extends Thread {
    int[] quantities;
    Usine usine;

    public Ouvrier(int[] quantities, Usine usine, String name) {
        super(name);
        if (quantities.length != Usine.NB_BACS) {
            throw new RuntimeException("Le tableau de quantités doit contenir " + Usine.NB_BACS + " entrées");
        }
        this.quantities = quantities;
        this.usine = usine;
    }

    @Override
    public void run() {
        while (usine.isOpen()) {
            for (int i = 0; i < Usine.NB_BACS; i++) {
                Bac bac = usine.getBacForComposant(i);
                bac.retirer(quantities[i]);
            }

            usine.addProductToLastPalette();
        }
    }
}