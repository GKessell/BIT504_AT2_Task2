import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    // Constants for game
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic-Tac-Toe";

    // Constants for dimensions used for drawing
    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    // Game state enumeration
    public enum GameState {
        Playing, Draw, Cross_won, Nought_won
    }

    // Game object variables
    private Board board;
    private GameState currentState;
    private Player currentPlayer;
    private JLabel statusBar;

    /** Constructor to setup the UI and game components on the panel */
    public GameMain() {
        this.addMouseListener(this); // Add the mouse listener to the panel

        // Setup the status bar
        statusBar = new JLabel(" ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        // Layout of the panel
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        board = new Board(); // Create a new instance of the Board class
        initGame(); // Initialise the game board
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.add(new GameMain()); // Create the GameMain panel and add it to the frame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /** Custom painting codes on this JPanel */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        board.paint(g);

        // Set status bar message
        if (currentState == GameState.Playing) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == Player.Cross) {
                statusBar.setText("X's Turn"); // Display X's turn message
            } else {
                statusBar.setText("O's Turn"); // Display O's turn message
            }
        } else if (currentState == GameState.Draw) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    /** Initialise the game-board contents and the current status of GameState and Player) */
    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board.cells[row][col].content = Player.Empty; // All cells empty
            }
        }
        currentState = GameState.Playing;
        currentPlayer = Player.Cross;
    }

    /** After each turn check if the current player has won or if it's a draw */
    public void updateGame(Player thePlayer, int row, int col) {
        if (board.hasWon(thePlayer, row, col)) {
            currentState = (thePlayer == Player.Cross) ? GameState.Cross_won : GameState.Nought_won; // Update state to the winner
        } else if (board.isDraw()) {
            currentState = GameState.Draw; // Set state to draw
        }
    }

    /** Event handler for mouse click on the JPanel */
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        if (currentState == GameState.Playing) {
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty) {
                board.cells[rowSelected][colSelected].content = currentPlayer; // Move
                updateGame(currentPlayer, rowSelected, colSelected); // Update game state

                // Switch player
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
            }
        } else {
            initGame(); // Game over and restart
        }

        repaint(); // Redraw the UI
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Auto-generated, event not used
    }
}