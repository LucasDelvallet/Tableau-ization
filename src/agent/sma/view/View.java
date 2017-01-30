package agent.sma.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import agent.sma.core.Agent;
import agent.sma.core.SMA;
import agent.sma.parameter.Parameter;

public class View extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private SMA sma;
	private Parameter parameters;
	
	public View(Parameter parameters){
		this.parameters = parameters;
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(parameters.getGridSizeX()*parameters.getBoxSize(),parameters.getGridSizeY()*parameters.getBoxSize()));
	
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.GRAY);
		
		List<Agent> agentlist = sma.getAgentlist();
		for(int i = 0; i < agentlist.size(); i++){
			Agent agent = agentlist.get(i);
			String agentType = agent.getClass().getSimpleName();
			if(agentType.equals("Background")){
				g2.setColor(agent.getColor());	
				g2.fillRect(agent.getCurrentPosition().getX(), agent.getCurrentPosition().getY(), parameters.getBoxSize(), parameters.getBoxSize());
			}
		}
		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void update(Observable o, Object arg) {
		sma = (SMA)o;
		this.invalidate();
		this.repaint();
	}
}
