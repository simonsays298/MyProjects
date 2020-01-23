package main;

public class Pepper extends Specific {
    protected int penalty, bonus, profit;
    protected String name;
    protected int countGoods;
    private static final int PENALTY = 4;
    private static final int PROFIT = 8;
    private static final int BONUS = 8;

    Pepper() {

        super.penalty = PENALTY;
        super.profit = PROFIT;
        super.bonus = BONUS;
        super.name = "Pepper";

    }

}
