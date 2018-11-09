package ca.mcgill.ecse420;



import java.util.Scanner;


public class example_testing {



  // this is the main class testing mutual exclusion

  public static void main(String[] args) {


  //declearing the  numbers to count
    int n = 10;



    String s1 = "bakery";
    String s2 = "filter";


    // create class to generate volatile object
    class testing {
      public volatile int k = 0;
    }
    final testing v = new testing();


    Scanner scanner = new Scanner(System.in);
    System.out.print("what lock test you want to perform? plz enter bakery or filter ");

    String test = scanner.nextLine();



    if (test.equals(s1)) {



      Bakery_lock b = new Bakery_lock(n);


      Runnable r = () -> {

        b.lock();
        try {
          v.k++; // value calculate



        } finally {

          b.unlock();



        }



        System.out.println("value of real k = " + v.k);

      };

      System.out
          .println("Bakery Lock algorithm to counting " + n + " we should end up with value 10");

      for (int i = 0; i < n; i++) {
        new Thread(r).start();



        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {

          e.printStackTrace();
        }


      }

      System.out.println(" program finished");

    } else if (test.equals(s2)) {


      Runnable r = () -> {


        Filter_lock l = new Filter_lock(n);

        l.lock();
        try {
          v.k++; // value calculate

        } finally {
          l.unlock();

        


        }



        System.out.println("value of real k = " + v.k);
      };

      System.out.println(
          "Filter Lock algorithm counting " + n + " threads...  we should end up with value 10");

      for (int i = 0; i < n; i++) {
        new Thread(r).start();
        try {
          Thread.sleep(20);
        } catch (InterruptedException e) {

          e.printStackTrace();
        }

      }

      System.out.println("program finished");



    } else {


      System.out.println("wrong input");


    }

  }


}
