package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Main {

    private static final class GameInputLoader {
        private final String mInputPath;

        private GameInputLoader(final String path) {
            mInputPath = path;
        }

        public GameInput load() {
            List<Integer> assetsIds = new ArrayList<>();
            List<String> playerOrder = new ArrayList<>();

            try {
                BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
                String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
                String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

                for (String strAssetId : assetIdsLine.split(",")) {
                    assetsIds.add(Integer.parseInt(strAssetId));
                }

                for (String strPlayer : playerOrderLine.split(",")) {
                    playerOrder.add(strPlayer);
                }
                inStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GameInput(assetsIds, playerOrder);
        }
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
        GameInput gameInput = gameInputLoader.load();

        List<Integer> mAssetOrder = new ArrayList<>();
        mAssetOrder = gameInput.getAssetIds();
        List<String> mPlayersOrder = new ArrayList<>();
        mPlayersOrder = gameInput.getPlayerNames();
        List<String> mAssetIds = new ArrayList<>();
        //convertim numerele in string-uri cu numele bunurilor
        mAssetIds = transformAssets(mAssetOrder);
        //adaugam intr-o lista jucatorii
        List<Basic> player = new ArrayList<Basic>();
        player = addPlayers(mPlayersOrder);

        final int size = 6;
        final int zero = 0;
        final int unu = 1;
        final int doi = 2;
        final int sizePlayer = 3;
        int count = size;
        int first = zero;
        int second = unu;
        int third = doi;
        /*
         * in functie de numarul de jucatori adaugam cartile intial in mana.
         */
        if (player.size() == sizePlayer) {

            player.get(first).assets = player.get(first).addCardsToComplete(mAssetIds);
            player.get(second).assets = player.get(second).addCardsToComplete(mAssetIds);
            player.get(third).assets = player.get(third).addCardsToComplete(mAssetIds);

        } else {
            player.get(first).assets = player.get(first).addCardsToComplete(mAssetIds);
            player.get(second).assets = player.get(second).addCardsToComplete(mAssetIds);           
        }

        int nrRound = zero;
        int cnt = 2 * mPlayersOrder.size();

        while (cnt > 0) {
            /*
             * in nrRound retinem rundele pentru Greedy si il incrementam
             * atunci cand este comerciant.
             */
            if (player.size() == sizePlayer) {
                if (player.get(second) instanceof Greedy || player.get(third) instanceof Greedy) {
                    nrRound++;
                }
                //realizam o runda cu functia implementata
                aRound(player.get(first), player.get(second), player.get(third),
                        sizePlayer, nrRound, mAssetIds);
                //initializam mita cu 0 dupa fiecare runda
                setBribeToNull(player.get(first), player.get(second), player.get(third));
                //completam cartile la jucatorii care au mai putin de 6 in mana
                player.get(0).assets = player.get(0).addCardsToComplete(mAssetIds);
                player.get(1).assets = player.get(1).addCardsToComplete(mAssetIds);
                player.get(2).assets = player.get(2).addCardsToComplete(mAssetIds);
                //interschimbam jucatorii, pentru a avea in first mereu seriful
                int aux = first;
                first = second;
                second = third;
                third = aux;
            } else {

                if (player.get(second) instanceof Greedy) {
                    nrRound++;
                }
                aRound(player.get(first), player.get(second), player.get(first),
                        2, nrRound, mAssetIds);
                setBribeToNull(player.get(first), player.get(second), player.get(first));

                player.get(0).assets = player.get(0).addCardsToComplete(mAssetIds);
                player.get(1).assets = player.get(1).addCardsToComplete(mAssetIds);

                int aux = first;
                first = second;
                second = aux;

            }

            cnt--;

        }
        /*
         * calculam banii dupa ce vindem de pe taraba la finalul rundelor
         */
        player.get(first).coins = getMoneyFromSelling(player.get(first),
                player.get(first).assetsOnMerchantStand);
        player.get(second).coins = getMoneyFromSelling(player.get(second),
                player.get(second).assetsOnMerchantStand);

        if (player.size() == sizePlayer) {
            player.get(third).coins = getMoneyFromSelling(player.get(third),
                    player.get(third).assetsOnMerchantStand);
        }
        //adaugam in taraba echivalentul bunurilor ilegale
        player.get(first).addNewOnMerchantStand(player.get(first));
        player.get(second).addNewOnMerchantStand(player.get(second));

        if (player.size() == sizePlayer) {
            player.get(third).addNewOnMerchantStand(player.get(third));
        }
        //calculam pentru fiecare jucator fiecare tip de bun
        CountGoods goodsplayer0 = new CountGoods();
        goodsplayer0.countGood(player.get(0).assetsOnMerchantStand);
        CountGoods goodsplayer1 = new CountGoods();
        goodsplayer1.countGood(player.get(1).assetsOnMerchantStand);
        //adaugam king si queen Bonus fiecarui jucator
        if (player.size() == sizePlayer) {
            CountGoods goodsplayer2 = new CountGoods();
            goodsplayer2.countGood(player.get(2).assetsOnMerchantStand);
            player.get(0).countGoods = goodsplayer0.countApple;
            player.get(1).countGoods = goodsplayer1.countApple;
            player.get(2).countGoods = goodsplayer2.countApple;

            Basic countGoods = new Basic();
            Specific apple = new Apple();
            countGoods.bonusKingQueen(player, apple, sizePlayer);

            player.get(0).countGoods = goodsplayer0.countBread;
            player.get(1).countGoods = goodsplayer1.countBread;
            player.get(2).countGoods = goodsplayer2.countBread;

            Specific bread = new Bread();
            countGoods.bonusKingQueen(player, bread, sizePlayer);

            player.get(0).countGoods = goodsplayer0.countChicken;
            player.get(1).countGoods = goodsplayer1.countChicken;
            player.get(2).countGoods = goodsplayer2.countChicken;
            Specific chicken = new Chicken();
            countGoods.bonusKingQueen(player, chicken, sizePlayer);

            player.get(0).countGoods = goodsplayer0.countCheese;
            player.get(1).countGoods = goodsplayer1.countCheese;
            player.get(2).countGoods = goodsplayer2.countCheese;
            Specific cheese = new Cheese();
            countGoods.bonusKingQueen(player, cheese, sizePlayer);

        }

        if (player.size() == 2) {
            player.get(0).countGoods = goodsplayer0.countApple;
            player.get(1).countGoods = goodsplayer1.countApple;

            Basic countGoods = new Basic();
            Specific apple = new Apple();
            countGoods.bonusKingQueen(player, apple, 2);

            player.get(0).countGoods = goodsplayer0.countBread;
            player.get(1).countGoods = goodsplayer1.countBread;

            Specific bread = new Bread();
            countGoods.bonusKingQueen(player, bread, 2);

            player.get(0).countGoods = goodsplayer0.countChicken;
            player.get(1).countGoods = goodsplayer1.countChicken;

            Specific chicken = new Chicken();
            countGoods.bonusKingQueen(player, chicken, 2);

            player.get(0).countGoods = goodsplayer0.countCheese;
            player.get(1).countGoods = goodsplayer1.countCheese;
            
            Specific cheese = new Cheese();
            countGoods.bonusKingQueen(player, cheese, 2);

        }
        //ordonam descrescator dupa bani jucatorii
        Collections.sort(player, specificComp);

        for (int b = 0; b < player.size(); b++) {
            if (player.get(b) instanceof Bribe) {
                System.out.println("BRIBED:" + " " + player.get(b).coins);
                continue;
            }
            if (player.get(b) instanceof Greedy) {
                System.out.println("GREEDY:" + " " + player.get(b).coins);
                continue;
            }
            if (player.get(b) instanceof Basic) {
                System.out.println("BASIC:" + " " + player.get(b).coins);
                continue;
            }
        }

    }

    protected static Comparator<Basic> specificComp = new Comparator<Basic>() {

        public int compare(final Basic c1, final Basic c2) {
            return c2.coins - c1.coins;
        }

    };

    static List<String> transformAssets(final List<Integer> mAssetOrder) {
        List<String> mAssetIds = new ArrayList<>();
        final int zero = 0;
        final int unu = 1;
        final int doi = 2;
        final int trei = 3;
        final int zece = 10;
        final int unsprezece = 11;
        final int doisprezece = 12;
        for (int i = 0; i < mAssetOrder.size(); i++) {
            if (mAssetOrder.get(i) == zero) {
                mAssetIds.add("Apple");
            }
            if (mAssetOrder.get(i) == unu) {
                mAssetIds.add("Cheese");

            }
            if (mAssetOrder.get(i) == doi) {
                mAssetIds.add("Bread");

            }
            if (mAssetOrder.get(i) == trei) {
                mAssetIds.add("Chicken");

            }
            if (mAssetOrder.get(i) == zece) {
                mAssetIds.add("Silk");

            }
            if (mAssetOrder.get(i) == unsprezece) {
                mAssetIds.add("Pepper");

            }
            if (mAssetOrder.get(i) == doisprezece) {
                mAssetIds.add("Barrel");
            }
        }

        return mAssetIds;

    }

    static List<Basic> addPlayers(final List<String> mPlayersOrder) {
        List<Basic> player = new ArrayList<>();
        for (int i = 0; i < mPlayersOrder.size(); i++) {
            if (mPlayersOrder.get(i).equals("greedy")) {
                Basic p = new Greedy();
                player.add(p);
            }
            if (mPlayersOrder.get(i).equals("bribed")) {
                Basic p = new Bribe();
                player.add(p);
            }
            if (mPlayersOrder.get(i).equals("basic")) {
                Basic p = new Basic();
                player.add(p);
            }
        }
        return player;
    }

    static void setBribeToNull(final Basic p1, final Basic p2, final Basic p3) {
        p1.bribe = 0;
        p2.bribe = 0;
        p3.bribe = 0;
    }

    static int getMoneyFromSelling(final Basic p, final List<Specific> assetsOnMerchantStand) {
        for (int b = 0; b < assetsOnMerchantStand.size(); b++) {

            if (assetsOnMerchantStand.get(b) instanceof Pepper) {
                p.coins = p.coins + assetsOnMerchantStand.get(b).profit;
                p.coins = p.coins + assetsOnMerchantStand.get(b).bonus;
                continue;
            }
            if (assetsOnMerchantStand.get(b) instanceof Silk) {
                p.coins = p.coins + assetsOnMerchantStand.get(b).bonus;
                p.coins = p.coins + assetsOnMerchantStand.get(b).profit;
                continue;
            }
            if (assetsOnMerchantStand.get(b) instanceof Barrel) {
                p.coins = p.coins + assetsOnMerchantStand.get(b).bonus;
                p.coins = p.coins + assetsOnMerchantStand.get(b).profit;
                continue;
            }
            p.coins = p.coins + assetsOnMerchantStand.get(b).profit;

        }
        return p.coins;
    }

    static void aRound(final Basic p1, final Basic p2, final Basic p3, final int playerOrder,
            final int nrRound, final List<String> cards) {
        final int doi = 2;
        final int trei = 3;
        if (playerOrder == doi) {
            //verificam tipul serifului
            if (p1 instanceof Greedy) {
                /*
                 * verificam tipul primului comerciant, cum al doilea
                 * va fi sigur ultimul tip de jucator care a ramas
                 */
                if (p2 instanceof Bribe) {
                    ((Bribe) p2).sack(p2.assets);
                    p2.decalreAssets(p2.sack, p2.assets);
                    p2.removeFromHand(p2.sack, p2.assets);

                    if (p2.bribe != 0) {
                        p2.addOnMerchantStand(p2.sack, p2);
                        p1.coins = p1.coins + p2.bribe;
                        return;
                    } else {
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        return;

                    }
                }

                if (p2 instanceof Basic) {
                    p2.createSack(p2.assets);
                    p2.decalreAssets(p2.sack, p2.assets);
                    p2.removeFromHand(p2.sack, p2.assets);
                    p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                    return;
                }

            }
            if (p1 instanceof Bribe) {

                if (p2 instanceof Greedy) {
                    if (nrRound % 2 != 0) {
                        p2.createSack(p2.assets);
                        p2.decalreAssets(p2.sack, p2.assets);
                        p2.removeFromHand(p2.sack, p2.assets);
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        return;

                    } else {
                        p2.createSack(p2.assets);

                        p2.decalreAssets(p2.sack, p2.assets);
                        ((Greedy) p2).addIfEven(p2.assets, p2.sack);

                        p2.removeFromHand(p2.sack, p2.assets);
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        return;
                    }
                }
                if (p2 instanceof Basic) {
                    p2.createSack(p2.assets);
                    p2.decalreAssets(p2.sack, p2.assets);
                    p2.removeFromHand(p2.sack, p2.assets);
                    p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                    return;
                }

            }

            if (p1 instanceof Basic) {
                if (p2 instanceof Bribe) {
                    ((Bribe) p2).sack(p2.assets);
                    p2.decalreAssets(p2.sack, p2.assets);
                    p2.removeFromHand(p2.sack, p2.assets);
                    p2.coins = p2.coins + p2.bribe;
                    p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                    return;

                }
                if (p2 instanceof Greedy) {
                    if (nrRound % 2 != 0) {
                        p2.createSack(p2.assets);
                        p2.decalreAssets(p2.sack, p2.assets);
                        p2.removeFromHand(p2.sack, p2.assets);
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        return;

                    } else {
                        p2.createSack(p2.assets);

                        p2.decalreAssets(p2.sack, p2.assets);
                        ((Greedy) p2).addIfEven(p2.assets, p2.sack);

                        p2.removeFromHand(p2.sack, p2.assets);
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        return;
                    }
                }

            }

        }

        if (playerOrder == trei) {

            if (p1 instanceof Greedy) {

                if (p2 instanceof Bribe) {
                    ((Bribe) p2).sack(p2.assets);
                    p2.decalreAssets(p2.sack, p2.assets);
                    p2.removeFromHand(p2.sack, p2.assets);

                    if (p2.bribe != 0) {
                        p2.addOnMerchantStand(p2.sack, p2);
                        p1.coins = p1.coins + p2.bribe;
                        // player p3 BASIC
                        p3.createSack(p3.assets);
                        p3.decalreAssets(p3.sack, p3.assets);
                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);

                        return;
                    } else {
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        // player p3 BASIC
                        p3.createSack(p3.assets);
                        p3.decalreAssets(p3.sack, p3.assets);
                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;

                    }
                }

                if (p2 instanceof Basic) {
                    p2.createSack(p2.assets);
                    p2.decalreAssets(p2.sack, p2.assets);
                    p2.removeFromHand(p2.sack, p2.assets);
                    p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                    // player p3 BRIBE
                    ((Bribe) p3).sack(p3.assets);
                    p3.decalreAssets(p3.sack, p3.assets);
                    p3.removeFromHand(p3.sack, p3.assets);

                    if (p3.bribe != 0) {
                        p3.addOnMerchantStand(p3.sack, p3);
                        p1.coins = p1.coins + p3.bribe;
                        return;
                    } else {
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;

                    }
                }
            }
            if (p1 instanceof Bribe) {

                if (p2 instanceof Greedy) {
                    if (nrRound % 2 != 0) {
                        p2.createSack(p2.assets);
                        p2.decalreAssets(p2.sack, p2.assets);
                        p2.removeFromHand(p2.sack, p2.assets);
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        // player p3 BASIC
                        p3.createSack(p3.assets);
                        p3.decalreAssets(p3.sack, p3.assets);
                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;

                    } else {
                        p2.createSack(p2.assets);
                        p2.decalreAssets(p2.sack, p2.assets);
                        ((Greedy) p2).addIfEven(p2.assets, p2.sack);
                        p2.removeFromHand(p2.sack, p2.assets);
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        // plater p3 BASIC
                        p3.createSack(p3.assets);
                        p3.decalreAssets(p3.sack, p3.assets);
                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;
                    }
                }
                if (p2 instanceof Basic) {
                    p2.createSack(p2.assets);
                    p2.decalreAssets(p2.sack, p2.assets);
                    p2.removeFromHand(p2.sack, p2.assets);
                    p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                    // player p3 GREEDY
                    if (nrRound % 2 != 0) {
                        p3.createSack(p3.assets);
                        p3.decalreAssets(p3.sack, p3.assets);
                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;

                    } else {
                        p3.createSack(p2.assets);

                        p3.decalreAssets(p3.sack, p3.assets);
                        ((Greedy) p3).addIfEven(p3.assets, p3.sack);

                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;
                    }
                }

            }
            if (p1 instanceof Basic) {
                if (p2 instanceof Bribe) {
                    ((Bribe) p2).sack(p2.assets);
                    p2.decalreAssets(p2.sack, p2.assets);
                    p2.removeFromHand(p2.sack, p2.assets);
                    p2.coins = p2.coins + p2.bribe;
                    p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                    // player p3 greedy
                    if (nrRound % 2 != 0) {
                        p3.createSack(p3.assets);
                        p3.decalreAssets(p3.sack, p3.assets);
                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;

                    } else {
                        p3.createSack(p3.assets);
                        p3.decalreAssets(p3.sack, p3.assets);
                        ((Greedy) p3).addIfEven(p3.assets, p3.sack);
                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;
                    }

                }
                if (p2 instanceof Greedy) {
                    if (nrRound % 2 != 0) {
                        p2.createSack(p2.assets);
                        p2.decalreAssets(p2.sack, p2.assets);
                        p2.removeFromHand(p2.sack, p2.assets);
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        // player p3 BRIBE
                        ((Bribe) p3).sack(p3.assets);
                        p3.decalreAssets(p3.sack, p3.assets);
                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.coins = p3.coins + p3.bribe;
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;

                    } else {
                        p2.createSack(p2.assets);
                        p2.decalreAssets(p2.sack, p2.assets);
                        ((Greedy) p2).addIfEven(p2.assets, p2.sack);
                        p2.removeFromHand(p2.sack, p2.assets);
                        p2.checkedBySheriff(p2.sack, p2.declareAsset, p1, p2, cards);
                        // player p3 BRIBE
                        ((Bribe) p3).sack(p3.assets);
                        p3.decalreAssets(p3.sack, p3.assets);
                        p3.removeFromHand(p3.sack, p3.assets);
                        p3.coins = p3.coins + p3.bribe;
                        p3.checkedBySheriff(p3.sack, p3.declareAsset, p1, p3, cards);
                        return;
                    }
                }

            }

        }
    }
}
