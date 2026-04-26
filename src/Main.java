public class Main {

    // ✅ ENUM: Length Units with conversion to base (FEET)
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

    // ✅ CLASS: QuantityLength
    static final class QuantityLength {

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

        // 🔥 UC6: ADD METHOD
        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }

            // Step 1: Convert both to base unit (feet)
            double base1 = this.unit.toBase(this.value);
            double base2 = other.unit.toBase(other.value);

            // Step 2: Add
            double sumBase = base1 + base2;

            // Step 3: Convert back to unit of first operand
            double resultValue = this.unit.fromBase(sumBase);

            // Step 4: Return new object (immutability)
            return new QuantityLength(resultValue, this.unit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ✅ MAIN METHOD (Test Cases)
    public static void main(String[] args) {

        System.out.println(new QuantityLength(1.0, LengthUnit.FEET)
                .add(new QuantityLength(2.0, LengthUnit.FEET)));

        System.out.println(new QuantityLength(1.0, LengthUnit.FEET)
                .add(new QuantityLength(12.0, LengthUnit.INCHES)));

        System.out.println(new QuantityLength(12.0, LengthUnit.INCHES)
                .add(new QuantityLength(1.0, LengthUnit.FEET)));

        System.out.println(new QuantityLength(1.0, LengthUnit.YARDS)
                .add(new QuantityLength(3.0, LengthUnit.FEET)));

        System.out.println(new QuantityLength(36.0, LengthUnit.INCHES)
                .add(new QuantityLength(1.0, LengthUnit.YARDS)));

        System.out.println(new QuantityLength(2.54, LengthUnit.CENTIMETERS)
                .add(new QuantityLength(1.0, LengthUnit.INCHES)));

        System.out.println(new QuantityLength(5.0, LengthUnit.FEET)
                .add(new QuantityLength(0.0, LengthUnit.INCHES)));

        System.out.println(new QuantityLength(5.0, LengthUnit.FEET)
                .add(new QuantityLength(-2.0, LengthUnit.FEET)));
    }
}