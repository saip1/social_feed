package com.motorola.sysomos.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import com.motorola.lucene.QueryIndex;

public class ReadIndex {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		FileWriter file= new FileWriter("datafile");
			
		ArrayList<String> list;
				
		QueryIndex qindex  = new QueryIndex();
		
		 /*
		 objlist = qindex.getTweetinfo("moto");
				
		for(int i=0;i<objlist.size();i++)
		{
			file.append(objlist.get(i).toString()+"\n");
		} 
		  //file.append(Integer.toString(j)); */
file.flush();
file.close();
	}

}
