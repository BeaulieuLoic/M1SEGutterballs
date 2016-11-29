package client;

public class Chaussure {
		
	private enum TypeChaussure{
		ville,
		bowling;
	}
	
	private int id;
	private TypeChaussure type;
	
	public Chaussure() {
		id = -1;
		type = TypeChaussure.bowling;
	}
	
	public Chaussure(int i) {
		id = i;
		type = TypeChaussure.ville;
	}

	
	public boolean isBowling(){
		return type==TypeChaussure.bowling;
	}
	
	public boolean isVille(){
		return type==TypeChaussure.ville;
	}

	public int getId(){
		return id;
	}
	
	@Override
	public String toString() {
		return "Chaussure [id=" + id + ", type=" + type + "]";
	}
	
	
}
