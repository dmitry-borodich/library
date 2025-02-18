package util.parsers;

import java.util.stream.Stream;

public class ParsedArguments {
    private Commands command;
    private String type;
    private String filePath;
    private int readerId;
    private int bookId;
    private int libraryId;

    public Commands getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = Stream.of(Commands.values()).filter(c -> c.alias.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Комманда " + command + " не найдена"));

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }
}

