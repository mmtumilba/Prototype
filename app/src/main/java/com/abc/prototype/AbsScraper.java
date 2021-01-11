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
    Vector <String> article;


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
    }

    public AbsScraper (String source, String link) {
        this.article = new Vector<String>();
        try{
            scrapeArticle(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            String title = counter + ". " + titles.get(i) + ".";
            sets = sets + title + "\n";
            if (counter == 5) {
                counter = 0;
                titleSets.add(sets);
                sets = "";
            }
        }
        if (counter != 0) {
            titleSets.add(sets);
        }
    }

    public void scrapeArticle(String url) throws IOException {	// param should contain link

        Document doc = Jsoup.connect(url).get();

        Elements articleElement = doc.select("article");
        Elements articleContainer = articleElement.select("div.article-content");
        Elements vidContainer = articleElement.select("div.media-block");
        Elements paragraphs;

        if (vidContainer.isEmpty()) {
            paragraphs = articleContainer.select("p");
        }
        else {
            paragraphs = vidContainer.select("p");
        }

        for (Element paragraph : paragraphs) {
            article.add(paragraph.text());
        }

    }
}