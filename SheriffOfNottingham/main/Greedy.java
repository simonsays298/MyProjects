package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Greedy extends Basic {
    protected List<String> assets = new ArrayList<>();
    protected List<Specific> sack = new ArrayList<>();
    private static final int COINS = 50;
    protected int coins = COINS;
    protected String declareAsset;
    protected int countGoods;
    protected List<Specific> assetsOnMerchantStand = new ArrayList<>();
    private static final int SIZE = 5;

    /**
     * Greedy in rundele pare va dori sa-si adauge un bun ilegal, daca are unul, in
     * sacul deja creat.
     * @param cards
     * @param sack
     */
    public void addIfEven(final List<String> cards, final List<Specific> sack) {

        List<String> vector = new ArrayList<>();
        int ok = 1;
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).equals("Silk") || cards.get(i).equals("Barrel")
            || cards.get(i).equals("Pepper")) {
                vector.add(cards.get(i));
                ok = 0;

            }

        }
        //daca am bunuri ilegale, adaug unul in sacul deja creat
        if (ok == 0) {

            List<Specific> obiecte = new ArrayList<Specific>();
            for (int i = 0; i < vector.size(); i++) {
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
            //ordonez descrescator dupa profit bunurile
            Collections.sort(obiecte, specificComp);

            if (sack.size() != SIZE) {
                sack.add(obiecte.get(0));

            }
        }

    }

    protected Comparator<Specific> specificComp = new Comparator<Specific>() {

        public int compare(final Specific c1, final Specific c2) {
            return c2.profit - c1.profit;
        }

    };

}
