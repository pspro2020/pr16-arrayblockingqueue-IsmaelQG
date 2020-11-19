package code;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Fregadero {
	
	private ArrayBlockingQueue<Plato> platosLimpios = new ArrayBlockingQueue<>(10);
	private ArrayBlockingQueue<Plato> platosSecos = new ArrayBlockingQueue<>(10);
	private ArrayBlockingQueue<Plato> alacena = new ArrayBlockingQueue<>(10);
	
	DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public void añadirPlatoLimpio(Plato plato) throws InterruptedException {
			platosLimpios.add(plato);
			System.out.printf("%s Lavando plato nº %d\n",LocalTime.now().format(format), plato.getId());
	}
	
	public void añadirPlatoSeco() throws InterruptedException {
			
		try {
			platosSecos.put(platosLimpios.peek());
			System.out.printf("%s Secando plato nº %d\n",LocalTime.now().format(format), platosSecos.element().getId());
			platosLimpios.take();
			TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1,4));
		}
		catch(NullPointerException e) {
			
		}
	}
	
	public void guardarPlato() throws InterruptedException {
			
		try {
			alacena.put(platosSecos.peek());
			System.out.printf("%s Guardando plato nº %d\n",LocalTime.now().format(format), platosSecos.element().getId());
			platosSecos.take();
			TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1,3));
		}
		catch(NullPointerException e) {
			
		}
	}

}
