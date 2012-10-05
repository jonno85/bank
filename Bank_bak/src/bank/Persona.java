package bank;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Persona implements Runnable, Observer
{
	private int numero;

	public Persona(int numero)
	{
		System.out.println(" :: new person takes number " + numero);
		this.numero = numero;
	}
	
	@Override
	public void run() 
	{
		doOperation();
	}

	private void doOperation() 
	{
		System.out.println("["+numero+"] goes to post counter");
		
		int time = new Random().nextInt(20);
		
		try
		{
			Thread.sleep(1000 * time);
		} catch(InterruptedException ie) {
			System.out.println("["+numero+"] goes out the post office before the time");
		}
		
		System.out.println("["+numero+"] finish its operation in " + time + " secondi");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
