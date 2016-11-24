package part1;
public class Client {

	private String id;
	private Chaussure chaussure;
	private Groupe groupe;
	private Guichet guichet;

	public Client(String id,Guichet guichet) {
		this.id = id;
		this.chaussure = new ChaussureVille();
		chaussure.setClient(this);
		this.guichet=guichet;
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
	
	public boolean equals(Client cl){
		return id.equals(cl.id);
	}

}
