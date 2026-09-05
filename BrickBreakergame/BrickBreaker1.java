import javax.swing.JFrame;

public class BrickBreaker1 {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Brick Breaker Deluxe");

        GamePlay1 game = new GamePlay1();

        frame.setBounds(10, 10, 700, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
    }
}