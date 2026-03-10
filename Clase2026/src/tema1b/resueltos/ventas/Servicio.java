package tema1b.resueltos.ventas;

public class Servicio extends Producto {
	private static final int TIEMPO_INSTALACION_APP = 120; // Suponemos 2 minutos por instalación de App
	private static final int TIEMPO_LIMPIEZA_VIRUS = 10*60; // Suponemos 10 minutos por instalación de App
	private static final int PRECIO_INSTALACION_APP = 2; // 2 euros por instalación de App
	private static final int PRECIO_LIMPIEZA_VIRUS = 5; // 2 euros por limpieza de virus
	
	private int tiempoServicio;  // En segundos
	
	/** Inicializa un servicio con precio 0 euros y tiempo 0 segundos
	 * @param nombre	Nombre del servicio
	 */
	public Servicio(String nombre) {
		super(nombre, 0);
		this.tiempoServicio = 0;
	}

	// Crea un nuevo servicio copia del actual
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Servicio serv = new Servicio( this.nombre );
		serv.tiempoServicio = this.tiempoServicio;
		serv.precioEuros = this.precioEuros;
		return serv;
	}

	public int getTiempoServicio() {
		return tiempoServicio;
	}

	public void setTiempoServicio(int tiempoServicio) {
		this.tiempoServicio = tiempoServicio;
	}

	@Override
	public int getTiempoTrabajo() {
		return tiempoServicio;
	}
	
	/** Calcula el tiempo de servicio y el precio de instalación de apps en función del número de apps a instalar
	 * @param numAppsAInstalar	Número &gt; 0 de apps que se quieren instalar
	 */
	public void calcInstalacionApps( int numAppsAInstalar ) {
		tiempoServicio = numAppsAInstalar * TIEMPO_INSTALACION_APP;
		precioEuros = numAppsAInstalar * PRECIO_INSTALACION_APP;
	}
	
	/** Calcula el tiempo de servicio de limpieza de virus
	 */
	public void calcTiempoLimpiezaVirus() {
		tiempoServicio = TIEMPO_LIMPIEZA_VIRUS;
		precioEuros = PRECIO_LIMPIEZA_VIRUS;
	}
	
}
