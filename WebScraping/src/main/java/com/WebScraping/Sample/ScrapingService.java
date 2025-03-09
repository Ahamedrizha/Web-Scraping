package com.WebScraping.Sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> scrapeProducts() {
        List<Product> productList = new ArrayList<>();

        try {
            // URL of the website to scrape
            Document doc = Jsoup.connect("https://www.scrapingcourse.com/ecommerce/").get();

            // Select elements containing product information
            Elements products = doc.select("li.product");

            for (Element product : products) {
                String name = product.select("h2").text();
                String priceText = product.select(".span").text();
                String price = product.select("span.price").text();

                String url = product.select("a").attr("href");
                String image = product.select("img").attr("src");

                Product p = new Product();
                p.setName(name);
                p.setPrice(price);
                p.setUrl(url);
                p.setImage(image);

                productList.add(p);
            }


            // Save to Database
            productRepository.saveAll(productList);
            String productListString = productList.toString();
            System.out.println(productListString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }
}
