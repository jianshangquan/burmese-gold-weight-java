package com.bytete;

import java.math.BigDecimal;

public class SIWeight {
    BigDecimal gram;

    public SIWeight(){
    }

    public SIWeight(BurmeseGoldWeight burmeseGoldWeight){
        gram = burmeseGoldWeight.toGram();
    }
}
