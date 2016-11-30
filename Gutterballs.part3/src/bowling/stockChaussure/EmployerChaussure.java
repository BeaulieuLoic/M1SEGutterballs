package bowling.stockChaussure;

import java.util.ArrayList;
import java.util.List;

import client.Client;

public class EmployerChaussure extends Thread {

	private List<PrioriteChaussureMonitor> listMonitor;
	
	public EmployerChaussure(StockChaussure sc){

		listMonitor = new ArrayList<>();
		
		listMonitor.add(new PrioriteChaussureMonitor(PrioriteChaussureMonitor.prioMax,sc, this));
		listMonitor.add(new PrioriteChaussureMonitor(PrioriteChaussureMonitor.prioInt, sc,this));
		listMonitor.add(new PrioriteChaussureMonitor(PrioriteChaussureMonitor.prioMin, sc, this));
	}
	
	public synchronized void wakeUpEmployer(){		
		notify();
	}
	
	public synchronized void attendreFinParcours(){}
	
	public synchronized void run(){
		while(true){
			try {
				//System.out.println("deb");
				wait();
				//System.out.println("fin");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i = 0; i < listMonitor.size(); i++) {
				listMonitor.get(i).switchCurrentClientChaussure();
			}
			
		}
	}

	/**
	 * pas de synchronized car utilisé qu'une fois pendants l'initialisation
	 * */
	protected List<PrioriteChaussureMonitor> getListMonitor() {
		return listMonitor;
	}
}
