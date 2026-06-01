package fr.toutain.nino;

import java.util.Random;

public class Livreur extends Thread {
    int composant; // type de composant, correspond à l'index de bac dans l'usine
    Usine usine;
    Random r;

    public Livreur(int composant, Usine usine, String name) {
        super(name);
        if (composant < 0 || composant >= Usine.NB_BACS) {
            throw new RuntimeException("Le type doit être entre 0 et " + (Usine.NB_BACS - 1));
        }
        this.composant = composant;
        this.usine = usine;
        this.r = new Random();
    }

    @Override
    public void run() {
        while (usine.isOpen()) {
            String livreur = this.getName();
            int quantity = r.nextInt(20, 30);
            Bac bac = usine.getBacForComposant(composant);
            bac.deposer(quantity);

            Palette palette = usine.removePalettePleine();

            if (palette != null) {
                System.out.println("Palette pleine récupérée par " + livreur + ", en cours de livraison");
            }

            try {
                this.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Le livreur " + livreur + " est en cours de livraison");
            }
        }
    }
}
