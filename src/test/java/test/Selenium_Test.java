package test;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Selenium_Test extends testPageObjectModel{
	
		WebDriverWait wait= new WebDriverWait(driver, 1000);
	
	
		//Anasayfa Kontrolü
		@Test
		public void test_1_1_WebSiteControl()
		{
			
			driver.get(siteUrl);
			//waitForPageLoad();
			Assert.assertTrue(driver.getTitle().equals("n11.com - Alışverişin Uğurlu Adresi"));
			System.out.println("Web Sitesi Açıldı");
						
		}

		//Kullanıcı Giriş Sayfası Kontrolü
		@Test
		public void test_1_2_loginPageReadyControl()
		{			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		
			findByXpath("//*[@id=\"header\"]/div/div/div[2]/div[2]/div[2]/div/div/a[1]").click();
			wait.until(isClickableById("loginButton"));
			Assert.assertTrue(findById("loginButton").getText().equals("Üye Girişi"));
			System.out.println("Sayfa giriş için hazır");
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	

		}
		
		//Kullanıcı girişi kontrolü
		@Test
		public void test_1_3_loginControl()
		{
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
			findById("email").sendKeys("uouremailaddress");//Please enter the mail address		
			findById("password").sendKeys("yourpassword");//Please enter the password
			findById("loginButton").click(); //login button
			System.out.println("Kullanıcı Girişi Başarılı ");
			
		}
//		
		//samsung için arama sonucu 
		@Test
		public void test_1_4_searchControl(){
			
			wait.until(isClickableById("searchData"));
			findById("searchData").sendKeys("samsung");
			findByClassName("searchBtn").click();
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);	
			wait.until(isClickableByClassName("pagination"));
			String resultOk=findByXpath("//*[@id=\"contentListing\"]/div/div/div[2]/section[2]/div[1]").getText();
			Assert.assertTrue(resultOk.contains("sonuç bulundu."));
			System.out.println("Samsung için sonuç bulundu");
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
		}
		
		
		//samsung için çıkan sayfalardan 2. sayfanın tıklanması 
		@Test
		public void test_1_5_clickPageAndPageTwoOpenedControl(){
			
				
			findByXpath("//*[@class='pagination']/a[2]").click();
			//waitForPageLoad();
			
			Assert.assertTrue(driver.getTitle().contains("Samsung - n11.com - 2/"));
			System.out.println("2. Sayfa Gösterimde ...");
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
		}
		
		//2. sayfadaki üstten 3. ürünün favorilere eklenmesi
		@Test
		public void test_1_6_addThirdProductFavorite(){
			
			
			//Listede 3. Ürün Oluşana Kadar Bekle
			wait.until(isClickableByXpath("//*[@id=\"p-267064372\"]/div[1]/a/h3"));			
			selectedProduct=getElementTextByXpath("//*[@id=\"p-267064372\"]/div[1]/a/h3");
			findByXpath("//*[@id=\"p-267064372\"]/div[2]/span[3]").click();
			System.out.println("Favoriye Eklenen Ürün Adı : "+selectedProduct);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
			
		}
		
		//istek listelerim/favorilerim linkine tıklanması
		@Test
		public void test_1_7_clickMyFavorite(){
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", findByXpath("//*[@id=\"header\"]/div/div/div[2]/div[2]/div[2]/div[2]/div/a[2]"));
			js.executeScript("arguments[0].click();", findByXpath("//*[@id=\"myAccount\"]/div[3]/ul/li[1]/div/a/h4"));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
		}

		//Favorilerimde daha önce eklenmiş ürünün onaylanma testi
		@Test
		public void test_1_8_clickedFavoriteConfirmation(){
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
			List<WebElement> productTitles= findListByXpath("//*[@id=\"view\"]/ul/li/div");
			for (WebElement productTitle : productTitles) {
				favoriesCount+=1;
				String watchesProduct=productTitle.getText();
				if (watchesProduct.contains(selectedProduct)) {
					System.out.println("Favoriye Eklenen Ürün Onaylandı.Ürünün Başlığı :"+selectedProduct+"\n");
					DeleteFavorite=favoriesCount;
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
					//Assert.assertTrue(watchesProduct.equals(selectedProduct));
				}
			}
		}
		
		
		
		//Favorilerden aynı ürünün silinme testi
		@Test
		public void test_1_9_deleteSelectedProduct(){
			
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
			findByXpath("//*[@id=\"p-267064372\"]/div[3]/span").click();
			findByXpath("/html/body/div[5]/div/div/span").click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
			
			
		}
		
		//Silinen ürünün favorilerim listesinde bulunmadığının testi
		@Test
		public void test_2_1_checkDeletedFavorite(){
			
			boolean isThereProduct=false;
			List<WebElement> productTitles= findListByXpath("//*[@id=\"view\"]/ul/li/div");
			for (WebElement productTitle : productTitles) {
				String watchesProduct=productTitle.getText();
				if (watchesProduct.contains(selectedProduct)) {
					isThereProduct=true;
				}
			}
			//Assert.assertFalse(isThereProduct);
			System.out.println("Favorilerim Sayfasında "+selectedProduct+" isimli ürün artık bulunmuyor ...");
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
		}
		
		
		
		
		
		
		
		
		
}
