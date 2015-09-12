import java.util.*;
import java.io.*;

public class ScheduleHTMLConstructor
{
    public static void main(String[] args) throws FileNotFoundException
    {
        System.out.println("This is a program for designing an html template for a table with css styling tags built in for a schedule.");
        
        int SCH_WEEKEND = changeSchWeekends();
        int SCH_TIME = changeSchHourIncrements();

        int k = 0;
        Scanner in = new Scanner(System.in); // System.out.println(in.delimiter());
        
        // Initialize the schedule
        String[][] scheduleContent = buildSchedule(SCH_WEEKEND, SCH_TIME);
        
        String fileName = writeScheduleToFile(scheduleContent, SCH_WEEKEND, SCH_TIME);
        
        System.out.println("The file "+fileName+" has been successfully written.");
    }
    
    
    //--------------------------------------------------------------------------------------------------
    //-------------Weekends-----------------------------------------------------------------------------
    public static int changeSchWeekends()
    {
        // Initialize the variables and scanner here
        boolean BADINPUT=true;
        int k = 0;
        String yn = new String();
        Scanner in = new Scanner(System.in);
        
        // Block to check if weekends are included
        while(BADINPUT)
        {
            System.out.println("Describe how this schedule will look:\n\t0. Ignore weekends \n\t1. Include weekends");
            k = in.nextInt();
            if(k*k == k) // the only integers satisfying this are 0 and 1
            {
                if(k==0) // if k==0, the schedule doesn't include weekends
                {
                    System.out.println("Your schedule will NOT include weekends, is this correct? (y/n)");
                    yn = in.next();
                    if(yn.equals("y"))
                        BADINPUT=false;
                }
                else // if k==1, the schedule includes weekends
                {
                    System.out.println("Your schedule WILL include weekends, is this correct? (y/n)");
                    yn = in.next();
                    if(yn.equals("y"))
                        BADINPUT=false;
                }
            }
            else
                System.out.println("Inappropriate input value received.");
        }
        // End block to check if weekends are included
        in.close();
        return k; // 0. no weekends, 1. weekends
    }
    
    //--------------------------------------------------------------------------------------------------    
    //-------------HourIncs-----------------------------------------------------------------------------
    public static int changeSchHourIncrements()
    {
        // Initialize the variables and scanner here
        boolean BADINPUT=true;
        int k = 0;
        String yn = new String();
        Scanner in = new Scanner(System.in);
        
        // Block to check if schedule is in 1 hour or 1/2 hour increments
        while(BADINPUT)
        {
            System.out.println("Choose how the time will be organized:\n\t0. 1 hour increments \n\t1. 1/2 hour increments");
            k = in.nextInt();
            if(k*k == k) // the only integers satisfying this are 0 and 1
            {
                if(k==0) // if k==0, the schedule is in 1 hour increments
                {
                    System.out.println("Your schedule will be in 1 hour increments, is this correct? (y/n)");
                    yn = in.next();
                    if(yn.equals("y"))
                        BADINPUT=false;
                }
                else // if k==1, the schedule is in 1/2 hour increments
                {
                    System.out.println("Your schedule will be in 1/2 hour increments, is this correct? (y/n)");
                    yn = in.next();
                    if(yn.equals("y"))
                        BADINPUT=false;
                }
            }
            else
                System.out.println("Inappropriate input value received.");
        }
        // End block to check if schedule is in 1 hour or 1/2 hour increments
        in.close();
        return k; // 0. 1 hour increments, 1. 1/2 hour increments
    }
    
