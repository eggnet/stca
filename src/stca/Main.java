package stca;

import java.util.MissingFormatArgumentException;

public class Main {
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
