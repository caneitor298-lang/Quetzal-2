import java.util.Locale;

public class ModuloEnergia extends Modulo {
    private final double energiaPorCiclo;
    private int ciclosAcumulados;

    public ModuloEnergia(String id, String nombre, int salud, boolean activo,
                         double costoConstruccion, double energiaPorCiclo, int ciclosAcumulados) {
        super(id, nombre, salud, activo, costoConstruccion);
        if (!Double.isFinite(energiaPorCiclo) || energiaPorCiclo <= 0 || ciclosAcumulados < 0) {
            throw new IllegalArgumentException("La generacion debe ser positiva y los ciclos no negativos.");
        }
        this.energiaPorCiclo = energiaPorCiclo;
        this.ciclosAcumulados = ciclosAcumulados;
    }

    @Override
    public String procesarCiclo(Mision mision) {
        if (!isActivo()) {
            return getId() + " no opera: modulo inactivo.";
        }
        mision.agregarEnergia(energiaPorCiclo);
        ciclosAcumulados++;
        return String.format(Locale.ROOT, "%s genera %.2f unidades de energia.", getId(), energiaPorCiclo);
    }

    public double getEnergiaPorCiclo() { return energiaPorCiclo; }
    public int getCiclosAcumulados() { return ciclosAcumulados; }
    public double getTotalGenerado() { return energiaPorCiclo * ciclosAcumulados; }

    @Override
    public String toString() {
        return "Tipo: Modulo de energia\n" + super.toString()
                + String.format(Locale.ROOT, "\nEnergia por ciclo: %.2f\nCiclos exitosos: %d"
                        + "\nTotal historico generado: %.2f",
                        energiaPorCiclo, ciclosAcumulados, getTotalGenerado());
    }
}
