package tema1c.resueltos.registros;

public interface RegistroDeUsuario {
	public String getUsuario(); // Devuelve el nombre del usuario
	public void setUsuario(String usuario); // Modifica el usuario
	public boolean esDeUsuario( String usuario ); // Informa si el usuario es o no igual a uno dado
}
