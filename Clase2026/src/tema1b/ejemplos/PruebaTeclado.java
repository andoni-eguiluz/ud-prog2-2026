package tema1b.ejemplos;

import java.awt.event.KeyEvent;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class PruebaTeclado {
	public static void main(String[] args) {
		VentanaGrafica v = new VentanaGrafica( 600, 400, "Test" );
		while (!v.estaCerrada()) {
			// Opción 1: tecla en tiempo real
			int tecla = v.getCodTeclaQueEstaPulsada();
			// Opción 2: tecla pulsada y soltada
			// tecla = v.getCodUltimaTeclaTecleada();
			// v.iste
			if (tecla == KeyEvent.VK_CONTROL) {
				System.out.println( "Pulsado control!" );
			}
			System.out.println( "Funcionando: " + tecla );
			v.espera( 40 );
		}
	}
}
