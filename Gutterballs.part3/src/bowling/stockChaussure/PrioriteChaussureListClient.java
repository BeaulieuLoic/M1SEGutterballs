package bowling.stockChaussure;

import java.util.ArrayList;
import java.util.List;

import client.Client;

public class PrioriteChaussureListClient extends PrioriteChaussureMonitor {

	private List<Client> listClient;
	
	public PrioriteChaussureListClient(int p,StockChaussure sc, EmployerChaussure e) {
		super(p, sc, e);
		listClient = new ArrayList<>();
	}

	public boolean gotClient(){
		return listClient.size() != 0;
	}
	
	
	public synchronized void switchChaussure(Client cl){
		if (listClient.size()==0) {
			employe.wakeUpEmployer();
		}
		//ajoute le client dans une liste car s'il vien dans ce monitor, sa priorité ne peut pas changer, car il veut rendre ces chaussure de bowling
		listClient.add(cl);
		try {
			wait();//attend que l'employer le chausse
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//le client est forcément chaussé
	}
	
	
	public synchronized boolean switchChaussureClientAct(){
		if (!gotClient()) {
			return true;
		}
		
		boolean aRetourner = true;
		for (Client client : listClient) {
			aRetourner = aRetourner && stockChaussure.emplSwitchChaussure(client);
		}
		listClient.clear();
		notifyAll();
		return aRetourner;
	}
}
