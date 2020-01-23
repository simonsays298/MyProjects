package main;

public class Apple extends Specific {
    protected int penalty, bonus, profit;
    protected String name;
    protected int countGoods;
    private static final int PENALTY = 2;
    private static final int PROFIT = 2;
    private static final int BONUS = 0;
    private static final int KINGBONUS = 20;
    private static final int QUEENBONUS = 10;

    Apple() {

        super.penalty = PENALTY;
        super.bonus = BONUS;
        super.profit = PROFIT;
        super.name = "Apple";
        super.kingBonus = KINGBONUS;
        super.queenBonus = QUEENBONUS;
    }

}
