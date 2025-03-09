package com.WebScraping.Sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/scraping")
public class ScrapingController {

    @Autowired
    private ScrapingService scrapingService;

    @Autowired
    private ExcelService excelService;

    @GetMapping("/scrape")
    public String scrapeAndSave() {
        List<Product> products = scrapingService.scrapeProducts();
        return "Scraped and saved " + products.size() + " products!";
    }

    @GetMapping("/export")
    public String exportData() {
        List<Product> products = scrapingService.scrapeProducts();
        excelService.exportToExcel(products, "products.xlsx");
        return "Data exported to products.xlsx";
    }
}
