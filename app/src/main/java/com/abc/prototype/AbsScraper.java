package com.abc.prototype;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Vector;


public class AbsScraper {

    private static Elements generateBasis (String category) throws IOException {
        String url = "https://news.abs-cbn.com/" + category;

        Document doc = Jsoup.connect(url).get();

        Elements articles = doc.select("article.clearfix");
        Elements titleBigContainer = articles.select("p.title");
        Elements titleSmallContainer = titleBigContainer.select("a");

        return titleSmallContainer;
    }

    public static Vector<String> scrapeTitleSets (String category) throws IOException {	// param should contain category
        Vector<String> titles = new Vector<String>();
        Vector<String> titleSets = new Vector<String>();

        Elements basis = generateBasis(category);

        for (Element title : basis) {
            titles.add(title.text());
        }

        titleSets = generateTitleSets(titles);		// this is the ouput
        return titleSets;
    }

//     TODO: 20/06/2020 modify code and include numbers prior to the title
//     TODO: 22/06/2020 lipat siguro ang generateTitleSets sa articleSelection
//     TODO: 22/06/2020 change initial scrape to 30
    public static Vector<String> generateTitleSets (Vector<String> titles) {
        Vector<String> titleSets = new Vector<String>();
        int counter = 0;
        StringBuilder sets = new StringBuilder();

        for (int i = 0; i < titles.size(); i++) {
            counter++;
            String title = titles.get(i);
            sets.append(title).append("\n");
            if (counter == 5) {
                counter = 0;
                titleSets.add(sets.toString());
                sets = new StringBuilder();
            } else if (i == (titles.size() - 1)) {
                titleSets.add(sets.toString());
            }
        }
        return titleSets;
    }

    public static Vector<String> scrapeLinks (String category) throws IOException {	// param should contain category
        Vector<String> links = new Vector<String>();

        Elements basis = generateBasis(category);

        for (Element title : basis) {
            String link = "https://news.abs-cbn.com" + title.attr("href");
            links.add(link);
        }

        return links;
    }
}