public class Main {

    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight w3 = new QuantityWeight(2.20462, WeightUnit.POUND);

        // ===== Equality =====
        System.out.println("Equality:");
        System.out.println(w1.equals(w2)); // true
        System.out.println(w1.equals(w3)); // true
        System.out.println(w2.equals(w3)); // true

        // ===== Conversion =====
        System.out.println("\nConversion:");
        System.out.println(w1.convertTo(WeightUnit.GRAM)); 
        System.out.println(w2.convertTo(WeightUnit.POUND));
        System.out.println(w3.convertTo(WeightUnit.KILOGRAM));

        // ===== Addition (default unit) =====
        System.out.println("\nAddition (Default Unit):");
        System.out.println(w1.add(w2)); // 2 kg
        System.out.println(w2.add(w1)); // 2000 g

        // ===== Addition (explicit unit) =====
        System.out.println("\nAddition (Explicit Unit):");
        System.out.println(w1.add(w2, WeightUnit.GRAM)); 
        System.out.println(w3.add(w1, WeightUnit.POUND));
    }
}


// ================= ENUM =================
enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}


// ================= CLASS =================
final class QuantityWeight {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    // ===== EQUALS =====
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityWeight other = (QuantityWeight) obj;

        double a = unit.toBase(this.value);
        double b = other.unit.toBase(other.value);

        return Math.abs(a - b) < EPSILON;
    }

    @Override
    public int hashCode() {
        double base = unit.toBase(value);
        return Double.hashCode(Math.round(base / EPSILON));
    }

    // ===== CONVERT =====
    public QuantityWeight convertTo(WeightUnit target) {

        if (target == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = unit.toBase(value);
        double converted = target.fromBase(base);

        return new QuantityWeight(converted, target);
    }

    // ===== ADD =====
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit target) {

        if (other == null || target == null)
            throw new IllegalArgumentException("Arguments cannot be null");

        double sumBase = unit.toBase(value) + other.unit.toBase(other.value);
        double result = target.fromBase(sumBase);

        return new QuantityWeight(result, target);
    }

    // ===== STRING =====
    @Override
    public String toString() {
        return String.format("Quantity(%.6f, %s)", value, unit);
    }
}