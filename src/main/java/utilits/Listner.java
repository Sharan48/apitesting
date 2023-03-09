package utilits;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class Listner extends ExtentManager implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentManager.setExtent();
        test=extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
       test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test Failed");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

   
    
}
