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
	
}
