package com.touchcorp.interview;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class BankTest {
    @Test
    public void TestGetFraudSet() {
        List<String> trans = new ArrayList<String>();
        trans.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 20.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 50.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 80.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 70.00");

        trans.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T13:15:54, 10.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T13:15:54, 20.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T13:15:54, 40.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T13:15:54, 31.00");

        trans.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-29T13:15:54, 10.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-29T13:15:54, 20.00");
        trans.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-29T13:15:54, 40.00");

        Set<String> fraudSet = Bank.getFraudSet(trans, "2014-04-29", 100);

        Set<String> expectedSet = new HashSet<String>();
        expectedSet.add("10d7ce2f43e35fa57d1bbf8b1e2");
        expectedSet.add("10d7ce2f43e35fa57d1bbf8b1e3");
        assertEquals(expectedSet, fraudSet);
    }

    class Client implements Runnable {
        private String cardNO;
        private String date;
        private double ammount;
        private int threshhold;

        public Client(String cardNO, String date, double ammount, int threshhold) {
            this.cardNO = cardNO;
            this.date = date;
            this.ammount = ammount;
            this.threshhold = threshhold;
        }

        public void run() {
            Bank.consume(cardNO, date, ammount, threshhold);
        }
    }

    @Test
    public void BankConsumeInMultipleThread() {
        Client[] client = new Client[100];
        for (int i = 0; i < 100; i++) {
            client[i] = new Client("88888888" + "i", "2014-10-18", i, 4950);
        }

        Thread[] t = new Thread[100];
        for (int i = 0; i < 100; i++) {
            t[i] = new Thread(client[i]);
        }

        for (int i = 0; i < 100; i++) {
            t[i].start();
        }

        for (int i = 0; i < 100; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //make sure every card is run out of credit
        for (int i = 0; i < 100; i++) {
            Card card = Bank.getCard("88888888" + "i");
            assertEquals(0, card.getCredit("2014-10-18"), 0.001D);
        }
    }
}
