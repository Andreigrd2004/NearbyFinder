package com.java_app.demo.api;

import com.java_app.demo.api.news.NewsService;
import com.java_app.demo.country.CountryService;
import com.java_app.demo.currency.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class ApiController {

    CountryService countryService;
    NewsService newsService;
    CurrencyService currencyService;

    @GetMapping("/location")
    public ResponseEntity<?> getLocation(@RequestParam("ip") String ip) {
        return countryService.getUserLocationByIp(ip);
    }

    @GetMapping("/news")
    public ResponseEntity<?> getNews(@RequestParam("country") String country_symbol) {
        return newsService.getCountry(country_symbol);
    }

    @GetMapping("/exchangerate")
    public ResponseEntity<?> getExchange(@RequestParam("source") String source) {
        return currencyService.getCurrencies(source);
    }
}