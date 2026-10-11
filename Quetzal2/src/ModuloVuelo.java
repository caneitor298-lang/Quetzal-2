import java.util.Locale;

public class ModuloVuelo extends Modulo {
    private final String tipoInstrumento;
    private final double datosPorCiclo;
    private final double consumoEnergia;
    private int ciclosAcumulados;

    public ModuloVuelo(String id, String nombre, int salud, boolean activo,
                       double costoConstruccion, String tipoInstrumento,
                       double datosPorCiclo, double consumoEnergia, int ciclosAcumulados) {
        super(id, nombre, salud, activo, costoConstruccion);
        if (tipoInstrumento == null || !(tipoInstrumento.equalsIgnoreCase("Camara")
                || tipoInstrumento.equalsIgnoreCase("Cámara")
                || tipoInstrumento.equalsIgnoreCase("Sensor"))) {
            throw new IllegalArgumentException("El instrumento debe ser camara o sensor.");
        }
        if (!Double.isFinite(datosPorCiclo) || datosPorCiclo <= 0
                || !Double.isFinite(consumoEnergia) || consumoEnergia < 0
                || ciclosAcumulados < 0) {
            throw new IllegalArgumentException("Capacidad, consumo o ciclos de vuelo invalidos.");
        }
        this.tipoInstrumento = tipoInstrumento;
        this.datosPorCiclo = datosPorCiclo;
        this.consumoEnergia = consumoEnergia;
        this.ciclosAcumulados = ciclosAcumulados;
    }

    @Override
    public String procesarCiclo(Mision mision) {
        if (!isActivo()) {
            return getId() + " no opera: modulo inactivo.";
        }
        if (!mision.consumirEnergia(consumoEnergia)) {
            return getId() + " no opera: energia insuficiente.";
        }
        mision.agregarDatos(datosPorCiclo);
        ciclosAcumulados++;
        return String.format(Locale.ROOT, "%s consume %.2f unidades y recolecta %.2f MB.",
                getId(), consumoEnergia, datosPorCiclo);
    }

    public String getTipoInstrumento() { return tipoInstrumento; }
    public double getDatosPorCiclo() { return datosPorCiclo; }
    public double getConsumoEnergia() { return consumoEnergia; }
    public int getCiclosAcumulados() { return ciclosAcumulados; }

    public double getTotalRecolectado() {
        return datosPorCiclo * ciclosAcumulados;
    }

    @Override
    public String toString() {
        return "Tipo: Modulo de vuelo\n" + super.toString()
                + "\nInstrumento: " + tipoInstrumento
                + String.format(Locale.ROOT, "\nDatos por ciclo: %.2f MB\nConsumo de energia: %.2f"
                        + "\nCiclos exitosos: %d\nTotal historico recolectado: %.2f MB",
                        datosPorCiclo, consumoEnergia, ciclosAcumulados, getTotalRecolectado());
    }
}
