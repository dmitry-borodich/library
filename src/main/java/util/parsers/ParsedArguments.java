package util.parsers;

import java.util.UUID;
import java.util.stream.Stream;

public class ParsedArguments {
    private Commands command;
    private String type;
    private String filePath;
    private UUID readerId;
    private UUID bookId;
    private UUID libraryId;

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

    public UUID getReaderId() {
        return readerId;
    }

    public void setReaderId(UUID readerId) {
        this.readerId = readerId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public UUID getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(UUID libraryId) {
        this.libraryId = libraryId;
    }
}

