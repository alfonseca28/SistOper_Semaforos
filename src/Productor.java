/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;//aqui se importa la libreria para generar numeros aleatorios
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aaron Alfonseca
 */
public class Productor extends Thread {//esto es un hilo, el cual se va a ejecutar concurrentemente

    private Random r = new Random();//se crea el objeto para almacenar numeros en aleatorio
    private Buffer b;//acceso al buffer
    private int size;//controla poner los numeros en el buffer

    public Productor(Buffer b, int size) {
        this.b = b;
        this.size = size;
    }

    @Override//esto es el codigo que se va a ejecutar concurrentemente cada que el producto produzca algo
    public void run() {
        System.out.println("-----Etapa de produccion-----");
        for (int i = 0; i < size; i++) {//como se tiene una cantidad de elementos a colocar se va a hacer un recorrido de elementos
            try {
                int aux = r.nextInt(10) + 1;//aqui se genera el numero aleatorio, esto dentro de un rango del numero 0 al 9
                b.poner(aux);//aqui se coloca el numero que el prodcutor procude (esta cantidad es aleatoria)
                System.out.println("Se ha producido " + aux + " paquete(s) en el almacén.");//aqui se muestra lo que se pone le en buffer y lo que el productor produce
            } catch (InterruptedException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
