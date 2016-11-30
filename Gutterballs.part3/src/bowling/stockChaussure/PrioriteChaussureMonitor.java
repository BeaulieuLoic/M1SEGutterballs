package bowling.stockChaussure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import client.Chaussure;
import client.Client;

public class PrioriteChaussureMonitor {
	public static final int prioMax = 0;//bowling to ville
	public static final int prioInt = 1;//1 client du groupe � d�ja bowling
	public static final int prioMin = 2;
	
	private int prio;
	private StockChaussure stock;
	private List<Client> listeClient;
	EmployerChaussure employe;
	
	
	public PrioriteChaussureMonitor(int p, StockChaussure sc, EmployerChaussure e){
		prio = p;
		stock = sc;
		listeClient = new LinkedList<>();
		employe = e;
	}
	
	public synchronized void switchChaussure(Client cl){
		listeClient.add(cl);
		int prioAvant = cl.getPriorite();
		Chaussure chaussureAvant = cl.getChaussure();
		employe.wakeUpEmployer();		
		while(chaussureAvant == cl.getChaussure() && prioAvant == cl.getPriorite()){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		listeClient.remove(cl);
	}
	
	public synchronized int switchCurrentClientChaussure(){
		if (prio == prioMin) {
			// lorsque la priorit� est minimal, on s'occupe d'un par un des clients car la priorité des autres clients peut changer
			if(listeClient.size() > 0 && stock.emplSwitchChaussure(listeClient.get(0))){
				notifyAll();
				return 1;
			}else{
				return 0;
			}
		}else{
			int acc = 0;
			for (int i = 0; i < listeClient.size(); i++) {
				if(stock.emplSwitchChaussure(listeClient.get(i))){
					acc++;
					listeClient.remove(i);
				}else{
					break;
				}
			}
			
			
			if (acc > 0) {
				notifyAll();
			}
			return acc;
		}
	}

	public int getNbClient() {
		// TODO Auto-generated method stub
		return listeClient.size();
	}
}
