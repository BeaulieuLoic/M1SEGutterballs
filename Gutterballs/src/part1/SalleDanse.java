package part1;

import java.util.LinkedList;
import java.util.List;

public class SalleDanse {
	private List<Groupe> listGroupe;
	
	public SalleDanse() {
		listGroupe = new LinkedList<>();
	}
	
	
	public synchronized void waitGroupe(Groupe grp){
		if (grp.isInSalle()) {
			System.out.println(grp+" est dans la salle de danse.");
			listGroupe.add(grp);
			notifyAll();
		}else{
			while(!grp.isInSalle()){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
