package com.abc.prototype;
import java.util.Vector;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class InquirerScraper {

    Vector<String> links;
    Vector<String> titles;
    Vector<String> titleSets;
    Vector<String> articleVector;

    String url;
    String category;
    String subcategory;

    Elements basis;
    Elements lifestyleBasis;

    public InquirerScraper (String category, String subcategory) {
        this.category = category;
        this.subcategory = subcategory;
        this.links = new Vector<String>();
        this.titles = new Vector<String>();
        this.titleSets = new Vector<String>();

        selectLink(category, subcategory);
        try {
            scrapeTitlesLinks();
            generateTitleSets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InquirerScraper (String category, String subcategory, String link) {
        this.articleVector = new Vector<String>();
        try{
            if (category == "lifestyle") {
                scrapeLifestyleArticle(link);
            } else {
                scrapeArticle(link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectLink (String category, String subcategory) {

        if (category == "news") {
            if (subcategory == "headlines") {
                url = "https://newsinfo.inquirer.net/category/latest-stories";
            }
            else if (subcategory == "metro") {
                url = "https://newsinfo.inquirer.net/category/inquirer-headlines/metro";
            }
            else if (subcategory == "region") {
                url = "https://newsinfo.inquirer.net/regions";
            }
            else if (subcategory == "nation") {
                url = "https://newsinfo.inquirer.net/category/inquirer-headlines/nation";
            }
            else if (subcategory == "world") {
                url = "https://newsinfo.inquirer.net/category/latest-stories/world-latest-stories";
            }
        }
        else if (category == "opinion") {
            if (subcategory == "editorial") {
                url = "https://opinion.inquirer.net/category/editorial";
            }
            else if (subcategory == "columns") {
                url = "https://opinion.inquirer.net/category/inquirer-opinion/columns";
            }
            else if (subcategory == "viewpoints") {
                url = "https://opinion.inquirer.net/category/viewpoints";
            }
            else if (subcategory == "talk_of_the_town") {
                url = "https://opinion.inquirer.net/category/inquirer-opinion/talk-of-the-town";
            }
            else if (subcategory == "lovelife") {
                url = "https://opinion.inquirer.net/category/love-life";
            }
        }
        else if (category == "sports") {
            if (subcategory == "basketball") {
                url = "https://sports.inquirer.net/category/section/basketball";
            }
            else if (subcategory == "boxing") {
                url = "https://sports.inquirer.net/category/section/boxing-mma";
            }
            else if (subcategory == "volleyball") {
                url = "https://sports.inquirer.net/category/section/volleyball";
            }
            else if (subcategory == "football") {
                url = "https://sports.inquirer.net/category/section/other-sports/football";
            }
            else if (subcategory == "other_sports") {
                url = "https://sports.inquirer.net/category/section/other-sports";
            }
            else if (subcategory == "pba") {
                url = "https://sports.inquirer.net/category/latest-stories/basketball/pba";
            }
            else if (subcategory == "uaap") {
                url = "https://sports.inquirer.net/category/varsity-sports/latest-varsity-sports/uaap";
            }
            else if (subcategory == "ncaa") {
                url = "https://sports.inquirer.net/category/varsity-sports/latest-varsity-sports/ncaa";
            }
            else if (subcategory == "one_championship") {
                url = "https://sports.inquirer.net/category/mixed-martial-arts/one-championship";
            }
        }
        else if (category == "lifestyle") {
            if (subcategory == "fashion") {
                url = "https://lifestyle.inquirer.net/category/latest-stories/fashion-and-beauty/";
            }
            else if (subcategory == "beauty") {
                url = "https://lifestyle.inquirer.net/category/beauty/";
            }
            else if (subcategory == "culture") {
                url = "https://lifestyle.inquirer.net/category/archives/arts-culture/";
            }
            else if (subcategory == "events") {
                url = "https://lifestyle.inquirer.net/category/events/";
            }
            else if (subcategory == "human_interest") {
                url = "https://lifestyle.inquirer.net/category/latest-stories-2/human-interest/";
            }
        }
        else if (category == "entertainment") {
            if (subcategory == "headlines") {
                url = "https://entertainment.inquirer.net/category/headlines";
            }
            else if (subcategory == "latest_entertainment_stories") {
                url = "https://entertainment.inquirer.net/category/latest-stories";
            }
            else if (subcategory == "columns") {
                url = "https://entertainment.inquirer.net/columnists";
            }
            else if (subcategory == "movies") {
                url = "https://entertainment.inquirer.net/category/movies";
            }
            else if (subcategory == "indie_films") {
                url = "https://entertainment.inquirer.net/category/indie-films";
            }
        }
        else if (category == "entertainment") {
            if (subcategory == "headlines") {
                url = "https://entertainment.inquirer.net/category/headlines";
            }
            else if (subcategory == "latest_entertainment_stories") {
                url = "https://entertainment.inquirer.net/category/latest-stories";
            }
            else if (subcategory == "columns") {
                url = "https://entertainment.inquirer.net/columnists";
            }
            else if (subcategory == "movies") {
                url = "https://entertainment.inquirer.net/category/movies";
            }
            else if (subcategory == "indie_films") {
                url = "https://entertainment.inquirer.net/category/indie-films";
            }
        }
        else if (category == "business") {
            if (subcategory == "stock_exhange") {
                url = "https://business.inquirer.net/category/stock-market-table";
            }
            else if (subcategory == "property_guide") {
                url = "https://business.inquirer.net/category/property-guide";
            }
            else if (subcategory == "columns") {
                url = "https://business.inquirer.net/columnists";
            }
            else if (subcategory == "stock_market_quarterly") {
                url = "https://business.inquirer.net/category/stock-market-quarterly";
            }
            else if (subcategory == "classifieds") {
                url = "https://business.inquirer.net/category/classifieds";
            }
        }
        else if (category == "technology") {
            if (subcategory == "headlines") {
                url = "https://technology.inquirer.net/category/headlines";
            }
            else if (subcategory == "latest_technology_stories") {
                url = "https://technology.inquirer.net/category/latest-stories";
            }
        }
        else if (category == "global_nation") {
            if (subcategory == "philippines") {
                url = "https://globalnation.inquirer.net/category/news/philippines";
            }
            else if (subcategory == "asia_and_pacific") {
                url = "https://globalnation.inquirer.net/category/world-news/asia-australia";
            }
            else if (subcategory == "americas") {
                url = "https://globalnation.inquirer.net/category/world-news/us-canada";
            }
            else if (subcategory == "middle_east_and_africa") {
                url = "https://globalnation.inquirer.net/category/world-news/middle-east-africa";
            }
            else if (subcategory == "europe") {
                url = "https://globalnation.inquirer.net/category/world-news/europe";
            }
            else if (subcategory == "global") {
                url = "https://globalnation.inquirer.net/category/global-pinoy";
            }
            else if (subcategory == "pinoy_columns") {
                url = "https://globalnation.inquirer.net/category/opinion/columnists";
            }
            else if (subcategory == "events") {
                url = "https://globalnation.inquirer.net/category/events";
            }
        }
    }

    //	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void generateBasis () throws IOException {
        Document doc = Jsoup.connect(url).post();

        Elements articles = doc.select("div#ch-ls-box");
        Elements h2 = articles.select("h2");
        basis = h2.select("a");
    }

    public void  generateLifestyleBasis() throws IOException{
        Document doc = Jsoup.connect(url).post();
        Elements h3 = doc.select("h3.title");
        lifestyleBasis = h3.select("a");
    }
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    public void scrapeTitlesLinks() throws IOException  {
        Elements thisBasis = new Elements();
        Document doc = Jsoup.connect(url).post();

        if (category == "lifestyle") {
            generateLifestyleBasis();
            thisBasis = lifestyleBasis;
        }
        else {
            generateBasis();
            thisBasis = basis;
        }

        for (Element title : thisBasis) {
            titles.add(title.text());
            links.add(title.attr("href"));
        }
    }

    public void generateTitleSets () {
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
        Elements articles = doc.select("div#article_content");
        Elements container = articles.select("div");
        Elements paragraphs = container.select("p");


        for (Element paragraph : paragraphs) {
            articleVector.add(paragraph.text());
        }
    }

    public void scrapeLifestyleArticle(String url) throws IOException {	// param should contain link
        Document doc = Jsoup.connect(url).get();
        Elements articles = doc.select("article#single_lb");
        Elements paragraphs = articles.select("p");

        for (Element paragraph : paragraphs) {
            articleVector.add(paragraph.text());
        }
    }
}
