package util.parsers;

public enum Commands {
    LOADLIBRARIES("-loadlib"),
    READFILE("-f"),
    LENDBOOK("-lend"),
    RETURNBOOK("-return"),
    PRINTOBJECT("-list"),
    CLEARDATABASE("-cleardb");

    public final String alias;

    private Commands (String alias) {
        this.alias= alias;
    }
}
