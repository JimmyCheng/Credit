package com.touchcorp.interview;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Bank {
    private static final ConcurrentMap<String, Card> cards = new ConcurrentHashMap<String, Card>(100);

    public static Card getCard(String cardNo){
        return cards.get(cardNo);
    }
    
    public static boolean consume(String cardNO, String date, double ammount, int threshhold) {
        if(ammount > threshhold){
            return false;
        }

        Card newCard = new Card(threshhold);
        newCard.consume(date, ammount);
        Card oldCard = cards.putIfAbsent(cardNO, newCard);
        // oldCard is null means this is the first record.
        if (oldCard == null) {
            return true;
        } else {
            return oldCard.consume(date, ammount);
        }
    }
    
    public static Set<String> getFraudSet(List<String> trans, String Date, int threshhold) {
        Set<String> fraudSet = new HashSet<String>();
        if (trans == null || trans.size() == 0) {
            return fraudSet;
        }

        for (String tran : trans) {
            String[] param = tran.split(",");
            if (param.length == 3) {
                try {
                    String cardNO = param[0].trim();
                    String dateString = param[1].trim();
                    String date = dateString.substring(0, dateString.indexOf('T'));
                    double ammount = Double.valueOf(param[2].trim());
                    if (ammount > threshhold || !consume(cardNO, date, ammount, threshhold)) {
                        fraudSet.add(cardNO);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("incorrect transaction info:" + tran);
                }
            } else {
                System.out.println("incorrect transaction info:" + tran);
                continue;
            }
        }
        return fraudSet;
    }
}
