package tema1c.interfacesAvanzados;

public class Test implements InterfazConDefault { // Probar A; también B; también  A, B { - ¿por qué da error?
	
	public static void main(String[] args) {
		Test test = new Test();
		System.out.println( test.comportamiento2() );
	}
	
	@Override
	public void comportamiento1() {
		System.out.println( "Test" );
	}
	
}
