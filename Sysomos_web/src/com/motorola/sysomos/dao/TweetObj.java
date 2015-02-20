package com.motorola.sysomos.dao;

import com.datastax.driver.core.Row;

public class TweetObj implements Comparable<TweetObj> {

	String tweet_text, time, tweet_id, date, region;

	public String getTweet_text() {
		return tweet_text;
	}

	public String getTime() {
		return time;
	}

	public String getTweet_id() {
		return tweet_id;
	}

	public void setTweet_text(String tweet_text) {
		this.tweet_text = tweet_text;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTweet_id(String tweet_id) {
		this.tweet_id = tweet_id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setInfluencelevel(int influencelevel) {
		this.influencelevel = influencelevel;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getInfluencelevel() {
		return influencelevel;
	}

	public int getFlag() {
		return flag;
	}

	public String getdate() {
		return date;
	}

	public String getregion() {
		return region;
	}

	int influencelevel, flag;

	public TweetObj() {

	}

	public TweetObj(Row row) {
		date = row.getString("date");
		tweet_id = row.getString("tweet_id");
		tweet_text = row.getString("tweet_text");
		influencelevel = row.getInt("influencelevel");
		time = row.getString("time");
		flag = row.getInt("flag");
		region = row.getString("region");
	}

	@Override
	public int compareTo(TweetObj o) {

		return (date+influencelevel+time+tweet_id).compareTo((o.getdate()+o.getInfluencelevel()+o.getTime()+o.tweet_id));

	}

}
