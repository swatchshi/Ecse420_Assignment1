package ca.mcgill.ecse420.a2;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class deadlock {
  
  private static Lock lock1 = new ReentrantLock();
  private static Lock lock2 = new ReentrantLock();
  
  
  
  
  public static void main(String[] args) {
    
    
    
    System.out.println("checking deadlock, if this message did't move off , deadlock is happend");
    deadlock sample = new deadlock();
    
    (new Thread(sample.new t1())).start();
    (new Thread(sample.new t2())).start();
    
    
    
    
    
    
    
}
  
  
  
  public class t1 implements Runnable{

    @Override
    public void run() {
      // TODO Auto-generated method stub
      
      lock1.lock();
      
      try {
        
        Thread.sleep(3000);
        lock2.lock();
        System.out.println("no deadlock 1");
        lock2.unlock();
        
        
        
        
      }catch(InterruptedException e) {
        
        
        e.printStackTrace();
        
        
      }
      
      lock1.unlock();
      
      
      
    }
    
    
    
    
    
    
    
  }
  
  
  public class t2 implements Runnable{

    @Override
    public void run() {
      // TODO Auto-generated method stub
      
      
      
      lock2.lock();
      
      try {
        
        Thread.sleep(3000);
        lock1.lock();
        System.out.println("no deadlock 2");
        lock1.unlock();
        
        
        
       
        
      }catch(InterruptedException e) {
        
        
        e.printStackTrace();
        
        
      }
      
      lock2.unlock();
      
      
      
    }
      
    }
    
    
    
    
    
    
  }
  
  
  
  
  
  
  
  
  
  
  
  


