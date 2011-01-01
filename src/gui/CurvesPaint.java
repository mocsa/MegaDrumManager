package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class CurvesPaint extends JPanel {
	   private Color lineColor = Color.BLACK;
	   private Color bgColor = Color.WHITE;
	   private Color gridColor = Color.LIGHT_GRAY;
	   private Color tickColor = Color.BLACK;
	   private Color labelsColor = Color.BLACK;
	   private Color hookColor = Color.RED;
	   private Font labelsFont = new Font("Tahoma", Font.PLAIN, 9);
	   private static final int xShift = 30;
	   private static final int yShift = 4;
	   public int [] yValues = {2, 32, 64, 96, 128, 160, 192, 224, 255};
	   private int xPos;
	   private int posId;
	   private boolean posCaptured;
	   private int yPos;
	   

	/**
	 * Create the panel.
	 */
	public CurvesPaint() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if (posCaptured) {
					yPos = arg0.getY();
					updateYvalues(yPos);
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// Capture initial position
				posCaptured = false;
				xPos = arg0.getX();
				yPos = arg0.getY();
				if ((xPos > xShift) && (xPos < (xShift + 256))) {
					posId = (xPos - xShift + 15)/32;
					posCaptured = true;
					updateYvalues(yPos);
				}
			}
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				posCaptured = false;
//				// Capture final position
//			}
		});
		setBackground(UIManager.getColor("Label.background"));
		setPreferredSize(new Dimension(300, 300));
	}

	private void updateYvalues(int y) {
		int yValue;
		
		if ((y > yShift) && (y < (yShift + 256))) {
			yValue = 256 - (y - yShift);
			if (yValue < 2) {
				yValue = 2;
			}
			if (yValue > 255) {
				yValue = 255;
			}
			yValues[posId] = yValue;
			repaint();
		}		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2); // paints background
		g2.setColor(bgColor);
		g2.fillRect(xShift, yShift, 256, 256);
		g2.setStroke(new BasicStroke(1));
		for (int i = 0; i < 9; i++) {
			g2.setColor(gridColor);
			g2.drawLine((i*32) + xShift, yShift, (i*32) + xShift, yShift + 256);
			g2.drawLine(xShift, (i*32) + yShift, xShift + 256, (i*32) + yShift);
			g2.setColor(tickColor);
			g2.drawLine((i*32) + xShift, yShift + 256, (i*32) + xShift, yShift + 256 + 4);
			g2.drawLine(xShift - 4, (i*32) + yShift, xShift, (i*32) + yShift);
			g2.setColor(labelsColor);
			g2.setFont(labelsFont);
			g2.drawString("P" + ((Integer)(i+1)).toString(), (i*32) + xShift - 5, yShift + 256 + 12);
			if (i > 0) {
				if (i < 8) {
					g2.drawString(((Integer)(256-i*32)).toString(), xShift - 20, (i*32) + yShift + 4);					
				} else {
					g2.drawString(((Integer)(2)).toString(), xShift - 20, (i*32) + yShift + 4);
				}
			} else {
				g2.drawString(((Integer)(255)).toString(), xShift - 20, (i*32) + yShift + 4);					
			}
		}
		for (int i = 0; i < 9; i++) {
			g2.setStroke(new BasicStroke(2));
			g2.setColor(lineColor);
			if (i < 8 ) {
				g2.drawLine(xShift + (i*32), 256 -yValues[i] + yShift,xShift + ((i+1)*32), 256 -yValues[i + 1] + yShift);
			}
			g2.setStroke(new BasicStroke(1));
			g2.setColor(hookColor);			
			g2.drawOval(xShift + (i*32) - 3, 256 -yValues[i] + yShift - 3, 6, 6);
		}
	}

}
