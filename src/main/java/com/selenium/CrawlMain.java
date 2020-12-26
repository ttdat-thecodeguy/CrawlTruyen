package com.selenium;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.util.List;


//support for truyenfull
public class CrawlMain {
	public static WebElement waitForElement(int seconds,WebDriver driver, String waitConditionLocator){
	    WebDriverWait wait = new WebDriverWait(driver, seconds);
	    return wait.until(ExpectedConditions.visibilityOfElementLocated
	              (By.xpath(waitConditionLocator)));
	}
	public  static void chuongContent(String url,WebDriver driver) throws IOException {	
		Document document = Jsoup.connect(url).get();		
		JavascriptExecutor js = null;
		if (driver instanceof JavascriptExecutor) {
		    js = (JavascriptExecutor) driver;
		}	
		//js.executeScript("return document.querySelectorAll('#ads-chapter-longtop').forEach(e => e.remove());");
		js.executeScript("document.querySelectorAll('#ads-chapter-longtop').forEach(e => e.remove())");
		js.executeScript("document.querySelectorAll('#ads-chapter-longbot').forEach(e => e.remove())");	
		Element elems = document.getElementById("chapter-c");
		System.out.println(elems.html());
	}
	
	public static void main(String[] args) throws IOException {
		System.setProperty("webdriver.chrome.driver",".\\src\\main\\resources\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();				
		//driver.manage().window().maximize();		
		try {
			String url = "https://truyenfull.vn/overgeared-tho-ren-huyen-thoai/";
            driver.get(url);
            // title in page
            String title = driver.findElement(By.className("title")).getText();
            // button showmore
            WebElement btnShowmore = driver.findElement(By.className("showmore")).findElement(By.tagName("a"));
            //click it
            btnShowmore.click();
            //get context
            WebElement context = driver.findElement(By.xpath("/html/body[@id='body_truyen']/div[@id='wrap']/div[@id='truyen']/div[@class='col-xs-12 col-sm-12 col-md-9 col-truyen-main']/div[@class='col-xs-12 col-info-desc']/div[@class='col-xs-12 col-sm-8 col-md-8 desc']/div[@class='desc-text']"));
            //list Chuong         
            WebElement el = driver.findElement(By.className("glyphicon-menu-right"));
            WebElement parent = null;
            while(el != null) {
            	List<WebElement> lChuong = driver.findElement(By.className("list-chapter")).findElements(By.tagName("li"));         
	            for (WebElement e : lChuong) {
	            	WebElement ec = e.findElement(By.tagName("a"));
	            	chuongContent(ec.getAttribute("href"), driver);
	            }
	            parent = el.findElement(By.xpath("./.."));
            	Jsoup.connect(parent.getAttribute("href"));	
	            el = driver.findElement(By.className("glyphicon-menu-right"));
            }
        } finally {
            driver.quit();
        }
	}
}
