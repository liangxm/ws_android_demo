package com.example.rssnews.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.example.rssnews.domain.RssFeed;
import com.example.rssnews.domain.RssItem;
import android.util.Log;

public class RssHandler extends DefaultHandler {

	private RssFeed rssFeed;
	private RssItem rssItem;

	private final int RSS_TITLE = 1;		// ���� title ��ǩ������1��ע��������title�������Ƕ�������item�ĳ�Ա������
	private final int RSS_LINK = 2;			// ���� link ��ǩ������2
	private final int RSS_AUTHOR = 3;
	private final int RSS_CATEGORY = 4;		// ����category��ǩ,���� 4
	private final int RSS_PUBDATE = 5; 		// ����pubdate��ǩ,����5,ע��������pubdate,�����Ƕ�������item��pubdate��Ա������
	private final int RSS_COMMENTS = 6;
	private final int RSS_DESCRIPTION = 7;	// ���� description ��ǩ������3
	private final int RSS_IMAGE = 8;

	private int currentFlag = 0;

	public RssHandler() {
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		rssFeed = new RssFeed();
		rssItem = new RssItem();

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		
		// ��ȡ�ַ���
		String text = new String(ch, start, length);
		Log.i("handler", "currentFlag:" + currentFlag);
		Log.i("handler", "content:[" + text+"]");

		switch (currentFlag) {
		case RSS_TITLE:
			rssItem.setTitle(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_PUBDATE:
			rssItem.setPubdate(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_CATEGORY:
			rssItem.setCategory(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_LINK:
			rssItem.setLink(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_AUTHOR:
			rssItem.setAuthor(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_DESCRIPTION:
			rssItem.setDescription(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_COMMENTS:
			rssItem.setComments(text);
			currentFlag = 0;// �����������Ϊ��ʼ״̬
			break;
		case RSS_IMAGE:
			rssItem.setImage(text);
			currentFlag = 0;
			break;
		default:
			break;
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if ("chanel".equals(localName)) { // �����ǩ��û�����ǹ��ĵ����ݣ����Բ�������currentFlag=0
			currentFlag = 0;
		} else if ("item".equals(localName)) {
			rssItem = new RssItem();
		} else if ("title".equals(localName)) {
			currentFlag = RSS_TITLE;
		} else if ("description".equals(localName)) {
			currentFlag = RSS_DESCRIPTION;
		} else if ("link".equals(localName)) {
			currentFlag = RSS_LINK;
		} else if ("pubDate".equals(localName)) {
			currentFlag = RSS_PUBDATE;
		} else if ("category".equals(localName)) {
			currentFlag = RSS_CATEGORY;
		} else if ("author".equals(localName)) {
			currentFlag = RSS_AUTHOR;
		} else if ("comments".equals(localName)) {
			currentFlag = RSS_COMMENTS;
		} else if ("image".equals(localName)) {
			currentFlag = RSS_IMAGE;
		}else{
			currentFlag = 0;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		
		// �������һ��item�ڵ�������ͽ�rssItem��ӵ�rssFeed�С�
		if ("item".equals(localName)) {
			rssFeed.addItem(rssItem);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	public RssFeed getRssFeed() {
		return rssFeed;
	}

}