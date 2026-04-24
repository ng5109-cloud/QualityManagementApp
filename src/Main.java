public class Main {

    // ===== Step 1: Enum for Units =====
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // ===== Step 2: Generic Quantity Class =====
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (Feet)
        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        // Equality check (core of UC3)
        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null) return false;

            if (getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBaseUnit());
        }
    }

    // ===== Main Method (UC3 Demo Only) =====
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);
        Quantity q3 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("1 ft == 12 in → " + q1.equals(q2)); // true
        System.out.println("1 in == 1 in → " + q3.equals(new Quantity(1.0, LengthUnit.INCH))); // true
        System.out.println("1 ft == 2 ft → " + q1.equals(new Quantity(2.0, LengthUnit.FEET))); // false
    }
}