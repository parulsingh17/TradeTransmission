package trades;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TradeMain {

    public static void main(String[] args) throws Exception {

        TradeTransmission tradeTransmission=new TradeTransmission();
        Date todaysDate=Calendar.getInstance ().getTime ();
        SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");

//		Adding Trade T1
		Trade t1=new Trade();
        t1.setTradeId("T1");
        t1.setVersion(1);
        t1.setCounterPartyId("CP-1");
        t1.setBookId("B1");
        t1.setMaturityDate(sd.parse("20/06/2021"));
        t1.setCreatedDate(todaysDate);
        t1.setExpired('N');
        tradeTransmission.addTrade(t1);

        Date maturityDate=sd.parse("20/05/2021");

        //Adding Trade T2
        Trade t2=new Trade();
        t2.setTradeId("T2");
        t2.setVersion(2);
        t2.setCounterPartyId("CP-2");
        t2.setBookId("B1");
        t2.setMaturityDate(maturityDate);
        t2.setCreatedDate(todaysDate);
        t2.setExpired('N');
        tradeTransmission.addTrade(t2);

        //Changing Trade T2 lower version
        /*Trade t22=new Trade();
        t22.setTradeId("T2");
        t22.setVersion(1);
        t22.setCounterPartyId("CP-1");
        t22.setBookId("B1");
        t22.setMaturityDate(maturityDate);
        t22.setCreatedDate(sd.parse("14/03/2015"));
        t22.setExpired('N');
        tradeTransmission.addTrade(t22);*/

        //Adding Trade T3
        Trade t3=new Trade();
        t3.setTradeId("T3");
        t3.setVersion(3);
        t3.setCounterPartyId("CP-3");
        t3.setBookId("B2");
        t3.setMaturityDate(maturityDate);
        t3.setCreatedDate(todaysDate);
        t3.setExpired('N');
        tradeTransmission.addTrade(t3);

        //Print all Trade
        System.out.println("\n\n");
        System.out.println("Displaying total number of Trade in the list");
        tradeTransmission.printTrade();
        System.out.println("\n\n");

        //Checking for all Expired Flag
        System.out.println("Checking for Expired Flag");
        Trade t32=new Trade();
        t32.setTradeId("T3");
        t32.setVersion(3);
        t32.setCounterPartyId("CP-3");
        t32.setBookId("B2");
        t32.setMaturityDate(sd.parse("20/05/2014"));
        t32.setCreatedDate(todaysDate);
        t32.setExpired('N');
        tradeTransmission.tradeHashMap.replace("T3", t32);
        tradeTransmission.checkExpiredDates();
        tradeTransmission.printTrade();



    }

}
