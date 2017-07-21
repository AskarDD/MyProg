package ru.innopolis.askar.habrarss.presenter.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ru.innopolis.askar.habrarss.model.Article;

/**
 * Created by admin on 21.07.2017.
 */

public class LoadLentaRss extends AsyncTaskLoader {
    private String address;
    private List<Article> articleItems;

    public LoadLentaRss(Context context, String url) {
        super(context);
        this.address = url;
        articleItems = new ArrayList<Article>();
    }

    @Override
    public Object loadInBackground() {
        if (address.startsWith("http")) {
            try {
                URL url = new URL(address);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(url.openStream()));
                document.getDocumentElement().normalize();
                articleItems = parseFromXML(document);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return articleItems;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    public void deliverResult(Object data) {
        super.deliverResult(data);
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
    }

    private List<Article> parseFromXML(Document document) {
        List<Article> itemList = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("item");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                List<String> categories = new ArrayList<>();

                Element entry = (Element) nodeList.item(i);

                Element _titleE = (Element) entry.getElementsByTagName("title").item(0);
                Element _guidE = (Element) entry.getElementsByTagName("guid").item(0);
                Element _linkE = (Element) entry.getElementsByTagName("link").item(0);
                Element _descriptionE = (Element) entry.getElementsByTagName("description").item(0);
                Element _pubDateE = (Element) entry.getElementsByTagName("pubDate").item(0);
                Element _dcCreatorE = (Element) entry.getElementsByTagName("dc:creator").item(0);
                NodeList categoryList = entry.getElementsByTagName("category");

                for (int j = 0; j < categoryList.getLength(); j++) {
                    Element _categoryE = (Element) categoryList.item(j);
                    categories.add(_categoryE.getTextContent());
                }

                String _title = _titleE.getTextContent();
                String _guid = _guidE.getTextContent();
                String _link = _linkE.getTextContent();
                String _description = _descriptionE.getTextContent();
                Date _pubDate = new Date(_pubDateE.getTextContent());
                String _dcCreator = _dcCreatorE.getTextContent();
                Article article = new Article(_title,_guid,_link,_description,_pubDate,_dcCreator,categories);

                itemList.add(article);
                if (itemList.size() > 20)
                    break;
            }
        }
        return itemList;
    }
}
