package com.abc.prototype;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

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

    Vector <String> titles;

    public Bookmark (Context context) {
        this.titles = getTitles(context);
    }

    public Vector<String> getTitles (Context context) {
        Vector <String> titles = new Vector <String>();
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("bookmarks.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
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


    public void addBookmark () {

    }
}
