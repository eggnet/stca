package stca;

import generator.Generator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import db.SocialAnalyzerDb;
import db.TechnicalAnalyzerDb;

public class Main {
	public static SocialAnalyzerDb socialDb = new SocialAnalyzerDb();
	public static TechnicalAnalyzerDb techDb = new TechnicalAnalyzerDb();
	public static void main(String[] args)	
	{
		System.out.println("Social Technical Network Analyzer tool developed by eggnet at UVic.");
		CommandLineParser parser = new GnuParser();
		
    	String techLowerWeight = "-1";
    	String techUpperWeight = "-1";
    	String SociallowerWeight = "-1";
    	String SocialupperWeight = "-1";
    	String FuzzylowerWeight = "-1";
    	String FuzzyupperWeight = "-1";
    	
    	String socialDbName = "";
    	String technicalDbName = "";
    	
		try
		{
			if (args.length < 1)
			{
				printMan();
				return;
			}
			else
			{
				Options options = new Options();
				Option socialDbArg      = OptionBuilder.withArgName("s").hasArg().create("s");
				Option technicalDbArg   = OptionBuilder.withArgName("t").hasArg().create("t");
				Option weightTech 	    = OptionBuilder.withArgName("tw").hasArgs(2).create("tw");
				Option weightSocial 	= OptionBuilder.withArgName("sw").hasArgs(2).create("sw");
				Option weightFuzzy 		= OptionBuilder.withArgName("fw").hasArgs(2).create("fw");
				Option help				= OptionBuilder.withArgName("h").hasArg().create("h");
				
				options.addOption(socialDbArg);
				options.addOption(technicalDbArg);
				options.addOption(weightTech);
				options.addOption(weightSocial);
				options.addOption(weightFuzzy);
				options.addOption(help);
				
				CommandLine line = parser.parse(options, args);
				
				// Check for Help
				if(line.hasOption("h"))
				{
					printMan();
					return;
				}
				
				// Check for Social
				if(line.hasOption("s"))
				{
				    String[] values = line.getOptionValues("s");
				    if(values.length != 1)
				    {
				    	System.out.println("-s flag used incorrectly.");
				    	printMan();
				    	return;
				    }
				    else
				    {
				    	socialDbName = values[0];
				    }
				}
				
				// Check for Technical
				if(line.hasOption("t")) {
				    String[] values = line.getOptionValues("t");
				    if(values.length != 1) {
				    	System.out.println("-t flag used incorrectly.");
				    	printMan();
				    	return;
				    }
				    else
				    {
				    	technicalDbName = values[0];
				    }
				}
				
				// Check for technical weight threshold
				if(line.hasOption("tw")) {
				    String[] values = line.getOptionValues("tw");
				    if(values.length == 2)
				    {
				    	techLowerWeight = values[0];
				    	techUpperWeight = values[1];
				    }
				    else
				    if(values.length == 1)
				    {
				    	techLowerWeight = values[0];
				    }
				    else
				    {
				    	System.out.println("-tw flag used incorrectly.");
				    	printMan();
				    	return;
				    }
				}
				
				// Check for social weight threshold
				if(line.hasOption("sw")) {
				    String[] values = line.getOptionValues("sw");

				    if(values.length == 2)
				    {
				    	SociallowerWeight = values[0];
				    	SocialupperWeight = values[1];
				    }
				    else
				    if(values.length == 1)
				    {
				    	SociallowerWeight = values[0];
				    }
				    else	
				    {
				    	System.out.println("-sw flag used incorrectly.");
				    	printMan();
				    	return;
				    }
				}
				
				// Check for technical fuzzy weight threshold
				if(line.hasOption("fw")) {
				    String[] values = line.getOptionValues("fw");
				    if(values.length == 2)
				    {
				    	FuzzylowerWeight = values[0];
				    	FuzzyupperWeight = values[1];
				    }
				    else
				    if(values.length == 1)
				    {
				    	FuzzylowerWeight = values[0];
				    }
					else
					{
				    	System.out.println("-fw flag used incorrectly.");
				    	printMan();
				    	return;
				    }
				}
				// Run the analyzer
				socialDb.connect(socialDbName);
				techDb.connect(technicalDbName);
				
				float tlowerW = Float.parseFloat(techLowerWeight);
				float tupperW = Float.parseFloat(techUpperWeight);
				float slowerW = Float.parseFloat(SociallowerWeight);
				float supperW = Float.parseFloat(SocialupperWeight);
				float flowerW = Float.parseFloat(FuzzylowerWeight);
				float fupperW = Float.parseFloat(FuzzyupperWeight);
				
				Generator gen = new Generator(socialDb, techDb, tlowerW, tupperW, slowerW, supperW,flowerW, fupperW);
				gen.generate();
				
				socialDb.close();
				techDb.close();
			}
		}
		catch (NumberFormatException ne)
		{
			System.out.print("ERROR: -tw or -fw or -sw Lower Upper values must be float. Example: -tw 0.0 1.5 or -tw 1.0");
			printMan();
		}
		catch (Exception e)
		{
			printMan();
		}
		
	}
	
	private static void printMan() {
		System.out.println("stca arguement: [-s socialDbName] [-t technicalDbName] [-tw|-sw|-fw lowerWeight upperWeight]");    
		System.out.println("for [-tw|-sw|-fw lowerWeight upperWeight] you can skip upperWeight");    
		System.out.println("Ex: -s hibernateogm_7_4 -t hibernateogm -tw 0.0 10 -sw 0.0"); 
		try {
			// Print the man page
			BufferedReader in = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("man.txt")));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);          
			}         
			in.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("There was an error printing the man page");
		}
	}
}
