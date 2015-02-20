package com.motorola.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.motorola.sysomos.dao.Dao;
import com.motorola.sysomos.dao.TweetObj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CreateIndex {

	static FileWriter file;
	Document doc;
	static StandardAnalyzer analyzer;
	static Directory index;
	static IndexWriterConfig config;
	static IndexWriter w;

	private static CreateIndex createindex = new CreateIndex();

	public static CreateIndex getCreateIndex() {
		return createindex;
	}

	public CreateIndex() {
		StandardAnalyzer analyzer = new StandardAnalyzer();

	    //Directory index;
		try {
			index = FSDirectory.open(new File("what_index"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //Directory index=new RAMDirectory(FSDirectory.open(new File("indexdir")), IOContext.DEFAULT);
	    
	    // 1. create the index
	   //Directory index = new RAMDirectory();

	    config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);

	   try {
		w = new IndexWriter(index, config);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		/*
		 * addDoc(w, "Lucene in Action", "193398817"); addDoc(w,
		 * "Lucene for Dummies", "55320055Z"); addDoc(w, "Managing Gigabytes",
		 * "55063554A"); addDoc(w, "The Art of Computer Science", "9900333X");
		 */

		// w.close();

	}

	public StandardAnalyzer getAnalyzer() {
		return analyzer;
	}

	public Directory getIndex() {
		return index;
	}

	public IndexWriterConfig getConfig() {
		return config;
	}

	public IndexWriter getIndexWriter() {
		return w;
	}
	
	 public void closeIndexWriter() throws IOException {
	        
	            w.close();
	        
	   }

	public void addDoc(String tweet_text, String tweet_id,String date,String influence_level) throws IOException {
		doc = new Document();
		doc.add(new TextField("tweet_text", tweet_text, Field.Store.YES));
		doc.add(new StringField("date", date, Field.Store.YES));
	    doc.add(new StringField("influence_level", influence_level, Field.Store.YES));
		doc.add(new StringField("tweet_id", tweet_id, Field.Store.YES));
		w.addDocument(doc);

	}
}