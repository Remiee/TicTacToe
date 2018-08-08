import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Map extends JFrame implements ActionListener {
    private int size;
    private JButton[][] buttons;
    private boolean firstPlayer;
    private int marksToWin;
    private int stepCount;


    public Map(int size) {
        size = Math.max(size, 3);
        this.size = size;
        buttons = new JButton[size][size];
        firstPlayer = true;
        marksToWin = 5;
        stepCount = 0;

        setSize(20 + size * 60, 20 + size * 60);
        setTitle("Tic Tac Toe");
        setLayout(new GridLayout(size, size));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        generateMap();
    }

    public void generateMap() {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        Insets insets = new Insets(0, 0, 0, 0);
        Color color = new Color(20, 50, 70);
        Color textColor = new Color (220, 220, 220);
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                JButton button = new JButton("");
                button.setFont(font);
                button.setForeground(textColor);
                button.setBackground(color);
                button.setOpaque(true);
                button.setMargin(insets);

                button.setActionCommand(y + " " + x);
                button.addActionListener(this);
                this.add(button);
                buttons[y][x] = button;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] coordinates = e.getActionCommand().split(" ");
        int y = Integer.valueOf(coordinates[0]);
        int x = Integer.valueOf(coordinates[1]);

        if (buttons[y][x].getText().isEmpty()) {
            if (firstPlayer) {
                buttons[y][x].setText("X");
                firstPlayer = false;
            } else {
                buttons[y][x].setText("O");
                firstPlayer = true;
            }
            ++stepCount;
        }
        if (someoneWon(x, y, buttons[y][x].getText())) {
            JOptionPane.showMessageDialog(this, "You won!");
            clear();
        } else if (stepCount == size * size) {
            JOptionPane.showMessageDialog(this, "The board is full.");
            clear();
        }
    }

    private boolean someoneWon(int x, int y, String player) {
        int numberOfMarks = 1 +
                countMarks(x, y, player, -1, 1) +
                countMarks(x, y, player, 1, -1);

        if (numberOfMarks >= marksToWin) {
            return true;
        }

        numberOfMarks = 1 +
                countMarks(x, y, player, -1, -1) +
                countMarks(x, y, player, 1, 1);

        if (numberOfMarks >= marksToWin) {
            return true;
        }

        numberOfMarks = 1 +
                countMarks(x, y, player, 0, -1) +
                countMarks(x, y, player, 0, 1);

        if (numberOfMarks >= marksToWin) {
            return true;
        }

        numberOfMarks = 1 +
                countMarks(x, y, player, -1, 0) +
                countMarks(x, y, player, 1, 0);

        if (numberOfMarks >= marksToWin) {
            return true;
        }
        return false;
    }

    private int countMarks(int x, int y, String player, int stepX, int stepY) {
        int j = x + stepX;
        int i = y + stepY;
        int counter = 0;

        while (coordsInBounds(i, j) && buttons[i][j].getText().equals(player)) {
            ++counter;
            j += stepX;
            i += stepY;
        }

        return counter;
    }

    private boolean coordsInBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private void clear() {
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                buttons[y][x].setText("");
            }
        }
        stepCount = 0;
    }
}
