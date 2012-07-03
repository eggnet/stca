package stca;

import generator.Generator;

import java.util.MissingFormatArgumentException;

import db.DbConnection;
import db.StcaDb;

public class Main {
	public static StcaDb socialDb = new StcaDb();
	public static void main(String[] args)	
	{
		System.out.println("Social Technical Network Analyzer tool developed by eggnet at UVic.");
		try {
			if (args.length < 1)
			{
				throw new ArrayIndexOutOfBoundsException();
			}
			else
			{
				try 
				{
					//TODO @braden
					Generator gen = new Generator(socialDb, null);
				} 
				catch (MissingFormatArgumentException e) 
				{
					e.printStackTrace();
				} 
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Usage SotectAnalyzer <technicalDatabse> <SocialDatabase>");
		}
	}
}
