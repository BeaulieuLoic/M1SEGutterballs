package part1;

public class Client extends Thread {

	private int id;
	private Chaussure chaussure;
	private Groupe groupe;
	private Guichet guichet;
	private boolean estDansSalleDanse;

	public Client(int id, Guichet guichet) {
		this.id = id;
		this.chaussure = new ChaussureVille();
		this.guichet = guichet;
		estDansSalleDanse = false;
	}

	public Chaussure getChaussure() {
		return chaussure;
	}

	public void setChaussure(Chaussure chaussure) {
		this.chaussure = chaussure;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + "]";
	}

	public boolean equals(Client cl) {
		return id == cl.id;
	}
	
	public void inSalle(){
		estDansSalleDanse = true;
		groupe.getSalleDanse().waitGroupe(groupe);
	}
	
	public void outSalle(){
		estDansSalleDanse = false;
	}
	
	public boolean isInSalle(){
		return estDansSalleDanse;
	}
	

	public void run() {
		System.out.println(this + "-> Guichet");
		guichet.addToGroup(this);// go to guichet et attendre
		System.out.println(this +" "+ groupe + " à finit d'attendre dans Guichet. -> go StockChaussure ");
		// go to salle des chaussures
		groupe.getStockChaussure().changeVtoB(this); // se chausse
		System.out.println(this +" "+ groupe + " à finit d'attendre dans stockChaussure. -> go SalleDeDanse");

		// go to salle de danse
		inSalle();
		System.out.println(this +" "+ groupe + " à finit d'attendre dans salleDanse. -> attend piste de jeu");
		
		
		
		
		// attends d'etre notifié par soit un membre de son groupe soit par le
		// bowling qu'une place est libre
		// go to piste de jeu
		// attendre que son groupe soit au complet sur la piste
		// Jouer :D
		// Soit prevenir le bowling que la partie est terminée soit il se barre
		// go to guichet
		// Payer D:
		// go to salle des chaussures
		// go exit
	}

}
