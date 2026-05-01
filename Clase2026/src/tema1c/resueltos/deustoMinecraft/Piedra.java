package tema1c.resueltos.deustoMinecraft;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class Piedra extends Entidad implements Chocable {
	private static final long serialVersionUID = 1L;
	
	private int tamanyo = 0;  // 0-3
    private int energia = 10;

    public Piedra(String nombre, int x, int y, int tamanyo, VentanaGrafica v ) {
        super(nombre, x, y);
        setVentana( v );
        setTamanyo( tamanyo );
    }
    
    /** Cambia el tamaño de la piedra
     * @param tamanyo	valor de 0 a 3, si no es así no se cambia
     */
    public void setTamanyo(int tamanyo) {
    	if (tamanyo < 0 || tamanyo > 3) {
    		return;
    	}
		this.tamanyo = tamanyo;
		energia = tamanyo * 10;
	}
    
    public int getEnergia() {
		return energia;
	}
    
    @Override
    public String toString() {
        return super.toString() + " | Piedra tamaño " + tamanyo + " | energía " + energia; 
    }
    
	// Interfaz Visualizable
	private static int TAM_DIBUJADO = 125;
	
	@Override
	public void dibujar() {
		if (getVentana()==null) {
			System.err.println( "Error: intentada dibujar entidad sin ventana");
			return;
		}
		getVentana().dibujaParteImagen( "Sprites-Minecraft.png", x, y, TAM_DIBUJADO, TAM_DIBUJADO, 1.0, 0.0, 1.0f, tamanyo*125, tamanyo*125+124, 250, 374 );
	}

	// Interfaz Chocable
	@Override
	public boolean chocaCon(Entidad e) {
		if (e instanceof Mob) {
			return ((Mob)e).chocaCon(this);
		} else {
			return false;
		}
	}
	
	@Override
	public void actuaConChoque(Entidad e) {
		if (e instanceof Mob) {
			energia--;
			if (energia<11) {
				tamanyo = 0;
			} else if (energia<21) {
				tamanyo = 1;
			} else if (energia<31) {
				tamanyo = 2;
			}
		}
	}
	
	
}