package fabrica;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class Lijadora implements Maquina {

    private Grosor grosor;
    private OrLija orLija;
    private Posicion pos;

    static Set<Posicion> posicionesValidas = EmunSet.of(
            Posicion.IzSu,
            Posicion.IzCe,
            Posicion.IzIn,
            Posicion.CeSu,
            Posicion.CeCe+


    );

    public Fresadora(Grosor grosor, OrLija orLija, Posicion pos) {

        if(!posicionesValidas.contains(posicion)){
            throw new IllegalArgumentException("Posición no válida: "+pos)
        }
        this.grosor = grosor;
        this.orLija = orLija;
        this.pos = pos;
    }

    @Override
    public void actua(Pieza pieza) {
        /// Obtener el cuadro en la posicion de la pieza
        Cuadro cuadro = pieza.getCuadro(posicion);

        /// Lista de las taladradoras en el cuadro
        List<String> taladradoras = cuadro.getTaladradoras();

        ///  Comprobar si hay taladradoras en la lista
        for(String taladradora : taladradoras){
            if(taladradora.matches("TL[1-3]")){
                /// Extraer el grosor de la taladradora
                int grosorTaladradora = Integer.paserInt(taladradora.substring(2));
                /// Comparacion del grosor
                if(grosorToValue(grosor)>grosorTaladradora){
                    /// Eliminar el taladro si el grosor es menor que el de la lija
                    eliminarTaladradora(cuadro, taladradora)
                }
            }
        }

        /// Obtener la lija
        String lijaActual = cuadro.getLija(posicion);

        if(lijaActual.isBlanck() || lijaActual.matches("L[NOSE][123]")){
            /// Coger el valor del grosor de la lija que hay
            int grosorExistente = lijaActual.matches("L[NSOE][1-3]")
                    ? Integer.parseInt(lijaActual.substring(2))
                    : 0;
            int grosorLijadora = grosorToValue(grosor);
            /// Comparar el grosor y comprobar la orientacion
            if(grosorLijadora> grosor Existente && orLija.equals(lijaActual.charAt(1))){
                /// si el grosor de la nueva lija es mayor y tienen la misma orientacion, se actualiza la lija en la posicion
                cuadro.setLija(posicion, "L" + lijaActual.charAt(1) + grosorLijadora);
            }else {
                /// Si no, se añade una nueva lija
                cuadro.addLija(pos, "L" + orLija + grosorLijadora);
            }
        }
    }
    /// Convertir el grosor a un valor entero
    int grosorToValue(Grosor grosor) {
        switch (grosor) {
            case Grueso:
                grosor = 3;
                break;
            case Medio:
                grosor = 2;
                break;
            case Fino:
            default:
                grosor = 1;
                break;
        }
    }

    /// Eliminar la taladradora
    private void eliminarTaladradora(Cuadro cuadro, String taladradora) {
        cuadro.removerTaladradora(taladradora);
    }

}
