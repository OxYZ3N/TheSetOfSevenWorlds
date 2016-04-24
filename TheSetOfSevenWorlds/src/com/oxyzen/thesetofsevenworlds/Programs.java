package com.oxyzen.thesetofsevenworlds;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Programs
{

	public static void main(String... args) throws InterruptedException, ExecutionException
	{
		final Thread t1 = new Thread();
		final Thread t2 = new Thread();
		Boolean timer = false;
		int count = 1;
		int countDec = 10;

		System.out.println("Valeur du Timer avant : " + timer);

		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<Boolean> result = service.submit(new Callable<Boolean>()
				{
			int compteur = 5;
			public Boolean call() throws Exception
			{
				//try
				//{
				t1.setDaemon(true);
				while (compteur > 0)
				{
					compteur--;
					t1.sleep(1000);
					System.out.println(compteur + " secondes avant de sortir de la boucle call() du thread " + t1.getName());
				}
				//t.interrupt();
				//t.wait(5000);
				//if (t.isAlive()) t.interrupt();
				//t.join(4000);
				//Thread.sleep(4000); // Go for a nap
				//t.yield();
				//}
				//catch (InterruptedException e)
				//{
				//	System.out.println("Grr interrupted :(");
				//}
				//return "Worth waiting eh ?";
				return true;
			}
				});
		System.out.println("Fait quelques ch™ses en attendant le rŽsultat. Un gros calcul par exemple :) ");
		int maValeur = MoveItSuckerBaby();
		if (maValeur==5) System.out.println("Gros calcul terminŽ :)");
		try
		{
			// The call for get() will block the main thread until the result is
			// known
			// Doing things while waiting for the result
			timer = result.get();
			System.out.println("Aprs le traitement");
			System.out.println("Valeur du Timer aprs : " + timer);
		}
		catch (InterruptedException e)
		{
			System.out.println("Grr interrupted :(");
		}
		catch (ExecutionException e)
		{
			if (t1.isAlive()) System.out.println("the process " + t1.getName() + " still working ");
			if (t1.currentThread() != null)
				System.out.println("the process " + t1.getName() + " still working ");
			System.out.println("Woops something went wrong");
		}
	}

	private static int MoveItSuckerBaby() {
		int billyBoy = 0;
		for (int i=2000000;i>0;i--)
		{
			billyBoy = i;;
			System.out.println(billyBoy);
			if (i==5) break;
		}
		return billyBoy;
	}
}