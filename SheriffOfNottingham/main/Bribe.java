package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bribe extends Basic {
    protected List<String> assets = new ArrayList<>();
    protected List<Specific> sack = new ArrayList<>();
    private static final int COINS = 50;
    protected int coins = COINS;
    protected String declareAsset;
    protected List<Specific> assetsOnMerchantStand = new ArrayList<>();
    private static final int BRIBE = 0;
    protected int bribe = BRIBE;
    protected int countGoods;
    private static final int UPPER = 10;
    private static final int LOWER = 5;
    private static final int SIZE = 2;

    /**
     * Metoda prin care realizam creearea sacului special pentru Bribe care da si
     * mita, ajutandu-ne de functia createSack din Basic, atunci cand este nevoie.
     * @param cards
     */
    public void sack(final List<String> cards) {


        Map<String, Integer> count = new HashMap<String, Integer>();
        count = super.frequency(cards);

        int ok = 1;
        List<String> vector = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            if (count.containsKey("Silk") || count.containsKey("Pepper")
            || count.containsKey("Barrel")) {
                vector.add(cards.get(i));
                ok = 0;
            }
        }
        //daca nu am bunuri ilegale aplic strategia de baza
        if (ok == 1) {
            super.createSack(cards);
        } else {
            //daca am cel putin 10 coins adaug cat mai multe bunuri ilegale
            if (super.coins >= UPPER) {

                int i;

                List<Specific> obiecte = new ArrayList<Specific>();

                for (i = 0; i < vector.size(); i++) {
                    if (vector.get(i).equals("Silk")) {
                        Specific s = new Silk();
                        obiecte.add(s);
                    }
                    if (vector.get(i).equals("Pepper")) {
                        Specific s = new Pepper();
                        obiecte.add(s);
                    }
                    if (vector.get(i).equals("Barrel")) {
                        Specific s = new Barrel();
                        obiecte.add(s);
                    }
                }
                //ordonez descrescator dupa profit
                Collections.sort(obiecte, specificComp);
                for (i = 0; i < obiecte.size(); i++) {
                    if (i < LOWER) {
                        super.sack.add(obiecte.get(i));
                    }

                }
                /*
                 * in functie de numarul de bunuri, setam o anumita mita
                 */
                if (super.sack.size() <= SIZE) {

                    super.coins = super.coins - LOWER;
                    super.bribe = LOWER;
                }
                if (super.sack.size() > SIZE) {

                    super.coins = super.coins - UPPER;
                    super.bribe = UPPER;
                }

            }
            //daca nu am bani suficienta aplic strategia de baza
            if (super.coins < LOWER) {
                super.createSack(cards);
            }
            //daca am intre 5 si 10 coins pot adauga maxim doua bunuri ilegale
            if (super.coins >= LOWER && super.coins < UPPER) {
                int i;

                List<Specific> obiecte = new ArrayList<Specific>();

                for (i = 0; i < vector.size(); i++) {
                    if (vector.get(i).equals("Silk")) {
                        Specific s = new Silk();
                        obiecte.add(s);
                    }
                    if (vector.get(i).equals("Pepper")) {
                        Specific s = new Pepper();
                        obiecte.add(s);
                    }
                    if (vector.get(i).equals("Barrel")) {
                        Specific s = new Barrel();
                        obiecte.add(s);
                    }
                }
                //sortez descrescator dupa profit
                Collections.sort(obiecte, specificComp);
                for (i = 0; i < obiecte.size(); i++) {
                    if (i < SIZE) {
                        super.sack.add(obiecte.get(i));
                    }

                }
                //setez mita
                if (super.sack.size() <= SIZE) {

                    super.coins = super.coins - LOWER;
                    super.bribe = LOWER;
                }
            }

        }

    }

    protected Comparator<Specific> specificComp = new Comparator<Specific>() {

        public int compare(final Specific c1, final Specific c2) {
            return c2.profit - c1.profit;
        }

    };
}
