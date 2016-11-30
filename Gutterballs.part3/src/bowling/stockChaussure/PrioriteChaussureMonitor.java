package bowling.stockChaussure;

import java.util.LinkedList;
import java.util.List;

import client.Chaussure;
import client.Client;

public class PrioriteChaussureMonitor {
	public static final int prioMax = 0;//bowling to ville
	public static final int prioInt = 1;//1 client du groupe à déja bowling
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
	
	public synchronized void switchCurrentClientChaussure(){
		
		if (prio == prioMin) {
			// lorsque la priorité est minimal, on s'occupe d'un par un des client car leurs priorité peuvent changer
			stock.emplSwitchChaussure(listeClient.get(0));
			notifyAll();
		}else{
			for (Client client : listeClient) {
				if(stock.emplSwitchChaussure(client)){
					notifyAll();
				}else{
					
					break;
				}	
			}
		}
	}
}
