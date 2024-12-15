public enum Direction {
    NORTH(1),SOUTH(0),EAST(3),WEST(2);
    private int frameLineNumber;

    Direction(int frameLineNumber) {
        this.frameLineNumber = frameLineNumber;
    }

    public int getFrameLineNumber() {
        return frameLineNumber;
    }
}
