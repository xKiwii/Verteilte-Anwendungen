package webapp;
public class Sitzplatz {
	
	private Zustaende zustand = Zustaende.FREI;
	private String name;
	private int nummer;
	
	
	public Sitzplatz(int nummer){
		this.nummer = nummer;
	}
	
	public Zustaende getZustand(){
		return zustand;
	}
	
	public void setZustand(Zustaende zustand){
		this.zustand = zustand;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String toString() { 
	    return "SitzplatzNr: '" + this.nummer + "', Zustand: '" + this.zustand + "', Name: '" + this.name + "'";
	} 
	
}
