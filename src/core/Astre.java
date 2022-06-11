package core;

import java.util.Objects;

public class Astre {
	private double masse;//kg
	private double taille;//m
	public Point position;//m
	public Point vecteur_vitesse;
	
	public static final double _G = 6.67408E-11;
	
	public Astre(double masse, double taille, Point position) {
		this(masse, taille, position, new Point(0, 0));
	}
	
	public Astre(double masse, double taille, Point position, Point v_initial) {
		this.masse = masse;
		this.taille = taille;
		this.position = Objects.requireNonNull(position);
		this.vecteur_vitesse = Objects.requireNonNull(v_initial);
	}
	
	public double taille () {
		return taille;
	}
	
	@Override
	public String toString() {
		return /*masse + "_" +*/ position.toString() ;//+ v_vitesse.toString() ;//+ "_" + v_force;
	}
	
	public void gravitation (Astre astre) {//application de la loi universelle de la gravitation
		Objects.requireNonNull(astre);
		if(collision(astre))
			return;
		var d_pow_2 = position.distancePow2From(astre.position);
		var p_unit =  astre.position.sub(position).mul(_G / (d_pow_2 * Math.sqrt(d_pow_2)));
		vecteur_vitesse = vecteur_vitesse.add(p_unit.mul(astre.masse));
		astre.vecteur_vitesse = astre.vecteur_vitesse.add(p_unit.mul(-masse));
	}
	
	public void deplacement (double temps) {
		position = position.add(vecteur_vitesse.mul(temps));
	}
	
	public Boolean collision (Astre astre) {
		if (position.distanceFrom(astre.position) <= (taille + astre.taille) / 2) {
			double e = .9;//.9999; //coefficient de resitution de 0 a 1
			var mava_mbvb = vecteur_vitesse.mul(masse).add(astre.vecteur_vitesse.mul(astre.masse));
			var v_a = vecteur_vitesse.sub(astre.vecteur_vitesse).mul(e * astre.masse).add(mava_mbvb).div(masse + astre.masse);
			astre.vecteur_vitesse = astre.vecteur_vitesse.sub(vecteur_vitesse).mul(e * masse).add(mava_mbvb).div(masse + astre.masse);
			vecteur_vitesse = v_a;
			return true;
		}
		return false;
	}
}
