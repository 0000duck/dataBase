package structure;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JLabel;
import javax.swing.Timer;

public class ShowMessageLabel extends JLabel {

	public ShowMessageLabel() {
		super();
	}

	public void setTimerText(String message, int time) {
		super.setText(message);
        Timer timer = new Timer(time * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText();
            }
        });
        timer.start();
    }
	
	private void setText() {
		 super.setText("");
	}
}


