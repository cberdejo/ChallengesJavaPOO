package fabrica;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Taladradora implements Maquina{
    private Posicion posicion;
    private Grosor grosor;

    static Set<Posicion> posicionesValidas = EnumSet.of(
            Posicion.IzSu,
            Posicion.IzCe,
            Posicion.IzIn,
            Posicion.CeSu,
            Posicion.CeCe
    );

    public Taladradora(Posicion posicion, Grosor grosor) {
        if (!posicionesValidas.contains(posicion)) {
            throw new IllegalArgumentException("Posición inválida: " + posicion);
        }
    }




    @Override
    public void actua(Pieza pieza) {
        // Obtiene el cuadro en la posición de la pieza
        Cuadro cuadro = pieza.getCuadro(posicion);

        // Obtiene la lista de lijas en el cuadro
        List<String> lijas = cuadro.getLijas();

        // Encontrar una lija de grosor 1 en cualquier cuadrante
        Optional<String> lija = lijas.stream()
                .filter(l -> l.matches("L[NOSE]1"))
                .findFirst();

        // Si se encuentra una lija con grosor 1
        lija.ifPresent(l -> {
            // Extraer el cuadrante de la lija (segundo carácter)
            String cuadrante = l.substring(1, 2);

            // Asigna null a la lija en el cuadrante correspondiente
            switch (cuadrante) {
                case "N":
                    cuadro.setLijaNorte(null);
                    break;
                case "S":
                    cuadro.setLijaSur(null);
                    break;
                case "E":
                    cuadro.setLijaEste(null);
                    break;
                case "O":
                    cuadro.setLijaOeste(null);
                    break;
                default:
                    throw new RuntimeException("Cuadrante no válido: " + cuadrante);
            }
        });

        // Determinar el grosor de la taladradora basado en el grosor de esta pieza
        int grosor;
        switch (this.grosor) {
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

        // Obtener el estado actual de la taladradora en el cuadro
        String taladradoraActual = cuadro.getTaladradora();

        // Verificar si la taladradora está vacía o tiene un grosor permitido (TL1, TL2, TL3)
        if (taladradoraActual.isBlank() || taladradoraActual.matches("TL[1-3]")) {
            // Extraer el grosor existente si la taladradora contiene un valor TL
            int grosorExistente = taladradoraActual.matches("TL[1-3]")
                    ? Integer.parseInt(taladradoraActual.substring(2))
                    : 0;

            // Si el grosor actual es mayor que el existente o la taladradora está vacía
            if (grosor > grosorExistente) {
                cuadro.setTaladradora("TL" + grosor);
            }
        }
    }


}
