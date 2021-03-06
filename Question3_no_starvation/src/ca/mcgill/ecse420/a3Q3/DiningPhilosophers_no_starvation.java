package ca.mcgill.ecse420.a3Q3;



public class DiningPhilosophers_no_starvation {
  

  public static void main(String[] args) {


    int numberOfPhilosophers = 5;

    // Initialize a object array, each cell is a Philosopher object
    Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];

    // Initialize a object array, each cell is a chop object
    Object[] chopsticks = new Object[numberOfPhilosophers];


    for (int i = 0; i < numberOfPhilosophers; i++) {
      chopsticks[i] = new Object();
    }


    for (int i = 0; i < numberOfPhilosophers; i++) { // philosopher# 1 2 3 4 5
      Object leftFork = chopsticks[i]; // 0 1 2 3 4
      Object rightFork = chopsticks[(i + 1) % numberOfPhilosophers];// 1 2 3 4 0



      // to solve the deadlock , we can let last philosopher pick take right chop first(others left
      // first)
      // this will break circular wait condition, so deadlock will not happened

      if (i == 4) {

        philosophers[i] = new Philosopher(rightFork, leftFork);
      }

      else {


        philosophers[i] = new Philosopher(leftFork, rightFork);


      }

      // create thread, also assigning name to them , ranging from 1 to 5
      Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1)); // 1 to 5

      t.start();
    }
  }


  public static class Philosopher implements Runnable {



    private Object leftchop;
    private Object rightchop;



    // Initialize philosopher object
    public Philosopher(Object leftchop, Object rightchop) {
      this.leftchop = leftchop;
      this.rightchop = rightchop;
    }



    // this method simply take input message and print them(perform action) , follow by sleep amount of time(Random) to avoid starvation
    private void doAction(String action) throws InterruptedException {

      if (action == ": Put down right chop. Back to thinking" || action == ": Put down left chop. Back to thinking") {
        
        
       //if one philosopher finish eating, he will think more time than others which is doing other action
       //this can give more chance to others who is waiting to use chop, ensuring fairness among all philosophers
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep(((int) (Math.random() * 100)));


      } else {


        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep(((int) (Math.random() * 10)));
      }
    }



    @Override
    public void run() {

      try {
        while (true) {

          // before do any action , all philosopher have to think
          doAction(": Thinking");


          // use synchronized keyword to ensure only each chop can only take by one philosopher
          // also we assume everyone take left
          synchronized (leftchop) {


            // philosopher 5 will pick right chop first follow by left ,as we Initialized,
            if (Thread.currentThread().getName().equals("Philosopher 5")) {
              
              
              doAction(": Picked up Right chop");


            } else {

              // philosopher 1-4 will pick left chop first
              
              
              if(Thread.currentThread().getName().equals("Philosopher 4")) {
                
              //to test starvation , let philosopher 4 wait more time before pick chop
              
               //and see if he ever can get eat
                
                
                try {
                  
                  Thread.sleep(3000);
                 
                
                  
                  doAction(": Picked up left chop");
                  
                  
                }catch(InterruptedException e) {
                  
                  
                  e.printStackTrace();
                  
                  
                }
                
                
                
              }
              
              else
              

              doAction(": Picked up left chop");
            }

            // same for right fork , preventing race condition
            synchronized (rightchop) {
              // eating

              if (Thread.currentThread().getName().equals("Philosopher 5")) {
                doAction(": Picked up left chop - start eating");


              } else {
                doAction(": Picked up right chop - start eating");
              }

              if (Thread.currentThread().getName().equals("Philosopher 5")) {

                doAction(": Put down left chop");

              } else

                doAction(": Put down right chop");
            }

            // Back to thinking

            if (Thread.currentThread().getName().equals("Philosopher 5")) {

              doAction(": Put down right chop. Back to thinking");

            } else
              doAction(": Put down left chop. Back to thinking");
          }
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
    }



  }

}
