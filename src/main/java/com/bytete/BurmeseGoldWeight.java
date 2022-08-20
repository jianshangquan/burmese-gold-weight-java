package com.bytete;

import com.bytete.Exceptions.GoldQualityOutOfBound;
import com.bytete.Exceptions.InvalidBurmeseWeight;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BurmeseGoldWeight {

    public enum PrintType{
        KYAT,
        PAE,
        YWAY,
        PATETHA,
        GRAM
    }

    public static final MathContext precision = new MathContext( 8);
    public static final BigDecimal ONE_PATETHA_IN_KYAT = BigDecimal.valueOf(100);
    public static final BigDecimal ONE_KYAT_IN_YWAY = BigDecimal.valueOf(128);
    public static final BigDecimal ONE_KYAT_IN_PAE = BigDecimal.valueOf(16);
    public static final BigDecimal ONE_PAE_IN_YWAY = BigDecimal.valueOf(8);

    public static final BigDecimal ONE_KYAT_IN_GRAM = BigDecimal.valueOf(16.66666666);
    public static final BigDecimal ONE_PAE_IN_GRAM = ONE_KYAT_IN_GRAM.divide(BigDecimal.valueOf(16), precision);

    public static final RoundingMode DEFAULT_ROUND_MODE = RoundingMode.HALF_DOWN;

    int patetha, kyat, pae;
    BigDecimal yway;


    public BurmeseGoldWeight(){
        patetha = 0;
        kyat = 0;
        pae = 0;
        yway = BigDecimal.ZERO;
    }


    public BurmeseGoldWeight(BurmeseGoldWeight burmeseGoldWeight){
        this(burmeseGoldWeight.patetha, burmeseGoldWeight.kyat, burmeseGoldWeight.pae, burmeseGoldWeight.yway);
    }

    public BurmeseGoldWeight(long gram){
        BurmeseGoldWeight w = fromGram(gram);
        this.kyat = w.kyat;
        this.pae = w.pae;
        this.yway = w.yway;
        this.patetha = w.patetha;
    }

    public BurmeseGoldWeight(int patetha, int kyat, int pae, double yway) {
        this(patetha, kyat, pae, BigDecimal.valueOf(yway));
    }

    public BurmeseGoldWeight(int patetha,  int kyat, int pae, BigDecimal yway) {
        if(kyat > 99 && kyat > -1) throw new InvalidBurmeseWeight("Kyat should between 0 ~ 99");
        if(pae > 15) throw new InvalidBurmeseWeight("Pae should between 0 ~ 15");
        if(yway.compareTo(ONE_PAE_IN_YWAY) == 1) throw new InvalidBurmeseWeight("Yway should between 0 and less than <8");
        this.patetha = patetha;
        this.kyat = kyat;
        this.pae = pae;
        this.yway = yway;
    }

    public BigDecimal toPatetha(){
        return toKyat().divide(ONE_PATETHA_IN_KYAT, precision);
    }
    public BigDecimal toKyat() {
        return toPae().divide(ONE_KYAT_IN_PAE, precision);
    }

    public BigDecimal toPae(){
        return new BigDecimal(patetha).multiply(ONE_KYAT_IN_PAE.multiply(ONE_PATETHA_IN_KYAT)).add(BigDecimal.valueOf(kyat).multiply(ONE_KYAT_IN_PAE).add(BigDecimal.valueOf(pae)).add(yway.divide(ONE_PAE_IN_YWAY))).round(precision);
    }

    public BigDecimal toYway(){
        return toPae().multiply(ONE_PAE_IN_YWAY).round(precision);
    }

    public BigDecimal toGram(){
        return toPae().multiply(ONE_KYAT_IN_GRAM.divide(ONE_KYAT_IN_PAE, precision)).round(precision);
    }

    private BurmeseGoldWeight fromGram(long gram){
        BigDecimal k = BigDecimal.valueOf(gram).multiply(ONE_PAE_IN_GRAM).divide(ONE_KYAT_IN_PAE, precision);
        return new BurmeseGoldWeight();
    }


    private BurmeseGoldWeight kyatToBurmeseWeight(BigDecimal kyat){
        BigDecimal pt, k,p,y;
        pt = kyat.divide(ONE_PATETHA_IN_KYAT).setScale(0, RoundingMode.DOWN);
        k = kyat.subtract(pt.multiply(ONE_PATETHA_IN_KYAT)).setScale(0, RoundingMode.DOWN);

        BigDecimal subtractPatethaAndKyat = pt.multiply(ONE_PATETHA_IN_KYAT).add(k);
        p = kyat.subtract(subtractPatethaAndKyat).multiply(ONE_KYAT_IN_PAE).setScale(0, RoundingMode.DOWN);
        y = kyat.subtract(subtractPatethaAndKyat).subtract(p.divide(ONE_KYAT_IN_PAE)).multiply(ONE_PAE_IN_YWAY);

        return new BurmeseGoldWeight(pt.intValue(), k.intValue(), p.intValue(), y);
    }

    public BurmeseGoldWeight byBurmeseGoldQuality(double quality) {
        if(quality > 16) throw new GoldQualityOutOfBound("Maximum Burmese gold quality is 16ပဲရည်");
        BigDecimal purifiedWeight = toKyat().divide(ONE_KYAT_IN_PAE).multiply(BigDecimal.valueOf(quality)).round(precision);
        return kyatToBurmeseWeight(purifiedWeight);
    }

    public BurmeseGoldWeight byInternationalGoldQuality(double quality) {
        if(quality > 24) throw new GoldQualityOutOfBound("Maximum International gold quality is 24K");
        BigDecimal burmeseQuality = BigDecimal.valueOf(quality).divide(BigDecimal.valueOf(24), precision).multiply(ONE_KYAT_IN_PAE);
        return byBurmeseGoldQuality(burmeseQuality.doubleValue());
    }



    @Override
    public String toString() {
        return this.patetha + "ပိဿာ, " + this.kyat +"ကျပ်, " + this.pae + "ပဲ, " + this.yway.round(precision) + "ရွေး ";
    }


    public String print(PrintType type) {
        switch (type){
            case KYAT: return toKyat() + " ကျပ်သား";
            case PAE: return toPae() + " ပဲ";
            case YWAY: return toYway() + " ရွေး";
            case PATETHA: return toPatetha() + " ပိဿာ";
            case GRAM: return toGram() + " gram";
        }
        return this.patetha + "ပိသာ, " + this.kyat +"ကျပ်, " + this.pae + "ပဲ, " + this.yway.round(precision) + "ရွေး ";
    }
}
