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
