package parser;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Commands> commandsMap = new HashMap<>();
        commandsMap.put("-loadlib", Commands.LOADLIBRARIES);
        commandsMap.put("-f", Commands.READFILE);
        commandsMap.put("-lend", Commands.LENDBOOK);
        commandsMap.put("-return", Commands.RETURNBOOK);
        commandsMap.put("-list", Commands.PRINTOBJECT);
        this.command = commandsMap.getOrDefault(command, this.command);
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

