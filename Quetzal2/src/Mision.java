import java.util.ArrayList;
import java.util.Locale;

public class Mision {
    private ArrayList<Modulo> modulos;
    private double energiaDisponible;
    private double datosEnOrbita;
    private double totalDescargado;
    private int cicloActual;

    public Mision() {
        modulos = new ArrayList<Modulo>();
        cargarInicial();
    }


    private void cargarInicial() {
        modulos.add(new ModuloEnergia("E01", "Panel Norte", 100, true, 500, 25, 3));
        modulos.add(new ModuloVuelo("V01", "Cámara UVG", 95, true, 1200, "Cámara", 15, 8, 2));
        modulos.add(new ModuloTierra("T01", "Antena Central", 90, true, 800,
                "Estación Central UVG", 10, 5, 30));
        modulos.add(new ModuloEnergia("E02", "Panel Sur", 85, true, 450, 20, 2));
        modulos.add(new ModuloVuelo("V02", "Sensor Solar", 90, true, 1000, "Sensor", 20, 10, 3));
        modulos.add(new ModuloTierra("T02", "Antena Oriente", 80, true, 950,
                "Estación Oriente UVG", 20, 6, 40));
        modulos.add(new ModuloEnergia("E03", "Panel Auxiliar", 70, false, 650, 30, 2));
        modulos.add(new ModuloVuelo("V03", "Sensor Térmico", 85, true, 700, "Sensor", 10, 6, 4));
        modulos.add(new ModuloTierra("T03", "Antena Reserva", 75, false, 600,
                "Estación Reserva UVG", 20, 7, 20));
        modulos.add(new ModuloVuelo("V04", "Cámara Reserva", 80, true, 1100, "Cámara", 20, 9, 2));

        energiaDisponible = 53;
        datosEnOrbita = 80;
        cicloActual = 5;
        totalDescargado = 0;
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloTierra) {
                ModuloTierra tierra = (ModuloTierra) modulo;
                totalDescargado += tierra.getTotalDescargado();
            }
        }
    }

  
    public ArrayList<Modulo> listarModulos() {
        return new ArrayList<Modulo>(modulos);
    }

    public Modulo buscarPorId(String id) {
        if (id == null) { return null; }
        for (Modulo modulo : modulos) {
            if (modulo.getId().equalsIgnoreCase(id.trim())) { return modulo; }
        }
        return null;
    }

    public ArrayList<Modulo> buscarPorNombre(String nombre) {
        ArrayList<Modulo> encontrados = new ArrayList<Modulo>();
        if (nombre == null) { return encontrados; }
        for (Modulo modulo : modulos) {
            if (modulo.getNombre().equalsIgnoreCase(nombre.trim())) {
                encontrados.add(modulo);
            }
        }
        return encontrados;
    }

    public ArrayList<Modulo> obtenerCatalogoOrdenado() {
        ArrayList<Modulo> catalogo = listarModulos();
        for (int i = 0; i < catalogo.size() - 1; i++) {
            for (int j = 0; j < catalogo.size() - 1 - i; j++) {
                Modulo actual = catalogo.get(j);
                Modulo siguiente = catalogo.get(j + 1);
                if (actual.getCostoConstruccion() > siguiente.getCostoConstruccion()
                        || (actual.getCostoConstruccion() == siguiente.getCostoConstruccion()
                        && actual.getId().compareTo(siguiente.getId()) > 0)) {
                    catalogo.set(j, siguiente);
                    catalogo.set(j + 1, actual);
                }
            }
        }
        return catalogo;
    }

    public ArrayList<String> avanzarCiclo() {
        ArrayList<String> resultados = new ArrayList<String>();
        for (Modulo modulo : modulos) {
            String resultado = modulo.procesarCiclo(this);
            resultados.add(resultado + String.format(Locale.ROOT,
                    " Recursos: energia=%.2f; orbita=%.2f MB; descargado=%.2f MB.",
                    energiaDisponible, datosEnOrbita, totalDescargado));
        }
        cicloActual++;
        return resultados;
    }

    public boolean consumirEnergia(double cantidad) {
        if (!Double.isFinite(cantidad) || cantidad < 0) {
            throw new IllegalArgumentException("El consumo no puede ser negativo ni infinito.");
        }
        if (energiaDisponible < cantidad) { return false; }
        energiaDisponible -= cantidad;
        return true;
    }

    public void agregarEnergia(double cantidad) {
        if (!Double.isFinite(cantidad) || cantidad < 0) {
            throw new IllegalArgumentException("La energia agregada debe ser no negativa.");
        }
        energiaDisponible += cantidad;
    }

    public void agregarDatos(double cantidad) {
        if (!Double.isFinite(cantidad) || cantidad < 0) {
            throw new IllegalArgumentException("Los datos agregados deben ser no negativos.");
        }
        datosEnOrbita += cantidad;
    }

    public double descargarDatos(double capacidad) {
        if (!Double.isFinite(capacidad) || capacidad < 0) {
            throw new IllegalArgumentException("La capacidad debe ser no negativa.");
        }
        double descargados = Math.min(capacidad, datosEnOrbita);
        datosEnOrbita -= descargados;
        totalDescargado += descargados;
        return descargados;
    }

    public int getCantidadTierra() {
        int cantidad = 0;
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloTierra) { cantidad++; }
        }
        return cantidad;
    }

    public int getCantidadTierraActivos() {
        int cantidad = 0;
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloTierra && modulo.isActivo()) { cantidad++; }
        }
        return cantidad;
    }

    public double getCapacidadDescargaActiva() {
        double capacidad = 0;
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloTierra && modulo.isActivo()) {
                capacidad += ((ModuloTierra) modulo).getCapacidadDescarga();
            }
        }
        return capacidad;
    }

    public ArrayList<ModuloTierra> obtenerMayoresDescargas() {
        ArrayList<ModuloTierra> mayores = new ArrayList<ModuloTierra>();
        double maximo = -1;
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloTierra) {
                ModuloTierra tierra = (ModuloTierra) modulo;
                if (tierra.getTotalDescargado() > maximo) {
                    maximo = tierra.getTotalDescargado();
                    mayores.clear();
                    mayores.add(tierra);
                } else if (tierra.getTotalDescargado() == maximo) {
                    mayores.add(tierra);
                }
            }
        }
        return mayores;
    }

    public double getEnergiaDisponible() { return energiaDisponible; }
    public double getDatosEnOrbita() { return datosEnOrbita; }
    public double getTotalDescargado() { return totalDescargado; }
    public int getCicloActual() { return cicloActual; }
}
