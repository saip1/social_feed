package com.motorola.sysomos;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.motorola.sysomos.dao.Dao;
import com.motorola.sysomos.dao.TweetObj;

/**
 * Servlet implementation class GetTweets
 */
@WebServlet("/GetTweets")
public class GetTweets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public String date,tweet_id;
	
	public int flag,level;

	TreeSet<TweetObj> treeset;
	
	Dao dao;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetTweets() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("action")!=null)
			doPost(request,response);
		
		cal.add(Calendar.DATE, -1); 

		date = request.getParameter("date");
		
		if(date==null)
			date=dateFormat.format(cal.getTime());
				
		dao = Dao.getDao();

		treeset = dao.getTweets(date);

		// ArrayList<String> list = obj.getTweets(date);

		request.setAttribute("date", date);
		request.setAttribute("tweets", treeset);

		RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");

		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		dao=Dao.getDao();

		date = request.getParameter("date").toString();
		level = Integer.parseInt(request.getParameter("level"));
		tweet_id = request.getParameter("tweet_id").toString();
		// tweet_text = request.getParameter("tweet_text").toString();

		flag = Integer.parseInt(request.getParameter("flag"));

		// obj.deleteTweet(date, 1, level, tweet_id);
		if (flag == 1)
			dao.updateTweet(date, 0, level, tweet_id);
		else
			dao.updateTweet(date, 1, level, tweet_id);

		treeset = dao.getTweets(date);

		// ArrayList<String> list = obj.getTweets(date);

		request.setAttribute("tweets", treeset);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("result.jsp");

		dispatcher.forward(request, response);
		
	}

}
