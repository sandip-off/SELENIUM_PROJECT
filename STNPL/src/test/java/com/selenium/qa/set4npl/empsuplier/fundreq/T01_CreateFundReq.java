package com.selenium.qa.set4npl.empsuplier.fundreq;

import java.awt.AWTException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.selenium.qa.Login;
import com.selenium.qa.set4npl.objects.CreateFundRequestPage;
import com.selenium.qa.set4npl.objects.FileUploadPage;

import com.selenium.qa.set4npl.objects.OperationalDetailsPage;
import com.selenium.qa.set4npl.objects.VehicleFinancingPage;
 class T01_CreateFundReqWithFullPayment extends Login {
    
    WebDriver driver;
    int randomNum1 = ThreadLocalRandom.current().nextInt(1000, 10000);
    String  insertedchassil="chassil" + randomNum1;
    String date = "25082025";
    public String userDir = System.getProperty("user.dir");
    public  String filePath = userDir + "\\pdffolder\\compat.pdf"; 
    // Page Object instances
    CreateFundRequestPage createFundPage;
    VehicleFinancingPage vehicleFinancingPage;
    OperationalDetailsPage operationalDetailsPage;
    FileUploadPage fileUploadPage;
    JavascriptExecutor js;
  
    
    @BeforeMethod
    public void setup() {
        driver = initializeBrowserAndOpenApplication("chrome");
        driver = loginAs("supplier");
        //driver.manage().deleteAllCookies();
      //  driver.navigate().refresh();
        
        // Initialize page objects
        createFundPage = new CreateFundRequestPage(driver);
        vehicleFinancingPage = new VehicleFinancingPage(driver);
        operationalDetailsPage = new OperationalDetailsPage(driver);
        fileUploadPage = new FileUploadPage(driver);
        js = (JavascriptExecutor) driver;
    }
    
    @AfterMethod
    public void teardown() {
        driver.quit();
    }
    public  void common() throws InterruptedException
    {
    	   // Generate random number for chassis
      
        Thread.sleep(2000);
          // Step 1: Basic Information
        createFundPage.clickCreateFundRequestButton();
        Thread.sleep(2000);
        createFundPage.selectEmbModel(1);
        Thread.sleep(2000);
        createFundPage.enterChassisNumber(insertedchassil);
        Thread.sleep(2000);
        createFundPage.enterFirstName("sandip");
        createFundPage.enterLastName("spt");
        
        js.executeScript("window.scrollBy(0, 250);");
        
        createFundPage.selectGender(1);
        Thread.sleep(2000);
        createFundPage.selectIdType(1);
        Thread.sleep(2000);
        createFundPage.enterIdNumber("123san");
        Thread.sleep(2000);
        createFundPage.enterAddress("kathmandu");
    }
    public void operationalDetails()
    {
        // Step 3: Operational Details
        operationalDetailsPage.enterEmployedPeople("1");
        operationalDetailsPage.enterOperatingDaysPerWeek("2");
        js.executeScript("window.scrollBy(0, 150);");
        operationalDetailsPage.enterKmPerDay("44");
        operationalDetailsPage.enterRoute("chit to ktm");
        js.executeScript("window.scrollBy(0, 150);");
        js.executeScript("window.scrollBy(0, 150);");
    }
    
    @Test(priority = 0, invocationCount = 1,description = "create subsidy req without accesories and with 100 payment and check subsidy is created ")
    public void CFR_001() throws InterruptedException, AWTException { 
           common();
          // Step 2: Vehicle Financing
        js.executeScript("window.scrollBy(0, 150);");
        vehicleFinancingPage.selectFullPaymentOption();
        Thread.sleep(2000);
        js.executeScript("window.scrollBy(0, 150);");
        vehicleFinancingPage.enterVehicleCost("50000");
        Thread.sleep(2000);
        js.executeScript("window.scrollBy(0, 250);");
        
          // Step 3: Operational Details
        operationalDetailsPage.selectScrappingYes();
        driver.findElement(By.id("only-owning")).click();
       operationalDetailsPage.enterEmployedPeople("1");
       driver.findElement(By.id("covid-no")).click();
       js.executeScript("window.scrollBy(0, 150);");
        operationalDetailsPage.enterOperatingDaysPerWeek("2");
        js.executeScript("window.scrollBy(0, 150);");
        operationalDetailsPage.enterKmPerDay("44");
        operationalDetailsPage.enterRoute("chit to ktm");
        driver.findElement(By.id("no-charger")).click();
        
      
        operationalDetailsPage.enterTitleTransferDate(date);
        operationalDetailsPage.clickNextButton();
        
          // Step 4: File Upload
        fileUploadPage.uploadAllcommonRequiredFiles(filePath);
        fileUploadPage.scrappingfileupload(filePath);
        js.executeScript("window.scrollBy(0, 150);");
        Thread.sleep(1000);
          //fileUploadPage.pressEscapeKey();
        fileUploadPage.clickPreviewButton();
        fileUploadPage.clickSubmitButton();
         // Locate the element
        WebElement fundRequestHeader = driver.findElement(By.xpath("//h2[.='Fund Request']"));

          // Assert that the element is visible
       Assert.assertTrue(fundRequestHeader.isDisplayed(), "fund couldnot submitted ");
       Thread.sleep(1000);
       //last row of table
       WebElement tableBody = driver.findElement(By.tagName("tbody"));
       List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
       WebElement lastRow = rows.get(rows.size() - 1);
       String chassisNo = lastRow.findElements(By.tagName("td")).get(2).getText();
       System.out.println("featchedchassisNo"+chassisNo);  
       //String createdchassil= driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
       Assert.assertEquals(chassisNo,insertedchassil, "subsidy creation got failed ");
    }
    @Test(priority = 2,description = "create subsidy with loan without accesories")
    public void CFR_002() throws InterruptedException
    {
    	 common();
         // Step 2: Vehicle Financing
       js.executeScript("window.scrollBy(0, 150);");
       vehicleFinancingPage.selectFullPaymentOption();
       Thread.sleep(2000);
       vehicleFinancingPage.enterVehicleCost("500000");
       Thread.sleep(2000);
       js.executeScript("window.scrollBy(0, 150);");
       operationalDetailsPage.selectScrappingYes();
       operationalDetails();
       operationalDetailsPage.enterTitleTransferDate(date);
       operationalDetailsPage.clickNextButton();
       fileUploadPage.uploadAllcommonRequiredFiles(filePath);
       js.executeScript("window.scrollBy(0, 150);");

       
       
    	
    }
    @Test
    public void CFR_003() throws InterruptedException
    {
    	common();
    	js.executeScript("window.scrollBy(0,150);");
    	vehicleFinancingPage.selectLoanOption();
    	Thread.sleep(2000);
    	
    }
  
 
}