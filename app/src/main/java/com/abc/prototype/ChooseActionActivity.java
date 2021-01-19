package com.abc.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static com.abc.prototype.Navigate.goToReadArticleActivity;
import static com.abc.prototype.Navigate.goToSourceSelection;

// // TODO: 09/01/2021 needs more work :)) 
public class ChooseActionActivity extends AppCompatActivity {

    private String source;
    private String title;
    private String link;
    private String category;
    private String subcategory;

    private TextView tv;
    private TextToSpeech mTTS;

    private Button btnRead;
    private Button btnBookmark;

    private final String ABS = "abs";
    private final String GMA = "gma";
    private final String INQUIRER = "inquirer";
    private final String PHILSTAR = "philstar";

    private Vector<String> article;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            source = extras.getString("source");
            link = extras.getString("link");
            title = extras.getString("title");
            category = extras.getString("category");

            Log.e("link", link);
            Log.e("title", title);
        }

        Context context = getApplicationContext();
        mTTS = TextReader.initialize(context);

        tv = findViewById(R.id.textViewChooseActionTitle);
        tv.setText(title);
        TextReader.say(mTTS, tv);

        btnRead = findViewById(R.id.buttonChooseActionRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.stop();

                Context context = getApplicationContext();
                goToReadArticleActivity(context,source, link, title, category, subcategory);
            }
        });
        
        btnBookmark = findViewById(R.id.buttonChooseActionBookmark);
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 09/01/2021 mechenism for bookmarking an article
                // ubra bookmark obj nga isulod sa xml file ang data
                // ubra sang article nga object (??)

                // scrape the article
                mTTS.stop();


                new BookmarkThread().execute();

                Context context = getApplicationContext();
                goToSourceSelection(context);
            }
        });
    }

    private class BookmarkThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            switch (source) {
                case ABS:
                    AbsScraper absScraper = new AbsScraper(source, link);
                    article = absScraper.article;

                    break;

//                case GMA:
//
//                    break;
//
//                case INQUIRER:
//
//                    break;
//
//                case PHILSTAR:
//
//                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Context context = getApplicationContext();

            Log.e("directory", getFilesDir().toString());

            String path = context.getFilesDir().toString();
            String filepath = path + "/bookmarks.xml";
            File file = new File(filepath);

            if (file.exists()) {
                Log.e("bookmarks.xml status", "bookmarks.xml exists");

                try {
//                AssetManager am = context.getAssets();
//                InputStream is = am.open("bookmarks.xml");

                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();

//                    Log.e("bookmarks.xml status", "add bookmark");
//                    Document document = db.parse(file);

                    Document document = db.parse(context.openFileInput("bookmarks.xml"));
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
                    StreamResult result = new StreamResult("bookmarks.xml");
                    transformer.transform(source, result);



                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else {
                Log.e("bookmarks.xml status", "need to create bookmarks.xml");
                FileOutputStream fos = null;

                try {

                    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                    Document document = documentBuilder.newDocument();

                    Element root = document.createElement("bookmarks");
                    document.appendChild(root);

                    fos = openFileOutput("bookmarks.xml", MODE_APPEND);
                    fos.write(document.getTextContent().getBytes());


                    Log.e("bookmarks contents", document.toString());

                } catch (FileNotFoundException  e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    Log.e("bookmarks.xml status", "created bookmarks.xml");
                }

            }
        }
    }


}