package main;

public class Silk extends Specific {
    protected int penalty, bonus, profit;
    protected String name;
    protected int countGoods;
    private static final int PENALTY = 4;
    private static final int PROFIT = 9;
    private static final int BONUS = 9;

    Silk() {

        super.penalty = PENALTY;
        super.profit = PROFIT;
        super.bonus = BONUS;
        super.name = "Silk";
    }

}
