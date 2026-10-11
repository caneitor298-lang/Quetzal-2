import java.util.Locale;

public class ModuloTierra extends Modulo {
    private final String estacion;
    private final double capacidadDescarga;
    private final double energiaDescarga;
    private double totalDescargado;

    public ModuloTierra(String id, String nombre, int salud, boolean activo,
                        double costoConstruccion, String estacion, double capacidadDescarga,
                        double energiaDescarga, double totalDescargado) {
        super(id, nombre, salud, activo, costoConstruccion);
        if (estacion == null || estacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La estacion es obligatoria.");
        }
        if (!Double.isFinite(capacidadDescarga) || capacidadDescarga <= 0
                || !Double.isFinite(energiaDescarga) || energiaDescarga < 0
                || !Double.isFinite(totalDescargado) || totalDescargado < 0) {
            throw new IllegalArgumentException("Capacidad, consumo o historial de tierra invalidos.");
        }
        this.estacion = estacion.trim();
        this.capacidadDescarga = capacidadDescarga;
        this.energiaDescarga = energiaDescarga;
        this.totalDescargado = totalDescargado;
    }

    @Override
    public String procesarCiclo(Mision mision) {
        if (!isActivo()) {
            return getId() + " no opera: modulo inactivo.";
        }
        if (mision.getDatosEnOrbita() <= 0) {
            return getId() + " no opera: no hay datos pendientes en orbita.";
        }
        // Se cobra el consumo completo incluso al descargar pocos MB.
        if (!mision.consumirEnergia(energiaDescarga)) {
            return getId() + " no opera: energia insuficiente.";
        }
        double descargados = mision.descargarDatos(capacidadDescarga);
        totalDescargado += descargados;
        return String.format(Locale.ROOT, "%s consume %.2f unidades y descarga %.2f MB.",
                getId(), energiaDescarga, descargados);
    }

    public String getEstacion() { return estacion; }
    public double getCapacidadDescarga() { return capacidadDescarga; }
    public double getEnergiaDescarga() { return energiaDescarga; }
    public double getTotalDescargado() { return totalDescargado; }

    @Override
    public String toString() {
        return "Tipo: Modulo de tierra\n" + super.toString() + "\nEstacion: " + estacion
                + String.format(Locale.ROOT, "\nCapacidad de descarga: %.2f MB\nEnergia por descarga: %.2f"
                        + "\nTotal historico descargado: %.2f MB",
                        capacidadDescarga, energiaDescarga, totalDescargado);
    }
}
