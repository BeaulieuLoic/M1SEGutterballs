package bowling;

import java.util.ArrayList;
import java.util.List;

public class Bowling extends Thread{

	private SalleDanse salleDanse;
	private List<PisteJeu> listPisteJeu;
	
	public Bowling(SalleDanse sd){
		salleDanse=sd;
		listPisteJeu = new ArrayList<>();
	}
	
	//pas de synchronized car utilisé uniquement pendant l'initialisation des objets
	public void addPiste(PisteJeu pj){
		listPisteJeu.add(pj);
	}
	
	public synchronized PisteJeu getPisteLibre(){
		for (PisteJeu pisteJeu : listPisteJeu) {
			if (!pisteJeu.estOccupe()) {
				return pisteJeu;
			}
		}
		return null;
	}
	
	public synchronized void nouvellePisteDispo(){
		notify();//il n'y à qu'un seul bowling à réveiller
	}
	
	public synchronized void attendreNotifPisteLibre(){
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public synchronized String getStat(){
		String aRetourner = "";
		int acc = 0;
		for (PisteJeu pisteJeu : listPisteJeu) {
			aRetourner += pisteJeu+"\n";
			acc += pisteJeu.getNbPartieJouer();
		}
		
		aRetourner += "Nombre total de partie jouées : "+ acc + "\n";
		return aRetourner ;
	}
	
	public void run() {
		while(true){
			attendreNotifPisteLibre();
			salleDanse.nouvellePisteDispo();			
		}	
	}
	
}
