/* Graphics and methods for a dot in the Bursting Dots game
 * 
 * Author: Eda Zhou
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class DotG {
	private int radius;
	private final int origRadius;
	private final int explodedAdd = 5;
	private int x, y;
	private int xVect, yVect;
	private Color color;

	private boolean exploded = false;
	private boolean lagged = false;

	// timer for exploding + shrinking
	Timer exploding = new Timer(100, new ActionListener() {
		public void actionPerformed(ActionEvent ae) {

			if (!exploded && radius < origRadius + explodedAdd)
				grow();

			else if (lagged) {
				exploding.setDelay(exploding.getDelay() / 15);
				lagged = false;
			}

			else if (!exploded) {
				exploding.setDelay(exploding.getDelay() * 15);
				exploded = true;
				lagged = true;
			}

			else {
				shrink();
				if (radius <= 0)
					exploding.stop();
			}

		}
	});

	// ctor for dot, randomly sets x and y vector
	public DotG(int x, int y, Color color) {
		Random num = new Random();
		this.x = x;
		this.y = y;
		this.color = color;
		this.radius = 5;
		origRadius = radius;
		this.xVect = (num.nextInt(radius / 2 - 1) + 1)
				* ((num.nextInt(2) == 0) ? -1 : 1);
		this.yVect = Math.round((float) Math.sqrt((radius / 2 * radius / 2)
				- (xVect * xVect))
				* ((num.nextInt(2) == 0) ? -1 : 1));
	}

	// paints the dots
	public void paint(Graphics g) {
		g.setColor(color);
		// if (x - radius < 0)
		// x = radius;
		// if (y - radius < 0)
		// y = radius;
		g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	// starts the timer for an exploding dot
	public void explode() {
		exploding.start();
	}

	// increases the radius by 1
	public void grow() {
		// if (radius < origRadius + explodedAdd)
		radius++;
		// if (radius == origRadius + explodedAdd){
		// exploded = true;
		// exploding.setDelay(exploding.getDelay() * 10);
		// }
	}

	// reduces the radius by 1
	public void shrink() {
		// if(radius == origRadius + explodedAdd)
		// exploding.setDelay(exploding.getDelay()/10);
		//if (radius >= 0)
			radius--;
		//else
		//	exploding.stop();
	}

	// returns radius
	public int getRadius() {
		return radius;
	}

	// returns color
	public Color getColor() {
		return color;
	}

	// returns x
	public int getX() {
		return x;
	}

	// returns y
	public int getY() {
		return y;
	}

	// returns x vector
	public int getXvect() {
		return xVect;
	}

	// returns y vector
	public int getYvect() {
		return yVect;
	}

	// sets location given x and y coord
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// sets x vector
	public void setXvect(int x) {
		this.xVect = x;
	}

	// sets y vector
	public void setYvect(int y) {
		this.yVect = y;
	}

	// multiplies y vector by -1
	public void bounceHoriz() {
		this.yVect = yVect * -1;
	}

	// multiplies x vector by -1
	public void bounceVert() {
		this.xVect = xVect * -1;
	}

	// adds x and y vector to the dot's x and y coord respectively 
	public void move() {
		this.x = x + xVect;
		this.y = y + yVect;
	}

	public static void main(String[] args) {
		DotG wee = new DotG(10, 10, new Color(2304040));
		wee.explode();
	}
}

// ___ explode(); updates radius, starts timer??

// void paint(Graphics g); paints circle/dot

