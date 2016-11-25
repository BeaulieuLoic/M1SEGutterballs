package part1.thread;

import part1.Chaussure;
import part1.ChaussureVille;
import part1.Groupe;
import part1.PisteJeu;
import part1.monitor.Guichet;
import part1.monitor.SalleDanse;

public class Client extends Thread {

	private int id;
	private Chaussure chaussure;
	private Groupe groupe;
	private Guichet guichet;
	
	private boolean estDansSalleDanse;
	private boolean estDansPisteJeu;
	
	private PisteJeu pisteJeu;
	private SalleDanse salleDanse;
	private Bowling bowling;

	public Client(int id, Guichet guichet, SalleDanse sd, Bowling bl) {
		this.id = id;
		this.chaussure = new ChaussureVille();
		this.guichet = guichet;
		
		salleDanse = sd;
		
		estDansSalleDanse = false;
		estDansPisteJeu = false;
		
		bowling = bl;
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
	
	public void setPisteJeu(PisteJeu pj){
		pisteJeu = pj;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + "]";
	}


	
	
	//pour salle danse
	public void goToSalle(){
		estDansSalleDanse = true;
	}
	
	public void outSalle(){
		estDansSalleDanse = false;
	}
	
	public boolean isInSalle(){
		return estDansSalleDanse;
	}	
	
	//pour piste jeu
	public void goToPisteJeu(){
		estDansPisteJeu = true;
	}
	
	public boolean isInPiste(){
		return estDansPisteJeu;
	}
	
	// ne pas appeler car pose probl�me ()
	public void outPiste(){
		estDansPisteJeu = false;
	}
	
	public void prevenirBowlingPartieFinit() {
		bowling.nouvellePisteDispo();
		
	}
	

	public void run() {
		//System.out.println(this + "-> Guichet");
		guichet.addToGroup(this);// go to guichet et attendre
		//System.out.println(this +" "+ groupe + " à finit d'attendre dans Guichet. -> go StockChaussure ");
		// go to salle des chaussures
		groupe.getStockChaussure().changeVtoB(this); // se chausse
		//System.out.println(this +" "+ groupe + " à finit d'attendre dans stockChaussure. -> go SalleDeDanse");

		// go to salle de danse et attend que tout les membres du groupe y soit
		goToSalle();
		salleDanse.waitGroupe(groupe);
		//System.out.println(this +" "+ groupe + " à finit d'attendre son groupe dans salleDanse. -> attend piste de jeu");		
		// attends d'etre notifié par soit un membre de son groupe soit par le
		// bowling qu'une place est libre
		salleDanse.waitPisteDispo(groupe);
		//System.out.println(this +" "+ groupe + "  piste de jeu trouver. -> go to pisteDeJeux");	
		// go to piste de jeu
		goToPisteJeu();

		// attendre que son groupe soit au complet sur la piste puis joue
		pisteJeu.waitGroupeAndPlay(groupe);
		//System.out.println(this +" "+ groupe + " à finit d'attendre son groupe dans pisteJeux. -> joue une partie");		
		
		// Jouer :D (le dernier client arriv� dans pisteJeu lance la partie)
		// Soit prevenir le bowling que la partie est terminée soit il se barre
		// go to guichet
		// Payer D:
		// go to salle des chaussures
		// go exit
	}
	
	
	public boolean equals(Client cl) {
		return id == cl.id;
	}




}