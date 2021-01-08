package com.abc.prototype;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Vector;


public class AbsScraper {

    Vector <String> links;
    Vector <String> titles;
    Vector <String> titleSets;

    String link;
    String title;
    String category;

    /**
     * instantiates absScraper
     *  initializes the ff variables: category
     *                                links
     *                                titles
     *                                titleSets
     * @param category passed from category selection
     */
    public AbsScraper (String category) {
        this.category = category;
        this.links = new Vector<String>();
        this.titles = new Vector<String>();
        this.titleSets = new Vector<String>();
        try {
            scrapeTitlesLinks(category);
            generateTitleSets(titles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("CHECKPOINT", "AbsScraper.class");
    }

    /**
     * populates links and titles
     * @param category passed from category selection
     * @throws IOException todo  pangitai kung para sa diin ni
     */
    public void scrapeTitlesLinks (String category) throws IOException {
        String url = "https://news.abs-cbn.com/" + category;

        Document doc = Jsoup.connect(url).get();

        Elements articles = doc.select("article.clearfix");
        Elements titleBigContainer = articles.select("p.title");
        Elements titleSmallContainer = titleBigContainer.select("a");

        for (Element title : titleSmallContainer) {
            titles.add(title.text());
        }

        for (Element title : titleSmallContainer) {
            String link = "https://news.abs-cbn.com" + title.attr("href");
            links.add(link);
        }

        generateTitleSets(titles);
    }

    /**
     * populates titleSets
     * @param titles scraped titles from the website
     */
    public void generateTitleSets (Vector<String> titles) {

        int counter = 0;
        String sets = "";

        for (int i = 0; i < titles.size(); i++) {
            counter++;
            String title = titles.get(i);
            sets = sets + title + "\n";
            if (counter == 5) {
                counter = 0;
                titleSets.add(sets);
                sets = "";
            } else if (i == (titles.size() - 1)) {
                titleSets.add(sets);
            }
        }

    }
}