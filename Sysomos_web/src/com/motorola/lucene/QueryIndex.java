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
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.motorola.sysomos.dao.Dao;
import com.motorola.sysomos.dao.ObjectStore;
import com.motorola.sysomos.dao.TweetObj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class QueryIndex {

	String querystr;

	CreateIndex cindex;

	TreeSet<TweetObj> treeset;

	TweetObj tobj;

	private Cluster cluster;
	private Session session;
	private PreparedStatement searchstatement;
	private BoundStatement boundstatement;

	private String tweet_text, tweet_id, date;
	private int level, flag;

	private Row row;

	private static ObjectStore store;

	public QueryIndex() {

		store = ObjectStore.getObjectStore();

		this.cluster = store.getCluster();
		this.session = store.getSession();
		this.searchstatement = store.getsearchstatement();

	}

	public TreeSet<TweetObj> getTweetinfo(String keyword)
			throws ParseException, IOException {

		treeset = new TreeSet<TweetObj>();

		querystr = keyword;

		Query q = new QueryParser("tweet_text", new StandardAnalyzer())
				.parse(querystr);

		int hitsPerPage = 100;

		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(
				"/Users/saip1/workspace/Sysomos_web/final_index")));
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(
				hitsPerPage, true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);

			boundstatement = new BoundStatement(searchstatement);

			ResultSet rs = session.execute(boundstatement.bind(d.get("date"),
					Integer.parseInt(d.get("influence_level")),
					d.get("tweet_id")));

			row = rs.one();

			tobj = new TweetObj();
			tobj.setDate(row.getString("date"));
			tobj.setInfluencelevel(row.getInt("influencelevel"));
			tobj.setFlag(row.getInt("flag"));
			tobj.setRegion(row.getString("region"));
			tobj.setTime(row.getString("time"));
			tobj.setTweet_id(row.getString("tweet_id"));
			tobj.setTweet_text(row.getString("tweet_text"));

			treeset.add(tobj);

		}

		reader.close();

		return treeset;
	}

}