package bowling.stockChaussure;

import java.util.ArrayList;
import java.util.List;

import client.Client;

public class EmployerChaussure extends Thread {

	private List<PrioriteChaussureMonitor> listMonitor;
	
	
	private StockChaussure stock;
	private boolean occuper = true;
	
	public EmployerChaussure(StockChaussure sc){
		stock = sc;
		
		listMonitor = new ArrayList<>();
		
		listMonitor.add(new PrioriteChaussureMonitor(PrioriteChaussureMonitor.prioMax,sc, this));
		listMonitor.add(new PrioriteChaussureMonitor(PrioriteChaussureMonitor.prioInt, sc,this));
		listMonitor.add(new PrioriteChaussureMonitor(PrioriteChaussureMonitor.prioMin, sc, this));		
	}
	
	public synchronized void wakeUpEmployer(){
		if (!occuper) {
			notify();
		}
		
	}
	
	
	public void run(){
		while(true){
			
			synchronized (this){
				if (listMonitor.get(PrioriteChaussureMonitor.prioMax).getNbClient() > 0
					||stock.gotChaussureBowling() && 
						(listMonitor.get(PrioriteChaussureMonitor.prioInt).getNbClient()>0 
						|| listMonitor.get(PrioriteChaussureMonitor.prioMin).getNbClient()>0)) {
				}else{
					try {
						occuper = false;
						wait();
						occuper = true;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
			
			
			listMonitor.get(PrioriteChaussureMonitor.prioMax).switchCurrentClientChaussure();
			if (stock.gotChaussureBowling()){
				listMonitor.get(PrioriteChaussureMonitor.prioInt).switchCurrentClientChaussure();
				if (stock.gotChaussureBowling()) {
					//switchCurrentClientChaussure sur le monitor prioMin va chaussé 1 client au maximum
					listMonitor.get(PrioriteChaussureMonitor.prioMin).switchCurrentClientChaussure();
				}
			}
		}
	}

	/**
	 * pas de synchronized car utilis� qu'une fois pendants l'initialisation
	 * */
	protected List<PrioriteChaussureMonitor> getListMonitor() {
		return listMonitor;
	}
	

}
