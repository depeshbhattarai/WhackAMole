import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WhackAMole extends JFrame {

    private static final int GRID_SIZE = 3; // Grid size for the game (3x3)
    private static final int MOLE_COUNT = 1; // Number of moles to appear at a time
    private JButton[][] buttons; // 2D array for buttons representing holes
    private Random random; // Random object to place moles randomly
    private int score = 0; // Player's score
    private JLabel scoreLabel; // Label to display score
    private ImageIcon moleIcon;
    private ImageIcon hammerIcon;

    public WhackAMole() {
        // Set up the frame
        setTitle("Whack-A-Mole Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load images
        moleIcon = new ImageIcon("mole.png");
        hammerIcon = new ImageIcon("hammer.png");

        // Initialize components
        buttons = new JButton[GRID_SIZE][GRID_SIZE];
        random = new Random();
        JPanel panel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        
        // Create buttons for the grid
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.LIGHT_GRAY);
                buttons[i][j].addActionListener(new ButtonClickListener());
                panel.add(buttons[i][j]);
            }
        }

        // Add the panel with buttons to the frame
        add(panel, BorderLayout.CENTER);

        // Create and add the score label
        scoreLabel = new JLabel("Score: 0");
        add(scoreLabel, BorderLayout.SOUTH);

        // Start the game
        Timer timer = new Timer(1000, new TimerListener());
        timer.start();
    }

    // Method to randomly place moles on the grid
    private void placeMoles() {
        // First, clear the grid
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setIcon(null);
            }
        }

        // Then, place moles randomly
        for (int i = 0; i < MOLE_COUNT; i++) {
            int x = random.nextInt(GRID_SIZE);
            int y = random.nextInt(GRID_SIZE);
            buttons[x][y].setIcon(moleIcon);
        }
    }

    // Inner class to handle button clicks
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (button.getIcon() == moleIcon) {
                score++;
                scoreLabel.setText("Score: " + score);
                button.setIcon(hammerIcon);

                // Revert back to default after 500ms
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button.setIcon(null);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    // Inner class to handle timer events
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            placeMoles();
        }
    }

    // Main method to run the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WhackAMole().setVisible(true);
            }
        });
    }
}
