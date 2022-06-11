package core;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Espace {
	private ArrayList<Astre> astres = new ArrayList<>();// pas private pour l'instant
	final double temps;

	/**
	 * Espace contient des astres.
	 * 
	 * @param temps entre les mises a jour
	 */
	public Espace(double temps) {
		this.temps = temps;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Astre> astres() {
		return (ArrayList<Astre>) astres.clone();
	}
	
	/**
	 * ajoute un astre dans l'espace
	 * 
	 * @param astre
	 */
	public void addAstre(Astre astre) {
		astres.add(Objects.requireNonNull(astre));
	}

	/**
	 * Met a jour l'espace (effectue les deplacements)
	 */
	public void update() {
		var n = temps;
		while (0 < --n) {
			for (var i = 0; i < astres.size(); ++i) {
				for (var j = i + 1; j < astres.size(); ++j) {
					astres.get(i).gravitation(astres.get(j));
				}
			}
			astres.forEach(astre -> astre.deplacement(1));//deplacement d'une seconde
		}
	}

	@Override
	public String toString() {
		return astres.stream().map(Astre::toString).collect(Collectors.joining(";", "", ""));
	}
}
