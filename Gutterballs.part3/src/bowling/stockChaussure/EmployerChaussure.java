package bowling.stockChaussure;

import java.util.ArrayList;
import java.util.List;

public class EmployerChaussure extends Thread {

	List<PrioriteChaussureMonitor> listMonitor;
	
	public EmployerChaussure(StockChaussure sc){

		listMonitor = new ArrayList<>();
		
		listMonitor.add(new PrioriteChaussureMonitor(PrioriteChaussureMonitor.prioMin, sc,this));
		// les autre monitor poss�de qu'un seul client
		listMonitor.add(new PrioriteChaussureMonitor(1, sc,this));
		
		// PrioriteChaussureBowling contient une liste de client car leurs priorit� est la plus �lev� et elle ne peut pas chang�
		listMonitor.add(new PrioriteChaussureListClient(PrioriteChaussureMonitor.prioMax,sc,this));
	}
	
	public synchronized void wakeUpEmployer(){
		notify();
	}
	
	public synchronized void attendreFinParcours(){}
	
	public synchronized void run(){
		while(true){
			try {
				//System.out.println("deb");
				wait();// attendre client
				//System.out.println("fin");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i = listMonitor.size()-1; i >=0 ; i--) {
				//switchChaussureClientAct renvois false si le client � demand� une chaussure de bowling et qu'il n'y en � plus
				if (!listMonitor.get(i).switchChaussureClientAct()) {
					// on arrete d'essayer de chauss� les clients de ce monitor (et des suivant qui demande aussi des chaussure de bowling) car il n'y � plus de chaussure de bowling
					i = 0;
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
