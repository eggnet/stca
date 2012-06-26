package sotec;

import java.util.MissingFormatArgumentException;

public class Main {
	public static void main(String[] args)	
	{
		System.out.println("SotectAnalyzer tool developed by eggnet at UVic.");
		try {
			if (args.length < 1)
			{
				throw new ArrayIndexOutOfBoundsException();
			}
			else
			{
				try 
				{
					// Todo
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
