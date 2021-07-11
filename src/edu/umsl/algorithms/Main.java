package edu.umsl.algorithms;

import java.util.Random;
import java.lang.Math;
import java.util.Scanner;

public class Main {

    //Main function
    public static void main(String[] args) {
        Algorithm run = new Algorithm();
    }

}

class Algorithm {
    Random rand;                    //Creates a random number generator
    int[] polynomial;               //Used to store the value of the coefficients
    int size;                       //Used to store the size/degree of the polynomial
    double xValue;
    final int DEGREE = 100001;      //Used to seed the random number generator for degree of polynomial
    final int COEFFICIENT = 100;    //Used to seed the random number generator for each coefficient
    boolean[] userSelection;


//Constructor

    Algorithm(){
        this.rand = new Random(System.currentTimeMillis());
        this.polynomial = new int[1];
        this.size = 0;
        this.xValue = 0;
        this.userSelection = new boolean[]{true,true,true,true};
        menu();
    }

//Algorithms

    //Multiplication Algorithm with for-loop
    private void multi(){
        long execution = System.currentTimeMillis();            //Time of Start
        double total = 0;                                       //Holds the value of the polynomial as it is being solved
        double x = 1;                                           //Initializes x value to 1 so first coefficient is not multiplied by x value
        for(int n = 0; n < this.size; n++){                     //For loop to iterate over array containing coefficients of polynomial
            total += this.polynomial[n] * x;                    //Each iteration adds the polynomial multiplied time the x value
            x*= this.xValue;                                    //Stores the value of x, each iteration multiplies the x value times x^n(n being
                                                                // the current position in the array)
        }
        execution = System.currentTimeMillis() - execution;     //Calculates the Run Time
        System.out.println("\tResult = " + total);               //Outputs the Calculation of the Polynomial
        System.out.println("\tExecution Time: " + execution + " ms");     //Outputs the Run time
    }

    //Power Function Algorithm
    private void power(){
        long execution = System.currentTimeMillis();            //Time of Start
        double total = 0;                                       //Holds the value of the polynomial as it is being solved
        double x = 0;                                              //Initializes x value to 0 so first coefficient is not multiplied by the x value
        for(int n : this.polynomial){                           //For loop to iterate over array containing coefficients of polynomial
            total += n * Math.pow(this.xValue, x);              //Each iteration adds the coefficient multiplied times x^n (n being the current
                                                                // position in the array)
            x++;                                                //Increments the exponent for each array iteration
        }
        execution = System.currentTimeMillis() - execution;     //Calculates the Run Time
        System.out.println("\tResult = " + total);               //Outputs the Calculation of the Polynomial
        System.out.println("\tExecution Time: " + execution + " ms");     //Outputs the Run time
    }

    //Horner's Rule Algorithm

        //Horner's Rule: Polynomial → a + bx + cx^2 + ... +yx^(n-1) zx^n == a + x(b + x(c + ... + x(y + zx)))
        // Horner's Rule greatly reduces the number of multiplications needed for a problem
        // This algorithm is easiest written in reverse starting with z*x then adding y followed with multiplication by x
        // total = z → total = z*x → total = z*x + y → total = (z*x + y)*x ...

    private void horner(){
        long execution = System.currentTimeMillis();            //Time of Start
        double total = this.polynomial[this.size-1];            //Holds the value of the polynomial as it is being solved, is initialized
                                                                // to the last coefficient in the array so that horners rule can be applied
        for(int n = this.size-2; n >= 0; n--){                  //For loop to iterate over array containing coefficients of polynomial, iterates array
                                                                // in reverse so horners rule can be applied
            total *= this.xValue;                               //Multiplies current total time value of x
            total += this.polynomial[n];                        //Adds the proceeding coefficient.
        }

        execution = System.currentTimeMillis() - execution;     //Calculates the Run Time
        System.out.println("\tResult = " + total);               //Outputs the Calculation of the Polynomial
        System.out.println("\tExecution Time: " + execution + " ms");     //Outputs the Run time

    }

//Random Numbers
    private int randNums(int bound){
        return this.rand.nextInt(bound);
    }

//Display Options
    private void display(){
        System.out.println("\nPlease select the method you would like to use to evaluate the Polynomial");
        if(this.userSelection[1]){
            System.out.println("\nPress [ 1 ] to evaluate using multiplication");
        }
        if(this.userSelection[2]) {
            System.out.println("\nPress [ 2 ] to evaluate using Math.pow()");
        }
        if(this.userSelection[3]) {
            System.out.println("\nPress [ 3 ] to evaluate using Horner's Rule");
        }
        System.out.println("\nPress [ 0 ] to exit the application");

        System.out.print("\nInput: ");
    }

//Menu
    private void menu(){
        initializePolynomial();
        System.out.println("Degree of Polynomial: " + this.size);
        int x = -1;
        while( x != 0) {
            if(userSelection[1] || userSelection[2] || userSelection[3]) {
                display();
                x = userInput();
                switch (x) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("\nMultiplication");
                        multi();
                        break;
                    case 2:
                        System.out.println("\nMath.pow");
                        power();
                        break;
                    case 3:
                        System.out.println("\nHorner's Rule");
                        horner();
                        break;
                }
            }else{
                x=0;
            }
        }
        System.out.println("Bye");
    }

