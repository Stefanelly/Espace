package visual;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import core.Astre;
import core.Espace;
import core.Point;

public final class Espace2D {
	JFrame frame = new JFrame();//Barre qui gene 0, 0;
	double echelle = 1;
	
	public Espace2D(int width, int height, double echelle) {//metre / pixel
		frame.setSize(width, height);
		frame.setTitle("Space");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.createBufferStrategy(2);
		this.echelle = 1/echelle;
		//frame.getToolkit().sync();
	}
	
	/**
	 * dessine un espace
	 * @param espace
	 */
	public void draw (Espace espace) {//BufferStrategy de l'exemple d'oracle.com
		int n = 10;
		BufferStrategy strategy = null;
		while (null == strategy && 0 != --n)
			strategy = frame.getBufferStrategy();
		if(0 == n)return;
		do {
			do {
				Graphics2D g2D = (Graphics2D)strategy.getDrawGraphics();
				if(null == g2D)
					return;
				g2D.clearRect(0, 0, frame.getWidth(), frame.getHeight());
				g2D.setColor(Color.BLACK);
				
				espace.astres().forEach(
						astre -> {
							double taille = astre.taille() * echelle;
							double x = astre.position.x() * echelle - taille / 2;
							double y = astre.position.y() * echelle - taille / 2;
							//System.out.println(x + " " + y + " " + taille);
							if (x > -taille && y > -taille && x <= frame.getWidth() && y <= frame.getHeight())
								g2D.fill(new Ellipse2D.Double(x, y, taille, taille));
						}
						);
				g2D.dispose();
				
			} while (strategy.contentsRestored());
			
			strategy.show();
			
		} while (strategy.contentsLost());
		
		Toolkit.getDefaultToolkit().sync();//sync pour ne pas lag
		
	}
	
	public static void main(String args[]) throws InterruptedException {
		long time_wait = 1000/30;
		
		var screen = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screen.getWidth();
		double height = screen.getHeight();
		
		Espace espace = new Espace(5E+2);
		espace.addAstre(new Astre(6.39E+23, 3389.5E+3 * 20, new Point(2E+9, 3E+9), new Point(1.5E+5, -2E+5)));//mars
		espace.addAstre(new Astre(5.972E+24, 6371E+3 * 20, new Point(8E+9, 4E+9), new Point(1E+4, 3E+5)));//terre
		espace.addAstre(new Astre(7.6E+22, 1737.4E+3 * 20, new Point(8E+9, 3.9E+9), new Point(-2E+5, -3E+5)));//lune
		espace.addAstre(new Astre(5.972E+24, 6371E+3 * 20, new Point(6E+9, 3.9E+9), new Point(-3E+5, 3E+5)));//terre
		espace.addAstre(new Astre(1.989E+30, 696340E+3, new Point(6E+9, 5E+9), new Point(-1E+5, 2E+5)));//soleil
		espace.addAstre(new Astre(1.989E+30, 696340E+3, new Point(5E+9, 4E+9), new Point(1E+5, -2E+5)));//soleil	
				
		var espace2d = new Espace2D((int)width, (int)height, 10000000);
		
		Toolkit.getDefaultToolkit().sync();
		
		while(true) {
			espace2d.draw(espace);
			Thread.sleep(time_wait);
			espace.update();
		}
	}
}
