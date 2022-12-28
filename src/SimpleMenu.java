import java.io.File;
import java.util.Scanner;

public class SimpleMenu implements Menu {
    private int state = 1;
    private final Scanner in = new Scanner(System.in);
    private String path = "src/";

    @Override
    public boolean isRunning() {
        return state != 0;
    }

    @Override
    public void proceed() {
        switch (state) {
            case 1 -> {
                System.out.println(ConstStrings.GREETING);
                changeState(1, 2);
                if (state != 0) {
                    ++state;
                }
            }
            case 2 -> state = 0;
            case 3 -> {
                System.out.println(ConstStrings.CHANGE_PATH);
                changePath();
                state = 0;
            }
        }
    }

    @Override
    public String getFolderPath() {
        return path;
    }

    private void changePath() {
        path = in.next();
        File dir = new File(path);
        File[] arrFiles = dir.listFiles();
        while (!dir.exists() || arrFiles == null) {
            System.out.println(ConstStrings.WRONG_PATH);
            path = in.next();
            dir = new File(path);
            arrFiles = dir.listFiles();
        }
        if (!path.endsWith("/")) {
            path = path + '/';
        }
    }

    private void changeState(int lower, int higher) {
        String str = in.next();
        while (!str.matches("[0-9]+") ||
                Integer.parseInt(str) < lower || Integer.parseInt(str) > higher) {
            System.out.println(ConstStrings.REWRITE);
            str = in.next();
        }
        state = Integer.parseInt(str);
    }
}
