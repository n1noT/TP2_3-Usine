package fr.toutain.nino;

import java.util.Vector;

public class Usine {
    public static final int NB_BACS = 3;
    Vector<Bac> bacs = new Vector<>();
    Vector<Palette> palettesPleines = new Vector<>();
    Palette paletteEnCours = new Palette();
    private boolean open = true;

    public Usine() {
        this.initBacs();
    }

    public void initBacs() {
        for (int i = 0; i < NB_BACS; i++) {
            Bac b = new Bac(100, i);
            bacs.add(b);
        }
    }

    // Un type de composant correspond à l'index de bac
    public Bac getBacForComposant(int i) {
        if (i >= 0 && i < NB_BACS) {
            return bacs.get(i);
        } else {
            return null;
        }
    }

    public synchronized void addProductToLastPalette() {
        int productQuantity = paletteEnCours.addProduct();

        if (productQuantity == Palette.PRODUCTS_QUANTITY) {
            palettesPleines.add(paletteEnCours);
            System.out.println("Palette pleine, création d'une nouvelle palette en cours");
            paletteEnCours = new Palette();
        }
    }

    public synchronized Palette removePalettePleine() {
        Palette palette = null;
        if (!palettesPleines.isEmpty()) {
            palette = palettesPleines.removeFirst();
        }

        return palette;
    }

    public boolean isOpen () {
        return this.open;
    }

    public void fermerUsine() {
        this.open = false;

        for (Bac bac : bacs) {
            // Fermer les bacs pour empecher le blocage des ouvriers
            bac.fermerBac();
        }
    }
}
