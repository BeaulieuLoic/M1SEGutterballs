package bowling.stockChaussure;

import java.util.List;

import client.Chaussure;
import client.Client;

public class GuichetStockChaussure {
	List<PrioriteChaussureMonitor> listMonitor;
	EmployerChaussure employe;
	
	public GuichetStockChaussure(EmployerChaussure e){
		listMonitor = e.getListMonitor();
		employe = e;
	}
	

	public synchronized void switchChaussure(Client cl){
		Chaussure chaussureAvant = cl.getChaussure();
		//switchChaussure renvoi true si le client a changer de chaussure
		while(chaussureAvant == cl.getChaussure()){
			employe.attendreFinParcours();
			listMonitor.get(cl.getPriorite()).switchChaussure(cl);
		}
	}
	
	
}
