package part1;

import java.util.HashMap;
import java.util.Map;

public class StockChaussure {
	private Map<Client,ChaussureVille> listeChaussureCLient;
	
	public StockChaussure(){
		listeChaussureCLient = new HashMap<>();
	}	
		
	public synchronized void changerChaussureVilleVersBowling(Client cl){
		if (cl.getChaussure() instanceof ChaussureVille) {
			ChaussureVille chaussureClient =  (ChaussureVille) cl.getChaussure();
			chaussureClient.setEstPorte(false);
			listeChaussureCLient.put(cl,chaussureClient);
			cl.setChaussure(new ChaussureBowling());// pour la v3 prendre une chaussure dans la liste, ou attendre s'il n'y en a plus
		}else{
			System.out.println("Erreur stockChaussure, le client veut prendre des chaussures de bowling alors qu'il n'à pas de chaussure de ville");
		}
	}

	public synchronized void changerChaussureBowlingVersVille(Client cl){
		if (cl.getChaussure() instanceof ChaussureBowling) {
			ChaussureBowling chaussureClient =  (ChaussureBowling) cl.getChaussure();
			chaussureClient.setEstPorte(false);
			cl.setChaussure(listeChaussureCLient.get(cl));
			cl.getChaussure().setEstPorte(true);
		}else{
			System.out.println("Erreur stockChaussure, le client veut prendre des chaussures de ville alors qu'il n'à pas de chaussure de bowling");
		}
	}
	
}
