package ca.mcgill.ecse420;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Bakery_lock implements Lock {


  private AtomicBoolean[] flag;
  private AtomicInteger[] label;
  private int n;



  public Bakery_lock(int n) {

    this.n = n;
    flag = new AtomicBoolean[n];
    label = new AtomicInteger[n];
    for (int i = 0; i < n; i++) {
      flag[i] = new AtomicBoolean();
      label[i] = new AtomicInteger();
    }



  }



  @Override
  public void lock() {

    int i = ThreadID.get();
    flag[i].set(true);
    label[i].set(getMax(label) + 1);// set the current label index to be 1 greater than the highest
                                    // label index
    for (int k = 0; k < n; k++) {

      // check the queue status, wait if someone before this thread,
      while ((k != i) && flag[k].get()
          && ((label[k].get() < label[i].get()) || ((label[k].get() == label[i].get()) && k < i))) {
        // wait
      }
    }
  }

  @Override
  public void unlock() {

    flag[ThreadID.get()].set(false);


  }

  private int getMax(AtomicInteger[] k) {
    int max = 0;
    for (AtomicInteger i : k) {
      if (i.get() > max) {
        max = i.get();
      }
    }
    return max;
  }



}
