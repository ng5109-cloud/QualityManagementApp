public class Main {

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        // UC7 Examples
        System.out.println(QuantityLength.add(q1, q2, LengthUnit.FEET));       // 2 FEET
        System.out.println(QuantityLength.add(q1, q2, LengthUnit.INCHES));     // 24 INCHES
        System.out.println(QuantityLength.add(q1, q2, LengthUnit.YARDS));      // ~0.667 YARDS

        System.out.println(QuantityLength.add(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(3.0, LengthUnit.FEET),
                LengthUnit.YARDS)); // 2 YARDS

        System.out.println(QuantityLength.add(
                new QuantityLength(36.0, LengthUnit.INCHES),
                new QuantityLength(1.0, LengthUnit.YARDS),
                LengthUnit.FEET)); // 6 FEET

        System.out.println(QuantityLength.add(
                new QuantityLength(2.54, LengthUnit.CENTIMETERS),
                new QuantityLength(1.0, LengthUnit.INCHES),
                LengthUnit.CENTIMETERS)); // ~5.08 CM

        System.out.println(QuantityLength.add(
                new QuantityLength(5.0, LengthUnit.FEET),
                new QuantityLength(0.0, LengthUnit.INCHES),
                LengthUnit.YARDS)); // ~1.667 YARDS

        System.out.println(QuantityLength.add(
                new QuantityLength(5.0, LengthUnit.FEET),
                new QuantityLength(-2.0, LengthUnit.FEET),
                LengthUnit.INCHES)); // 36 INCHES
    }
}


// ✅ ENUM
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double toBase(double value) {
        return value * toFeetFactor;
    }

    public double fromBase(double baseValue) {
        return baseValue / toFeetFactor;
    }
}


// ✅ CLASS
final class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null || !Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value or unit");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    // 🔥 UC6 (default: first operand unit)
    public QuantityLength add(QuantityLength other) {
        return add(this, other, this.unit);
    }

    // 🔥 UC7 (explicit target unit)
    public static QuantityLength add(QuantityLength q1,
                                    QuantityLength q2,
                                    LengthUnit targetUnit) {

        if (q1 == null || q2 == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        if (!Double.isFinite(q1.value) || !Double.isFinite(q2.value)) {
            throw new IllegalArgumentException("Invalid numeric values");
        }

        // Step 1: Convert to base (feet)
        double base1 = q1.unit.toBase(q1.value);
        double base2 = q2.unit.toBase(q2.value);

        // Step 2: Add
        double sumBase = base1 + base2;

        // Step 3: Convert to target unit
        double result = targetUnit.fromBase(sumBase);

        return new QuantityLength(result, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}