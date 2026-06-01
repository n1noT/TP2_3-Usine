package fr.toutain.nino;

import java.util.Random;
import java.util.Vector;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        Usine usine = new Usine();
        Vector<Thread> listeEmployes = new Vector<>();

        for (int i = 0; i < Usine.NB_BACS; i++) {
            Livreur livreur = new Livreur(i, usine, "Liv" + i);
            livreur.start();
            listeEmployes.add(livreur);

            Ouvrier ouvrier = new Ouvrier(randomQuantities(), usine, "Ouv" + i);
            ouvrier.start();
            listeEmployes.add(ouvrier);
        }

        ContreMaitre contreMaitre = new ContreMaitre(usine, listeEmployes);
        contreMaitre.start();
    }

    private static int[] randomQuantities() {
        int max = Usine.NB_BACS;
        int[] quantities = new int[max];
        for (int i = 0; i < max; i++) {
            quantities[i] = random.nextInt(1, 4);
        }

        return quantities;
    }
}