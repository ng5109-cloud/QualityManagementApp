public class Main {

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        // ✅ Conversion
        System.out.println(q1.convertTo(LengthUnit.INCHES)); // 12 INCHES

        // ✅ Addition (explicit target)
        System.out.println(q1.add(q2, LengthUnit.FEET)); // 2 FEET
        System.out.println(q1.add(q2, LengthUnit.YARDS)); // ~0.667 YARDS

        // ✅ Equality
        System.out.println(new QuantityLength(36.0, LengthUnit.INCHES)
                .equals(new QuantityLength(1.0, LengthUnit.YARDS))); // true

        // ✅ More cases
        System.out.println(new QuantityLength(2.54, LengthUnit.CENTIMETERS)
                .convertTo(LengthUnit.INCHES)); // ~1 INCH

        System.out.println(LengthUnit.INCHES.convertToBaseUnit(12.0)); // 1.0 feet
    }
}


// ✅ STANDALONE ENUM (UC8 CORE CHANGE)
enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    // 🔥 Convert THIS unit → base (feet)
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    // 🔥 Convert base (feet) → THIS unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }
}


// ✅ SIMPLIFIED CLASS
final class QuantityLength {

    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 0.0001;

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

    // ✅ UC5 Conversion (delegated to enum)
    public QuantityLength convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityLength(converted, targetUnit);
    }

    // ✅ UC6 (default: same unit)
    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    // ✅ UC7 (explicit target)
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityLength(result, targetUnit);
    }

    // ✅ UC1–UC4 Equality (with epsilon)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}