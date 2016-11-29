package bowling.stockChaussure;

import java.util.ArrayList;
import java.util.List;

public class EmployerChaussure extends Thread {

	List<PrioriteChaussureMonitor> listMonitor;
	
	public EmployerChaussure(StockChaussure sc){

		listMonitor = new ArrayList<>();
		
		listMonitor.add(new PrioriteChaussureMonitor(PrioriteChaussureMonitor.prioMin, sc,this));
		// les autre monitor possède qu'un seul client
		listMonitor.add(new PrioriteChaussureMonitor(1, sc,this));
		
		// PrioriteChaussureBowling contient une liste de client car leurs priorité est la plus élevé et elle ne peut pas changé
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
				//switchChaussureClientAct renvois false si le client à demandé une chaussure de bowling et qu'il n'y en à plus
				if (!listMonitor.get(i).switchChaussureClientAct()) {
					// on arrete d'essayer de chaussé les clients de ce monitor (et des suivant qui demande aussi des chaussure de bowling) car il n'y à plus de chaussure de bowling
					i = 0;
				}
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
