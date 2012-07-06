package stca;

import generator.Generator;

import java.util.MissingFormatArgumentException;

import db.SocialAnalyzerDb;
import db.TechnicalAnalyzerDb;

public class Main {
	public static SocialAnalyzerDb socialDb = new SocialAnalyzerDb();
	public static TechnicalAnalyzerDb techDb = new TechnicalAnalyzerDb();
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
					socialDb.connect(args[0]);
					techDb.connect(args[1]);
					Generator gen = new Generator(socialDb, techDb);
					gen.generate();
					socialDb.close();
					techDb.close();
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
