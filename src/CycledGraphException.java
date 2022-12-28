public class CycledGraphException extends Exception {
    @Override
    public String getMessage() {
        return ConstStrings.CYCLE_EXCEPTION_MESSAGE;
    }
}
