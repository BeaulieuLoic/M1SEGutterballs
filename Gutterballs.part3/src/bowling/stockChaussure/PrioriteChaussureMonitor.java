package bowling.stockChaussure;

import client.Client;
import client.Groupe;

public class PrioriteChaussureMonitor {
	
	private int priorite;
	
	public static final int prioMin = 0;
	// 0 = client qui veut des chaussure bowling mais dont personne dans son groupe n'à de chaussure
	public static final int prioIntermediaire = 1;
	// 1 = client qui veut des chaussure bowling et dont une personne de son groupe en possède
	public static final int prioMax = 2;
	// 2 = client qui veut rendre ces chaussure de bowling
	
	
	public PrioriteChaussureMonitor(int prio){
		priorite = prio;
	}
	
	
	public synchronized boolean switchToChaussureBowling(Client cl){
		return false;
	}
	
	public synchronized boolean switchToChaussreVille(Client cl){
		return false;
	}

}
