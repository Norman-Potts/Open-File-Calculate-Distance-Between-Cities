/**
 *  This was created by Norman Potts
 *  2018/May/01
 *  Open File Calculate Distance Between Cities
 * 
 *  Cleaned up on May 6th 2018.
 *  Created by Norman Potts
 * 
 */
package OpenFileCalculateDistanceBetweenCities;

import java.io.FileNotFoundException;

/** Class OpenFileCalculateDistanceBetweenCities
 *  
 *      This class makes use of the command line to received the names of the
 *      input files. The lines for this functionality are commented out because
 *      it was easier to develop with hard coded file names. The input files 
 *      used sit in the src folder of this project. The input file holds 
 *      information about some Canadian cities. The output file will get written
 *      to when the input data is sorted by province name. The work is done
 *      with the cruncher java class. The methods are called in the order 
 *      specified in the instructions.
 *
 * @author Norman
 */
public class OpenFileCalculateDistanceBetweenCities 
{

    public static void main(String[] args) throws FileNotFoundException 
    {
    
        /*if(args.length < 2)
	{
            System.err.println("Usage: java -jar lab5.jar infile outfile");
	    System.exit(99);
	}*/

	//Cruncher dataManipulator = new Cruncher(args[0], args[1]);
        /// Hard coded is alot easier to develop with.
        String input = "./src//input.txt";
        String output = "./src//output.txt";
        Cruncher dataManipulator = new Cruncher(input, output);
        
	dataManipulator.openFiles();
	dataManipulator.findDistance();
	dataManipulator.writeRecords();
	dataManipulator.closeFiles();
        
    }///End of method main.   
}