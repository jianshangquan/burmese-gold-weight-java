package com.bytete;

import java.math.BigDecimal;

public class App 
{
    public static void main( String[] args ) {

        // Usage

        // From gram
        BurmeseGoldWeight fromGram = new BurmeseGoldWeight(new SIWeight(1234));
        BurmeseGoldWeight fromGram2 = new BurmeseGoldWeight(1234);

        // Use directly use with constructor
        BurmeseGoldWeight ringWithCopper = new BurmeseGoldWeight(0,1,0,0); // ရွှေထည် ၁ကျပ်သား
        BurmeseGoldWeight wastage = new BurmeseGoldWeight(0, 0, 1, 2); // အလျေ့ာအတွက် ၁ပဲ ၂ရွေး
        BurmeseGoldWeight copper = new BurmeseGoldWeight(0, 0, 1, 0); // ကြေး(အတွင်းစပ်) ၁ပဲ


        BurmeseGoldWeight purifiedGold = ringWithCopper.byBurmeseGoldQuality(15); // ၁၅ပဲရည် အခေါက်ရွှေချွတ်ပြီး
        BurmeseGoldWeight purifiedGold2 = ringWithCopper.byInternationalGoldQuality(22); // 22/24 K အခေါက်ရွှေချွတ်ပြီး


        BurmeseGoldWeight gold = ringWithCopper.add(wastage); // ရွှေထည် + အလျော့အတွက် = အထည်လုပ် အချိန်
        BurmeseGoldWeight pureGold = ringWithCopper.substract(copper); // ရွှေထည် - ကြေး = အခေါက်


        // ရောင်းစျေး/ ဝယ်စျေး
        ringWithCopper.getBurmeseMarketValuePrice(3_000_000); // အခေါက်ရွှေ သိန်း၃၀ ပေါက်စျေး၏ ရွှေထည်ရောင်းစျေး
        ringWithCopper.getBurmeseMarketValuePrice(
                2_500_000, // အခေါက်ရွှေစျေး
                50_000 // ခွာစျေး
        ); // အခေါက်ရွှေ ၂၅သိန်း ပေါက်စျေး၏ ရွှေထည်ရောင်းစျေး;



        // Conversion
        ringWithCopper.toPatetha(); // စုစုပေါင်း ပိဿာအချိန်
        ringWithCopper.toKyat(); // စုစုပေါင်း ကျပ်အချိန်
        ringWithCopper.toPae(); // စုစုပေါင်း ပဲအချိန်
        ringWithCopper.toYway(); // စုစုပေါင်း ရွှေးအချိန်
        ringWithCopper.toGram(); // စုစုပေါင်း gram ဂရမ်အချိန်



        // Printing
        ringWithCopper.toString(); // Output: "0ပိဿာ 1ကျပ် 0ပဲ 0ရွေး";
        ringWithCopper.print(BurmeseGoldWeight.PrintType.PATETHA); // Output: "0.01ပိဿာ"
        ringWithCopper.print(BurmeseGoldWeight.PrintType.KYAT); // Output: "1ကျပ်"
        ringWithCopper.print(BurmeseGoldWeight.PrintType.PAE); // Output: "16ပဲ"
        ringWithCopper.print(BurmeseGoldWeight.PrintType.YWAY); // Output: "128ရွေး"
        ringWithCopper.print(BurmeseGoldWeight.PrintType.GRAM); // Output: "16.66666666ဂရမ်"


    }
}
