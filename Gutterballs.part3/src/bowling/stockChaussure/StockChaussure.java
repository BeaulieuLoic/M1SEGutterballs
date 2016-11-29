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

		for (int i = 0; i < Main.nbPiste * Groupe.nbMaxClient; i++) {
			listChaussureBowling.add(new Chaussure());
		}
		
		
	}

	

	/**
	 * Change le type des chaussures du client. Ville vers bowling.
	 * 
	 */
	private boolean changeVtoB(Client cl) {
		if (listChaussureBowling.size() == 0) {
			return false;// utilsé pour faire attendre le client lorsqu'il n'y à
							// plus de chaussure de bowling
			
		}

		listeChaussureCLient.put(cl, cl.getChaussure());
		cl.setChaussure(new Chaussure());// pour la v3 prendre une chaussure
											// dans la liste, ou attendre s'il
											// n'y en a plus

		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;

	}

	/**
	 * Change le type des chaussures du client. Bowling vers Ville.
	 */
	private void changeBtoV(Client cl) {
		if (listeChaussureCLient.size() == 0) {
			System.out
					.println("!!!!! Erreur changeBtoV plus de chaussure de client !!!!!");
		}else{
			cl.setChaussure(listeChaussureCLient.get(cl));
			listChaussureBowling.add(cl.getChaussure());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
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
			changeBtoV(cl);
			return true;
		} else {
			return changeVtoB(cl);
		}
	}
	
	


}
