package fr.toutain.nino;

public class Bac {
    private final int maxQuantity;
    private int type;
    private int nbComposants = 0;
    private boolean open = true;

    public Bac(int maxQuantity, int type) {
        this.maxQuantity = maxQuantity;
        if (type < 0 || type > Usine.NB_BACS) {
            throw new RuntimeException("Le type doit être entre 0 et " + (Usine.NB_BACS - 1));
        }
        this.type = type;
    }

    public synchronized void deposer(int quantity) {
        while (open && (nbComposants + quantity) > maxQuantity) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("Le livreur " + Thread.currentThread().getName() + " attends de déposer " + quantity + " composants de type " + type);
            }
        }

        if (!open) { return; }

        nbComposants += quantity;
        this.notifyAll();
    }

    public synchronized int retirer(int quantity) {
        while (open && (nbComposants - quantity) < 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                System.out.println("L'ouvrier " + Thread.currentThread().getName() + " attends des composants de type " + type);
            }
        }

        if (!open) { return 0; }

        nbComposants -= quantity;
        this.notifyAll();

        return quantity;
    }

    public synchronized void fermerBac() {
        this.open = false;
        this.notifyAll();
    }
}
