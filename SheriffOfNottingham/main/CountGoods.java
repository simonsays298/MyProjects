package main;

import java.util.List;

public class CountGoods {
    private static final int NULL = 0;
    protected int countApple = NULL;
    protected int countChicken = NULL;
    protected int countBread = NULL;
    protected int countCheese = NULL;

    /**
     * Urmatoare metoda va calcula numarul de bunuri de un anumit tip.
     * @param assets
     */
    public void countGood(final List<Specific> assets) {
        for (int i = 0; i < assets.size(); i++) {
            if (assets.get(i) instanceof Apple) {
                this.countApple++;
            }
            if (assets.get(i) instanceof Bread) {
                this.countBread++;
            }
            if (assets.get(i) instanceof Cheese) {
                this.countCheese++;
            }
            if (assets.get(i) instanceof Chicken) {
                this.countChicken++;
            }
        }
    }
}
