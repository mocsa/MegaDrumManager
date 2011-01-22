package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class PanelMidiLevelBar extends JPanel {
	
	private Color bgColor = Color.WHITE;
	private Color fontColor = Color.BLACK;
	private int barWidth = 16;
	private int barHeight = 254;
	private Font levelFont = new Font("Tahoma", Font.PLAIN, 9);
	
	public int level = 0;
	public Color fgColor = Color.BLUE;
	/**
	 * Create the panel.
	 */
	public PanelMidiLevelBar() {
		setPreferredSize(new Dimension(barWidth, barHeight));
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2); // paints background
		g2.setColor(bgColor);
		g2.fillRect(0, 0, barWidth, barHeight);
		g2.setColor(fgColor);
		g2.fillRect(0, barHeight - (level*2), barWidth, level*2);		
		g2.setColor(fontColor);
		g2.setFont(levelFont);
		g2.drawString(((Integer)(level)).toString(), 0, barHeight/2);
//		g2.drawLine((i*32) + xShift, yShift, (i*32) + xShift, yShift + 256);
	}
	
	
}
