package part1;

import java.util.ArrayList;
import java.util.List;

public class Bowling {

	private SalleDanse salleDanse;
	private List<PisteJeu> listPisteJeu;
	
	public Bowling(SalleDanse sd){
		salleDanse=sd;
		listPisteJeu = new ArrayList<>();
	}
	
	public void addPiste(PisteJeu pj){
		listPisteJeu.add(pj);
	}
	
	public PisteJeu getPisteLibre(){
		for (PisteJeu pisteJeu : listPisteJeu) {
			if (!pisteJeu.estOccupe()) {
				return pisteJeu;
			}
		}
		return null;//faire dormir le bowling si pas de piste libre et réveiller quand une piste devient libre
		
	}
	
}
