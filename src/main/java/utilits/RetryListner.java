package utilits;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListner implements IRetryAnalyzer {

    @Override
    public boolean retry(ITestResult result) {
        int counter=0;
        int retry=2;
        if(counter<retry){
            counter++;
            return true;
        }
        return false;
    }
    
}
