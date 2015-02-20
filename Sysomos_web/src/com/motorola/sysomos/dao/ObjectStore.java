package com.motorola.sysomos.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class ObjectStore {
	
	private static ObjectStore store;

	private static String server_ip = "104.40.134.121";
	private static String keyspace = "test";
	private static String tablename = "influencer_tweets";

	private static Cluster cluster = Cluster.builder()
			.addContactPoint(server_ip).withPort(9042).build();
	private static Session session = cluster.connect(keyspace);
	
	private static PreparedStatement preparedstatement = session
			.prepare(
					"select * from influencer_tweets  where date = ? ORDER BY influencelevel DESC")
			.setConsistencyLevel(ConsistencyLevel.ONE);
	private static PreparedStatement updatestatement = session
			.prepare(
					"update influencer_tweets set flag = ? where date = ? and influencelevel = ? and tweet_id = ?")
			.setConsistencyLevel(ConsistencyLevel.ONE);
	private static PreparedStatement searchstatement = session
			.prepare(
					"select * from influencer_tweets  where date = ? and influencelevel = ? and tweet_id = ?")
			.setConsistencyLevel(ConsistencyLevel.ONE);
	public static ObjectStore getObjectStore()
	{
		if(store == null)
			store = new ObjectStore();
		return store;
	}
	
	public static Cluster getCluster() {
		return cluster;
	}
	public static Session getSession() {
		return session;
	}
	public static PreparedStatement getPreparedstatement() {
		return preparedstatement;
	}
	public static PreparedStatement getUpdatestatement() {
		return updatestatement;
	}
	public static PreparedStatement getsearchstatement() {
		return searchstatement;
	}

	
}
