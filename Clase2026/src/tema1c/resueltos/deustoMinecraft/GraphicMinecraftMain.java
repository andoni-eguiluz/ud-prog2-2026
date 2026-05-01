package tema1c.resueltos.deustoMinecraft;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import utils.ventanas.ventanaBitmap.VentanaGrafica;

public class GraphicMinecraftMain {

	private static VentanaGrafica v;
	private static Mundo mundo;
	private static Jugador jug;
	
	public static void main(String[] args) {
		v = new VentanaGrafica( 800, 600, "Deusto Minecraft" );
		// Lógica de persistencia con fichero binario
		mundo = cargaMundoPrevioSiExiste();
		if (mundo == null || mundo.getJugador()==null) { // Creación de mundo por defecto si no hay carga de mundo previo
	        mundo = new Mundo();
	        jug = new Jugador( "Eva", 50, 70, 80 );
	        jug.setVentana( v );
	        mundo.agregarEntidad( jug );
	        // Mob m = new Mob( "Slime", 320, 180, true, 20.0 );
	        // m.setVentana( v );
	        mundo.agregarEntidad( new Piedra( "p1", 200, 150, 2, v ) );
	        mundo.agregarEntidad( new Piedra( "p2", 420, 100, 0, v ) );
	        mundo.agregarEntidad( new Slime( "Slime", 320, 180, true, 20.0, v ) );
	        mundo.agregarEntidad( Mob.crearMob( "Esqueleto", 220, 120, true, 20.0, v ) );
	        mundo.agregarEntidad( Mob.crearMob( "Orco", 420, 240, true, 20.0, v ) );
	        mundo.agregarEntidad( Mob.crearMob( "Murciélago", 520, 80, true, 20.0, v ) );
	        mundo.dibujar();
	        edicion();
		} else {
			jug = mundo.getJugador();
	        mundo.dibujar();
		}
        juego();
	}
	
	private static void edicion() {
		// Ciclo de edición hasta pulsar "f"
		int teclaPulsada = 0;

		v.setMensaje( "Modo de edición" );
		v.setDibujadoInmediato( false );
		while (teclaPulsada != KeyEvent.VK_F && !v.estaCerrada()) {
			Point click = v.getRatonClicado();
			if (click != null) {
				String aCrear = "";
				if (v.isTeclaPulsada( KeyEvent.VK_S )) {  // Slime
					aCrear = "Slime";
				} else if (v.isTeclaPulsada( KeyEvent.VK_M )) {  // "Murciélago"
					aCrear = "Murciélago";
				} else if (v.isTeclaPulsada( KeyEvent.VK_E )) {  // "Esqueleto"
					aCrear = "Esqueleto";
				} else if (v.isTeclaPulsada( KeyEvent.VK_O )) {  // "Orco"
					aCrear = "Orco";
				} else if (v.isTeclaPulsada( KeyEvent.VK_P )) {  // "Piedra"
					aCrear = "Piedra";
				}
				Entidad e = null;
				if (aCrear.equals( "Piedra" ) ) {
					e = new Piedra( "piedra", click.x, click.y, 3, v );
				} else if (!aCrear.isEmpty()) {
					e = Mob.crearMob(aCrear, click.x, click.y, true, teclaPulsada, v );
					// Antes del constructor factoría:
			        // e = new Mob( aCrear, click.x, click.y, true, 20.0 );
			        // e.setVentana( v );
				}
				if (e!=null) {
					mundo.agregarEntidad( e );
					v.borra();
					mundo.dibujar();
					v.repaint();
				}
			}
			v.espera( 40 );
			teclaPulsada = v.getCodTeclaQueEstaPulsada();
		}
		// Fin de edición - pregunta de si el usuario quiere guardar este mundo editado
		String resp = v.sacaDialogoConBotones( "Fin de edición", "Editado nuevo mundo. ¿Quieres guardarlo?", "Sí", "No" );
		if ("Sí".equals(resp)) {
			String nombreFic = v.sacaDialogoConTextoEntrada( "Guardado de mundo", "Introduce nombre de fichero para guardar:" );
			if (nombreFic!=null && !nombreFic.isEmpty()) {
				guardarMundo( nombreFic );
			}
		}
	}
	
