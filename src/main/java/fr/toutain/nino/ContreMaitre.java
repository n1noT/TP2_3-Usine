package fr.toutain.nino;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class ContreMaitre extends Thread {
    private Usine usine;
    private Vector<Thread> listeEmployes;
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    public ContreMaitre (Usine usine, Vector<Thread> listeEmployes) {
        this.usine = usine;
        this.listeEmployes = listeEmployes;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                this.sleep(100);

                String currentTime = sdf.format(new Date());
                System.out.println("Employés que faites-vous ? Il est " + currentTime.toString());
                for (Thread employe : listeEmployes) {
                    employe.interrupt();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        usine.fermerUsine();
        System.out.println("Fin de journée");

        // Forcer l'arrêt après la fin de journée
        for (Thread employe : listeEmployes) {
            employe.interrupt();
        }
    }
}
