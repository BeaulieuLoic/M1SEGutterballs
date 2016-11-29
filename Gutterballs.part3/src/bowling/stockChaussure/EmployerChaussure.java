package bowling.stockChaussure;

public class EmployerChaussure extends Thread {

	public synchronized void wakeUpEmployer(){
		notify();
	}
	
	public synchronized void attenderClient(){
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void run(){
		while(true){
			attenderClient();
		}
	}
}
