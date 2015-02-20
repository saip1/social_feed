package com.motorola.sysomos.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;

import com.motorola.sysomos.dao.Dao;
import com.motorola.sysomos.dao.TweetObj;
import com.motorola.lucene.*;

public class BuildIndex {

	static FileWriter file;
	
	public static void main(String[] args) throws IOException, ParseException{
		// TODO Auto-generated method stub
		TreeSet<TweetObj> treeset = Dao.getDao().getTweets("2015-02-05");
		file=new FileWriter("datafile");
		TweetObj obj;
		
		String tweet_text,tweet_id,date,influence_level;
		
		//CreateIndex cindex=CreateIndex.getCreateIndex();
		
		CreateIndex cindex=new CreateIndex();
		
		Iterator<TweetObj> iterator = treeset.iterator();
				
		while(iterator.hasNext()) {
			
			obj = iterator.next();
			
			tweet_text = obj.getTweet_text();
			tweet_id = obj.getTweet_id();
			date = obj.getdate();
			influence_level = Integer.toString(obj.getInfluencelevel());
				/*CreateIndex.getCreateIndex().addDoc(
						list.get(i).getTweet_text(),
						list.get(i).getdate() + ":"
								+ list.get(i).getInfluencelevel() + ":"
								+ list.get(i).getTweet_id()); */
			file.append(tweet_text);
			file.append(":");
			file.append(tweet_id);
			cindex.addDoc(tweet_text,tweet_id,date,influence_level);
			
		}
		
		cindex.closeIndexWriter();	
		
		file.flush();
		file.close();

	}

}
