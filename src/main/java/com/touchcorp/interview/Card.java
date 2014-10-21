package com.touchcorp.interview;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Card {
    private double threshhold;
    private final ConcurrentMap<String, Double> records = new ConcurrentHashMap<String, Double>(100);

    public Card() {
        threshhold = 100;
    }

    public Card(double threshold) {
        this.threshhold = threshold;
    }
    
    public double getCredit(String date){
        return records.get(date) - threshhold;
    }

    public boolean consume(String date, double ammount) {
        Double oldAmmount, newAmmount;

        while (true) {
            oldAmmount = records.putIfAbsent(date, ammount);

            //oldAmmount is null means this card haven't record before.
            if (oldAmmount == null){
                return true;
            }

            newAmmount = oldAmmount + ammount;
            if (newAmmount > threshhold) {
                return false;
            } else {
                if (records.replace(date, oldAmmount, newAmmount)) {
                    return true;
                }
            }
        }
    }

    public double getThreshHold() {
        return threshhold;
    }

    public void setThreshHold(double threshHold) {
        this.threshhold = threshHold;
    }
}
