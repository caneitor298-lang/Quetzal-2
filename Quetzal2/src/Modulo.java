import java.util.Locale;

public abstract class Modulo {
    private final String id;
    private final String nombre;
    private final int salud;
    private final boolean activo;
    private final double costoConstruccion;

    protected Modulo(String id, String nombre, int salud, boolean activo,
                     double costoConstruccion) {
        if (id == null || id.trim().isEmpty() || nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID y el nombre son obligatorios.");
        }
        if (salud < 0 || salud > 100) {
            throw new IllegalArgumentException("La salud debe estar entre 0 y 100.");
        }
        if (!Double.isFinite(costoConstruccion) || costoConstruccion < 0) {
            throw new IllegalArgumentException("El costo debe ser un numero no negativo.");
        }
        this.id = id.trim();
        this.nombre = nombre.trim();
        this.salud = salud;
        this.activo = activo;
        this.costoConstruccion = costoConstruccion;
    }

    public abstract String procesarCiclo(Mision mision);

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getSalud() { return salud; }
    public boolean isActivo() { return activo; }
    public double getCostoConstruccion() { return costoConstruccion; }

    @Override
    public String toString() {
        return "ID: " + id + "\nNombre: " + nombre
                + "\nSalud: " + salud + "\nActivo: " + (activo ? "Si" : "No")
                + String.format(Locale.ROOT, "\nCosto de construccion: %.2f", costoConstruccion);
    }
}
