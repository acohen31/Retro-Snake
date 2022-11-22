import javax.swing.JFrame;

public class GameGUI extends JFrame{
    
    public GameGUI() {
        GamePanel panel = new GamePanel();
        this.add(panel);
        this.setTitle("SNAKE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
}
