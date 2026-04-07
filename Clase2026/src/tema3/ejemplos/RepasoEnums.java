package tema3.ejemplos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class RepasoEnums {

    public static void main(String[] args) {
        // Codificar colores de parchís
        int color = 3; // ¿qué era el 3?
        System.out.println( "Tu color es el " + color );
        // Enum codificar y asocia el nombre de ese código al código
        ColorParchis color2 = ColorParchis.AZUL;
        System.out.println( color2 );
        // Se puede convertir a string
        System.out.println( ColorParchis.AMARILLO.toString() );
        // Y se puede convertir desde string
        String colorString = "RoJO";
        try {
            ColorParchis color3 = ColorParchis.valueOf( colorString );
            System.out.println( color3 );
        } catch (IllegalArgumentException e) {
            System.out.println( "Conversión errónea de " + colorString );
        }
        // Recorrer
        for (ColorParchis c : ColorParchis.values()) {
            System.out.println( "Color " + c + " es el " + c.ordinal() );
        }
        // Enum códigos y valores limitados --> claves dentro de un mapa
        ArrayList<Ficha> l = new ArrayList<>( Arrays.asList(
            new Ficha(1,ColorParchis.ROJO), new Ficha(2,ColorParchis.AMARILLO),
            new Ficha(3,ColorParchis.AZUL), new Ficha(4,ColorParchis.AMARILLO),
            new Ficha(5,ColorParchis.ROJO), new Ficha(6,ColorParchis.AZUL),
            new Ficha(7,ColorParchis.VERDE), new Ficha(8,ColorParchis.AMARILLO)
        ));
        // Conteo de fichas
        HashMap<ColorParchis,Integer> mapa = new HashMap<>();
        for (Ficha f : l) {
            if (mapa.containsKey( f.color )) {
                mapa.replace( f.color, mapa.get(f.color) + 1);
            } else {
                mapa.put( f.color, 1 );
            }
        }
        System.out.println( mapa );
        // Lista de fichas
        TreeMap<ColorParchis,ArrayList<Ficha>> mapa2 = new TreeMap<>();
        for (Ficha f : l) {
            if (mapa2.containsKey( f.color )) {
                mapa2.get( f.color ).add( f );
            } else {
                mapa2.put( f.color, new ArrayList<>( Arrays.asList( f )) );
            }
        }
        System.out.println( mapa2 );
    }

}

class Ficha {
    public int num;
    public ColorParchis color;
    public Ficha(int num, ColorParchis color) {
        this.num = num;
        this.color = color;
    }
    @Override
    public String toString() {
        return num + "-" + color;
    }
    
}


enum ColorParchis {   // implements Comparable   y también hashCode + equals
    ROJO, VERDE, AZUL, AMARILLO
    ;  // Obligatorio si hay más cosas
    // public static final ROJO = new ColorParchis(0);
    // public static final VERDE = new ColorParchis(1);
    // public static ColorParchis[] values() {
    //    return new ColorParchis[] { ROJO, VERDE, AZUL, AMARILLO };
    // }
    // public int ordinal()   0-rojo 1-verde etc.

    // atributos
    // métodos
    public double porcentajeVictorias() {
        if (this==ROJO) {
            return 25.4;
        } else if (this==VERDE) {
            return 24.8;
        } else if (this==AZUL) {
            return 24.3;
        } else {
            return 25.2;
        }
    }
}