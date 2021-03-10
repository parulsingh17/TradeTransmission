package trades;

import java.util.Date;
import java.util.HashMap;

public class TradeTransmission {

    HashMap<String,Trade> tradeHashMap = new HashMap<String,Trade>();

    //add Trade
    public void addTrade(Trade T) throws Exception{
        if(tradeHashMap.containsKey(T.getTradeId())){
            //Checking Version
            checkVersion(T, tradeHashMap.get(T.getTradeId()).getVersion());

            if(checkMaturityDate(T.getMaturityDate(), tradeHashMap.get(T.getTradeId()).getMaturityDate())){
                tradeHashMap.replace(T.getTradeId(), T);
                System.out.println(T.getTradeId()+" is added to the Store");
            }else{
                System.out.println("Store does not allow the trade ("+T.getTradeId()+") as it has less maturity date then current date");
            }
        }else{
            if(checkMaturityDate(T.getMaturityDate(), T.getCreatedDate())){
                tradeHashMap.put(T.getTradeId(), T);
                System.out.println(T.getTradeId()+" is added to the Store");
            }else{
                System.out.println("Store does not allow the trade ("+T.getTradeId()+") as it has less maturity date then current date");
            }
        }
    }

    //Check if the lower version is being received by the store it will reject the trade and throw an exception.
    //If the version is same it will override the existing record
    public void checkVersion(Trade t,int version) throws Exception{
        if(t.getVersion()< version){
            throw new Exception(t.getVersion()+" is less than "+ version);
        }
    }

    //Check if maturityDate
    public boolean checkMaturityDate(Date maturityDate, Date CurrentDate) {
        if(CurrentDate.compareTo(maturityDate)>0)
            return false;
        return true;
    }

    //printAllTrades
    public void printTrade(){
        for(String tId : tradeHashMap.keySet()){
            System.out.println(tradeHashMap.get(tId).toString());
        }
    }

    public void checkExpiredDates(){
        //SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate=new Date();
        for(String strKey : tradeHashMap.keySet() ){
            if(currentDate.compareTo(tradeHashMap.get(strKey).getMaturityDate())>0){
                Trade t=tradeHashMap.get(strKey);
                t.setExpired('Y');
                tradeHashMap.replace(strKey, t);
            }
        }
    }

    //Check if no trade Exists
    public boolean checkIfTradeEmpty(){
        return tradeHashMap.isEmpty();
    }

}
