package model.cooperantes;

public class Hilo extends Thread{
	int numHilo;
	int miParte;
	int miCuenta;
	
	private final Contador cont;

	
	public int getNumHilo() {
		return numHilo;
	}
	public int getMiCuenta() {
		return miCuenta;
	}
	public void setMiCuenta(int miCuenta) {
		this.miCuenta = miCuenta;
	}

	public Hilo(int numHilo, int miParte, Contador cont) {
		this.numHilo = numHilo; 
		this.miParte = miParte;
		this.cont = cont;
	}
	
	public void run() {
		for (int i = 0; i < miParte; i++) {
			this.cont.incrementa();
			miCuenta++;
		}
		System.out.printf("Hilo %d terminado, cuenta: %d\n", numHilo, getMiCuenta());
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
