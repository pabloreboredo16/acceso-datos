package interfaces.saludos;

public class MainSaludable {
    public static void main(String[] args) {

        Persona persona = new Persona("Pablo");
        Robot robot = new Robot("R2D2");

        persona.saludar();
        robot.saludar();
    }
}