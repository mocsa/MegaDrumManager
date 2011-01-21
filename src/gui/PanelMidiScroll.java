package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class PanelMidiScroll extends JPanel {
	
	private Color bgColor = Color.WHITE;
	private BufferedImage offImage;	
	private Graphics2D g2offScreen;
	//public Color fgColor = Color.BLUE;
	
	public boolean pauseScroll = false;
	public boolean autoPause = false;
	private int runsToPause = 0;
	
	private Timer timerScroll;
	private TimerTask timerScrollTask;
	/**
	 * Create the panel.
	 */
	public PanelMidiScroll(Dimension dim) {
		setPreferredSize(dim);
		timerScrollTask = new TimerTask() {
			public void run() {
				repaint();
			}
		};
		timerScroll = new Timer();
		timerScroll.scheduleAtFixedRate(timerScrollTask, 1000, 100);
	}
	
	public void reSetSize(Dimension dim) {
		setPreferredSize(dim);
		setSize(dim);
		offImage = new BufferedImage(dim.width, dim.height,BufferedImage.TYPE_INT_RGB);
		g2offScreen = (Graphics2D) offImage.getGraphics();
		g2offScreen.setColor(bgColor);
		g2offScreen.fillRect(0, 0, dim.width, dim.height);
	}
	
	public void reSetTimer(int period) {
		timerScroll.cancel();		
		timerScroll = new Timer();
		timerScrollTask = new TimerTask() {
			public void run() {
				repaint();
			}
		};
		timerScroll.scheduleAtFixedRate(timerScrollTask, period, period);

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2); // paints background
		if (g2offScreen != null ) {
			if (!autoPause) {
				runsToPause++;
			}
			if (runsToPause > 0 ) {
				runsToPause --;
				if (!pauseScroll) {
					g2offScreen.copyArea(1, 0, this.getPreferredSize().width-1, this.getPreferredSize().height, -1, 0);
				}
			}
			g2offScreen.setColor(bgColor);
			g2offScreen.drawLine(this.getPreferredSize().width-1, 0, this.getPreferredSize().width - 1, this.getPreferredSize().height);
			g2.drawImage(offImage, null, 0, 0);
		}
	}
	
	public void showHit(int level, Color color) {
		runsToPause = this.getPreferredSize().width/4;
		g2offScreen.setColor(color);
		g2offScreen.drawLine(this.getPreferredSize().width-1, 128 - level, this.getPreferredSize().width - 1, this.getPreferredSize().height);
	}
}
