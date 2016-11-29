package bowling.stockChaussure;


import client.Client;


public class PrioriteChaussureMonitor {
	
	private int priorite;
	protected StockChaussure stockChaussure;
	
	public static final int prioMin = 0;
	// 0 = client qui veut des chaussure bowling mais dont personne dans son groupe n'à de chaussure
	public static final int prioIntermediaire = 1;
	// 1 = client qui veut des chaussure bowling et dont une personne de son groupe en possède
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
		//1er arrivé, 1er servi
		if (clientAChanger == null) {
			clientAChanger = cl;
			employe.wakeUpEmployer();// nouveau client arrivé
		}
		
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// le client n'a pas forcément changer de chaussure mais va peut etre changer de priorité donc retour à switchChaussure de la classe EmployerChaussure
		// qui va déterminé si le client va changer sa priorité.
	}
	
	
	
	// si la fonction return false, cela signifie que le client voulais des chaussures de bowling et qu'il n'y en à plus
	public synchronized boolean switchChaussureClientAct(){
		if (!gotClient()) {
			return true;
		}
		
		boolean aRetourner = stockChaussure.emplSwitchChaussure(clientAChanger);
		
		// rien à faire car plus de chaussure de bowling et donc pas la peine de reveiller les clients qui demandes des chaussures de bowling
		if (aRetourner) {
			clientAChanger = null;
			notifyAll();
		}
		return aRetourner;
	}
	


}
