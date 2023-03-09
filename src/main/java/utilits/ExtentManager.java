package utilits;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    public static ExtentReports extent;
    public static ExtentTest test;

    public static void setExtent() {

        //Will conrtain all test
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/APIReport.html");

        htmlReporter.config().setCSS("css-string");
        htmlReporter.config().setDocumentTitle("page title");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setJS("js-string");
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setReportName("RestAssured Report");
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);


    }

}
