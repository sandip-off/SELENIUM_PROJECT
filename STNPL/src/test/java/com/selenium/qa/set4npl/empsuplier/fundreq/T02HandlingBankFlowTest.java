package com.selenium.qa.set4npl.empsuplier.fundreq;

import java.awt.AWTException;
import com.selenium.qa.set4npl.objects.HandlingBankFlowUtility;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.selenium.qa.Login;

public class T02HandlingBankFlowTest extends Login {
    
    WebDriver driver;
    HandlingBankFlowUtility handlingBankFlow;
    
   @BeforeMethod
   public void setup() throws InterruptedException {
        // Initialize the driver
        driver = initializeBrowserAndOpenApplication("firefox");
        driver=loginAs("handlingbank");
        
        // Create instance of utility class
     handlingBankFlow= new HandlingBankFlowUtility(driver);
        
        // Perform common setup
       // handlingBankFlow.setup();
    }
    
    @AfterMethod
    public void teardown() {
    	if (handlingBankFlow != null) {
            handlingBankFlow.teardown();
        }
    }
    
   @Test(priority = 1, description = "Test processing eligible request")
    public void testEligibleRequest() throws InterruptedException, AWTException {
    //	handlingBankFlow.setup();
    	
        handlingBankFlow.processEligibleRequest();
    }
    
    @Test(priority = 2, description = "Test processing incomplete request")
    public void testIncompleteRequest() throws InterruptedException {
        handlingBankFlow.setup();
    	handlingBankFlow.processIncompleteRequest();
    }
    
    @Test(priority = 3, description = "Test processing rejected request")
    public void testRejectedRequest() throws InterruptedException {
        handlingBankFlow.setup();
    	handlingBankFlow.processRejectedRequest();
    }
    
    @Test(priority = 4, description = "Test processing report request")
    public void testReportRequest() throws InterruptedException {
    	handlingBankFlow.setupForReport();
        handlingBankFlow.processReportRequest();
    }
    
   
   
}