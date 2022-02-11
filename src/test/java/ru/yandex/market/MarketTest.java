package ru.yandex.market;

import org.junit.jupiter.api.Test;
import pages.HomeYandexPage;

import java.util.List;
import java.util.Map;

public class MarketTest extends BaseTest {

    @Test
    public void testYandexMarket(){
        HomeYandexPage yandexPage = new HomeYandexPage(driver,"Яндекс Маркет");
//        yandexPage.find("Яндекс Маркет");
        yandexPage.getCollectResults();
        yandexPage.goPage("Яндекс.Маркет");
    }
}
