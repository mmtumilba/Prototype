package com.abc.prototype;
import java.util.Vector;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        this.url = selectLink(category, subcategory);
        try {
            scrapeTitlesLinks();
            generateTitleSets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InquirerScraper (String category, String subcategory, String link) {
        this.articleVector = new Vector<String>();
        this.category = category;
        try{
            if (category.equals("lifestyle")) {
                scrapeLifestyleArticle(link);
            } else {
                scrapeArticle(link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String selectLink (String category, String subcategory) {
        String output ="";
        if (category.equals("news") ) {
            if (subcategory.equals("headlines")) {
                output = "https://newsinfo.inquirer.net/category/latest-stories";
            }
            else if (subcategory.equals("metro")) {
                output = "https://newsinfo.inquirer.net/category/inquirer-headlines/metro";
            }
            else if (subcategory.equals("region")) {
                output = "https://newsinfo.inquirer.net/regions";
            }
            else if (subcategory.equals("nation")) {
                output = "https://newsinfo.inquirer.net/category/inquirer-headlines/nation";
            }
            else if (subcategory.equals("world")) {
                output = "https://newsinfo.inquirer.net/category/latest-stories/world-latest-stories";
            }
        }
        else if (category.equals("opinion")) {
            if (subcategory.equals("editorial")) {
                output = "https://opinion.inquirer.net/category/editorial";
            }
            else if (subcategory.equals("columns")) {
                output = "https://opinion.inquirer.net/category/inquirer-opinion/columns";
            }
            else if (subcategory.equals("viewpoints")) {
                output = "https://opinion.inquirer.net/category/viewpoints";
            }
            else if (subcategory.equals("talk_of_the_town")) {
                output = "https://opinion.inquirer.net/category/inquirer-opinion/talk-of-the-town";
            }
            else if (subcategory.equals("lovelife")) {
                output = "https://opinion.inquirer.net/category/love-life";
            }
        }
        else if (category.equals("sports")) {
            if (subcategory.equals("basketball")) {
                output = "https://sports.inquirer.net/category/section/basketball";
            }
            else if (subcategory.equals("boxing")) {
                output = "https://sports.inquirer.net/category/section/boxing-mma";
            }
            else if (subcategory.equals("volleyball")) {
                output = "https://sports.inquirer.net/category/section/volleyball";
            }
            else if (subcategory.equals("football")) {
                output = "https://sports.inquirer.net/category/section/other-sports/football";
            }
            else if (subcategory.equals("other_sports")) {
                output = "https://sports.inquirer.net/category/section/other-sports";
            }
            else if (subcategory.equals("pba")) {
                output = "https://sports.inquirer.net/category/latest-stories/basketball/pba";
            }
            else if (subcategory.equals("uaap")) {
                output = "https://sports.inquirer.net/category/varsity-sports/latest-varsity-sports/uaap";
            }
            else if (subcategory.equals("ncaa")) {
                output = "https://sports.inquirer.net/category/varsity-sports/latest-varsity-sports/ncaa";
            }
            else if (subcategory.equals("one_championship")) {
                output = "https://sports.inquirer.net/category/mixed-martial-arts/one-championship";
            }
        }
        else if (category.equals("lifestyle")) {
            if (subcategory.equals("fashion")) {
                output = "https://lifestyle.inquirer.net/category/latest-stories/fashion-and-beauty/";
            }
            else if (subcategory.equals("beauty")) {
                output = "https://lifestyle.inquirer.net/category/beauty/";
            }
            else if (subcategory.equals("culture")) {
                output = "https://lifestyle.inquirer.net/category/archives/arts-culture/";
            }
            else if (subcategory.equals("events")) {
                output = "https://lifestyle.inquirer.net/category/events/";
            }
            else if (subcategory.equals("human_interest")) {
                output = "https://lifestyle.inquirer.net/category/latest-stories-2/human-interest/";
            }
        }
        else if (category.equals("entertainment")) {
            if (subcategory.equals("headlines")) {
                output = "https://entertainment.inquirer.net/category/headlines";
            }
            else if (subcategory.equals("latest_entertainment_stories")) {
                output = "https://entertainment.inquirer.net/category/latest-stories";
            }
            else if (subcategory.equals("columns")) {
                output = "https://entertainment.inquirer.net/columnists";
            }
            else if (subcategory.equals("movies")) {
                output = "https://entertainment.inquirer.net/category/movies";
            }
            else if (subcategory.equals("indie_films")) {
                output = "https://entertainment.inquirer.net/category/indie-films";
            }
        }

        else if (category.equals("business")) {
            if (subcategory.equals("stock_exhange")) {
                output = "https://business.inquirer.net/category/stock-market-table";
            }
            else if (subcategory.equals("property_guide")) {
                output = "https://business.inquirer.net/category/property-guide";
            }
            else if (subcategory.equals("columns")) {
                output = "https://business.inquirer.net/columnists";
            }
            else if (subcategory.equals("stock_market_quarterly")) {
                output = "https://business.inquirer.net/category/stock-market-quarterly";
            }
            else if (subcategory.equals("classifieds")) {
                output = "https://business.inquirer.net/category/classifieds";
            }
        }
        else if (category.equals("technology")) {
            if (subcategory.equals("headlines")) {
                output = "https://technology.inquirer.net/category/headlines";
            }
            else if (subcategory.equals("latest_technology_stories")) {
                output = "https://technology.inquirer.net/category/latest-stories";
            }
        }
        else if (category.equals("global_nation")) {
            if (subcategory.equals("philippines")) {
                output = "https://globalnation.inquirer.net/category/news/philippines";
            }
            else if (subcategory.equals("asia_and_pacific")) {
                output = "https://globalnation.inquirer.net/category/world-news/asia-australia";
            }
            else if (subcategory.equals("americas")) {
                output = "https://globalnation.inquirer.net/category/world-news/us-canada";
            }
            else if (subcategory.equals("middle_east_and_africa")) {
                output = "https://globalnation.inquirer.net/category/world-news/middle-east-africa";
            }
            else if (subcategory.equals("europe")) {
                output = "https://globalnation.inquirer.net/category/world-news/europe";
            }
            else if (subcategory.equals("global")) {
                output = "https://globalnation.inquirer.net/category/global-pinoy";
            }
            else if (subcategory.equals("pinoy_columns")) {
                output = "https://globalnation.inquirer.net/category/opinion/columnists";
            }
            else if (subcategory.equals("events")) {
                output = "https://globalnation.inquirer.net/category/events";
            }
        }
        return output;
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

        if (category.equals("lifestyle")) {
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
            if (paragraph.hasText()) {
                articleVector.add(paragraph.text());
            }

//            if (paragraph.select("img") != null) {
//                String img = "Contains image.";
//                articleVector.add(img);
//            }

//            if (paragraph.hasText()) {
//                if (!checkForAlphabet(paragraph.toString())) {
//                    return;
//                }else {
//                    articleVector.add(paragraph.text());
//                }
//                if (paragraph.toString().contains("Subscribe to INQUIRER PLUS TO GET ACCESS")) {
//                    return;
//                } else {
//                    articleVector.add(paragraph.text());
//                }

//            }

        }
    }

    public void scrapeLifestyleArticle(String url) throws IOException {	// param should contain link
        Document doc = Jsoup.connect(url).get();
        Elements articles = doc.select("article#single_lb");
        Elements paragraphs = articles.select("p");

        for (Element paragraph : paragraphs) {
            if (paragraph.select("img") != null) {
                String img = "Contains image.";
                articleVector.add(img);
            }

            if (paragraph.hasText()) {
//                if (!checkForAlphabet(paragraph.toString())) {
//                    return;
//                }else {
//                    articleVector.add(paragraph.text());
//                }
                if (paragraph.toString().contains("Subscribe to INQUIRER PLUS TO GET ACCESS")) {
                    return;
                } else {
                    articleVector.add(paragraph.text());
                }

            }
        }
    }

    public boolean checkForAlphabet (String paragraph) {
        boolean output;
        Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");
        Matcher matcher = pattern.matcher(paragraph);

        if (matcher.matches()) {
            output = true;
        } else {
            output = false;
        }
        return output;
    }
}
