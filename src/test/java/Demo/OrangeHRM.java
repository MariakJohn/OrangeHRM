
package Demo;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import junit.framework.Assert;

public class OrangeHRM {
	public static void ScreenShot(WebDriver driver, String test) throws IOException {
		TakesScreenshot screen = (TakesScreenshot) driver;
		File srcFile = screen.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File("./ScreeshotOrangeHRM/" + test + ".png"));

	}

	public String BaseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;

	@BeforeTest
	public void setup() {
		System.out.println("Before Test Executed");
		driver = new ChromeDriver();

		// open url
		driver.get(BaseUrl);
		driver.manage().window().maximize();

		// time kept as 15
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	// ************************************************************************************************************

	@Test(priority = 1, enabled = true)
	public void InvalidCredentials() throws IOException, InterruptedException {
		System.out.println("1 @Test InvalidCredentials");

		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("afdhjm");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(5000);
		ScreenShot(driver, "Invalid Login");
		Thread.sleep(5000);
		String ExpecTit = "Invalid credentials";
		// WebElement ActuTitt = driver.findElement(By.xpath("//p[@class='oxd-text
		// oxd-text--p oxd-alert-content-text']"));
		String ActuTit = driver.getTitle();
		/*
		 */
		if (ActuTit.equals("ExpecTit")) {
			System.out.println("Credential matching");
		} else {
			System.out.println(" Failed" + ActuTit);
		}
	}

	// **********************************************************************************************************

	@Test(priority = 2, enabled = true)
	public void loginTestWithValidCredentials() throws IOException, InterruptedException {
		System.out.println("2 @Test loginTestWithValidCredentials");

		login();
		Thread.sleep(1000);
		ScreenShot(driver, "Login Successful");
		Thread.sleep(1000); // verify if login successful String pageTitle =
		driver.getTitle();
		String ExpecTit = "OrangeHRM";
		String pageTitle = driver.getTitle();
		/*
		 * if (pageTitle.equals("ExpecTit")) { System.out.println("Login Successful"); }
		 * else { System.out.println("Login Failed"); }
		 */

		Assert.assertEquals(pageTitle, ExpecTit);
		System.out.println("Login Successful");
		logOut();

	}

	// ***********************************************************************************************************************

	@Test(priority = 3, enabled = true)
	public void addEmployeeWithPic() throws InterruptedException, IOException {
		System.out.println("3 @Test addEmployeeWithPic");

		login();
		Thread.sleep(1000);
		// click on PIM
		driver.findElement(By.linkText("PIM")).click();
		driver.findElement(By.xpath("//button[normalize-space()='Add']")).click();
		// Adding employee details
		driver.findElement(By.name("firstName")).sendKeys("Mannn");
		driver.findElement(By.name("lastName")).sendKeys("Jo");
		/*
		 * driver.findElement(By.xpath(
		 * "//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']"
		 * )) .sendKeys("123456789");
		 */
		// Add employee pic
		driver.findElement(
				By.xpath("//button[@class='oxd-icon-button oxd-icon-button--solid-main employee-image-action']"))
				.click();
		Thread.sleep(1000);

		// pic upload
		Runtime.getRuntime().exec("C://Users//Manu Maria John//Documents//SeleniumPractice2/Fileupload.exe");
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		Thread.sleep(10000);
		ScreenShot(driver, "Employee added");
		Thread.sleep(5000);

		// checking text matching or not
		WebElement AcText = driver.findElement(By.xpath("//a[normalize-space()='Personal Details']"));
		String ExText = "Personal Details";
		if (AcText.equals(ExText)) {
			System.out.println("Matching");
		} else {
			System.out.println("Not ");

		}

		System.out.println("Employee added");

		// Taking Screenshot
		Thread.sleep(5000);
		ScreenShot(driver, "Employee Personal Added");
		Thread.sleep(5000);
		logOut();
	}

	// ********************************************************************************************************************************

	@Test(priority = 4, enabled = true)

	public void searchEmployeeByName() throws InterruptedException, IOException {
		System.out.println("4 @Test searchEmployeeByName");
		login();
		Thread.sleep(1000);
		// click on PIM
		driver.findElement(By.linkText("PIM")).click();
		Thread.sleep(500);

		driver.findElement(By.linkText("Employee List")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//input[@placeholder='Type for hints...']")).sendKeys("Mannn");
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
		// driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
		System.out.println("Found Employee");
		Thread.sleep(5000);

		// verify if employee is added
		WebElement AcText = driver.findElement(By.xpath("//div[@role='rowgroup']//div[1]//div[1]//div[3]//div[1]"));
		String ExText = "Mannn";
		if (AcText.equals(ExText)) {
			System.out.println("Found Employee");
		} else {
			System.out.println("Not Found Employee");

		}

		// Assert.assertEquals("Personal Details", conmessage);
		logOut();
	}

	// *****************************************************************************************************************

	@Test(priority = 5, enabled = true)

	public void searchEmployeeByID() throws InterruptedException, IOException {
		System.out.println("5 @Test searchEmployeeByID");
		String empId = "52655";
		login();
		Thread.sleep(1000);
		// click on PIM
		driver.findElement(By.xpath("//span[normalize-space()='PIM']")).click();
		Thread.sleep(500);

		driver.findElement(By.linkText("Employee List")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath(
				"//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']"))
				.sendKeys("0382");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
		System.out.println("Found Employee");
		Thread.sleep(5000);

		ScreenShot(driver, "Found Employee");
		Thread.sleep(5000);

		// verify if employee is added
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0," + 500 + ")");
		String actId = driver
				.findElement(By.cssSelector("div[class='oxd-table-card'] div:nth-child(2) div:nth-child(1)")).getText();

		if (actId.equalsIgnoreCase("52655")) {
			System.out.println("Matching id");

		} else {
			System.out.println("Not Matching id");

		}

		logOut();
	}

//**************************************************************************************************************
	@Test(priority = 6, enabled = true)
	public void fileUpload() throws InterruptedException, IOException {
		System.out.println("6 @Test fileUpload");

		login();

		Thread.sleep(1000);
		// click on PIM
		driver.findElement(By.xpath("//span[normalize-space()='PIM']")).click();
		Thread.sleep(500);

		// Click on Configuration buttom
		driver.findElement(By.xpath("//span[@class='oxd-topbar-body-nav-tab-item']")).click();
		// Click on Data Import
		driver.findElement(By.xpath("//a[normalize-space()='Data Import']")).click();
		// Click on Browse
		driver.findElement(By.xpath("//div[@class='oxd-file-button']")).click();

		// upload data from desktop
		Runtime.getRuntime().exec("C://Users//Manu Maria John//Documents//SeleniumPractice2//datas.exe");
		// Runtime.getRuntime().exec("C:\Users\Manu Maria
		// John\Documents\SeleniumPractice2\datas.exe");
		Thread.sleep(1000);

		// click on upload
		driver.findElement(By.xpath("//button[normalize-space()='Upload']")).click();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[normalize-space()='Ok']")).click();
		Thread.sleep(1000);

		// logout
		logOut();

	}

	// ********************************************************************************************************

	@Test(priority = 7, enabled = true)
	public void deleteEmployee() throws InterruptedException, IOException {
		System.out.println("7 @Test deleteEmployee ");
		login();
		Thread.sleep(1000);

		// click on PIM
		driver.findElement(By.linkText("PIM")).click();
		Thread.sleep(1000);

		// click on Employee list
		driver.findElement(By.linkText("Employee List")).click();
		Thread.sleep(1000);

		// Search by name
		driver.findElement(By.xpath("//input[@placeholder='Type for hints...']")).sendKeys("Mannn");
		// driver.findElement(By.xpath("//div[@class='oxd-input-group
		// oxd-input-field-bottom-space']//div//input[@class='oxd-input
		// oxd-input--active']")).sendKeys("52655");
		driver.findElement(By.xpath("//button[normalize-space()='Search']")).click();
		System.out.println("Found Employee");
		Thread.sleep(1000);

		// click on trash
		driver.findElement(By.xpath("//i[@class='oxd-icon bi-trash']")).click();
		Thread.sleep(1000);

		// confirm delete
		driver.findElement(By.xpath("//button[normalize-space()='Yes, Delete']")).click();
		System.out.println("Deleted Employee");
		Thread.sleep(1000);

		// Confirm employee name deleted or not
		String ActTeextt = driver.findElement(By.xpath("//span[normalize-space()='No Records Found']")).getText();
		String ExpTextt = "No Records Found";
		Assert.assertEquals(ActTeextt, ExpTextt);
		System.out.println("Pass:  No Records Found");
		logOut();
	}

	// ********************************************************************************************************

	@Test(priority = 8, enabled = true)
	public void employeesList() throws InterruptedException, IOException {
		System.out.println("8 @Test employeesList ");

		login();

		// click on PIM
		driver.findElement(By.linkText("PIM")).click();
		Thread.sleep(1000);

		// click on Employee list
		driver.findElement(By.linkText("Employee List")).click();
		Thread.sleep(1000);

		JavascriptExecutor jss1 = (JavascriptExecutor) driver;
		jss1.executeScript("scroll(0,2900)");

		List<WebElement> emp_lists = driver.findElements(By.xpath("//div[@class='oxd-table-card']/div/div[3]"));
		Thread.sleep(1000);
		
		for (int j = 0; j < emp_lists.size(); j++) {
			// print first name
			String last_name = emp_lists.get(j).getText();
			System.out.println("First name:  "+last_name );

		}

		
		
		
		Thread.sleep(5000);
		logOut();
	}

	// ********************************************************************************************************

	public void login() throws InterruptedException {

		// open url
		driver.get(BaseUrl);

		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		System.out.println("Logged in");
		Thread.sleep(5000);
	}

	public void logOut() throws InterruptedException, IOException {

		// Dropdown
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[3]/ul/li/span/i")).click();
		// click on Logout;
		driver.findElement(By.linkText("Logout")).click();
		System.out.println("Logged out");

		Thread.sleep(1000);
		ScreenShot(driver, "Logout Successful");
		Thread.sleep(10000);

	}

	@AfterTest
	public void tearDown() {

		// logOut();

		driver.close();
		driver.quit();

	}

}
