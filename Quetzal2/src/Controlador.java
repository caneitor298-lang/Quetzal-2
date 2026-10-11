import java.util.ArrayList;

public class Controlador {
    private final Mision mision;
    private final VistaConsola vista;

    public Controlador(Mision mision, VistaConsola vista) {
        this.mision = mision;
        this.vista = vista;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = vista.leerOpcion();
            switch (opcion) {
                case 1:
                    vista.mostrarModulos(mision.listarModulos());
                    break;
                case 2:
                    if (vista.leerTipoBusqueda() == 1) {
                        String id = vista.leerTexto("Ingrese el ID: ");
                        Modulo encontrado = mision.buscarPorId(id);
                        ArrayList<Modulo> resultados = new ArrayList<Modulo>();
                        if (encontrado != null) { resultados.add(encontrado); }
                        vista.mostrarModulos(resultados);
                    } else {
                        String nombre = vista.leerTexto("Ingrese el nombre completo: ");
                        vista.mostrarModulos(mision.buscarPorNombre(nombre));
                    }
                    break;
                case 3:
                    vista.mostrarMensaje("CATALOGO POR COSTO ASCENDENTE");
                    vista.mostrarModulos(mision.obtenerCatalogoOrdenado());
                    break;
                case 4:
                    ArrayList<String> resultados = mision.avanzarCiclo();
                    vista.mostrarCiclo(resultados, mision.getCicloActual(),
                            mision.getEnergiaDisponible(), mision.getDatosEnOrbita(),
                            mision.getTotalDescargado());
                    break;
                case 5:
                    vista.mostrarResumen(mision.getCantidadTierra(), mision.getCantidadTierraActivos(),
                            mision.getCapacidadDescargaActiva(), mision.getTotalDescargado(),
                            mision.obtenerMayoresDescargas());
                    break;
                case 0:
                    vista.mostrarMensaje("Programa finalizado.");
                    break;
                default:
                    vista.mostrarMensaje("Opcion invalida.");
            }
        } while (opcion != 0);
    }
}
