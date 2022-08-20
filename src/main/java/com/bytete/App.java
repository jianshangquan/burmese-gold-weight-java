package com.bytete;

import java.math.BigDecimal;

public class App 
{
    public static void main( String[] args ) {
        BurmeseGoldWeight gold = new BurmeseGoldWeight(0,1,0,0);
        BurmeseGoldWeight waste = new BurmeseGoldWeight(0, 0, 1, 2);

        BigDecimal total = gold.byBurmeseGoldQuality(15)
                               .add(waste)
                               .getBurmeseMarketValuePrice(3000000, 25000);

        System.out.println(gold.byBurmeseGoldQuality(15).substract(waste));


    }
}
