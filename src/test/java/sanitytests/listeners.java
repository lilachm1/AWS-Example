package sanitytests;

import org.testng.ITestContext ;
import org.testng.ITestListener ;
import org.testng.ITestResult ;


public class listeners implements ITestListener{

    public void onStart(ITestContext execution)
    {
        // TODO Auto-generated method stub
    }

    public void onFinish(ITestContext execution)
    {
        // TODO Auto-generated method stub
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0)
    {
        // TODO Auto-generated method stub
    }

    public void onTestSkipped(ITestResult test)
    {
        // TODO Auto-generated method stub
    }

    public void onTestStart(ITestResult test)
    {
        // TODO Auto-generated method stub
    }

    public void onTestSuccess(ITestResult test)
    {
        System.out.println("------------ Test: " + test.getName() + " Passed ------------" + test.getName());
    }

    public void onTestFailure(ITestResult test)
    {
        System.out.println("------------ Test "  + test.getName() + " Failed, ------------");
    }
}

