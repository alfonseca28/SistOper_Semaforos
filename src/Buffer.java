
import java.util.concurrent.*;//esta librerias nos permite utilizar los semaforos

/**
 *
 * @author Aaron Alfonseca
 */
public class Buffer {
    //es la del recurso compartido

    private int b[];//se define un arreglo de enteros
    
    private int i = 0, j = 0;//se crean dos apuntadores para saber cual es la posicion dispobivle en el buffer (la i es para el productor y la j para el consumidor)
    
    private Semaphore mutex = new Semaphore(1, true);//nos permite la exclusion mutua (que un solo proceso pueda modificar el buffer) para que no haya colision
    
    private Semaphore hayDatos = new Semaphore(0, true);//permite saber si en el buffer hay datos para poder consumirlos
    
    private Semaphore hayEspacio;//esste semaforo valida para saber si cuando se vaya a poner algo en el buffer haya espacio en el buffer

    public Buffer(int tam) {
        b = new int[tam];
        hayEspacio = new Semaphore(tam, true);
    }//se recibe un tamaño para el arreglo, el true es para la prioridad en los procesos

    public void poner(int dato) throws InterruptedException {
        //se recibe el dato
        hayEspacio.acquire();
        //se verifica si hay espacio para poner el dato en el buffer, si no hay espacio espera, sino se pone
        //inicia el paso de la seccion critica
        mutex.acquire();//si hay espacio se ingresa al buffer
        b[i] = dato;//se coloca el dato en el arreglo

        i = (i + 1) % b.length;//con el dato se calculo el siguiente espacio disponible a lo largo del arrgle del buffer
        mutex.release();//aqui se garantiza la exclusion mutua, lo que aqui se hace es que se le suma al contador 1
        //termina la seccion critica
        hayDatos.release();
    }
    
    public int extraer() throws InterruptedException {
        //hay que verificar si hay datos y ya de ahi que se puedan extraer
        hayDatos.acquire();//verificamos si hay datos en el buffer
        mutex.acquire();//verificamos si hay un espacio disponible dentro del buffer
        int actual = j;
        j = (j + 1) % b.length;
        mutex.release();//con el dato se calculo el siguiente espacio disponible a lo largo del arrgle del buffer
        hayEspacio.release();//aqui al sacar un elemento ya hay espacio 

        return b[actual];
    }
}
