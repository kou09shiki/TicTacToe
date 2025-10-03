import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean playerX = true;
    private Timer clickAnimateTimer;
    private JButton lastClicked;

    public TicTacToe() {
        setTitle("Bubbly Tic Tac Toe 🎉");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        Font bubblyFont = new Font("Comic Sans MS", Font.BOLD, 40);

        // Initialize buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               JButton button = new JButton("");
button.setFont(bubblyFont);
button.setBackground(new Color(255, 200, 220));
button.setFocusPainted(false);
button.setBorder(new LineBorder(Color.PINK, 3, true));
button.setBorderPainted(true);
button.addActionListener(this);
buttons[i][j] = button;
add(button);

// Optional hover effect
button.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(250, 174, 180));
    }
    public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(255, 200, 220));
    }
});
    // Timer for bubbly click animation
       clickAnimateTimer = new Timer(100, new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        animateClick();
    }
});
            }
        }
    }  
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        if (!button.getText().equals("")) return;

        button.setText(playerX ? "X" : "O");
        button.setForeground(playerX ? Color.BLUE : Color.RED);

        lastClicked = button;
        clickAnimateTimer.start();

        if (checkWinner()) {
            JOptionPane.showMessageDialog(this,
                    (playerX ? "Player X" : "Player O") + " wins! 🎊");
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw! 😅");
            resetBoard();
        }

        playerX = !playerX;
    }

    private void animateClick() {
        if (lastClicked == null) return;

        Color original = lastClicked.getBackground();
        lastClicked.setBackground(new Color(255, 150, 200));

        // Reset back to normal after one tick
        clickAnimateTimer.stop();
        Timer resetTimer = new Timer(150, _ -> lastClicked.setBackground(original));
        resetTimer.setRepeats(false);
        resetTimer.start();
    }

    private boolean checkWinner() {
        // Check rows & cols
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().equals("")
                    && buttons[i][0].getText().equals(buttons[i][1].getText())
                    && buttons[i][1].getText().equals(buttons[i][2].getText()))
                return true;

            if (!buttons[0][i].getText().equals("")
                    && buttons[0][i].getText().equals(buttons[1][i].getText())
                    && buttons[1][i].getText().equals(buttons[2][i].getText()))
                return true;
        }

        // Check diagonals
        if (!buttons[0][0].getText().equals("")
                && buttons[0][0].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][2].getText()))
            return true;

        if (!buttons[0][2].getText().equals("")
                && buttons[0][2].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][0].getText()))
            return true;

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals("")) return false;
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(new Color(255, 200, 220));
            }
        playerX = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TicTacToe().setVisible(true);
        });
    }
}
