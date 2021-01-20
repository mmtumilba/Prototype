package com.abc.prototype;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

public class Bookmark {

    public Vector <String> titles;
    public Vector <String> titleSets;
    public Vector <String> article;

    private String title;

    public Bookmark (Context context) {
        this.titles = getTitles(context);
        this.titleSets = generateTitleSets(titles);
    }

    public Bookmark (Context context, int articleIndex) {
        this.article = getArticle(context, articleIndex);
    }


    private Vector<String> getArticle (Context context, int articleIndex) {

        Vector<String> output = new Vector<String>();

        try {

//            String path = context.getFilesDir().toString();
//            String filepath = path + "/bookmarks.xml";
//            File file = new File(filepath);

            AssetManager am = context.getAssets();
            InputStream is = am.open("bookmarks.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);


//            Document doc = db.parse(file);



            doc.getDocumentElement().normalize();
            NodeList bookmark = doc.getElementsByTagName("bookmark");
            Node node = bookmark.item(articleIndex);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                NodeList article = eElement.getElementsByTagName("paragraph");
                for (int i = 0; i < article.getLength(); i++) {
                    output.add(eElement.getElementsByTagName("paragraph").item(i).getTextContent());
                }
            }

            //////////////////////////////////////////////////////////////
            Element root = doc.getDocumentElement();


            Element newBookmark = doc.createElement("bookmark");

            Element titleT = doc.createElement("title");
            titleT.appendChild(doc.createTextNode(title));
            newBookmark.appendChild(titleT);

            Element articleA = doc.createElement("article");
            for (int i = 0; i < article.size(); i++) {
                Element paragraph = doc.createElement("paragraph");
                paragraph.appendChild(doc.createTextNode(article.get(i)));
                articleA.appendChild(paragraph);

            }
            newBookmark.appendChild(articleA);

            root.appendChild(newBookmark);


            DOMSource source = new DOMSource(doc);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("bookmarks.xml");
            transformer.transform(source, result);

            //////////////////////////////////////////////////////////////


        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return output;
    }


    private Vector<String> getTitles (Context context) {
        Vector <String> titles = new Vector <String>();

        try {


//            String path = context.getFilesDir().toString();
//            String filepath = path + "/bookmarks.xml";
//            File file = new File(context.getFilesDir().toString() + "/bookmarks.xml");
//
//            String path = context.getFilesDir().toString();
//            String filepath = path + "/bookmarks.xml";
//            File file = new File(filepath);

            AssetManager am = context.getAssets();
            InputStream is = am.open("bookmarks.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);


//            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();
            NodeList bookmark = doc.getElementsByTagName("bookmark");

            for (int itr = 0; itr < bookmark.getLength(); itr++) {
                Node node = bookmark.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    titles.add(eElement.getElementsByTagName("title").item(0).getTextContent());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return titles;
    }

    private Vector <String> generateTitleSets (Vector <String> titles) {
        Vector <String> output = new Vector <String>();

        int counter = 0;
        String sets = "";

        for (int i = 0; i < titles.size(); i++) {
            counter++;
            String title = counter + ". " + titles.get(i) + ".";
            sets = sets + title + "\n";
            if (counter == 5) {
                counter = 0;
                output.add(sets);
                sets = "";
            }
        }
        if (counter != 0) {
            output.add(sets);
        }

        return output;
    }

}
