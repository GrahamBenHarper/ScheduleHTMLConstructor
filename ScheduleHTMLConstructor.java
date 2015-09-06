import java.util.*;

public class ScheduleHTMLConstructor
{
    public static void main(String[] args)
    {
        int SCH_WEEKEND;
        int SCH_TIME;
        boolean SCH_CONTINUE;
        String[] SCH_DAYS = {"Sa","Su","M","T","W","Th","F"};

        boolean BADINPUT;
        int k = 0;
        String yn = new String();
        String days = new String();
        String startT = new String();
        String endT = new String();
        String commitment = new String();
        boolean[] bdays = new boolean [7];
        Scanner in = new Scanner(System.in);
        // System.out.println(in.delimiter());
        System.out.println("This is a program for designing an html template for a table with css styling tags built in for a schedule.");
                // Block to check if weekends are included
        BADINPUT=true;
        while(BADINPUT)
        {
            System.out.println("Describe how this schedule will look:\n\t0. Ignore weekends \n\t1. Include weekends");
            k = in.nextInt();
            if(k*k == k) // the only integers satisfying this are 0 and 1
            {
                if(k==0)
                {
                    System.out.println("Your schedule will NOT include weekends, is this correct? (y/n)");
                    yn = in.next();
                    if(yn.equals("y"))
                        BADINPUT=false;
                }
                else
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
        SCH_WEEKEND=k;
        // End block to check if weekends are included
        
        // Block to check if schedule is in 1 hour or 1/2 hour increments
        BADINPUT=true;
        while(BADINPUT)
        {
            System.out.println("Choose how the time will be organized:\n\t0. 1 hour increments \n\t1. 1 half-hour increments");
            k = in.nextInt();
            if(k*k == k) // the only integers satisfying this are 0 and 1
            {
                if(k==0)
                {
                    System.out.println("Your schedule will be in 1 hour increments, is this correct? (y/n)");
                    yn = in.next();
                    if(yn.equals("y"))
                        BADINPUT=false;
                }
                else
                {
                    System.out.println("Your schedule will be in 1 half-hour increments, is this correct? (y/n)");
                    yn = in.next();
                    if(yn.equals("y"))
                        BADINPUT=false;
                }
            }
            else
                System.out.println("Inappropriate input value received.");
        }
        // End block to check if schedule is in 1 hour or 1/2 hour increments
        SCH_TIME=k;
        
        // Initialize the schedule
        
        
        String[][] scheduleContent = new String [5+2*SCH_WEEKEND][24*(1+SCH_TIME)];
        
        
        // Block to add content to the schedule
        SCH_CONTINUE=true;
        while(SCH_CONTINUE)
        {
            System.out.println("Please enter the days of the week of the commitment (Enter a string containing Sa, Su, M, T, W, Th, or F)");
            days = in.next();
            System.out.println("Please enter the start time of the commitment. (whole or half hour increments in military time HH:MM)");
            startT = in.next();
            System.out.println("Please enter the end time of the commitment. (whole or half hour increments in military time HH:MM)");
            endT = in.next();
            System.out.println("Please enter the title of the commitment.");
            commitment = in.next();
            
            for(int i=0; i<7; i++)
            {
                if(days.toLowerCase().contains(SCH_DAYS[i].toLowerCase()))
                    bdays[i] = true;
                else
                    bdays[i] = false;
            }
            
            for(int i=0;i<7; i++)
            {
                if(bdays[i])  
                {
                    for(int j=Integer.valueOf(startT); j<Integer.valueOf(endT); j++)
                        scheduleContent[i][j] = commitment;
                }
            }
                
            System.out.println("Would you like to continue adding to the schedule? (y/n)");
            yn = in.next();
            if(!yn.equals("y"))
                SCH_CONTINUE=false;
        }
        
        System.out.println("Done");
    }
}