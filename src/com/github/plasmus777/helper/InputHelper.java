package com.github.plasmus777.helper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {

    //Creates a loop to ask the user for a valid String input (not null && not blank)
    public static String getValidString(String askMessage, String errorMessage, Scanner scanner){
        String value = null;

        do{
            System.out.println(askMessage);
            while(!scanner.hasNextLine()){
                System.out.println(errorMessage);
                scanner.next();
            }
            value = scanner.nextLine();


        } while (value == null || value.isBlank());

        return value;
    }

    //Creates a loop to ask the user for a valid long input (> 0)
    public static long getPositiveLong(String askMessage, String errorMessage, Scanner scanner){
        long value = 0;

        do{
            System.out.println(askMessage);
            while(!scanner.hasNextLong()){
                System.out.println(errorMessage);
                scanner.next();
            }
            value = scanner.nextLong();


        } while (value <= 0);

        return value;
    }

    //Creates a loop to ask the user for a valid float input (> 0)
    public static float getPositiveFloat(String askMessage, String errorMessage, Scanner scanner){
        float value = 0;

        do{
            System.out.println(askMessage);
            while(!scanner.hasNextFloat()){
                System.out.println(errorMessage);
                scanner.next();
            }
            value = scanner.nextFloat();


        } while (value <= 0);

        return value;
    }

    //Creates a loop to ask the user for a valid boolean input (true or false)
    public static boolean getValidBoolean(String askMessage, String errorMessage, Scanner scanner){
        boolean verified = false;
        boolean value = false;

        do{
            System.out.println(askMessage);
            while(!scanner.hasNextBoolean()){
                System.out.println(errorMessage);
                scanner.next();
            }
            value = scanner.nextBoolean();
            verified = true;

        } while (!verified);

        return value;
    }
}
