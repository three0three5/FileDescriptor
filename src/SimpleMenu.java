import java.io.File;
import java.util.Scanner;

public class SimpleMenu implements Menu {
    private int state = 1;
    private final Scanner in = new Scanner(System.in);
    private String path = "src/";

    /**
     * Возвращает состояние меню.
     * @return true, если меню еще работает, и false иначе.
     */
    @Override
    public boolean isRunning() {
        return state != 0;
    }

    /**
     * Переход меню в новое состояние в зависимости от ответа пользователя.
     * Работает только в случае, если меню работает (isRunning).
     */
    @Override
    public void proceed() {
        switch (state) {
            case 1 -> {
                System.out.println(ConstOutputStrings.GREETING);
                changeState(1, 2);
                if (state != 0) {
                    ++state;
                }
            }
            case 2 -> state = 0;
            case 3 -> {
                System.out.println(ConstOutputStrings.CHANGE_PATH);
                changePath();
                state = 0;
            }
        }
    }

    /**
     * Получить путь к корневой папке, выбранной пользователем.
     * @return строка-путь к корневой папке.
     */
    @Override
    public String getFolderPath() {
        return path;
    }

    private void changePath() {
        path = in.next();
        File dir = new File(path);
        File[] arrFiles = dir.listFiles();
        while (!dir.exists() || arrFiles == null) {
            System.out.println(ConstOutputStrings.WRONG_PATH);
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
            System.out.println(ConstOutputStrings.REWRITE);
            str = in.next();
        }
        state = Integer.parseInt(str);
    }
}
