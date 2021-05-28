/** 
 *  Open File Calculate Distance Between Cities
 *  Cleaned up on May 6th 2018.
 *  Created by Norman Potts
 */
package OpenFileCalculateDistanceBetweenCities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Scanner;

/** Class Cruncher
 *      Class Cruncher as specified by the instructions given from the
 *      professor.
 *      
 *      Has six methods and five global methods. The constructor receives the
 *      name of the input file and output file from main. The main method then
 *      calls methods openFiles, findDistance, writeRecords, then closeFiles. 
 *      This makes the program:
 *          1. Read the contents of the input file into an array.
 *          2. Ask for two cities to find distance between the two. Loops
 *             until correct input is received.
 *          3. Prints the distance to the user, handles any errors along the way.
 *          4. Sorts the contents of the input file and writes the sorted 
 *             contents to the output file.
 * 
 *      
 *      
 * @author Norman
 */
public class Cruncher {
    
    private static String inputfileName = "";   
    private static String outputfileName = "";
    boolean dontMakeDistanceCalculation = false;  /// Flag for when to cancel the distance calculation.
    int[] placements = new int[2];                /// Used to remember the element of the selected cityies.
    private static String[][] inputStrArr;        /// Array to hold each line contents of the input text file.
    private static String[] outputStrArr;         /// Array used to hold each output line.
    
    
    /** Constructor Cruncher
     *      Receives the input and output file names.     
     * 
     * @param input
     * @param output 
     */
    public Cruncher(String input, String output)
    {
        inputfileName = input;
        outputfileName = output;                                               
    }/// End of constructor Cruncher

    
    
    
    /** Method findDistance
     *      Makes distance calculation if it is okay too.
     */
    public void findDistance() 
    {
        if( dontMakeDistanceCalculation == false) 
        {
            try 
            {
                /* For city one and city two, extract the information from the
                   string arrays using the specified element positions. Send 
                   those strings to convert_to_Radians. That method returns 
                   the radian amount of the degrees and minutes in the strings.
                   Finally use the lat and long of each city to calculate the 
                   distance between the too.                
                */                
                int one =  placements[0];
                int two =  placements[1];
                String[] A = inputStrArr[one][2].split(" "); ///Lattidue: 43* 9' N   
                String[] B = inputStrArr[one][3].split(" "); ///Longitude: 80* 15' W            
                String[] C = inputStrArr[two][2].split(" "); ///Lattidue: 43* 39' N
                String[] D = inputStrArr[two][3].split(" "); ///Longitude: 109* 23' W                                               
                double Lat1 = 0.00;
                double Lat2 = 0.00;
                double Long1 = 0.00;
                double Long2 = 0.00;                        
                Lat1 = convert_to_Radians(A);
                Lat2 = convert_to_Radians(B);
                Long1 = convert_to_Radians(C);
                Long2 = convert_to_Radians(D);
                double earthRadius = 6371;   // in km
                double distance = Math.acos(Math.sin(Lat1) * Math.sin(Lat2) + Math.cos(Lat1) * Math.cos(Lat2) * Math.cos(Long2 - Long1)) * earthRadius;
                System.out.println(" Distance in KM: "+ distance);
            }
            catch(Exception e){
                System.err.println("Distance calculation failed...");
            }                    
        }
    }/// End of method findDistance.

    
    
    
    /** Method convert_to_Radians
     *      Converts the string array to radians
     *      The parameter A is a string that looks like this 109* 23' W 
     *      The method parses out the digits with parseDouble and replaceAll.
     *      Requires both those methods because there is some junk characters 
     *      that needs to be replaced with nothing. Finally the method runs the
     *      calculation for radians and returns it.
     * 
     * @param  A
     */
    public double convert_to_Radians(String[] A)
    {           
        double rad = 0.00;
        double degree = 0.00;
        double minute = 0.00;
        try {            
            degree = Double.parseDouble(A[0].replaceAll("[^0-9]", ""));
            minute = Double.parseDouble(A[1].replaceAll("[^0-9]", ""));                     
        }
        catch(NumberFormatException e)
        {
            System.err.println(" Double parse error. \n" + e.getMessage());
        }        
        rad = ( degree+ (minute/60) ) * 3.14 / 180;
        
        return rad;        
    }/// End of method convert_to_Radians
    
    
    
    
    /** Method openFiles
     *      Opens the input file and reads every line into the array 
     *      'inputStrArr'. The method has minor user input control. The method 
     *      will loop the input until acceptable input is received. Acceptable
     *      input would be a string that can be separated into an array of two 
     *      strings with a space. Both strings are then made lowercase and 
     *      compared. If they are the same that would also be unacceptable input
     *      causing another loop. When acceptable input is received the method 
     *      will continue. It opens up the input file and counts every line. 
     *      After a count of all the lines the inputStrArr and outputStrArr are
     *      initialized with that count. Then the method starts reading the file
     *      again and breaks every line up into four different variables. Every
     *      line is split by the ':' character. The four pieces  are Provience,
     *      city, lat, long. If ethire of the two cities specified in the 
     *      constructor are found the user a 'found' message displayed and 
     *      the flags for finding those items are switched.  When the file read
     *      is complete the flags are tested again and if they have not been 
     *      found, then the 'dontMakeDistanceCalculation' flag is set to true 
     *      stopping the distance calculation.
     *       
     */
    public void openFiles() 
    {
        System.out.println("Please select a city by typing them like 'City Provience' followed by ENTER key.");                       
        String[] cityArrOne, cityArrTwo;        
        boolean Correct = false;                        
        do 
        {
            Scanner input = new Scanner( System.in);
            System.out.print("City One: ");
            String cityOne = input.nextLine();
            System.out.print("City Two: ");
            String cityTwo = input.nextLine();                                                                   
            cityArrOne = cityOne.split(" ");
            cityArrTwo = cityTwo.split(" ");            
            if(cityArrOne.length != 2 || cityArrTwo.length != 2)
            {
                System.out.println(" City input inncorect. ");
            }            
            else
            {
                String x = make_lower_case(cityOne);
                if( x.equals(make_lower_case(cityTwo))  )
                {
                    System.out.println(" Those citys appear to be the same...");                
                }
                else
                {    Correct = true;   }
                
            }        
        }
        while(Correct == false);                                                    
        Boolean foundCityOne = false;
        Boolean foundCityTwo = false;                        
        try
        {
            /// Opening input file...
            File inputfile = new File(inputfileName);
            if( inputfile.exists() )
            {                
                Scanner inputScanner = new Scanner(new FileInputStream( inputfile ) );
                int lineCount = 0;                
                while( inputScanner.hasNextLine())
                {
                    String line = inputScanner.nextLine();
                    lineCount++;                       
                }                             
                inputStrArr = new String[lineCount][4];
                outputStrArr = new String[lineCount];                                                
                inputScanner = new Scanner(new FileInputStream( inputfile ) );
                lineCount = 0;                
                while( inputScanner.hasNextLine())
                {                   
                    String city ="";
                    String province = "";
                    String lat = "";
                    String Long = "";
                    String line = inputScanner.nextLine();
                    String[] firstsplit = line.split(":");
                    int Stringsize = firstsplit.length;
                    if(Stringsize != 4 )
                    {    
                        System.err.println(" String shouldnt be this size, stringsize"+ Stringsize); 
                    }
                    else
                    {
                        city = firstsplit[1];
                        province = firstsplit[0];
                        lat = firstsplit[2];
                        Long = firstsplit[3];                        
                        inputStrArr[lineCount][0] = city;
                        inputStrArr[lineCount][1] = province;
                        inputStrArr[lineCount][2] = lat;
                        inputStrArr[lineCount][3] = Long;                            
                        if( city.equals(cityArrOne[0]) && province.equals( cityArrOne[1] ) )
                        {
                            System.out.println(" City One found!");
                            foundCityOne = true;
                            placements[0] = lineCount;
                        }                        
                        if( city.equals(cityArrTwo[0]) && province.equals( cityArrTwo[1] )  )
                        {
                            System.out.println(" City Two found!");
                            foundCityTwo = true;
                            placements[1] = lineCount;
                        }                    
                    }    
                    lineCount++;  
                }                                
                if(foundCityOne == false )
                {                   
                    System.err.println("City One was not found!");
                    dontMakeDistanceCalculation = true;
                }                
                if(foundCityTwo == false )
                {
                    System.err.println("City Two was not found!");
                    dontMakeDistanceCalculation = true;
                }                                               
            }
            else
            {                
                System.out.println("File Not found.");
            }
        }
        catch(FileNotFoundException | SecurityException ex )
        {   
           System.err.println(" File read opening problem... ");
        }        
    }/// End of method openFiles

    
    
    
    /** Method writeRecords
     *      Writes to output file the sorted contents of the input file.
     *      Records are sorted by province and city.
     */
    public void writeRecords() throws FileNotFoundException 
    {
        ///Sort input file by provience and city into output
        for(int i = 0; i < outputStrArr.length; i++ )
        {                     
            /// Build the line together again with the provicen first...
            outputStrArr[i] = inputStrArr[i][1]+":"+inputStrArr[i][0]+":"+inputStrArr[i][2]+":+"+inputStrArr[i][3];                                                                                          
        }
        Arrays.sort(outputStrArr);        
        try 
        {
            String newline = System.getProperty("line.separator");
            Formatter output = new Formatter(outputfileName );            
            for(int z = inputStrArr.length - 1; z >= 0; z-- )            
            {  
                String line = outputStrArr[z];
                output.format(line+ newline);                
            }
            output.close();            
        }
        catch(FormatterClosedException closedExeption)
        {                   
            System.err.println(" "+closedExeption.getMessage()); 
        }        
    }/// End of method writeRecords

    
    
    
    /** Method closeFiles
     *      Instructions never specified that this method was actually needed. 
     */
    public void closeFiles() 
    {       //// Not used...
        
    }/// End of method closeFiles
    
    
    
    
    /** Method make_lower_case
     *      Makes a string lowercase.
     * @param str
     * @return lowercase
     */
    public String make_lower_case(String str)
    {        
        char[] s = str.toCharArray();
        for( int i=0; i< s.length; i++)
        {
            if(s[i] >= 'A' && s[i] <= 'Z')
            {
                s[i] += ('a'-'A');
            }
        }
        String lowercase = new String(s);
        return lowercase;
    }/// End of string lowercase
    
       
}/// End of class cruncher
