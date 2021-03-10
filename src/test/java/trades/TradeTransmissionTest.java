package trades;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Order of Junit Testcases
public class TradeTransmissionTest {

    TradeTransmission tradeTransmission = new TradeTransmission();
    Date todaysDate= Calendar.getInstance ().getTime ();
    SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");

    @Test
    void testIfTradeEmpty(){
        assertTrue(tradeTransmission.checkIfTradeEmpty());
    }

    //Check if 1st Trade is added
    //T1	1	CP-1	B1	20/05/2021	today date	N
    @Test
    @Order(1)
    void testAddTrade() throws Exception{
        Trade t1=new Trade();
        t1.setTradeId("T1");
        t1.setVersion(1);
        t1.setCounterPartyId("CP-1");
        t1.setBookId("B1");
        t1.setMaturityDate(sd.parse("20/05/2021"));
        t1.setCreatedDate(todaysDate);
        t1.setExpired('N');
        tradeTransmission.addTrade(t1);
        //Checking if
        assertEquals(1,tradeTransmission.tradeHashMap.size());
    }

    //Check for Version

    //Check if Version is high the list will be updated
    //T2	2	CP-1	B1	20/05/2021	today date	N
    //T2	5	CP-5	B1	20/05/2021	today date 	N
    @Test
    @Order(2)
    void testVersionHigh() throws Exception{
        Date maturityDate=sd.parse("20/05/2021");
        Trade t2=new Trade();
        t2.setTradeId("T2");
        t2.setVersion(2);
        t2.setCounterPartyId("CP-2");
        t2.setBookId("B1");
        t2.setMaturityDate(maturityDate);
        t2.setCreatedDate(todaysDate);
        t2.setExpired('N');
        tradeTransmission.addTrade(t2);

        //Changing Version as 3 and Changing Counter-Party ID to CP-4
        Trade t22=new Trade();
        t22.setTradeId("T2");
        t22.setVersion(3);
        t22.setCounterPartyId("CP-4");
        t22.setBookId("B1");
        t22.setMaturityDate(maturityDate);
        t22.setCreatedDate(todaysDate);
        t22.setExpired('N');
        tradeTransmission.addTrade(t22);

        assertEquals("CP-4",tradeTransmission.tradeHashMap.get("T2").getCounterPartyId());
    }

    //Check if Version is same the list will be updated
    //T1	1	CP-1	B1	20/05/2020	today date  N
    //T1	1	CP-2	B1	20/05/2020	today date	N
    @Test
    @Order(3)
    void testVersionSame() throws Exception{
        //Same Version as before and Changing Counter-Party ID to CP-2
        Trade t1=new Trade();
        t1.setTradeId("T1");
        t1.setVersion(1);
        t1.setCounterPartyId("CP-2");
        t1.setBookId("B1");
        t1.setMaturityDate(sd.parse("20/05/2021"));
        t1.setCreatedDate(todaysDate);
        t1.setExpired('N');
        tradeTransmission.addTrade(t1);
        assertEquals("CP-2",tradeTransmission.tradeHashMap.get("T1").getCounterPartyId());
    }

    //Check if Version is low the trade will be rejected
    //T3	5	CP-3	B1	20/05/2014	today date	N
    //T3	1	CP-2	B1	20/05/2014	today date	N
    @Test
    @Order(4)
    void testVersionLow() throws Exception{
        Date maturityDate=sd.parse("20/05/2021");

        Trade t3=new Trade();
        t3.setTradeId("T3");
        t3.setVersion(5);
        t3.setCounterPartyId("CP-3");
        t3.setBookId("B1");
        t3.setMaturityDate(maturityDate);
        t3.setCreatedDate(todaysDate);
        t3.setExpired('N');
        tradeTransmission.addTrade(t3);
        int sizeofList=tradeTransmission.tradeHashMap.size();

        //Now Adding Another List
        Trade t6=new Trade();
        t6.setTradeId("T3");
        t6.setVersion(1);
        t6.setCounterPartyId("CP-2");
        t6.setBookId("B1");
        t6.setMaturityDate(maturityDate);
        t6.setCreatedDate(todaysDate);
        t6.setExpired('N');

        assertThrows(Exception.class,()->tradeTransmission.addTrade(t6),"1 is less than 5");

    }

    //Check if maturity Date is greater than todays date the trade is added
    //T4	5	CP-4	B3	20/05/2021	today date	N
    @Test
    @Order(5)
    void testMaturityGreater() throws Exception{
        Date maturityDate=sd.parse("20/05/2021");

        Trade t7=new Trade();
        t7.setTradeId("T4");
        t7.setVersion(5);
        t7.setCounterPartyId("CP-4");
        t7.setBookId("B3");
        t7.setMaturityDate(maturityDate);
        t7.setCreatedDate(todaysDate);
        t7.setExpired('N');
        tradeTransmission.addTrade(t7);

        assertEquals(t7,tradeTransmission.tradeHashMap.get("T4"));
    }

    //Check if maturity Date is lower than todays date the Trade will not be added
    //T5  5  CP-3  B1  20/05/2020   today date  N
    @Test
    @Order(6)
    void testMaurityLower() throws Exception{
        Date maturityDate=sd.parse("20/05/2020");
        Trade t8=new Trade();
        t8.setTradeId("T5");
        t8.setVersion(5);
        t8.setCounterPartyId("CP-4");
        t8.setBookId("B3");
        t8.setMaturityDate(maturityDate);
        t8.setCreatedDate(todaysDate);
        t8.setExpired('N');
        tradeTransmission.addTrade(t8);
        assertNull(tradeTransmission.tradeHashMap.get("T5"));
    }


