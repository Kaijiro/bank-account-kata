package fr.lcdlv.bankAccount;

public class Amount {
    private int amountInCents;

    private Amount(int amountInCents) {
        this.amountInCents = amountInCents;
    }

    public static Amount of(int amountInCents) {
        return new Amount(Math.abs(amountInCents));
    }

    public Amount add(Amount amount) {
        return Amount.of(this.amountInCents + amount.amountInCents);
    }

    public Amount subtract(Amount amount) {
        return Amount.of(this.amountInCents - amount.amountInCents);
    }

    @Override
    public String toString() {
        return String.format("%.2f", Double.valueOf(this.amountInCents / 100));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Amount amount = (Amount) o;

        return amountInCents == amount.amountInCents;
    }

    @Override
    public int hashCode() {
        return amountInCents;
    }
}
