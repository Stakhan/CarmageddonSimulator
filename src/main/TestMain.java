package main;

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TestMain extends JFrame {

public int running; // program will not run until this is 1
Random r = new Random();
public void performtast(){
     while (running == 1) { // if running is 1, do this
try { 

int delay = randInt(4864,7834); // 336415, 783410 15 97
Robot robot = new Robot(); 
int keypress = randInt(65, 86);

Thread.sleep(delay);
robot.keyPress(keypress);

}       catch (InterruptedException ex) { 
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AWTException e) { 
e.printStackTrace(); 
} 
}
}
public static int randInt(int min, int max) {
    // returns random number
    Random rand = new Random();
int randomNum = rand.nextInt((max - min) + 1) + min;
return randomNum;
}

private JLabel label;
private JButton button;

public TestMain() {
setLayout(new FlowLayout());
label = new JLabel("Not Running");
add(label);


button = new JButton("Start");
add(button);

event f = new event();
button.addActionListener(f);
}


public class event implements ActionListener {
 @Override
    public void actionPerformed(ActionEvent f) {
        label.setText("Running");
        System.out.println("Running");
        running = 1; // changes running to 1? but doesn't start the program?
        performtast();
}
}




public static void main(String[] args) throws InterruptedException, AWTException {

TestMain gui = new TestMain();
gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
gui.setSize(180, 80);
gui.setVisible(true);
gui.setResizable(false);
gui.setTitle("Anti AFK");

}
}