//User Input
    private int userInput(){
        int x = -1;
        boolean badInput = true;
        while(badInput){
            Scanner input = new Scanner(System.in);
            try{
                x = input.nextInt();
                if(x>3 || x<0){
                    System.out.println("Please Input a value between 0 and 3\n");
                }else{
                    if(this.userSelection[x]){
                        badInput = false;
                        userSelection[x] = false;
                    }else{
                        System.out.println("This method has already been used to evaluate the polynomial");
                        System.out.println("Please select another method");
                        display();
                    }
                }
            }catch(Exception ex){
                System.out.println("Please input a valid integer between 0 and 3\n");
                display();
            }
        }
        return x;
    }

//Generate Polynomial
    private void initializePolynomial(){
         this.size = randNums(DEGREE) + 100000;
         this.polynomial = new int [this.size];
         for(int i = 0; i < size; i++){
             this.polynomial[i] = randNums(COEFFICIENT) + 1;
         }
         this.xValue = (double)randNums(11) / 10;
    }
}


/*
→ Compare three different algorithms that are used to evaluate polynomials.

    → Goal: understand importance of the efficiency of an algorithm.

    → First two algorithms are based on the straightforward method
        → they use different ways to evaluate the monomials.

    → The third algorithm uses the Horner’s Rule to evaluate polynomials.

    → Use random number generating functions to create large
      polynomials to be evaluated by these three algorithms, and compare their response times.
*/
/*
Requirements:
1. (Format of your program)
    → Java(preferred), C/C++, or Python
    → Write one program to implement all three programs
    → Place program in one folder named BrianBredahlProject1
    → Zip folder to submit
    → Put program in package: edu.umsl.algorithms
*/
/*
2. (Generate the polynomial coefficients and the point to be evaluated)
    → Coefficients: integer between 1 and 100
    → store coefficients in integer array
    → size of array determined by degree of polynomial
        → good choice 100,000-200,000
    → value of variable x: double 0.0-1.0
*/
/*
3. (User’s interface)
    → Create simple user interface
    → Display simple menu
    → Program asks user to choose one of three methods to evaluate the polynomial
    → Use 1, 2, or 3 to represent the selected method
    → Testing in a while loop so user can test all methods in one execution
    → Use 0 to exit
*/
/*
4. (Monomial evaluation)
    → First algorithm uses multiplications in a for-loop
    → Second algorithm uses a power function (such as Math.pow(x, k))
*/
/*
5. (Horner’s Rule)
    → Use Horner's Rule to evaluate entire polynomial
*/
/*
6. (Output)
    → Print degree number of polynomial
    → Display menu
    → Display results
    → Display execution time in milliseconds
    → We expect all three algorithms to produce the same evaluation result
    → When user enters 0 to terminate, display "Bye!"

*/
/*
Requirements:
1. (Format of your program)

    It is desirable to use Java as the programming language. But if you use C/C++
    or Python, it is still acceptable. Try to use one program to implement all three
    algorithms and do testing in the same program. When you submit your project,
    place your program or programs in one folder with your name and Proj1, P1,
    Project1, etc. as the folder name, then zip your folder and submit it.

    If you use Java, try to put your program in the package: edu.umsl.algorithms.
    In this way, it is more convenient for me to run your programs.


2. (Generate the polynomial coefficients and the point to be evaluated)

    Each coefficient should be an integer between 1 and 100. You use an integer array
    to store all the coefficients generated, and the size of the array is determined by the
    degree of the polynomial. Based on my experiments, a good choice of the degree
    number should be between 100,000 and 200,000.

    The value of the variable x should be a double between 0.0 and 1.0, exclusive.
    The reason to choose the value in this range is simple: We can easily avoid the
    overflow problem. If the value is 0.0 or 1.0, the evaluation time is very fast, and
    it does not reflect the general case. When you detect if this case happens, call the
    1 random function another time.


3. (User’s interface)

    In order to provide the users a convenient way to test your program, you should
    create a simple user’s interface. When a user runs your program, you display a
    simple menu by printing out appropriate messages on the screen. Your program
    asks the user to choose one of the three methods to evaluate the polynomial using
    1, 2, or 3 to represent the selected method. Put your testing part in a while loop,
    so that the user can test all the methods in one execution, and use 0 for the exit code.


4. (Monomial evaluation)

    In the first two methods, we use the straightforward method to evaluate the polynomials,
    in which we need to evaluate the monomials x^k. Our first algorithm just uses direct
    multiplications in a for-loop. Our second algorithm uses a power function, such as
    Math.pow(x, k), to calculate the monomials.


5. (Horner’s Rule)

    Our third algorithm uses the Horner’s Rule to evaluate the whole polynomial without calculating
    the monomials. The main objective of this project is to see how much faster this algorithm is
    comparing with the first two simple algorithms.


6. (Output)

    Before you display the menu on the screen, you print out the degree number of the
    polynomial first, because this number only needs to be displayed once. Then for
    each algorithm, you need to display the result first, and then the execution time
    in milliseconds. We expect that all three algorithms produce the same evaluation
    result. After the user enters 0 code to terminate the program, display a (Bye!)
    message to tell the user that the whole program completes the execution.

    Any updates about this project will be posted in Canvas as announcements.
*/


