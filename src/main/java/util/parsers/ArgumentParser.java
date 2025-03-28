package util.parsers;

import database.DatabaseSetup;

import java.util.UUID;

public class ArgumentParser {
    public static ParsedArguments parse(String[] args) {
        ParsedArguments parsedArguments = new ParsedArguments();
        parsedArguments.setCommand(args[0]);

        switch(parsedArguments.getCommand()) {
            case LOADLIBRARIES:
                parsedArguments.setFilePath(args[1]);
                break;
            case READFILE:
                parsedArguments.setType(args[1]);
                parsedArguments.setFilePath(args[2]);
                parsedArguments.setLibraryId(UUID.fromString(args[3]));
                break;
            case LENDBOOK:
            case RETURNBOOK:
                parsedArguments.setReaderId(UUID.fromString(args[1]));
                parsedArguments.setBookId(UUID.fromString(args[2]));
                parsedArguments.setLibraryId(UUID.fromString(args[3]));
                break;
            case PRINTOBJECT:
                parsedArguments.setType(args[1]);
                if (parsedArguments.getType().equals("borrowed")) {
                    parsedArguments.setReaderId(UUID.fromString(args[2]));
                    parsedArguments.setLibraryId(UUID.fromString(args[3]));
                }
                else if(parsedArguments.getType().equals("libraries")){}
                else {
                    parsedArguments.setLibraryId(UUID.fromString(args[2]));
                }
                break;
            case CLEARDATABASE:
                DatabaseSetup.clearDatabase();
                break;
        }
        return parsedArguments;
    }
}
