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
	int nbA=0;
	int nbB=0;
	
	public StockChaussure() {
		listeChaussureCLient = new HashMap<>();
		listChaussureBowling = new LinkedList<>();

		for (int i = 0; i < Main.nbClientGrp*Main.nbPiste; i++) {
			listChaussureBowling.add(new Chaussure());
		}
	}
	
	/**
	 * pas synchronized car seul e thread employerChaussure y accède
	 * */
	public boolean gotChaussureBowling(){
		return listChaussureBowling.size() > 0;
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
		
		cl.setChaussure(listChaussureBowling.get(0));
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
			if (cl.getChaussure().isBowling()) {
				System.out.println(listeChaussureCLient);
			}
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
	 * pas de synchronized car seul le thread de employerChaussure va acc�d� �
	 * cette classe
	 * 
	 * si return false, cela signifie que le client voulais des chaussures de
	 * bowling et qu'il n'y en � plus
	 *
	 * */
	protected boolean emplSwitchChaussure(Client cl) {
		if (cl.getChaussure().isBowling()) {
			nbA++;
			//System.out.println("aaaa"+nbA);
			return changeBtoV(cl);
		} else {
			nbB++;
			//System.out.println("bbbbbb"+nbB);
			return changeVtoB(cl);
		}
	}
	
	


}
