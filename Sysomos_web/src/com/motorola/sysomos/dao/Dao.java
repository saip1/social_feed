package com.motorola.sysomos.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class Dao {

	private Cluster cluster;
	private Session session;
	private PreparedStatement preparedstatement, updatestatement;

	private String tweet_text, tweet_id, time;
	private int level, flag;
	private TreeSet<TweetObj> treeset;
	private TweetObj obj;

	private static Dao dao = new Dao();

	public static Dao getDao() {
		return dao;
	}

	public Dao() {
		ObjectStore store = ObjectStore.getObjectStore();
		this.cluster = store.getCluster();
		this.session = store.getSession();
		this.preparedstatement = store.getPreparedstatement();
		this.updatestatement = store.getUpdatestatement();

	}

	public TreeSet<TweetObj> getTweets(String date) {

		BoundStatement boundstatement = new BoundStatement(preparedstatement);

		ResultSet rs = session.execute(boundstatement.bind(date));

		treeset = new TreeSet<TweetObj>();

		for (Row row : rs.all()) {
			obj = new TweetObj();

			obj.setDate(row.getString("date"));
			obj.setInfluencelevel(row.getInt("influencelevel"));
			obj.setFlag(row.getInt("flag"));
			obj.setRegion(row.getString("region"));
			obj.setTime(row.getString("time"));
			obj.setTweet_id(row.getString("tweet_id"));
			obj.setTweet_text(row.getString("tweet_text"));

			treeset.add(obj);
		}
		return treeset;

		// to-do obj.settweet_id()
		// to-do obj.setdate()
		// tweetobjimpl implements tweetobj
	}

	public void updateTweet(String date, int flag, int level, String tweet_id) {

		BoundStatement boundstatement = new BoundStatement(updatestatement);

		session.execute(boundstatement.bind(flag, date, level, tweet_id));

	}

}
