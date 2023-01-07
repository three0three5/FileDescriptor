public class ConstOutputStrings {
    public final static String GREETING = """
            Выберите опцию (введите число):
            1 - Использовать текущий путь корневой папки (src)
            2 - Выбрать другой путь
            """;
    public final static String REWRITE = "Пожалуйста, введите корректную опцию";
    public final static String CHANGE_PATH = "Введите путь к папке";
    public final static String WRONG_PATH = "Такого пути нет или каталог пуст, попробуйте еще раз";
    public final static String ERROR_WHILE_WRAPPING = "Произошла ошибка во время обработки файлов!";
    public final static String CYCLE_EXCEPTION_MESSAGE = "В графе обнаружен цикл, сортировка невозможна";
    public final static String NO_START_POINTS = "Нет файлов, которые бы не требовались для других!";
}
