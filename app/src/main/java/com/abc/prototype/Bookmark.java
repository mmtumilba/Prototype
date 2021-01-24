package com.abc.prototype;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
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

    public Bookmark (Context context, String title, Vector<String> article) {
        this.title = title;
        this.article = article;
        File newXML = new File(context.getFilesDir() + "/bookmarks.xml");
        if (newXML.exists()) {
            Log.e("EXISTENCE", "IT LIIIIVESS");
            boolean inlist = inList(context, title);
            if (!inlist) {
                addBookmark(context);
            }
        } else {
            Log.e("EXISTENCE", "MUST CREATE");
            createXMLFile(context);
        }
    }


    private boolean inList (Context context, String title) {
        boolean output = false;

        try {

            File file = new File(context.getFilesDir() + "/bookmarks.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();
            NodeList titleList = doc.getElementsByTagName("title");
            for (int ind = 0; ind < titleList.getLength(); ind++) {
                Node titleNode = titleList.item(ind);
//                String title_content = bookmark_content.getElementsByTagName("title").item(0).getContext();
                if (titleNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) titleNode;
                    String title_content = eElement.getTextContent();
                    if (title_content.equals(title)) {
                        output = true;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }


    public void createXMLFile (Context context) {
        String filename = "bookmarks.xml";
        try {
            FileOutputStream fos;
            fos = context.openFileOutput(filename,Context.MODE_APPEND);

            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fos, "UTF-8");
            serializer.startDocument(null, Boolean.valueOf(true));
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            serializer.startTag(null, "bookmarks");
            serializer.startTag(null, "bookmark");

            serializer.startTag(null, "title");
            serializer.text(title);
            serializer.endTag(null, "title");

            serializer.startTag(null, "article");
                for(String par : article) {
                    serializer.startTag(null, "paragraph");
                    serializer.text(par);
                    serializer.endTag(null, "paragraph");
                }
            serializer.endTag(null, "article");

            serializer.endTag(null, "bookmark");
            serializer.endTag(null, "bookmarks");

            serializer.endDocument();
            serializer.flush();

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Vector<String> getArticle (Context context, int articleIndex) {

        Vector<String> output = new Vector<String>();

        try {

            File file = new File(context.getFilesDir() + "/bookmarks.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

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

    public void addBookmark (Context context) {
        try {

            File file = new File(context.getFilesDir() + "/bookmarks.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            Element root = document.getDocumentElement();


            Element newBookmark = document.createElement("bookmark");

            Element titleT = document.createElement("title");
            titleT.appendChild(document.createTextNode(title));
            newBookmark.appendChild(titleT);

            Element articleA = document.createElement("article");
            for (int i = 0; i < article.size(); i++) {
                Element paragraph = document.createElement("paragraph");
                paragraph.appendChild(document.createTextNode(article.get(i)));
                articleA.appendChild(paragraph);

            }
            newBookmark.appendChild(articleA);

            root.appendChild(newBookmark);

            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
            Log.e("checkpoint!!!", titleT.getTextContent());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Vector<String> getTitles (Context context) {
        Vector <String> titles = new Vector <String>();

        try {

            File file = new File(context.getFilesDir() + "/bookmarks.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();


            Document doc = db.parse(file);

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
