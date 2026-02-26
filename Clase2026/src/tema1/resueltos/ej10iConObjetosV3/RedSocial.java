package tema1.resueltos.ej10iConObjetosV3;

import java.util.ArrayList;

public class RedSocial {
	private ArrayList<UsuarioRedSocial> usuariosRS;
	private String nombre;
	private String urlPrincipal;
	private TipoRedSocial tipo = TipoRedSocial.GENERALISTA;
	
	
	/** Crea una red social nueva, SIN USUARIOS
	 * @param nombre	El nombre de esa red social
	 * @param urlPrincipal	Su dirección de internet
	 */
	public RedSocial(String nombre, String urlPrincipal, TipoRedSocial tipo) {
		this.nombre = nombre;
		this.urlPrincipal = urlPrincipal;
		this.tipo = tipo;
		this.usuariosRS = new ArrayList<UsuarioRedSocial>();
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrlPrincipal() {
		return urlPrincipal;
	}

	public void setUrlPrincipal(String urlPrincipal) {
		this.urlPrincipal = urlPrincipal;
	}
	
	public TipoRedSocial getTipo() {
		return tipo;
	}

	/** Añade un usuario a la red social al final de la lista
	 * @param usuario	Usuario nuevo
	 */
	public void anyadir( UsuarioRedSocial usuario ) {
		usuariosRS.add( usuario );
	}
	
	/** Visualiza en consola de salida slínea a línea usuario tabulador nº seguidores
	 */
	public void visualizarUsuariosYSeguidores() {
		for (int i=0; i<usuariosRS.size(); i++) {
			if (usuariosRS.get(i) != null) {
				System.out.println( usuariosRS.get(i).toString() );	
			}
		}
	}
	
	/** Ordena los usuarios de la RS por n. seguidores de mayor a menor
	 */
	public void ordenaUsuariosPorSeguidores() {
		for (int pasada=0; pasada<usuariosRS.size()-1; pasada++) {
			for (int comp=0; comp<usuariosRS.size()-1; comp++) {  // TODO mejorar que solo se hagan las comparaciones necesarias
				// boolean hayQueIntercambiar = usuarios[comp].numSeguidores < usuarios[comp+1].numSeguidores;
				boolean hayQueIntercambiar = usuariosRS.get(comp).tieneMenosSeguidoresQue( usuariosRS.get(comp+1) );
				if (hayQueIntercambiar) {
					UsuarioRedSocial auxNombre = usuariosRS.get(comp);
					usuariosRS.set( comp, usuariosRS.get(comp+1) );
					usuariosRS.set( comp+1, auxNombre );
				}
			}
		}
	}
	

	/** Busca un usuario en la red social
	 * @param usuario	Usuario a buscar
	 * @return	Devuelve la posición 0 - n-1 en la que está, -1 si no se encuentra
	 */
	public int buscar( UsuarioRedSocial usuario ) {
		return usuariosRS.indexOf(usuario);
	}
	
	/** Borra un usuario
	 * @param usuario	Usuario a borrar. Si no existe (comparando con equals), no se hace nada
	 */
	public void borrar( UsuarioRedSocial usuario ) {
		usuariosRS.remove( usuario );
	}	

	/** Devuelve el tamaño de la red social
	 * @return	Número de usuarios actuales
	 */
	public int tamanyo() {
		return usuariosRS.size();
	}
	
	/** Devuelve un usuario
	 * @param indice	Posición del usuario a recuperar (de 0 a n-1)
	 * @return	Usuario situado en esa posición
	 */
	public UsuarioRedSocial get( int indice ) {
		return usuariosRS.get( indice );
	}
	
	/** Devuelve la media de seguidores de esta red social
	 * @return	Valor medio de MILES DE seguidores, como media de seguidores de todos los usuarios. Si no hay usuarios, devuelve 0
	 */
	public double calcularMediaSeguidores() {
		int suma = 0;
		for (UsuarioRedSocial u : usuariosRS) {
			suma += u.getNumMilesSeguidores();
		}
		return (usuariosRS.size()==0) ? 0.0 : suma * 1.0 / usuariosRS.size();
	}
	
	
	@Override
	public String toString() {
		return nombre + " - " + usuariosRS.size() + " usuarios: " + usuariosRS;
	}
	
}
