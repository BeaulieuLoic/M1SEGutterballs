package part1.thread;

import java.util.ArrayList;
import java.util.List;

import part1.PisteJeu;
import part1.monitor.SalleDanse;

public class Bowling extends Thread{

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
	
	public void nouvellePisteDispo(){
		salleDanse.nouvellePisteDispo();
	}
	
	public void run() {
		while(true){

		}	
	}
	
}
