/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.listener;

import java.util.TimerTask;
import tranhdayroi.domparser.TranhDepCom;
import tranhdayroi.domparser.TranhTreoTuongVn;
import tranhdayroi.domparser.TuongXinhComVn;
import tranhdayroi.domparser.Parser;

/**
 *
 * @author MinhDuc
 */
public class ScheduledParsingTask extends TimerTask{

    @Override
    public void run() {        
        System.out.println("===== START PARSING =====");
        
        int totalSucc = 0;
        int totalFail = 0;
        
        Parser mp = new TuongXinhComVn();
        mp.parseFromDomain(mp);
        totalSucc += mp.getnSuccess();
        totalFail += mp.getnFail();
        
        mp = new TranhDepCom();
        mp.parseFromDomain(mp);
        totalSucc += mp.getnSuccess();
        totalFail += mp.getnFail();
        
        mp = new TranhTreoTuongVn();
        mp.parseFromDomain(mp);
        totalSucc += mp.getnSuccess();
        totalFail += mp.getnFail();
        
        System.out.println("===== FINISH PARSING =====");
        System.out.println(totalSucc+" new Paintings. " + totalFail+" duplicated (skipped).");
    }
    
}
