package com.bytete;

import com.bytete.Exceptions.GoldQualityOutOfBound;

public class App 
{
    public static void main( String[] args ) {
        BurmeseGoldWeight weight = new BurmeseGoldWeight(1000,99,15,0);
        System.out.println(weight.byBurmeseGoldQuality(16).print(BurmeseGoldWeight.PrintType.PATETHA));
    }
}
