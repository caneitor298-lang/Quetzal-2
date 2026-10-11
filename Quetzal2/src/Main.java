public class Main {
    public static void main(String[] args) {
        Mision mision = new Mision();
        VistaConsola vista = new VistaConsola();
        Controlador controlador = new Controlador(mision, vista);
        controlador.iniciar();
    }
}
