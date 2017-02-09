package agent.sma.view;

import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GUIHelper {

	private JFrame frame;

	public GUIHelper(JComponent component, String frameName) {
		frame = new JFrame(frameName);
		frame.getContentPane().add(component);
		frame.pack();
		frame.setVisible(true);

	}

	public void closeFrame() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

}