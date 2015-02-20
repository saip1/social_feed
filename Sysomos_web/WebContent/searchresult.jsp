<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.HashMap,java.util.ArrayList,java.util.*,com.motorola.sysomos.dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Results</title>

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
   background-color:#E0E0E0 ;
}
table th	{
    background-color: DeepSkyBlue ;
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
		Search: <input type="text" name="search" size="35"/> <input type="submit">
	</form>
	</div>

<div align="right">
	<form action="GetTweets" method="get">
		date: <input type="text" name="date" /> <input type="submit">
	</form>
</div>

	<%!String tweet_text, text_encode, tweet_id, time, img_src,location;
	int level, flag;%>


	<table style="width: 80%" align="center">
		<caption>Tweets of the day</caption>
		<tr>
			<th>Comments</th>
			<th>Importance</th>
			<th>Time</th>
			<th>Location</th>
			<th>Acceptable</th>
		</tr>
		<tr>
			<%
				ArrayList<TweetObj> objlist = (ArrayList<TweetObj>) request
						.getAttribute("searchlist");
				TweetObj obj;
				//HashMap<String, String> inmap;
				//String tweet;

				for (int i = 0; i < objlist.size(); i++) {
					obj = objlist.get(i);
					time = obj.getTime();
					level = obj.getInfluencelevel();
					flag = obj.getFlag();
					tweet_id = obj.getTweet_id();
					tweet_text = obj.getTweet_text();
					location=obj.getregion();
					if (flag == 1)
						img_src = "images/tick.jpg";
					else
						img_src = "images/x.png";
					//text_encode = java.net.URLEncoder.encode(tweet_text, "UTF-8");
			%>
		
		<tr>

			<td><%=tweet_text%></td>
			<td><%=level%></td>
			<td><%=time%></td>
			<td><%=location%></td>
			<td><a
				href="Update?date=
				<%=request.getParameter("date")%>&level=<%=level%>
				&tweet_id=<%=tweet_id%>&flag=<%=flag%>"><img
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


</body>
</html>