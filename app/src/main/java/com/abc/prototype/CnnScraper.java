package com.abc.prototype;

import java.util.LinkedHashSet;
import java.util.Vector;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class CnnScraper {

    Vector<String> links;
    Vector<String> titles;
    Vector<String> titleSets;
    Vector<String> article;

    String category;

    public CnnScraper (String category) {
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

    public CnnScraper (String url, String category) {
        this.article = new Vector<String>();
        try {
            scrapeArticle(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void scrapeTitlesLinks (String category) throws IOException {
        String url = "https://cnnphilippines.com/" + category + "/";

        Vector<String> otherArticlesLinks =  new Vector<String>();
        Vector<String> filteredLinks = new Vector<String>();


        Document doc = Jsoup.connect(url).get();
        Elements body = doc.select("div.col-xs-12.col-sm-6.col-md-4.padding-5.section-right");
        Elements section = body.select("section.xs-special.right-most.0");
        Elements latest = section.select("a");

        for (Element details : latest) {
            titles.add(details.text());
            String link = "https://cnnphilippines.com" + details.attr("href");
            links.add(link);
        }

        Elements otherArticles = doc.select("div.img-container.picture");
        Elements container = otherArticles.select("a");
        Elements div = container.select("div");

        for(Element links : container) {
            String link = "https://cnnphilippines.com" + links.attr("href");
            otherArticlesLinks.add(link);
        }

        for(Element title : div) {
            titles.add(title.text());
        }

        filteredLinks = removeDuplicates(otherArticlesLinks);
        links.addAll(filteredLinks);
    }

    private Vector<String> removeDuplicates (Vector<String> vector) {
        LinkedHashSet<String> set = new LinkedHashSet<String> (vector);
        Vector<String> output = new Vector<String> ();
        output.addAll(set);

        return output;
    }

    private void scrapeArticle (String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements container = doc.select("div.article-maincontent-p.cnn-life-body");
        Elements paragraph = container.select("p");

        for(Element sentence : paragraph) {
            if (sentence.hasText()) {
                article.add(sentence.text());
            }
        }

    }
}
