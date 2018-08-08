import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Map map = new Map(10);
            map.setVisible(true);
        });

    }
}