    //--------------------------------------------------------------------------------------------------
    //-------------BuildSch-----------------------------------------------------------------------------
    public static String[][] buildSchedule(int weekends, int increments)
    {
        // Initialize the variables and scanner here
        boolean cont = true;
        String yn = new String();
        
        String days = new String();
        String startT = new String();
        boolean startHalf = false;
        String endT = new String();
        boolean endHalf = false;
        String commitment = new String();
        
        boolean[] bdays = {false,false,false,false,false,false,false};
        Scanner in = new Scanner(System.in);
        String[] SCH_DAYS = {"Su","M","Tu","W","Th","F","Sa"};
        String[][] scheduleContent = new String [5+2*weekends][24*(1+increments)];
        
        while(cont)
        {
            System.out.println("Please enter the days of the week of the commitment (Enter a string containing Su, M, Tu, W, Th, F, or Sa)");
            days = in.nextLine();
            System.out.println("Please enter the starting hour of the commitment. (Give an hour in military time)");
            startT = in.nextLine();
            if(increments==1)
            {
                System.out.println("Is this a half-hour? (y/n)");
                startHalf = in.nextLine().equals("y");
            }
            System.out.println("Please enter the ending hour of the commitment. (Give an hour in military time)");
            endT = in.nextLine();
            if(increments==1)
            {
                System.out.println("Is this a half-hour? (y/n)");
                endHalf = in.nextLine().equals("y");
            }
            System.out.println("Please enter the title of the commitment.");
            commitment = in.nextLine();
            
            for(int i=1-weekends; i<6+weekends; i++) // Don't check SaSu if the schedule has no weekends
            {
                if(days.toLowerCase().contains(SCH_DAYS[i].toLowerCase()))
                    bdays[i] = true;
                else
                    bdays[i] = false;
            }
            
            for(int i=1-weekends;i<6+1*weekends; i++)
            {
                if(bdays[i] && increments==0)  
                {
                    for(int j=Integer.valueOf(startT); j<Integer.valueOf(endT); j++)
                        scheduleContent[i-1+weekends][j] = commitment;
                }
                else if(bdays[i] && increments==1)
                {
                    for(int j=2*Integer.valueOf(startT)+(startHalf ? 1 : 0); j<2*Integer.valueOf(endT)+(startHalf ? 1 : 0); j++)
                        scheduleContent[i-1+weekends][j] = commitment;
                }
            }
            
            System.out.println("Would you like to continue adding to the schedule? (y/n)");
            yn = in.nextLine();
            if(!yn.equals("y"))
                cont=false;
        }
        return scheduleContent;
    }
    
    //--------------------------------------------------------------------------------------------------
    //-------------WriteSch-----------------------------------------------------------------------------
    public static String writeScheduleToFile(String[][] scheduleContent, int weekends, int increments) throws FileNotFoundException
    {
        Scanner in = new Scanner(System.in);
        String fileName = new String();
        int startH;
        int endH;
        
        
        System.out.println("Please enter a file name you'd like to write your schedule table to. (Please no spaces or file extensions)");
        fileName = in.next();
        PrintStream output = new PrintStream(new File(fileName+".txt"));
        
        System.out.println("Please enter the hour that you would like your schedule to start at (Give an hour in military time)");
        startH = Integer.valueOf(in.next());
        System.out.println("Please enter the hour that you would like your schedule to end at (Give an hour in military time)");
        endH = Integer.valueOf(in.next());
        
        System.out.println("Please wait while the file is created.");
        
        // Begin writing to the file
        output.println("<!-- This file was created by Graham Harper's java HTML schedule table generator -->");
        output.println("<!-- If this program has made your HTML coding easier, please star it on Github  -->");
        output.println("<table class=\"schedule\">");
        output.println("<thead>");
        output.println("\t<tr class=\"head\">");
        output.println("\t\t<td></td>");
        if(weekends == 0)
        {
            for(String day : new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday"})
                output.println("\t\t<td>"+day+"</td>");
        }
        else
        {
            for(String day : new String[] {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"})
                output.println("\t\t<td>"+day+"</td>");
        }
        output.println("\t</tr>");
        output.println("</thead>");
        output.println("<tbody>");
        
        for(int i = startH*(1+increments); i<endH*(1+increments); i++)
        {
            if((i-startH*(1+increments))%2==0)
                output.println("\t<tr class=\"even\">");
            else
                output.println("\t<tr class=\"odd\">");
            
            if(increments==0)
                output.println("\t\t<td>"+((i-1)%12+1)+":00</td>");
            else
            {
                if(i%2==0)
                    output.println("\t\t<td>"+((i/2-1)%12+1)+":00</td>");
                else
                    output.println("\t\t<td>"+(((i-1)/2-1)%12+1)+":30</td>");
                
            }

            for(int j=0;j<(5+2*weekends);j++)
            {
                if(scheduleContent[j][i]!=null)
                    output.println("\t\t<td>"+scheduleContent[j][i]+"</td>");
                else
                    output.println("\t\t<td></td>");
            }
            
            output.println("\t</tr>");
        }
        
        output.println("</tbody>");
        output.println("</table>");
        output.close();
        
        return (fileName+".txt");
    }
}