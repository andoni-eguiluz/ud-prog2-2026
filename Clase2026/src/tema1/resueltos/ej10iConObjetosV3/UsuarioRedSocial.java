package tema1.resueltos.ej10iConObjetosV3;

public class UsuarioRedSocial {

    private String nombre = "";
    private int numMilesSeguidores = 0; // da igual = 0 que nada

    /** Crea un nuevo usuario
     * @param nombre    Nombre de ese usuario
     * @param numMilesSeguidores Número de seguidores, en miles
     */
    public UsuarioRedSocial( String nombre, int numMilesSeguidores ) {
        this.nombre = nombre;
        this.setNumMilesSeguidores( numMilesSeguidores );
    }

    /** Crea un nuevo usuario de red social con 0 seguidores
     * @param nombre    Nombre del nuevo usuario
     */
    public UsuarioRedSocial( String nombre ) {
        this.nombre = nombre;
        this.setNumMilesSeguidores( 0 );
    }

    /** Compara los seguidores de este usuario y otro
     * @param u2    Segundo usuario que comparar
     * @return  true si this tiene menos seguidores que u2, false en caso contrario
     */
    public boolean tieneMenosSeguidoresQue( UsuarioRedSocial u2 ) {
        return this.numMilesSeguidores < u2.numMilesSeguidores;
    }

    /** Convierte a string el usuario, con su nombre, tabulador y sus seguidores
     * @return  String formateado
     */
    public String toString() {
        return this.nombre + "\t" + this.numMilesSeguidores;
    }

    public String getNombre() {
        return this.nombre;
    }

    // Si nombre es inmutable, nos vale con no publicar set
    // /** Modifica el nombre del usuario
    //  * @param nombre    Nuevo nombre
    //  */
    // public void setNombre( String nombre ) {
    //     this.nombre = nombre;
    // }

    public int getNumMilesSeguidores() {
        return numMilesSeguidores;
    }

    public void setNumMilesSeguidores( int numMilesSeguidores ) {
        this.numMilesSeguidores = numMilesSeguidores;
        if (this.numMilesSeguidores < 0) {
            this.numMilesSeguidores = 0;
            System.out.println( "Error en cambio de usuario: seguidores negativos" );
        }
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        UsuarioRedSocial usuario2 = (UsuarioRedSocial) obj; ///!!!!OJO
        return this.nombre.equals( usuario2.nombre );
    }

}
