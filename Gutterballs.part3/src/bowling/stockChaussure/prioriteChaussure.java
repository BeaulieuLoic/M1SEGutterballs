package bowling.stockChaussure;

import client.Client;

public class prioriteChaussure {
	
	private int priorite = 0;
	
	public prioriteChaussure(int prio){
		priorite = prio;
	}
	
	
	public synchronized boolean switchToChaussureBowling(Client cl){
		return false;
	}
	
	public synchronized boolean switchToChaussreVille(Client cl){
		return false;
	}

}