	private static int NUM_MOVS_POR_FOT = 3;
	private static void juego() {
		// Funcionando hasta cerrar la ventana
		v.setMensaje( "Modo de juego" );
		boolean finalJuego = false;
		while (!v.estaCerrada() && !finalJuego) {
			// Entrada y gestión de jugador
			boolean arriba = false;
			boolean abajo = false;
			boolean izq = false;
			boolean der = false;
			if (v.isTeclaPulsada( KeyEvent.VK_UP )) {
				arriba = true;
			} else if (v.isTeclaPulsada( KeyEvent.VK_DOWN )) {
				abajo = true;
			}
			if (v.isTeclaPulsada( KeyEvent.VK_LEFT )) {
				izq = true;
			} else if (v.isTeclaPulsada( KeyEvent.VK_RIGHT )) {
				der = true;
			}
			if (arriba) {  // Ojo que las coordenadas están al revés en la ventana
				for (int i=0; i<NUM_MOVS_POR_FOT; i++) { jug.abajo(); }
			} else if (abajo) {
				for (int i=0; i<NUM_MOVS_POR_FOT; i++) { jug.arriba(); }
			}
			if (izq) {
				for (int i=0; i<NUM_MOVS_POR_FOT; i++) { jug.izquierda(); }
			} else if (der) {
				for (int i=0; i<NUM_MOVS_POR_FOT; i++) { jug.derecha(); }
			}
			
			// Físicas - lógica
			for (Entidad e : mundo.getListaEntidades()) {
				if (e instanceof ConInteligencia) {
					ConInteligencia ci = (ConInteligencia) e;
					ci.mover( jug );
				}
			}
			
			// Físicas - choques
			for (Entidad e1 : mundo.getListaEntidades()) {
				if (e1 instanceof Chocable) {
					for (Entidad e2 : mundo.getListaEntidades()) {
						if (e2 instanceof Chocable) {
							if (((Chocable) e1).chocaCon(e2)) {
								((Chocable)e1).actuaConChoque( e2 );
								// System.out.println( "Choque " + e1 + " con " + e2 );
							}
						}
					}
				}
			}
			
			// Lógica de vidas
			ArrayList<Entidad> lABorrar = new ArrayList<>();
			for (Entidad e : mundo.getListaEntidades()) {
				if (e instanceof Mob) {
					if (((Mob) e).getPuntosVida() < 0) {
						lABorrar.add( e );
					}
				} else if (e instanceof Piedra) {
					if (((Piedra)e).getEnergia() < 0) {
						lABorrar.add( e );
					}
				} else if (e instanceof Jugador) {
					if (((Jugador)e).getExperiencia() < 0) {
						finalJuego = true;
						v.setMensaje( "OOOhhhh... El juego ha acabado" );
						v.sacaDialogo( "¡Derrota!", "OOOhhhh... los mobs han podido contigo" );
					}
				}
			}
			for (Entidad e : lABorrar) {
				mundo.getListaEntidades().remove( e );
			}
			// Conteo de mobs
			int cuentaMobs = 0;
			for (Entidad e : mundo.getListaEntidades()) {
				if (e instanceof Mob) {
					cuentaMobs++;
				}
			}
			if (cuentaMobs == 0) {
				finalJuego = true;
				v.setMensaje( "Enhorabuena! Has conseguido vencer a los mobs!" );
				v.sacaDialogo( "¡Victoria!", "Enhorabuena! Has conseguido vencer a los mobs!" );
			}
			
			// Redibujado
			v.borra();
			mundo.dibujar();
			v.repaint();
			// Espera
			v.espera( 40 );
		}
		v.acaba();
	}

	// PERSISTENCIA
	
	/** Intenta cargar un mundo previo, pidiendo al usuario elección entre los existentes
	 * @return
	 */
	private static Mundo cargaMundoPrevioSiExiste() {
		// Ver si hay mundos previos en carpeta actual
		File carpeta = new File(".");  // Carpeta en curso
        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
        	ArrayList<String> listaMundos = new ArrayList<>();
            for (File archivo : archivos) {
                // Verificar que es archivo y termina en .dmc
                if (archivo.isFile() && archivo.getName().toLowerCase().endsWith(".dmc")) {
                	listaMundos.add( archivo.getName() );
                }
            }
            if (listaMundos.size() > 0) {  // Hay mundos previos
            	String[] opciones = listaMundos.toArray( new String[0] );
            	String ficheroElegido = v.sacaDialogoConOpciones( "Carga", "Selecciona mundo previo a cargar (esc para editar)", opciones );
            	if (ficheroElegido!=null && !ficheroElegido.isEmpty()) {
            		try {
            			ObjectInputStream ois = new ObjectInputStream( new FileInputStream( ficheroElegido ) );
            			Mundo mundo2 = (Mundo) ois.readObject();
            			ois.close();
            			for (Entidad e : mundo2.getListaEntidades()) {
            				e.setVentana( v );  // Actualiza la ventana de las entidades cargadas (la ventana no se guarda con la entidad)
            			}
            			return mundo2;
            		} catch (Exception e) {
            			v.sacaDialogo( "Error", "Error en cargado de mundo " + ficheroElegido + ".dmc" );
            		}
            	}
            }
        }
		return null;
	}
	
	/** Guarda el mundo actual en fichero binario
	 * @param nombreFichero	Nombre del fichero - se le añade la extensión ".dmc"
	 */
	private static void guardarMundo( String nombreFichero ) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( nombreFichero + ".dmc" ) );
			oos.writeObject( mundo );
			oos.close();
		} catch (Exception e) {
			v.sacaDialogo( "Error", "Error en guardado de mundo " + nombreFichero + ".dmc" );
		}
	}
	
}