package main;

public class Chicken extends Specific {
    protected int penalty, bonus, profit;
    protected String name;
    protected int countGoods;
    private static final int PENALTY = 2;
    private static final int PROFIT = 4;
    private static final int BONUS = 0;
    private static final int KINGBONUS = 10;
    private static final int QUEENBONUS = 5;

    Chicken() {

        super.penalty = PENALTY;
        super.bonus = BONUS;
        super.profit = PROFIT;
        super.name = "Chicken";
        super.kingBonus = KINGBONUS;
        super.queenBonus = QUEENBONUS;

    }
}
