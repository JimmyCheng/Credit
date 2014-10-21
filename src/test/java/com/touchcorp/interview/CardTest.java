package com.touchcorp.interview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CardTest {

    private Card card;

    @Test
    public void CardConsumeInSingleThread() {
        card = new Card(1000);
        boolean consumed = card.consume("2014-10-18", 200);
        assertTrue(consumed);
        consumed = card.consume("2014-10-18", 300);
        assertTrue(consumed);
        consumed = card.consume("2014-10-18", 400);
        assertTrue(consumed);
        consumed = card.consume("2014-10-18", 400);
        assertFalse(consumed);
    }

    class Consume implements Runnable{
        private int ammount;
        public Consume(int val){
            ammount = val;
        }
        public void run() {
            card.consume("2014-10-18", ammount);
        }
    }
    
    @Test
    public void CardConsumeWith100Threads() {
        card = new Card(4950);
        Consume[] con = new Consume[100];
        for(int i=0; i<100; i++){
            con[i] = new Consume(i);
        }
         
        Thread[] t = new Thread[100];
        for(int i=0; i<100; i++){
            t[i] = new Thread(con[i]); 
        }
        
        for(int i=0; i<100; i++){
            t[i].start(); 
        }        
        
        for(int i=0; i<100; i++){
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }        

        assertEquals(0, card.getCredit("2014-10-18"), 0.001D);
    }
    
    @Test
    public void CardConsumeWith1000Threads() {
        card = new Card(499500);
        Consume[] con = new Consume[1000];
        for(int i=0; i<1000; i++){
            con[i] = new Consume(i);
        }
         
        Thread[] t = new Thread[1000];
        for(int i=0; i<1000; i++){
            t[i] = new Thread(con[i]); 
        }
        
        for(int i=0; i<1000; i++){
            t[i].start(); 
        }
        
        for(int i=0; i<1000; i++){
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }
        assertEquals(0, card.getCredit("2014-10-18"), 0.001D);
    }
    
    @Test 
    public void CardConsumeWith1100Threads() {
        card = new Card(499500);
        Consume[] con = new Consume[1100];
        for(int i=0; i<1100; i++){
            con[i] = new Consume(i);
        }
         
        Thread[] t = new Thread[1100];
        for(int i=0; i<1100; i++){
            t[i] = new Thread(con[i]); 
        }
        
        for(int i=0; i<1100; i++){
            t[i].start(); 
        }
        
        for(int i=0; i<1100; i++){
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
        }
        
        //the thread from 1000 - 1100 failed to consume.
        assertEquals(0, card.getCredit("2014-10-18"), 0.001D);
    }    
}
