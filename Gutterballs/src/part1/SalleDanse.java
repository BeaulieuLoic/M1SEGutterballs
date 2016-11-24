package part1;

import java.util.LinkedList;
import java.util.List;

public class SalleDanse {
	private List<Groupe> listGroupe;
	
	public SalleDanse() {
		listGroupe = new LinkedList<>();
	}
	
	public void addGroupe(Groupe grp){
		listGroupe.add(grp);
	}
}
