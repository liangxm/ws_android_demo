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

	private final String RSS_TITLE = "title";		// ���� title ��ǩ������1��ע��������title�������Ƕ�������item�ĳ�Ա������
	private final String RSS_LINK = "link";			// ���� link ��ǩ������2
	private final String RSS_AUTHOR = "author";
	private final String RSS_CATEGORY = "category";		// ����category��ǩ,���� 4
	private final String RSS_PUBDATE = "pubDate"; 		// ����pubdate��ǩ,����5,ע��������pubdate,�����Ƕ�������item��pubdate��Ա������
	private final String RSS_COMMENTS = "comments";
	private final String RSS_DESCRIPTION = "description";	// ���� description ��ǩ������3
	private final String RSS_IMAGE = "image";

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