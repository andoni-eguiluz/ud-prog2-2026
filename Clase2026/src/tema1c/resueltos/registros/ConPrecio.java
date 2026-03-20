package tema1c.resueltos.registros;

public interface ConPrecio {
	
	/** Devuelve el precio del registro
	 * @return	Precio en euros del registro
	 */
	double getPrecio();
	
	/** Informa si el precio está o no en el rango entre los precios inferior y superior indicados
	 * @param precioInferior	Precio menor del rango
	 * @param precioSuperior	Precio mayor del rango
	 * @return	true si el precio está en ese rango (mayor o igual que el precio menor, menor o igual que el mayor), false en caso contrario
	 */
	boolean estaPrecioEnRango( double precioInferior, double precioSuperior );
}
