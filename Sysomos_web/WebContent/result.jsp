<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.HashMap,java.util.ArrayList,java.util.TreeSet,java.util.*,com.motorola.sysomos.dao.TweetObj,java.text.DateFormat,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tweets</title>
<style>
table {
	border-collapse: collapse;
}

table, td, th {
	border: 1px LightSlateGray;
}

table tr:nth-child(even) {
	background-color: #eee;
}

table tr:nth-child(odd) {
	background-color: #E0E0E0;
}

table th {
	background-color: DeepSkyBlue;
	color: white;
}

th {
	background-color: Aqua;
	color: white;
}

th, td {
	padding: 5px;
}
</style>
</head>
<body>

	<div align="center">
		<form action="Search" method="get"">
			Search: <input type="text" name="search" size="35" /> <input
				type="submit">
		</form>
	</div>

	<div align="right">
		<form action="GetTweets" method="get">
			date: <input type="text" name="date" /> <input type="submit">
		</form>
	</div>
	<%!String tweet_text, text_encode, tweet_id, time, img_src, date, location;
	int level, flag;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();%>


	<table style="width: 80%" align="center" cellpadding="2">
		<caption>Tweets of the day</caption>
		<tr>
			<th>date</th>
			<th>Comments</th>
			<th>Importance</th>
			<th>Time</th>
			<th>Location</th>
			<th>Acceptable</th>
		</tr>
		<tr>
			<%
				TreeSet<TweetObj> treeset = (TreeSet<TweetObj>) request
						.getAttribute("tweets");
				Iterator<TweetObj> iterator = treeset.iterator();
				TweetObj obj;
				//HashMap<String, String> inmap;
				//String tweet;

				while (iterator.hasNext()) {
					obj = iterator.next();
					date = obj.getdate();
					time = obj.getTime();
					level = obj.getInfluencelevel();
					flag = obj.getFlag();
					tweet_id = obj.getTweet_id();
					tweet_text = obj.getTweet_text();
					location = obj.getregion();
					if (flag == 1)
						img_src = "images/tick.jpg";
					else
						img_src = "images/x.png";
					//text_encode = java.net.URLEncoder.encode(tweet_text, "UTF-8");
			%>
		
		<tr>
			<td><%=date%></td>
			<td><%=tweet_text%></td>
			<td><%=level%></td>
			<td><%=time%></td>
			<td><%=location%></td>
			<td><a
				href="GetTweets?date=
				<%=date%>&level=<%=level%>
				&tweet_id=<%=tweet_id%>&flag=<%=flag%>&action="update""><img
					width="20" height="20" src=<%=img_src%>></a></td>

		</tr>
		<%-- 	
				
	<li><a href=/Update?date=
		<%=request.getAttribute("date")%>
		&level=<%=i%> &tweet=<%=tweet%>
		&action="retain"><%=tweet %></a></li>
		
--%>
		<%
			}
		%>
	</table>

</body>
</html>