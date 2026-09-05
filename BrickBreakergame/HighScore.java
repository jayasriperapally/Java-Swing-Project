import java.io.*;

public class HighScore {

    private static final String FILE = "highscore.txt";

    public static int load() {

        try {

            BufferedReader br =
                    new BufferedReader(new FileReader(FILE));

            return Integer.parseInt(br.readLine());

        } catch(Exception e){

            return 0;
        }
    }

    public static void save(int score){

        try{

            PrintWriter pw =
                    new PrintWriter(FILE);

            pw.println(score);

            pw.close();

        }catch(Exception e){}
    }

}