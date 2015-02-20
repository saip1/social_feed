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
import java.util.Iterator;
import java.util.TreeSet;

public class Index {
	
	static FileWriter filewriter;
	
public static void main(String[] args) throws IOException, ParseException {
    // 0. Specify the analyzer for tokenizing text.
    //    The same analyzer should be used for indexing and searching
    StandardAnalyzer analyzer = new StandardAnalyzer();

    Directory index=FSDirectory.open(new File("final_index"));
    
    //Directory index=new RAMDirectory(FSDirectory.open(new File("indexdir")), IOContext.DEFAULT);
    
    // 1. create the index
   //Directory index = new RAMDirectory();

    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);

   IndexWriter w = new IndexWriter(index, config);
    
    //**************************
    
    StringBuilder sb;
    String keyword,key,str;
    
    Dao dao= Dao.getDao();
    TweetObj obj;
    TreeSet<TweetObj> treeset = dao.getTweets("2015-02-10");
    Iterator<TweetObj> iterator= treeset.iterator();
    
   // filewriter = new FileWriter("datafile");
	//filewriter.append(Integer.toString(list.size()));
    
    filewriter = new FileWriter("datafile");
    while(iterator.hasNext())
    {
    	obj=iterator.next();
    	//keyword=obj.getTweet_text();
    	keyword=obj.getTweet_id()+":"+obj.getInfluencelevel()+":"+obj.getdate()+":"+obj.getTweet_text();
       // key=obj.getdate()+":"+obj.getInfluencelevel()+":"+obj.getTweet_id();
    	sb = new StringBuilder();
    	sb.append(obj.getdate());
    	sb.append(":");
    	sb.append(obj.getInfluencelevel());
    	sb.append(":");
    	sb.append(obj.getTweet_id());
    	key=sb.toString();
    	//filewriter.append(obj.getdate()+":"+obj.getInfluencelevel()+":"+obj.getTweet_id());
    	//filewriter.append(keyword);
    	//filewriter.append("\n");
    	
    	String[] tokens = keyword.split(":");
    	
    	filewriter.append("hello");
    	
    	addDoc(w,tokens[3],tokens[0],tokens[1],tokens[2]);
    }
    
   // filewriter.flush();
    //filewriter.close();
    
    
    
    //**************************
	
	keyword="what the hell";
	key="123";
	
	//addDoc(w, keyword, key);
	
    
   /* addDoc(w, "Lucene in Action", "193398817");
    addDoc(w, "Lucene for Dummies", "55320055Z");
    addDoc(w, "Managing Gigabytes", "55063554A");
    addDoc(w, "The Art of Computer Science", "9900333X"); */
    
   w.close();

    // 2. query
    String querystr = "Moto+X";

    // the "title" arg specifies the default field to use
    // when no field is explicitly specified in the query.
    Query q = new QueryParser("tweet_text", analyzer).parse(querystr);

    // 3. search
    int hitsPerPage = 10;
   //IndexReader reader = DirectoryReader.open(FSDirectory.open(new File("new_index_dir")));
    IndexReader reader = DirectoryReader.open(index);
    //IndexReader reader = IndexReader.open(index);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
    searcher.search(q, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    
    // 4. display results
  	//filewriter.append("Found " + hits.length + " hits.");
   // System.out.println("Found " + hits.length + " hits.");
    for(int i=0;i<hits.length;i++) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      filewriter.append(d.get("tweet_id") + "\t" +"\n");
      //System.out.println((i + 1) + ". " + d.get("key") + "\t" + d.get("keyword"));
    }
    
	filewriter.flush();
	filewriter.close();
    // reader can only be closed when there
    // is no need to access the documents any more.
    reader.close();
  }

  private static void addDoc(IndexWriter w, String tweet_text,String tweet_id,String influence_level,String date) throws IOException {
    Document doc = new Document();
    doc.add(new TextField("tweet_text", tweet_text, Field.Store.YES));

    // use a string field for isbn because we don't want it tokenized
    doc.add(new StringField("date", date, Field.Store.YES));
    doc.add(new StringField("influence_level", influence_level, Field.Store.YES));
    doc.add(new StringField("tweet_id", tweet_id, Field.Store.YES));
    w.addDocument(doc);
  }
}