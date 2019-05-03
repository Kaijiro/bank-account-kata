package fr.lcdlv.bankAccount.statements;

public class StatementPrinter {

    private StringBuilder builder;

    public StatementPrinter() {
        this.builder = new StringBuilder();
    }

    void appendSeparator() {
        builder.append(" | ");
    }

    public void createHeader() {
        builder.append("   Date   ");
        appendSeparator();
        builder.append("  Transaction  ");
        appendSeparator();
        builder.append("  Balance  ");
        appendSeparator();
    }

    public void newLine() {
        builder.append('\n');
    }

    void append(String stringToAppend){
        builder.append(stringToAppend);
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }
}
