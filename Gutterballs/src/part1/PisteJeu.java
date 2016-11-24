package part1;
public class PisteJeu {
	private Groupe groupe;
	
	public PisteJeu(){
		groupe = null;
	}
	
	public void lancerPartie(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean estOccupe(){
		return groupe != null;
	}
	
	public void setGroupe(Groupe grp){
		groupe = grp;
	}
}
