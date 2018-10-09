package ca.mcgill.ecse420.a3Q1;


//this code will generate deadlock
public class DiningPhilosophers_deadlock {

  public static void main(String[] args) {


    int numberOfPhilosophers = 5;
    Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];


    Object[] chopsticks = new Object[numberOfPhilosophers];


    for (int i = 0; i < numberOfPhilosophers; i++) {
      chopsticks[i] = new Object();
    }


    for (int i = 0; i < philosophers.length; i++) {     //philosopher# 1 2 3 4 5
      Object leftFork = chopsticks[i];                              // 0 1 2 3 4
      Object rightFork = chopsticks[(i + 1) % numberOfPhilosophers];// 1 2 3 4 0

      philosophers[i] = new Philosopher(leftFork, rightFork);

      Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1)); // 1 to 5
      t.start();
    }
  }

  public static class Philosopher implements Runnable {



    private Object leftFork;
    private Object rightFork;

    public Philosopher(Object leftFork, Object rightFork) {
      this.leftFork = leftFork;
      this.rightFork = rightFork;
    }


    private void doAction(String action) throws InterruptedException {
      System.out.println(Thread.currentThread().getName() + " " + action);
      Thread.sleep(((int) (Math.random() * 10)));
    }



    @Override
    public void run() {

      try {
        while (true) {

          // before do any action , all philosopher have to think
          doAction(": Thinking");
          synchronized (leftFork) {
            doAction(": Picked up left fork");
            synchronized (rightFork) {
              // eating
              doAction(": Picked up right fork - eating");

              doAction(": Put down right fork");
            }

            // Back to thinking
            doAction(": Put down left fork. Back to thinking");
          }
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
    }



  }


}


