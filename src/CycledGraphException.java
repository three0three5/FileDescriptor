public class CycledGraphException extends Exception {
    @Override
    public String getMessage() {
        return ConstOutputStrings.CYCLE_EXCEPTION_MESSAGE;
    }
}
