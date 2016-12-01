package bowling.stockChaussure;

import java.util.HashMap;
import java.util.Map;

import client.Chaussure;
import client.Client;


public class StockChaussure {
	private Map<Client,Chaussure> listeChaussureCLient;
	
	public StockChaussure(){
		listeChaussureCLient = new HashMap<>();
	}	
		
	/**
	 * Change le type des chaussures du client.
	 * Ville vers bowling.
	 */
	public synchronized void changeVtoB(Client cl){
		if (cl.getChaussure().isVille()) {
			listeChaussureCLient.put(cl,cl.getChaussure());
			cl.setChaussure(new Chaussure());// pour la v3 prendre une chaussure dans la liste, ou attendre s'il n'y en a plus
			
			try {
				Thread.sleep(Main.Main.dureeChausse);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else{
			System.out.println("Erreur stockChaussure, le client veut prendre des chaussures de bowling alors qu'il n'a pas de chaussure de ville");
		}
	}
	/**
	 * Change le type des chaussures du client.
	 * Bowling vers Ville.
	 */
	public synchronized void changeBtoV(Client cl){
		if (cl.getChaussure().isBowling()) {
			//ChaussureBowling chaussureClient =  (ChaussureBowling) cl.getChaussure();//pour v2
			cl.setChaussure(listeChaussureCLient.get(cl));
			try {
				Thread.sleep(Main.Main.dureeChausse);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else{
			System.out.println("Erreur stockChaussure, le client veut prendre des chaussures de ville alors qu'il n'a pas de chaussure de bowling");
		}
	}
	
}
