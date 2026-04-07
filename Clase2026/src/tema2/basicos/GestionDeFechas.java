package tema2.basicos;

// Estos tres imports son los más habituales al trabajar con fechas
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import java.text.DateFormat;
import java.text.ParseException;


/** Ejemplo de gestión sencilla de fechas en Java
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class GestionDeFechas {

	// El throws ParseException lo entenderemos más adelante al ver excepciones
	public static void main(String[] args) throws ParseException {
		comoFuncionanLasFechas();
		formateoYParsingDeFechas();
		cogiendoDatosDelObjetoDate();
	}
	
	private static void comoFuncionanLasFechas() {
		// El tipo para gestionar fechas en Java es la clase java.util.Date:
		Date d1 = new Date();  // El objeto Date guarda una fecha/hora todo junto. Por defecto se crea con la fecha/hora de hoy y ahora 
		System.out.println( d1 ); // El formato por defecto es un poco feo, luego lo cambiamos: Thu Mar 14 19:52:32 CET 2019
		
		// ¿Qué hay "dentro" de un objeto Date? Realmente solo hay un long, que de hecho se puede usar en el constructor:
		Date d2 = new Date( 0L );  // Fecha construida con el long 0
		System.out.println( d2 );  // Las 0 horas GMT del 1 de enero de 1970, por convenio
		
		// A partir del 1 de enero de 1970 se cuentan los milisegundos que han pasado. Un long nos daría para bastante porque llega hasta...
		d2 = new Date( Long.MAX_VALUE );
		System.out.println( d2 ); // Aquí nada menos (año 292.278.994 DC)  [no hay peligro de otro "efecto 2000"]

		// Y también vale en negativo, contando milisegundos "antes" del 1 de enero de 1970. También llega hasta el año 292.269.055 AC
		d2 = new Date( Long.MIN_VALUE );
		System.out.println( d2 + " (este antes de Cristo)" );
		
		// En cualquier momento se puede sacar los milisegundos de una fecha con getTime():
		System.out.println( "Milisegundos de ahora: " + d1.getTime() );
		
	}
	
	private static void formateoYParsingDeFechas() throws ParseException {
		System.out.println();
		// Para formatear de forma más precisa una fecha podemos usar la clase DateFormat:
		Date d1 = new Date();
		DateFormat dfLocal = DateFormat.getDateInstance();
		System.out.println( "Día de hoy formato estándar: " + dfLocal.format(d1) );
		DateFormat dfLocalLargo = DateFormat.getDateInstance( DateFormat.LONG );
		System.out.println( "Día de hoy formato largo: " + dfLocalLargo.format(d1) );
		DateFormat dfLocalDiaYHora = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.LONG );
		System.out.println( "Día de hoy formato largo y hora: " + dfLocalDiaYHora.format(d1) );

		// Pero es más personalizable con objetos de la clase SimpleDateFormat (método format()):
		SimpleDateFormat f1 = new SimpleDateFormat( "d/M" ); // d significa día del mes, M = número del mes
		System.out.println( "Día de hoy formateado 1: " + f1.format(d1) );
		SimpleDateFormat f2 = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" ); // y = año (si se ponen varios se maqueta con esos dígitos), H=hora, m=minuto, s=segundos
		System.out.println( "Día de hoy formateado 2: " + f2.format(d1) );
		
		// Documentación completa de formateo:
		// https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html

		System.out.println();
		
		// Los formateadores también valen para convertir de string a fecha, con el método parse()
		String ej = "28/12/2015 3:2:1";
		Date d3 = f2.parse(ej);
		System.out.println( "Fecha creada partiendo de " + ej + " ==> " + f2.format(d3) );
	}

	
	private static void cogiendoDatosDelObjetoDate() throws ParseException {
		System.out.println();
		// Si se quiere operar con una fecha para obtener sus datos particulares,
		// hay métodos que están desaconsejados (deprecated):
		Date ahora = new Date( System.currentTimeMillis() );
		System.out.println( "Día de ahora getDay() = " + ahora.getDay() );
		
		// Se podría sacar formateando pero es más apropiado con la clase GregorianCalendar:
		GregorianCalendar gc = new GregorianCalendar();
		System.out.println( "Día de hoy con GregorianCalendar: " + gc.get( GregorianCalendar.DAY_OF_MONTH ) );
		
		// Que tiene unas cuantas constantes para cada cosa que queramos:
		System.out.println( "Mes: " + gc.get( GregorianCalendar.MONTH ) );
		System.out.println( "Año: " + gc.get( GregorianCalendar.YEAR ) );
		System.out.println( "Hora: " + gc.get( GregorianCalendar.HOUR_OF_DAY ) );
		System.out.println( "Minutos: " + gc.get( GregorianCalendar.MINUTE ) );
		System.out.println( "Segundos: " + gc.get( GregorianCalendar.SECOND ) );
		
		// Y un objeto GregorianCalendar también se puede reasignar.
		// Por ejemplo la hora que sería mañana a estas horas:
		GregorianCalendar gc2 = new GregorianCalendar();
		gc2.setTime( new Date( ahora.getTime() + 24*60*60*1000L ) );
		System.out.println( "Mañana es " + gc2.get( GregorianCalendar.DAY_OF_MONTH ) );
		
		// Tanto los longs, como date, como gregorian calendar se pueden comparar:
		System.out.println( "Comparación de " + gc.getTime() + " y " + gc2.getTime() + ": " + gc.compareTo( gc2 ) );
		
		// Se puede asignar también con datos específicos, por ejemplo si nos meten una fecha concreta por teclado o ventana.
		// Ejemplo de asignación de una fecha concreta:  25/5/2020 10:00
		// Una manera - con simpledateformat
		SimpleDateFormat f3 = new SimpleDateFormat( "dd/MM/yyyy HH:mm" );
		Date fechaConcreta1 = f3.parse( "25/05/2020 10:00" );
		System.out.println( fechaConcreta1 );
		// Otra manera - con gregoriancalendar
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.set( GregorianCalendar.YEAR, 2020 );
		gcal.set( GregorianCalendar.MONTH, 4 );  // OJO QUE EL MES EMPIEZA EN 0 (mayo = 4)
		gcal.set( GregorianCalendar.DAY_OF_MONTH, 25 );
		gcal.set( GregorianCalendar.HOUR_OF_DAY, 10 );
		gcal.set( GregorianCalendar.MINUTE, 0 );
		gcal.set( GregorianCalendar.SECOND, 0 );
		Date fechaConcreta2 = gcal.getTime();
		System.out.println( fechaConcreta2 );
	}

}
