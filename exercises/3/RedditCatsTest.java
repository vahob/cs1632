// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class RedditCatsTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
    driver = new ChromeDriver(options);
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void fUNTITLE() {
    driver.get("https://www.reddit.com/r/cats/");
    driver.manage().window().setSize(new Dimension(966, 990));
    driver.findElement(By.xpath("//div[@id=\'SHORTCUT_FOCUSABLE_DIV\']/div[2]/div/div/div/div[2]/div/div/div/div/div/h1")).click();
    assertThat(driver.getTitle(), is("Cats"));
  }
  @Test
  public void fUNJOINBUTTONEXISTS() {
    driver.get("https://www.reddit.com/r/cats/");
    driver.manage().window().setSize(new Dimension(969, 990));
    driver.findElement(By.cssSelector(".\\_3I4Wpl_rl6oTm02aWPZayD")).click();
    assertThat(driver.findElement(By.xpath("//div[@id=\'SHORTCUT_FOCUSABLE_DIV\']/div[2]/div/div/div/div[2]/div/div/div/div/div[2]/button")).getText(), is("JOIN"));
  }
  @Test
  public void fUNRULE3() {
    driver.get("https://www.reddit.com/r/cats/");
    driver.manage().window().setSize(new Dimension(977, 990));
    driver.findElement(By.cssSelector(".\\_3I4Wpl_rl6oTm02aWPZayD")).click();
    //  The rules box is located in the bottom right corner
    assertThat(driver.findElement(By.xpath("//div[@id=\'SHORTCUT_FOCUSABLE_DIV\']/div[2]/div/div/div/div[2]/div[3]/div[2]/div/div[5]/div/div[2]/div[3]/div/div[2]/div")).getText(), is("No NSFW, animal abuse, or cruelty"));
  }
  @Test
  public void fUNRULES11ITEMS() {
    driver.get("https://www.reddit.com/r/cats/");
    driver.manage().window().setSize(new Dimension(981, 990));
    driver.findElement(By.cssSelector(".\\_3I4Wpl_rl6oTm02aWPZayD")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".\\_2RkQc9Gtsq3cPQNZLYv4zc"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    // 11th element present
    {
      List<WebElement> elements = driver.findElements(By.xpath("//div[@id=\'SHORTCUT_FOCUSABLE_DIV\']/div[2]/div/div/div/div[2]/div[3]/div[2]/div/div[5]/div/div[2]/div[11]/div/div/div"));
      assert(elements.size() > 0);
    }
    // 12th element not present
    {
      List<WebElement> elements = driver.findElements(By.xpath("//div[@id=\'SHORTCUT_FOCUSABLE_DIV\']/div[2]/div/div/div/div[2]/div[3]/div[2]/div/div[5]/div/div[2]/div[12]/div/div/div"));
      assert(elements.size() == 0);
    }
  }
  @Test
  public void fUNSIGNUPLINK() {
    driver.get("https://www.reddit.com/r/cats/");
    driver.manage().window().setSize(new Dimension(972, 990));
    driver.findElement(By.cssSelector(".\\_3I4Wpl_rl6oTm02aWPZayD")).click();
    // variable for sign up button
    {
      WebElement element = driver.findElement(By.xpath("//a[contains(@href, \'https://www.reddit.com/register/?dest=https%3A%2F%2Fwww.reddit.com%2Fr%2Fcats%2F\')]"));
      String attribute = element.getAttribute("href");
      vars.put("signUp", attribute);
    }
    // link should be the same
    assertEquals(vars.get("signUp").toString(), "https://www.reddit.com/register/?dest=https%3A%2F%2Fwww.reddit.com%2Fr%2Fcats%2F");
  }
  
}