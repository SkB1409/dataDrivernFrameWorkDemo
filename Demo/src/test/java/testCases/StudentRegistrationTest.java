package testCases;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import utilities.Constants;
import utilities.ExcelUtils;

public class StudentRegistrationTest {

	static ExcelUtils excelUtils = new ExcelUtils();

	static String excelFilePath = Constants.Path_TestData + Constants.File_TestData;

	DataFormatter fromatter = new DataFormatter();

	public static void main(String args[]) throws IOException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\khadh\\eclipse-workspace\\automation\\Drivers\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.get("https://demoqa.com/automation-practice-form");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));

		// Identify the WebElements for the student registration form
		WebElement firstName = driver.findElement(By.id("firstName"));
		WebElement lastName = driver.findElement(By.id("lastName"));
		WebElement email = driver.findElement(By.id("userEmail"));
		WebElement genderMale = driver.findElement(By.id("gender-radio-1"));
		WebElement mobile = driver.findElement(By.id("userNumber"));
		WebElement address = driver.findElement(By.id("currentAddress"));
		WebElement submitBtn = driver.findElement(By.id("submit"));

		excelUtils.setExcelFile(excelFilePath, "Student_Data");

		for (int i = 1; i <= excelUtils.getRowCountInSheet(); i++) {
			firstName.sendKeys(excelUtils.getCellData(i, 0));
			lastName.sendKeys(excelUtils.getCellData(i, 1));
			email.sendKeys(excelUtils.getCellData(i, 2));
			mobile.sendKeys(excelUtils.getCellData(i, 3));
			address.sendKeys(excelUtils.getCellData(i, 4));

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", genderMale);

			submitBtn.click();

			// Verify the confirmation message
			WebElement confirmationMessage = driver
					.findElement(By.xpath("//div[text()='Thanks for submitting the form']"));

			// check if confirmation message is displayed
			if (confirmationMessage.isDisplayed()) {
				// if the message is displayed , write PASS in the excel sheet using the method
				// of ExcelUtils
				excelUtils.setCellValue(i, 6, "PASS", excelFilePath);
			} else {
				// if the message is not displayed , write FAIL in the excel sheet using the
				// method of ExcelUtils
				excelUtils.setCellValue(i, 6, "FAIL", excelFilePath);
			}

			// close the confirmation popup
			WebElement closebtn = driver.findElement(By.id("closeLargeModal"));
			closebtn.click();

			// wait for page to come back to registration page after close button is clicked
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
		}
		// closing the driver
		driver.quit();
	}

}