    //Check if Maturity Date is Same as Todays Date the list will be added
    //T7 7  CP-5  B4  todaysDate  todaysDate  N
    @Test
    @Order(7)
    void testSameMaturity() throws Exception{
        Date todaysDate=Calendar.getInstance ().getTime ();
        Trade t11=new Trade();
        t11.setTradeId("T7");
        t11.setVersion(7);
        t11.setCounterPartyId("CP-5");
        t11.setBookId("B4");
        t11.setMaturityDate(todaysDate);
        t11.setCreatedDate(todaysDate);
        t11.setExpired('N');
        tradeTransmission.addTrade(t11);
        assertNotNull(tradeTransmission.tradeHashMap.get("T7"));
    }

    //Check If Maturity Date is Expired it will update the Expired Flag
    @Test
    @Order(8)
    void testExpiry() throws ParseException{
        Date maturityDate=sd.parse("20/05/2020");
        Trade t16=new Trade();
        t16.setTradeId("T10");
        t16.setVersion(6);
        t16.setCounterPartyId("CP-4");
        t16.setBookId("B1");
        t16.setMaturityDate(maturityDate);
        t16.setCreatedDate(todaysDate);
        t16.setExpired('N');
        tradeTransmission.tradeHashMap.put("T10",t16); // hardcoded as it need to be tested and the conditio is false
        tradeTransmission.checkExpiredDates();
        assertEquals('Y',tradeTransmission.tradeHashMap.get("T10").getExpired());
    }

    //Empty the HashMap to add / update given testcase from the table
    void removeAllTrade(){
        tradeTransmission.tradeHashMap.clear();
    }

    //Check the testcase for T1	1	CP-1	B1	20/05/2020	<today date>	N
    //Adding the trade will fail so Checking the size of the map to be empty
    @Test
    @Order(9)
    void test1() throws Exception{
        Date maturityDate=sd.parse("20/05/2020");
        Trade t17=new Trade();
        t17.setTradeId("T1");
        t17.setVersion(1);
        t17.setCounterPartyId("CP-2");
        t17.setBookId("B1");
        t17.setMaturityDate(maturityDate);
        t17.setCreatedDate(todaysDate);
        t17.setExpired('N');
        tradeTransmission.addTrade(t17);
        assertEquals(0, tradeTransmission.tradeHashMap.size());
    }

    //Check the testcase for T2	2	CP-2	B1	20/05/2021	<today date>	N
    //Adding the trade will be added in the trade map
    @Test
    @Order(10)
    void test2() throws Exception{
        Date maturityDate=sd.parse("20/05/2021");
        Trade t18 = new Trade();
        t18.setTradeId("T2");
        t18.setVersion(2);
        t18.setCounterPartyId("CP-2");
        t18.setBookId("B1");
        t18.setMaturityDate(maturityDate);
        t18.setCreatedDate(todaysDate);
        t18.setExpired('N');
        tradeTransmission.addTrade(t18);
        assertEquals(1, tradeTransmission.tradeHashMap.size());
    }
    //Check the testcase for T2	1	CP-1	B1	20/05/2021	14/03/2015	N
    //Adding the trade will not be added to the trade list
    @Test
    @Order(11)
    void test3() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");
        Trade t18=new Trade();
        t18.setTradeId("T2");
        t18.setVersion(2);
        t18.setCounterPartyId("CP-2");
        t18.setBookId("B1");
        t18.setMaturityDate(maturityDate);
        t18.setCreatedDate(todaysDate);
        t18.setExpired('N');
        tradeTransmission.addTrade(t18);
        assertEquals(1, tradeTransmission.tradeHashMap.size());

        maturityDate=sd.parse("20/05/2021");
        Date createdDate=sd.parse("14/03/2015");
        Trade t19=new Trade();
        t19.setTradeId("T2");
        t19.setVersion(1);
        t19.setCounterPartyId("CP-2");
        t19.setBookId("B1");
        t19.setMaturityDate(maturityDate);
        t19.setCreatedDate(createdDate);
        t19.setExpired('N');
        assertThrows(Exception.class,()->tradeTransmission.addTrade(t19));
    }

    @Test
    @Order(12)
    void test4() throws Exception
    {
        Date maturityDate=sd.parse("20/05/2021");
        Trade t17=new Trade();
        t17.setTradeId("T1");
        t17.setVersion(1);
        t17.setCounterPartyId("CP-2");
        t17.setBookId("B1");
        t17.setMaturityDate(maturityDate);
        t17.setCreatedDate(todaysDate);
        t17.setExpired('N');
        tradeTransmission.tradeHashMap.put("T1", t17);

        maturityDate=sd.parse("20/05/2021");
        Trade t18=new Trade();
        t18.setTradeId("T2");
        t18.setVersion(2);
        t18.setCounterPartyId("CP-2");
        t18.setBookId("B1");
        t18.setMaturityDate(maturityDate);
        t18.setCreatedDate(todaysDate);
        t18.setExpired('N');
        tradeTransmission.tradeHashMap.put("T2", t18);

        maturityDate=sd.parse("20/05/2020");
        Trade t20=new Trade();
        t20.setTradeId("T3");
        t20.setVersion(3);
        t20.setCounterPartyId("CP-3");
        t20.setBookId("B2");
        t20.setMaturityDate(maturityDate);
        t20.setCreatedDate(todaysDate);
        t20.setExpired('N');
        tradeTransmission.tradeHashMap.put("T3", t20);

        tradeTransmission.checkExpiredDates();
        tradeTransmission.printTrade();
        assertEquals('Y',tradeTransmission.tradeHashMap.get("T3").getExpired());
    }
}
