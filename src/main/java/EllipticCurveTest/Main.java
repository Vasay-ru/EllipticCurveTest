package EllipticCurveTest;

import org.web3j.crypto.Credentials;

/**
 *
 * @author vasay
 */
public class Main {
    
    private static long millionsTotal = 10000000;
    private static long millions = -1;
    
    synchronized private static long getMillions(String threadName){
        System.out.println(threadName + " take new million at " + System.currentTimeMillis() );
        millions++;
        return millions;
    }
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }        
        
        int iThreadSize = 16;
        long startTime = System.currentTimeMillis();
        
        Thread[] TCreate = new Thread[iThreadSize];
        
        for (int i = 0; i < TCreate.length; i++) {

            TCreate[i] = new Thread(new Runnable() {
                public void run() {
                  Credentials credentials;
                  for(;;){
                    long currMillions = getMillions( Thread.currentThread().getName())*100000;
                    if(currMillions>=millionsTotal)
                        break;
                    for(int j=0;j<100000;j++)
                      credentials = Credentials.create(String.format("%064x", (currMillions+j)));
                  }
                  
                }
            });
            TCreate[i].setName("Thread "+i);
            TCreate[i].start();
        }

        for (int j = 0; j < TCreate.length; j++)
            while (TCreate[j].isAlive())
                 Thread.sleep(1000);
        
        System.out.println("The End! " + (System.currentTimeMillis()-startTime) );

    }
    
}
