package fr.toutain.nino;

public class Palette {
    public static int PRODUCTS_QUANTITY = 10;
    private int products = 0;

    public synchronized int addProduct() {
        if (products < PRODUCTS_QUANTITY) {
            products++;
        }

        return products;
    }
}
