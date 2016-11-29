package bowling.stockChaussure;


import client.Client;


public class PrioriteChaussureMonitor {
	
	private int priorite;
	protected StockChaussure stockChaussure;
	
	public static final int prioMin = 0;
	// 0 = client qui veut des chaussure bowling mais dont personne dans son groupe n'� de chaussure
	public static final int prioIntermediaire = 1;
	// 1 = client qui veut des chaussure bowling et dont une personne de son groupe en poss�de
	public static final int prioMax = 2;
	// 2 = client qui veut rendre ces chaussure de bowling
	
	private Client clientAChanger;
	
	protected EmployerChaussure employe;
	
	public PrioriteChaussureMonitor(int prio, StockChaussure sc, EmployerChaussure e){
		priorite = prio;
		stockChaussure = sc;
		employe = e;
	}
	
	public boolean gotClient(){
		return clientAChanger != null;
	}
	
	
	
	public synchronized void switchChaussure(Client cl){
		//1er arriv�, 1er servi
		if (clientAChanger == null) {
			clientAChanger = cl;
			employe.wakeUpEmployer();// nouveau client arriv�
		}
		
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// le client n'a pas forc�ment changer de chaussure mais va peut etre changer de priorit� donc retour � switchChaussure de la classe EmployerChaussure
		// qui va d�termin� si le client va changer sa priorit�.
	}
	
	
	
	// si la fonction return false, cela signifie que le client voulais des chaussures de bowling et qu'il n'y en � plus
	public synchronized boolean switchChaussureClientAct(){
		if (!gotClient()) {
			return true;
		}
		
		boolean aRetourner = stockChaussure.emplSwitchChaussure(clientAChanger);
		
		// rien � faire car plus de chaussure de bowling et donc pas la peine de reveiller les clients qui demandes des chaussures de bowling
		if (aRetourner) {
			clientAChanger = null;
			notifyAll();
		}
		return aRetourner;
	}
	


}
