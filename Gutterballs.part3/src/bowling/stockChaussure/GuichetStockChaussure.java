package bowling.stockChaussure;

import java.util.List;

import client.Chaussure;
import client.Client;

public class GuichetStockChaussure {
	List<PrioriteChaussureMonitor> listMonitor;
	EmployerChaussure employe;
	
	private boolean guichetOuvert;
	
	public GuichetStockChaussure(EmployerChaussure e){
		listMonitor = e.getListMonitor();
		employe = e;
		guichetOuvert = true;
	}
	
	public synchronized void fermerGuichet(){
		guichetOuvert = false;
	}
	
	public synchronized void ouvrirGuichet(){
		guichetOuvert = true;
		notifyAll();
	}
	
	/**
	 *
	 * */
	public void switchChaussure(Client cl){
		Chaussure chaussureAvant = cl.getChaussure();
		while(chaussureAvant == cl.getChaussure()){
			listMonitor.get(cl.getPriorite()).switchChaussure(cl);
		}
	}
	
	
}
