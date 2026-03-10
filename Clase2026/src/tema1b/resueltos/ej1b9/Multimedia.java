package tema1b.resueltos.ej1b9;

import javax.swing.JOptionPane;

public class Multimedia {
	private String nombre;
	
	/** Crea un nuevo objeto multimedia
     * @param nombre	Nuevo nombre. Si fuera nulo, el nombre se deja en string vacío
	 */
	public Multimedia(String nombre) {
		super();
        this.setNombre( nombre );
	}
	
	public String getNombre() {
		return nombre;
	}

    /** Cambia el nombre del objeto
     * @param nombre	Nuevo nombre. Si fuera nulo, el nombre se deja en string vacío
     */
    public void setNombre(String nombre) {
        if (nombre==null) {
            nombre = "";
            return;
        }
        this.nombre = nombre;        
    }

	public void pedir() {
		setNombre( JOptionPane.showInputDialog( "Introduce nombre:", nombre ) );
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Multimedia) {
			Multimedia m = (Multimedia) obj;
			return m.nombre.equals(nombre);
		} else if (obj instanceof String) {
			String string = (String) obj;
			return string.equals( this.nombre );
		} else {
			return false;
		}
	}

}
