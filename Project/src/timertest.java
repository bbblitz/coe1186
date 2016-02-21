import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple demo that uses java.util.Timer to schedule a task to execute once 5
 * seconds have passed.
 */

public class timertest {
  Toolkit toolkit;

  Timer timer;

  public timertest(long milis) {
    toolkit = Toolkit.getDefaultToolkit();
    timer = new Timer();
    timer.schedule(new RemindTask(), milis);
  }

  class RemindTask extends TimerTask {
    public void run() {
      System.out.println("Time's up!");
      toolkit.beep();
      timer.cancel(); //Not necessary because we call System.exit
      //System.exit(0); //Stops the AWT thread (and everything else)
    }
  }

  public static void main(String args[]) {
    System.out.println("About to schedule task.");
    for(int i = 0; i < 100; i++)
      new timertest((long)5000);
    System.out.println("Done");
  }
}
