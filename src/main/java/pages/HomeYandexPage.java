package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeYandexPage {
    private WebDriver driver;
    WebElement searchButton, searchInput;
    private String selectorSearchItems="//div[@class='composite bno']";
    private String selectorURL = ".//a[@href]";
    private String selectorNamePage = ".//h2";
    private String selectorDiscriprion = "//div[@class='composite bno']//div[@class='TextContainer OrganicText organic__text text-container Typo Typo_text_m Typo_line_m']";

    private List<WebElement> searchItems = new ArrayList<>();
    private List<Map<String,Object>> collectResults = new ArrayList<>();


    public HomeYandexPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://yandex.ru/");
        this.searchButton = driver.findElement(By.xpath("//div[@class='search2__button']//button[@type='submit']"));
        this.searchInput = driver.findElement(By.xpath("//div[@class='search2__input']//input[@aria-label='Запрос']"));
        searchItems = driver.findElements(By.xpath(selectorSearchItems));
    }

    public HomeYandexPage(WebDriver driver, String name) {
        this.driver = driver;
        driver.get("https://yandex.ru/search/?text=" + name);
        searchItems = driver.findElements(By.xpath(selectorSearchItems));
    }

    public void find(String keyFind) {
        searchInput.sendKeys(keyFind);
        searchButton.click();
    }

    public List<Map<String, Object>> getCollectResults() {
        for(WebElement result : searchItems){
            collectResults.add(Map.of(
                            "WEB_ELEMENT", result,
                            "URL", result.findElement(By.xpath(selectorURL)),
                            "NAME_PAGE", result.findElement(By.xpath(selectorNamePage)).getText(),
                            "DISCRIPTION", result.findElement(By.xpath(selectorDiscriprion)).getText()
                    )
            );
        }
        return collectResults;
    }

    public boolean goPage(String namePage){
        WebElement pageLink = (WebElement) collectResults.stream()
                .filter(x->x.get("NAME_PAGE").toString().contains(namePage))
                .findFirst()
                .get().get("WEB_ELEMENT");
        pageLink.findElement(By.xpath(selectorURL)).click();
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab : tabs){
            driver.switchTo().window(tab);
            if(driver.getTitle().contains(namePage))
                return true;
        }
        Assertions.fail("Не удалось открыть вкладку, содержащую " + namePage);
        return false;
    }
}
