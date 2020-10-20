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
public class D3Test {
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
  public void fUNCATHY() {
    driver.get("https://cs1632.appspot.com/cathy");
    driver.manage().window().setSize(new Dimension(929, 990));
    driver.findElement(By.cssSelector("body")).click();
    // test the third element
    {
      List<WebElement> elements = driver.findElements(By.xpath("//ol/li[3]"));
      assert(elements.size() > 0);
    }
    driver.findElement(By.cssSelector("ol > li:nth-child(5)")).click();
    // 4th element should not exist
    {
      List<WebElement> elements = driver.findElements(By.xpath("//ol/li[4]"));
      assert(elements.size() == 0);
    }
  }
  @Test
  public void fUNFACT() {
    driver.get("https://cs1632.appspot.com/hello/fact");
    driver.manage().window().setSize(new Dimension(929, 990));
    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.linkText("Factorial")).click();
    driver.findElement(By.name("value")).click();
    driver.findElement(By.name("value")).click();
    driver.findElement(By.name("value")).sendKeys("5");
    driver.findElement(By.cssSelector("input:nth-child(2)")).click();
    // Test that "/fact"
    assertThat(driver.findElement(By.xpath("//h2")).getText(), is("Factorial of 5 is 120!"));
  }
  @Test
  public void fUNFIB() {
    driver.get("https://cs1632.appspot.com/fib");
    driver.manage().window().setSize(new Dimension(929, 990));
    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.name("value")).click();
    driver.findElement(By.name("value")).sendKeys("5");
    driver.findElement(By.cssSelector("input:nth-child(2)")).click();
    // Test "/fib"
    assertThat(driver.findElement(By.xpath("//h2")).getText(), is("Fibonacci of 5 is 5!"));
  }
  @Test
  public void fUNHELLO() {
    driver.get("https://cs1632.appspot.com/hello");
    driver.manage().window().setSize(new Dimension(929, 990));
    driver.findElement(By.cssSelector("body")).click();
    // test hello page
    assertThat(driver.findElement(By.xpath("//h2")).getText(), is("Hello CS1632, from Dr. Ahn!"));
  }
  @Test
  public void fUNHELLOTRAILING() {
    driver.get("https://cs1632.appspot.com/hello/Jazzy");
    driver.manage().window().setSize(new Dimension(933, 990));
    driver.findElement(By.cssSelector("body")).click();
    // test trailing hello
    assertThat(driver.findElement(By.xpath("//h2")).getText(), is("Hello CS1632, from Jazzy!"));
  }
  @Test
  public void fUNINVALIDVALUE() {
    driver.get("https://cs1632.appspot.com/fib");
    driver.manage().window().setSize(new Dimension(933, 990));
    driver.findElement(By.cssSelector("html")).click();
    driver.findElement(By.name("value")).click();
    driver.findElement(By.name("value")).sendKeys("-100");
    driver.findElement(By.cssSelector("input:nth-child(2)")).click();
    // Test "/fib" invalid value
    assertThat(driver.findElement(By.xpath("//h2")).getText(), is("Fibonacci of -100 is 1!"));
  }
  @Test
  public void fUNLINKS() {
    driver.get("https://cs1632.appspot.com");
    driver.manage().window().setSize(new Dimension(933, 990));
    driver.findElement(By.cssSelector(".header")).click();
    // storing the link
    {
      WebElement element = driver.findElement(By.xpath("//a[contains(@href, \'/\')]"));
      String attribute = element.getAttribute("href");
      vars.put("home_href", attribute);
    }
    // Test that the link is "/"
    assertEquals(vars.get("home_href").toString(), "https://cs1632.appspot.com" + "/");
  }
  @Test
  public void fUNWELCOME() {
    driver.get("https://cs1632.appspot.com/");
    driver.manage().window().setSize(new Dimension(930, 990));
    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.cssSelector(".jumbotron > .lead")).click();
    // Test that the homepage displays
    assertThat(driver.findElement(By.cssSelector(".jumbotron > .lead")).getText(), is("Debugging is twice as hard as writing the code in the first place. Therefore, if you write the code as cleverly as possible, you are, by definition, not smart enough to debug it.\n- Brian W. Kernighan"));
  }
  @Test
  public void dEFECT1FUNFIB() {
    driver.get("https://cs1632.appspot.com/fib");
    driver.manage().window().setSize(new Dimension(1028, 960));
    driver.findElement(By.cssSelector("html")).click();
    driver.findElement(By.cssSelector(".jumbotron")).click();
    driver.findElement(By.name("value")).click();
    driver.findElement(By.name("value")).sendKeys("10");
    driver.findElement(By.cssSelector("input:nth-child(2)")).click();
    driver.findElement(By.cssSelector("h2")).click();
    // Fib of 10 is 55 not 1.
    assertThat(driver.findElement(By.xpath("//h2")).getText(), is("Fibonacci of 10 is 55!"));
  }
  @Test
  public void dEFECT2FUNINVALIDVALUE() {
    driver.get("https://cs1632.appspot.com/fact");
    driver.manage().window().setSize(new Dimension(1025, 959));
    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.name("value")).click();
    driver.findElement(By.name("value")).sendKeys("0.1");
    driver.findElement(By.cssSelector("input:nth-child(2)")).click();
    // Invalid string should be 1
    assertThat(driver.findElement(By.xpath("//h2[contains(.,\'Factorial of 0.1 is 1!\')]")).getText(), is("Factorial of 0.1 is 1!"));
  }
  @Test
  public void dEFECT3FUNHELLOTRAILING() {
    driver.get("https://cs1632.appspot.com/hello/.");
    driver.manage().window().setSize(new Dimension(1023, 963));
    driver.findElement(By.cssSelector("body")).click();
    driver.findElement(By.cssSelector("h2")).click();
    // Trailing value should work value for any value.
    assertThat(driver.findElement(By.cssSelector("h2")).getText(), is("Hello CS1632, from .!"));
  }
}
