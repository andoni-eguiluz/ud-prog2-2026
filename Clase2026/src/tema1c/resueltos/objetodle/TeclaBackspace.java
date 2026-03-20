package tema1c.resueltos.objetodle;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class TeclaBackspace extends Elemento implements TeclaEspecial, SensibleAlRaton {

	public TeclaBackspace() {
		super( EstadoElemento.TECLA );
	}

	@Override
	public Elemento duplicar() {
		return new TeclaBackspace();
	}

	@Override
	public char aCaracter() {
		return (char) KeyEvent.VK_BACK_SPACE;
	}

	@Override
	public void procesaTecla(Objetodle objetodle) {
		Combi intento = objetodle.getIntentoActual();
		if (intento.getCombinacion().size()>0) {
			// Quita el último
			intento.getCombinacion().remove( intento.getCombinacion().size()-1 );
		}
	}

	@Override
	public void dibuja() {
		super.dibuja();
		objetodle.dibujaTextoCentrado( x, y, tam, tam, "BS", Letra.TIPO_LETRA, Color.BLACK, true );
	}

	@Override
	public void dibujaInverso() {
		objetodle.dibujaRect( x, y, tam, tam, 2f, Color.WHITE, Color.BLACK );
		objetodle.dibujaTextoCentrado( x, y, tam, tam, "BS", Letra.TIPO_LETRA, Color.WHITE, true );
	}

}
