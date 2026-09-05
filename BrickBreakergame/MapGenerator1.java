import java.awt.*;

public class MapGenerator1 {

    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator1(int row, int col) {

        map = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    public void draw(Graphics2D g) {

        Color[] colors = {
                Color.RED,
                Color.ORANGE,
                Color.YELLOW,
                Color.GREEN,
                Color.CYAN,
                Color.BLUE,
                Color.MAGENTA
        };

        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map[0].length; j++) {

                if (map[i][j] > 0) {

                    g.setColor(colors[(i + j) % colors.length]);

                    g.fillRoundRect(
                            j * brickWidth + 80,
                            i * brickHeight + 50,
                            brickWidth,
                            brickHeight,
                            12,
                            12);

                    g.setColor(Color.WHITE);
                    g.setStroke(new BasicStroke(2));

                    g.drawRoundRect(
                            j * brickWidth + 80,
                            i * brickHeight + 50,
                            brickWidth,
                            brickHeight,
                            12,
                            12);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}