package main;

public class Cheese extends Specific {
    protected int penalty, bonus, profit;
    protected String name;
    protected int countGoods;
    private static final int PENALTY = 2;
    private static final int PROFIT = 3;
    private static final int BONUS = 0;
    private static final int KINGBONUS = 15;
    private static final int QUEENBONUS = 10;

    Cheese() {

        super.penalty = PENALTY;
        super.profit = PROFIT;
        super.bonus = BONUS;
        super.name = "Cheese";
        super.kingBonus = KINGBONUS;
        super.queenBonus = QUEENBONUS;

    }

}
