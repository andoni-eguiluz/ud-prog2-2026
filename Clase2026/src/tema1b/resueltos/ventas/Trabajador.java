package tema1b.resueltos.ventas;

public class Trabajador {
	private String nombre;
	private double eficiencia;    // Eficiencia 1.0 es que tarda el tiempo estándar. 2.0 Tarda la mitad y así sucesivamente
	private int tiempoPendiente;  // Tiempo que está ocupado el trabajador/a (0 = libre) en segundos
	private Producto venta;       // Venta de producto en la que está ocupado
	
	public Trabajador(String nombre, double eficiencia) {
		super();
		this.nombre = nombre;
		this.eficiencia = eficiencia;
		this.venta = null;
		this.tiempoPendiente = 0;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getEficiencia() {
		return eficiencia;
	}

	public void setEficiencia(double eficiencia) {
		this.eficiencia = eficiencia;
	}

	public int getTiempoPendiente() {
		return (int) Math.round(tiempoPendiente / eficiencia);  // Los segundos que quedan dependen de la eficiencia
	}
	
	/** Asigna un tiempo pendiente de trabajo a un trabajador
	 * @param tiempo	Tiempo en segundos de ese trabajo
	 */
	public void setTiempoPendiente( int tiempo ) {
		this.tiempoPendiente = tiempo;
	}
	
	/** Informa si el trabajador está libre
	 * @return	true si no tiene trabajo, false si todavía lo tiene (tiempoPendiente &gt; 0)
	 */
	public boolean isLibre() {
		return tiempoPendiente == 0;
	}

	public Producto getVenta() {
		return venta;
	}

	public void setVenta(Producto venta) {
		this.venta = venta;
	}
	
	/** Simula el paso de tiempo en la tienda. Si el trabajador está ocupado, trabaja en su función y disminuye su tiempo pendiente
	 * @param segundos	Tiempo que pasa en segundos
	 */
	public void pasaElTiempo( int segundos ) {
		if (tiempoPendiente==0) return; // Si el trabajador está libre sigue libre (nada que hacer)
		int segundosMenos = (int) Math.round(segundos * eficiencia);  // El tiempo pasa en función de la eficiencia
		tiempoPendiente -= segundosMenos;
		if (tiempoPendiente<=0) {  // El trabajador se libera (ha acabado su trabajo
			tiempoPendiente = 0; 
			System.out.println( "Trabajador " + nombre + " finaliza su trabajo." );  // quitar de consola cuando se haya depurado el proceso
		}
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
