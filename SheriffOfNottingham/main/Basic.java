package main;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basic {
    protected List<String> assets = new ArrayList<>();
    protected List<Specific> sack = new ArrayList<>();
    static final int COINS = 50;
    protected int coins = COINS;
    protected String declareAsset;
    protected List<Specific> assetsOnMerchantStand = new ArrayList<>();
    protected int bribe;
    protected int countGoods;

    /**
     * Urmatoarea metoda va implementa sacul, metoda ce va fi preluata de restul
     * jucatorilor, cu exceptia lui Bribe.
     * @param cards
     */
    public void createSack(final List<String> cards) {

        Map<String, Integer> count = new HashMap<String, Integer>();
        //calculam frecventa fiecarui tip cu HashMap.
        count = frequency(cards);
        int max = -1;
        this.assets = cards;
        //vector in care retinem frecventa maxima ce se repeta
        List<String> vector = new ArrayList<>();

        int k = 0;
        int ok = 1;
        // verificam daca avem doar bunuri ilegale
        for (int i = 0; i < cards.size(); i++) {
            if (count.containsKey("Apple")
                    || count.containsKey("Bread")
                    || count.containsKey("Cheese") || count.containsKey("Chicken")) {
                ok = 0;
            }
        }
        // calculam frecventa maxima a bunurilor
        if (ok == 0) {
            for (int i = 0; i < cards.size(); i++) {

                if (count.containsKey(cards.get(i)) && max < count.get(cards.get(i))
                        && (cards.get(i) != "Silk" && cards.get(i) != "Pepper"
                        && cards.get(i) != "Barrel")) {
                    max = count.get(cards.get(i));
                }
            }
            // daca avem aceeasi frecventa maxima adaugam in vector
            for (int i = 0; i < cards.size(); i++) {
                if (count.containsKey(cards.get(i)) && max == count.get(cards.get(i))) {
                    vector.add(cards.get(i));
                    k++;

                }
            }
            // cream o lista de tipul Specific cu bunurile cu aceeasi frecventa
            List<Specific> obiecte = new ArrayList<Specific>();
            for (int i = 0; i < vector.size(); i++) {
                if (vector.get(i).equals("Apple")) {
                    Specific s = new Apple();
                    obiecte.add(s);
                }
                if (vector.get(i).equals("Chicken")) {
                    Specific s = new Chicken();
                    obiecte.add(s);
                }
                if (vector.get(i).equals("Cheese")) {
                    Specific s = new Cheese();
                    obiecte.add(s);
                }
                if (vector.get(i).equals("Bread")) {
                    Specific s = new Bread();
                    obiecte.add(s);
                }
            }
            int i;

            max = -1;
            Specific legalGood = null;
            //cautam bunul cu profitul cel mai mare care sa fie si legal
            for (i = 0; i < obiecte.size(); i++) {

                if (max < obiecte.get(i).profit && obiecte.get(i).penalty == 2) {
                    max = obiecte.get(i).profit;
                    legalGood = obiecte.get(i);
                }
            }

            String s = legalGood.name;
            //odata gasit il adaugam in sac de cate ori apare
            for (i = 0; i < count.get(s); i++) {
                sack.add(legalGood);
            }
        }
        //caz in care avem doar bunuri ilegale
        if (ok == 1) {
            for (int i = 0; i < cards.size(); i++) {

                if (count.containsKey(cards.get(i))
                        && max < count.get(cards.get(i))) {
                    max = count.get(cards.get(i));

                }
            }
            // daca avem aceeasi frecventa punem in vector
            for (int i = 0; i < cards.size(); i++) {
                if (count.containsKey(cards.get(i))
                        && max == count.get(cards.get(i))) {
                    vector.add(cards.get(i));
                    k++;

                }
            }
            // cream o lista de tipul Specific cu bunurile cu aceeasi frecventa
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
            max = -1;
            Specific illegalGood = null;
            //calculam  bunul cu profitul cel mai mare
            for (int i = 0; i < obiecte.size(); i++) {

                if (max < obiecte.get(i).profit) {
                    max = obiecte.get(i).profit;
                    illegalGood = obiecte.get(i);
                }
            }
            //cum este basic adaugam doar un bun ilegal
            sack.add(illegalGood);

        }

    }

    /**
     * Urmatoarea metoda va realiza declararea bunului de catre un comerciant,
     * metoda ce va fi apelata si de restul jucatorilor.
     * @param sack
     * @param cards
     */
    public void decalreAssets(final List<Specific> sack, final List<String> cards) {
        int ok = 1;
        for (int i = 0; i < sack.size(); i++) {
            if (sack.get(i).name.equals("Apple") || sack.get(i).name.equals("Bread")
                    || sack.get(i).name.equals("Cheese") || sack.get(i).name.equals("Chicken")) {
                ok = 0;
            }
        }
        //daca sunt doar bunuri ilegale in sac, le declaram ca fiind de tipul Apple
        if (ok == 1) {

            this.declareAsset = "Apple";

        } else {
            //altfel declaram primul bun legal care este, cum sunt toate la fel
            this.declareAsset = sack.get(0).name;
        }

    }

    /**
     * Urmatoarea metoda va scoate cartile din mana ale jucatorilor daca acestea se
     * afla deja in sac ; Aceasta la randul ei va fi refolosita si de ceilalti
     * jucatori.
     * @param sack
     * @param cards
     */
    public void removeFromHand(final List<Specific> sack, final List<String> cards) {
        this.assets = cards;
        //daca un bun se afla in sac, il eliminam din mana
        for (int i = 0; i < sack.size(); i++) {
            for (int j = 0; j < this.assets.size(); j++) {
                if (sack.get(i).name.equals(this.assets.get(j))) {
                    this.assets.remove(j);
                }
            }
        }
    }

    /**
     * Urmatoarea metoda va realiza inspectia de catre serif pentru un comerciant
     * anume, precum si punerea pe taraba a bunurilor in cazul in care comerciantul
     * trece inspectia ; metoda folosita si de restul jucatorilor.
     * @param sack         - sacul realizat de comerciant.
     * @param declareAsset - bunul declarat de catre comerciant.
     * @param sheriff
     * @param p            - player ce va fi inspectat.
     * @param cards        - adaugam la sfarsitul pachetului cartile confiscate.
     */
    public void checkedBySheriff(final List<Specific> sack, final String declareAsset,
            final Basic sheriff, final Basic p, final List<String> cards) {
        int ok = 0;
        //verificam daca comerciantul are in sac doar bunul declarat
        for (int i = 0; i < sack.size(); i++) {
            if (sack.get(i).name != declareAsset) {
                ok = 1;
            }
        }
        //daca nu a mintit primeste bani de la serif si isi adauga bunurile pe taraba
        if (ok == 0) {
            p.coins = p.coins + sack.size() * sack.get(0).penalty;

            sheriff.coins = sheriff.coins - sack.size() * sack.get(0).penalty;
            addOnMerchantStand(sack, p);

        } else {
            /*
             * in caz contrat fiecare bun nedeclarat ii este confiscat
             * si trebuie sa plateasca penalizare serifului ; isi va
             * adauga pe taraba doar bunurile declarate.
             */
            for (int i = 0; i < sack.size(); i++) {
                if (sack.get(i).name != declareAsset) {
                    p.coins = p.coins - sack.get(i).penalty;
                    sheriff.coins = sheriff.coins + sack.get(i).penalty;
                    cards.add(sack.get(i).name);
                } else {
                    p.assetsOnMerchantStand.add(sack.get(i));
                }
            }

        }
        p.sack.removeAll(p.sack);

    }

    /**
     * Metoda apelata in principal daca un comerciant ofera mita si nu mai este
     * verificat, punand astfel toate bunurile din sac pe taraba.
     * @param sack
     * @param p
     */
    public void addOnMerchantStand(final List<Specific> sack, final Basic p) {

        for (int i = 0; i < sack.size(); i++) {
            p.assetsOnMerchantStand.add(sack.get(i));
        }

        p.sack.removeAll(p.sack);

    }

    /**
     * Metoda folosita pentru a stabili jucatorii ce vor primii king si queen bonus.
     * @param player
     * @param goods
     */
    public void bonusKingQueen(final List<Basic> player, final Specific goods, final int size) {
        final int trei = 3;
        final int zero = 0;
        final int unu = 1; 
        final int doi = 2;
        int nr = trei;
        /*
         * vom compara bunurile de un anume tip ale fiecarui jucator pentru
         * a stabili cel sau cei care vor primi kingBonus, respectiv
         * queenBonus ( indiferent de numarul de jucatori).
         */
        int i = zero;
        int j = unu; 
        int k = doi;
        if (size == trei) {
            while (nr > 0) {
                compare(player.get(i), player.get(j), player.get(k),goods);
                int aux = i;
                i = j; 
                j = k;
                k = aux;
                nr--;
            }
                if (player.get(0).countGoods == player.get(1).countGoods
                        && player.get(0).countGoods == player.get(2).countGoods) {
                    player.get(0).coins = player.get(0).coins + goods.kingBonus;
                    player.get(1).coins = player.get(1).coins + goods.kingBonus;
                    player.get(2).coins = player.get(2).coins + goods.kingBonus;
                }

            
        }
        if (size == 2) {
            if (player.size() == 2) {
                if (player.get(0).countGoods > player.get(1).countGoods) {
                    player.get(0).coins = player.get(0).coins + goods.kingBonus;
                    player.get(1).coins = player.get(1).coins + goods.queenBonus;
                }
                if (player.get(0).countGoods < player.get(1).countGoods) {
                    player.get(1).coins = player.get(1).coins + goods.kingBonus;
                    player.get(0).coins = player.get(0).coins + goods.queenBonus;
                }
                if (player.get(0).countGoods == player.get(1).countGoods) {
                    player.get(1).coins = player.get(1).coins + goods.kingBonus;
                    player.get(0).coins = player.get(0).coins + goods.kingBonus;
                }
            }
        }

    }

    /**
     * Calculam frecventa aparitiei fiecarui bun dintr-un set de carti.
     * @param cards
     * @return
     */
    public Map<String, Integer> frequency(final List<String> cards) {

        Map<String, Integer> countGoods = new HashMap<String, Integer>();
        //ne folosim de HashMap pentru a calcula mai usor frecventa bunurilor
        for (String goods : cards) {
            countGoods.put(goods, countGoods.getOrDefault(goods, 0) + 1);
        }

        return countGoods;

    }

    /**
     * Adaug bunuri pe taraba daca am un bun ilegal, pentru a calcula mai apoi king
     * si queen bonus.
     * @param p
     */
    public void addNewOnMerchantStand(final Basic p) {
        for (int b = 0; b < p.assetsOnMerchantStand.size(); b++) {
            if (p.assetsOnMerchantStand.get(b) instanceof Pepper) {
                Specific chicken = new Chicken();
                p.assetsOnMerchantStand.add(chicken);
                p.assetsOnMerchantStand.add(chicken);
                continue;
            }
            if (p.assetsOnMerchantStand.get(b) instanceof Silk) {
                Specific cheese = new Cheese();
                p.assetsOnMerchantStand.add(cheese);
                p.assetsOnMerchantStand.add(cheese);
                p.assetsOnMerchantStand.add(cheese);
                continue;
            }
            if (p.assetsOnMerchantStand.get(b) instanceof Barrel) {
                Specific bread = new Bread();
                p.assetsOnMerchantStand.add(bread);
                p.assetsOnMerchantStand.add(bread);
                continue;
            }
        }
    }


    /**
     * Metoda care completeaza numarul de carti din mana.
     * @param cards
     * @return
     */
    public List<String> addCardsToComplete(final List<String> cards) {
        final int size = 6;
        if (this.assets.size() < size) {
            int c = size - this.assets.size();
            while (c > 0) {
                this.assets.add(cards.remove(0));
                c--;
            }
        }

        return this.assets;
    }
    /*
     * Metoda prin care implementam compararea numarului de bunuri 
     * ale unor jucatori.
     */
    public void compare(final Basic p1, final Basic p2, final Basic p3, final Specific goods) {
        if (p1.countGoods > p2.countGoods
                && p1.countGoods > p3.countGoods) {
            if (p2.countGoods > p3.countGoods) {
                p1.coins = p1.coins + goods.kingBonus;
                p2.coins = p2.coins + goods.queenBonus;
            }
            if (p2.countGoods < p3.countGoods) {
                p1.coins = p1.coins + goods.kingBonus;
                p3.coins = p3.coins + goods.queenBonus;
            }
            if (p2.countGoods == p3.countGoods) {
                p1.coins = p1.coins + goods.kingBonus;
                p2.coins = p2.coins + goods.queenBonus;
                p3.coins = p3.coins + goods.queenBonus;
            }
        }
        if (p1.countGoods == p2.countGoods
                && p1.countGoods > p3.countGoods) {
            p1.coins += goods.kingBonus;
            p2.coins += goods.kingBonus;
            p3.coins += goods.queenBonus;
        }
    }

}
