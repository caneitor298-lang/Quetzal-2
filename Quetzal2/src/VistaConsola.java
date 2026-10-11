import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class VistaConsola {
    private final Scanner lector;

    public VistaConsola() {
        lector = new Scanner(System.in, "UTF-8");
    }

    public int leerOpcion() {
        System.out.println("\n===== DEFENSA DE QTZ2 =====");
        System.out.println("1. Listar modulos");
        System.out.println("2. Buscar por ID o nombre");
        System.out.println("3. Consultar catalogo por costo");
        System.out.println("4. Avanzar un ciclo");
        System.out.println("5. Resumen de comunicaciones");
        System.out.println("0. Salir");
        while (true) {
            System.out.print("Seleccione una opcion: ");
            if (!lector.hasNextLine()) { return 0; }
            String entrada = lector.nextLine().trim();
            try {
                int opcion = Integer.parseInt(entrada);
                if (opcion >= 0 && opcion <= 5) { return opcion; }
            } catch (NumberFormatException e) {
                // Se solicita otra entrada sin terminar el programa.
            }
            System.out.println("Opcion invalida. Ingrese un numero entre 0 y 5.");
        }
    }

    public int leerTipoBusqueda() {
        System.out.println("1. Buscar por ID\n2. Buscar por nombre");
        while (true) {
            System.out.print("Tipo de busqueda: ");
            if (!lector.hasNextLine()) { return 1; }
            try {
                int opcion = Integer.parseInt(lector.nextLine().trim());
                if (opcion == 1 || opcion == 2) { return opcion; }
            } catch (NumberFormatException e) {
            }
            System.out.println("Ingrese 1 o 2.");
        }
    }

    public String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!lector.hasNextLine()) { return ""; }
            String texto = lector.nextLine().trim();
            if (!texto.isEmpty()) { return texto; }
            System.out.println("El texto no puede estar vacio.");
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarModulos(ArrayList<Modulo> modulos) {
        if (modulos.isEmpty()) {
            mostrarMensaje("No se encontraron modulos.");
            return;
        }
        for (Modulo modulo : modulos) {
            System.out.println("\n" + modulo.toString());
            System.out.println("----------------------------------------");
        }
    }

    public void mostrarCiclo(ArrayList<String> resultados, int ciclo, double energia,
                             double orbita, double descargado) {
        System.out.println("\n===== RESULTADOS DEL CICLO " + ciclo + " =====");
        for (String resultado : resultados) { System.out.println(resultado); }
        System.out.printf(Locale.ROOT, "Energia disponible: %.2f%nDatos en orbita: %.2f MB%n"
                + "Total historico descargado: %.2f MB%n", energia, orbita, descargado);
    }

    public void mostrarResumen(int total, int activos, double capacidad, double descargado,
                               ArrayList<ModuloTierra> mayores) {
        System.out.println("\n===== RESUMEN DE COMUNICACIONES =====");
        System.out.println("Modulos de tierra: " + total);
        System.out.println("Modulos de tierra activos: " + activos);
        System.out.printf(Locale.ROOT, "Capacidad total activa: %.2f MB por ciclo%n"
                + "Total historico descargado: %.2f MB%n", capacidad, descargado);
        if (mayores.isEmpty()) {
            mostrarMensaje("No hay modulos de tierra.");
            return;
        }
        System.out.println("Modulos con mayor historial de descarga:");
        for (ModuloTierra tierra : mayores) {
            System.out.printf(Locale.ROOT, "%s | ID: %s | Estacion: %s | Descargado: %.2f MB%n",
                    tierra.getNombre(), tierra.getId(), tierra.getEstacion(), tierra.getTotalDescargado());
        }
    }
}
