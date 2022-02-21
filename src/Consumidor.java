
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Damian Cazarin
 */
public class Consumidor extends Thread {//el consumidor hereda la funcionalidad del hilo, por ende se ejecuta concurrentemente

    private Buffer b;//el buffer es con lo que se interactua
    private int size;//elementos que se quieren consumir del productor

    public Consumidor(Buffer b, int size) {
        //recibe de parametros el buffer, y el tamaño
        this.b = b;
        this.size = size;
    }

    @Override
    public void run() {//esta es la funcion en la que se accede al buffer
        System.out.println("-----Etapa de consumo-----");
        for (int i = 0; i < size; i++) {
            try {
                int aux = b.extraer();//aqui se tiene un apuntador auxiliar para saber que elemento se va a extraer del buffer
                System.out.println("El repartidor ha recogido " + aux + " paquete(s) del almacén");//se imprime que es lo que se extrae del buffer (lo que consume el consumidor)
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Buffer b = new Buffer(10);//se crea el buffer de tamaño de 5 que es donde se colocara lo que se produce y lo que se quita cuando se consume
        Productor p1 = new Productor(b, 10);//se crea el productor el cual insertara 10 elementos producidos
        Consumidor c1 = new Consumidor(b, 10);//se crea el consumidor y que consume los elementos que se producen
        p1.start();
        c1.sleep(500);
        p1.sleep(510);
        c1.start();
        //aqui se ejecuta la inicializacion de cada hilo, tanto del productor como del consumidor (ambos casos)
    }
}
