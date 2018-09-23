/* Main Graphics class for the Bursting Dots game
 *
 * Author: Eda Zhou
 */


import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GameGraphics extends JPanel{

	private ArrayList<DotG> dotsOn = new ArrayList<DotG>();
	private ArrayList<DotG> dotsExp = new ArrayList<DotG>();

	private int numExp = 0;
	private final int goal;
	private int width, height;
	private final int dotsAdd;
	private boolean firstClick = false;

	//private Boundary bounds = new Boundary(100, 200, 150, 250);

	JFrame frame = new JFrame("Burstin' Dots!");

	Timer run = new Timer(10, new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			repaint();
		}
	});

	// ctor that creates the frame, adds mouse and key listeners, and adds the dots
	public GameGraphics() {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setMinimumSize(new Dimension(400, 500));
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.getContentPane().add(this);
		frame.setVisible(true);

		width = frame.getContentPane().getWidth();
		height = frame.getContentPane().getHeight();

		setPreferredSize(new Dimension(width, height ));
		setSize(width, height);
		setBackground(Color.white);

		dotsAdd = 30;
		addDots(dotsAdd);
		goal = 15;
		//goal = dotsOn.size() - dotsOn.size()/2;

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!firstClick && e.getButton() == 1) {
					DotG add = new DotG(e.getX(), e.getY(), new Color(
							(float) Math.random(), (float) Math.random(),
							(float) Math.random(), (float) Math.random()));
					add.explode();
					dotsExp.add(add);
					repaint();
					firstClick = true;
				}
				repaint();
//				System.out.println(e.getX() + " " + e.getY());
//				System.out.println(width + " " + height);
//				System.out.println(frame.getWidth() + " " + frame.getHeight());
//				System.out.println(frame.getContentPane().getWidth() + " " + frame.getContentPane().getHeight());
			}
		});

		frame.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent ae){
				if(ae.getKeyChar() == 'r')
					reset();
			}
		});

		run.start();
	}

	// paints all the dots and checks to see if the 'normal' ones should bounce or explode
	// before moving them. If they explode, they get removed and added to the
	// exploding dots ArrayList. Also calls done() method when all dots are done exploding

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawString(numExp + " / " + goal, width - width/8, height - height/8);

		//updates counter for how many dots exploded

		for(int m = 0; m < dotsOn.size(); m++) {
		//for (DotG i : dotsOn) {
			DotG i = dotsOn.get(m);
			if (i.getX() - i.getRadius() <= 0
					|| i.getX() + i.getRadius() >= width)
				i.bounceVert();
			if (i.getY() - i.getRadius() <= 0
					|| i.getY() + i.getRadius() >= height)
				i.bounceHoriz();

			if(hitDot(i)){
				i.explode();
				dotsExp.add(i);
				dotsOn.remove(m);
				numExp++;
				m--;
			}

			// hit boundary
//			if (bounds.on(i.getX(), i.getY())){
			// }

			i.move();
			i.paint(g);
		}


		for (int m = 0; m < dotsExp.size();) {
			DotG i = dotsExp.get(m);
			if (i.getRadius() == 0) {
				dotsExp.remove(m);
			} else {
				i.paint(g);
				m++;
			}
		}

		if(firstClick && dotsExp.size() == 0)
		done(g);
	}

//	public void boundary(int x1, int x2, int y1, int y2){
//		for (DotG i : dotsOn) {
//			if(i.getX() + i.getRadius() > x1 && i.getX() - i.getRadius() < x2)
//				i.bounceVert();
//			if(i.getY() + i.getRadius() > y1 && i.getY() - i.getRadius() < y2)
//				i.bounceHoriz();
//			i.move();
//		}
//	}


	// prints out different messages on given graphics depending on how many dots exploded/if goal reached
	public void done(Graphics g) {
		g.setColor(Color.BLACK);
		if (numExp >= goal) {
			g.drawString("CONGRATS! \n press r to reset", width/4, height/2);
		}

		else if (numExp == 0)
			g.drawString("Oopsies, Mr. Marshall says to go back to the coloring book", width/6, height/2);
		else g.drawString("Awww, so close \n press r to try again", width/4, height/2);

	}

	// resets the game
	public void reset(){
		while (dotsOn.size() > 0)
			dotsOn.remove(0);
		while (dotsExp.size() > 0)
			dotsExp.remove(0);
		firstClick = false;
		numExp = 0;
		addDots(dotsAdd);
	}

	// checks if given dot had hit any of the exploding dots and returns a boolean accordingly
	public boolean hitDot(DotG i) {
		for (DotG k : dotsExp) {
			if (i.getY() + i.getRadius() > k.getY() - k.getRadius()
					&& i.getY() - i.getRadius() < k.getY() + k.getRadius()
					&& i.getX() + i.getRadius() > k.getX() - k.getRadius()
					&& i.getX() - i.getRadius() < k.getX() + k.getRadius()) {
				return true;
			}
		}
		return false;
	}

	//adds given amount of dots to panel (with random color and coordinates
	public void addDots(int x) {

		for (int i = 0; i < x; i++) {
			Random num = new Random();
			dotsOn.add(new DotG(0, 0, new Color((float) Math.random(),
					(float) Math.random(), (float) Math.random(), (float) Math
							.random())));

			dotsOn.get(i).setLocation(
					num.nextInt(width - dotsOn.get(i).getRadius()) + 1,
					num.nextInt(height - dotsOn.get(i).getRadius()) + 1);

//			while (bounds.on(dotsOn.get(i).getX() + dotsOn.get(i).getRadius(),
//					dotsOn.get(i).getY() + dotsOn.get(i).getRadius())) {
//				dotsOn.get(i).setLocation(
//						num.nextInt(width - dotsOn.get(i).getRadius()) + 1,
//						num.nextInt(height - dotsOn.get(i).getRadius()) + 1);
//			}
		}
	}

	public static void main(String[] args) {

		GameGraphics game = new GameGraphics();
	}

}
