package ca.mcgill.ecse420;


public class Filter_lock implements Lock {



  private int[] level;
  private int[] victim;
  private int n;


  public Filter_lock(int n) {

    this.n = n;


    level = new int[n];

    victim = new int[n];

    for (int i = 0; i < n; i++) {

      level[i] = 0;



    }

  }



  public void lock() {

    int index = ThreadID.get();


    for (int i = 1; i < n; i++) { //attempt level i

      level[index] = i;
      victim[i] = index;

      boolean flag = true;

      while (flag) {

        
        
        
        flag = false;

        for (int k = 1; k < n; k++) {
          
          
        //check the each level and number of victims
          if ((k != index) && (level[k] >= i) && victim[i] == index) {
            
            //back to while and wait
            flag = true;
            break;


          }


        }



      }



    }



  }



  @Override
  public void unlock() {

    int me = ThreadID.get();

    level[me] = 0;


  }



}
