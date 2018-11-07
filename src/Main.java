import java.io.IOException;

public class Main {

    public static void main(String [] args) {

        Vocabulary voc = new Vocabulary("./src/2008short.csv");
        try {
            voc.loadfile();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
