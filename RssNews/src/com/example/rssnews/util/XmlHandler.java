package com.example.rssnews.util;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.example.rssnews.domain.RssFeed;
import com.example.rssnews.domain.RssItem;
  
public class XmlHandler extends DefaultHandler{  
	
	private final String TAG = this.getClass().getSimpleName();
    
	private RssFeed rssFeed;
	private RssItem rssItem;

	private final String RSS_TITLE = "title";		// 若是 title 标签，记做1，注意有两个title，但我们都保存在item的成员变量中
	private final String RSS_LINK = "link";			// 若是 link 标签，记做2
	private final String RSS_AUTHOR = "author";
	private final String RSS_CATEGORY = "category";		// 若是category标签,记做 4
	private final String RSS_PUBDATE = "pubDate"; 		// 若是pubdate标签,记做5,注意有两个pubdate,但我们都保存在item的pubdate成员变量中
	private final String RSS_COMMENTS = "comments";
	private final String RSS_DESCRIPTION = "description";	// 若是 description 标签，记做3
	private final String RSS_IMAGE = "image";
	private final String RSS_CONTENT = "content:encoded";

	//parse start time
	private long start_time;
	
	//(1)
	private StringBuilder sb = new StringBuilder();

	public XmlHandler() {
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		
		start_time = System.currentTimeMillis();
		rssFeed = new RssFeed();
		rssItem = new RssItem();
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		
		//(2) don't lost the data however execute characters
		sb.append(ch, start, length);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		
		//(3) start collective the data and clear the history data firstly
		sb.setLength(0);
		if ("item".equals(localName)) {
			rssItem = new RssItem();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		
		// (4)change to get value here
		String value = sb.toString(); 
		
		if (RSS_TITLE.equals(localName)) {
			rssItem.setTitle(value);
		} else if (RSS_DESCRIPTION.equals(localName)) {
			rssItem.setDescription(value);
		} else if (RSS_LINK.equals(localName)) {
			rssItem.setLink(value);
		} else if (RSS_PUBDATE.equals(localName)) {
			rssItem.setPubdate(value);
		} else if (RSS_CATEGORY.equals(localName)) {
			rssItem.setCategory(value);
		} else if (RSS_AUTHOR.equals(localName)) {
			rssItem.setAuthor(value);
		} else if (RSS_COMMENTS.equals(localName)) {
			rssItem.setComments(value);
		} else if (RSS_IMAGE.equals(localName)) {
			rssItem.setImage(value);
		} else if (RSS_CONTENT.equals(localName)) {
			rssItem.setContent(value);
		}
		
		
		if ("item".equals(localName)) {
			rssFeed.addItem(rssItem);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		
		long end = System.currentTimeMillis();
		Log.d(TAG, "Parse List's Xml Cost: " + (end - start_time) + " !!");
	}

	public RssFeed getRssFeed() {
		return rssFeed;
	}
}