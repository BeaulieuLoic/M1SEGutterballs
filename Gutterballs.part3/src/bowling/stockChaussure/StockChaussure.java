package bowling.stockChaussure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import client.*;
import Main.Main;

public class StockChaussure {
	private Map<Client, Chaussure> listeChaussureCLient;
	private List<Chaussure> listChaussureBowling;
	

	private List<PrioriteChaussureMonitor> listMonitor;
	public StockChaussure() {
		listeChaussureCLient = new HashMap<>();
		listChaussureBowling = new LinkedList<>();

		for (int i = 0; i < Groupe.nbMaxClient; i++) {
			listChaussureBowling.add(new Chaussure());
		}
	}
	
	public int getNbChaussureBowling(){
		return listChaussureBowling.size();
	}

	/**
	 * Change le type des chaussures du client. Ville vers bowling.
	 * 
	 */
	private boolean changeVtoB(Client cl) {
		
		if(listChaussureBowling.size() == 0){
			return false;
		}
		
		
		listeChaussureCLient.put(cl, cl.getChaussure());
		cl.setChaussure(listChaussureBowling.get(0));// pour la v3 prendre une chaussure
											// dans la liste, ou attendre s'il
											// n'y en a plus
		listChaussureBowling.remove(0);

		try {
			Thread.sleep(Main.dureeChausse);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return true;
	}

	/**
	 * Change le type des chaussures du client. Bowling vers Ville.
	 */
	private boolean changeBtoV(Client cl) {
		if (listeChaussureCLient.size() == 0) {
			System.out
					.println("!!!!! Erreur changeBtoV plus de chaussure de client !!!!!");
		}else{
			listChaussureBowling.add(cl.getChaussure());
			cl.setChaussure(listeChaussureCLient.get(cl));
			
			try {
				Thread.sleep(Main.dureeChausse);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * pas de synchronized car seul le thread de employerChaussure va accédé à
	 * cette classe
	 * 
	 * si return false, cela signifie que le client voulais des chaussures de
	 * bowling et qu'il n'y en à plus
	 *
	 * */
	protected boolean emplSwitchChaussure(Client cl) {
		if (cl.getChaussure().isBowling()) {
			return changeBtoV(cl);
		} else {
			return changeVtoB(cl);
		}
	}
	
	


}
