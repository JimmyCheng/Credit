package com.touchcorp.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Store {
    public static void main(String args[]){
        List<String> trans = new ArrayList<String>();
        trans.add("10d7ce2f43e35fa57d1bbf8b1e1, 2014-04-29T11:15:54, 20.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e1, 2014-04-29T12:15:54, 20.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e1, 2014-04-29T13:15:54, 60.01");
        
        trans.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T11:15:54, 10.0xxx0");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T12:15:54, 20.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 50.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T14:15:54, 80.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T15:15:54, 70.00");
        
        trans.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T11:15:54, 10.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T12:15:54, 20.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T13:15:54, 40.00");
        
        trans.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-29T11:15:54, 10.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-29T12:15:54, 20.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-29T13:15:54, 40.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-29T14:15:54, 31.00");
        
        Set<String> fraudSet = Bank.getFraudSet(trans, "2014-04-29", 100);
        
        System.out.println("Be cautious to following cards:");
        for(String cardNO: fraudSet){
            System.out.println(cardNO);
        }
    }
}
