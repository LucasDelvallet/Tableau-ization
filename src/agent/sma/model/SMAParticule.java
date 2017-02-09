package agent.sma.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import agent.sma.core.Agent;
import agent.sma.core.SMA;
import agent.sma.parameter.Parameter;
import processing.CodeParser;

public class SMAParticule extends SMA {

	private int x;
	private int y;
	private boolean l;
	private boolean d;
	private boolean r;
	private boolean u;
	private int turn;

	public SMAParticule(Parameter parameters, String fileName) {
		super(parameters, fileName);
	}

	@Override
	protected void initAgent(Parameter parameters) {
		x = 0;
		y = 0;
		l = true;
		d = false;
		r = false;
		u = false;
		turn = 0;

		agentlist = new ArrayList<Agent>();

		for (int i = 0; i < environment.getWidth(); i += parameters.getBoxSize()) {
			for (int j = 0; j < environment.getHeight(); j += parameters.getBoxSize()) {
				Agent a = new Background(environment, parameters, new Position(i, j));
				agentlist.add(a);
				environment.agentsPosition[i / parameters.getBoxSize()][j / parameters.getBoxSize()] = a;
			}
		}

		CodeParser codeParser = new CodeParser();
		LinkedHashMap<String, Integer> wordsByCount = codeParser.parseFile(fileName);

		float ratioUpscale = computeRatioUpscale(wordsByCount);
		for (Entry<String, Integer> entry : wordsByCount.entrySet()) {
			int hashCode = entry.getKey().hashCode();
			String sColor = "#" + intToARGB(hashCode);

			Color color = Color.decode(sColor);

			for (int i = 0; i < (entry.getValue() * ratioUpscale); i++) {
				agentlist.add(new Particule(environment, parameters, new Position(x, y), color, hashCode,
						entry.getValue() + turn));
				checkLeft();
				checkDown();
				checkRight();
				checkUp();
			}
		}
	}

	public float computeRatioUpscale(LinkedHashMap<String, Integer> wordsByCount) {
		int total = 0;
		for (Entry<String, Integer> entry : wordsByCount.entrySet()) {
			total += entry.getValue();
		}

		if (total < environment.getHeight() / 2) {
			return (float) environment.getHeight() / 2 / total;
		}
		return 1;
	}

	public void checkLeft() {
		if (l) {
			y++;
			if (y >= environment.getHeight() - turn) {
				l = false;
				d = true;
				y--;
			}
		}
	}

	public void checkDown() {
		if (d) {
			x++;
			if (x >= environment.getWidth() - turn) {
				d = false;
				r = true;
				x--;
			}
		}
	}

	public void checkRight() {
		if (r) {
			y--;
			if (y <= turn) {
				r = false;
				u = true;
			}
		}
	}

	public void checkUp() {
		if (u) {
			x--;
			if (x <= turn + 1) {
				u = false;
				l = true;
				turn++;
			}
		}
	}

	public static String intToARGB(int i) {
		return Integer.toHexString(((i >> 16) & 0xFF)) + Integer.toHexString(((i >> 8) & 0xFF))
				+ Integer.toHexString((i & 0xFF));
	}
}
