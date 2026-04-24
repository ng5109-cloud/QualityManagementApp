public class Main {

    // ===== Updated Enum (UC4) =====
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0); // cm → inch → feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // ===== Same Quantity Class (NO CHANGE from UC3) =====
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

        private double toBaseUnit() {
            return unit.toFeet(value);
        }

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

    // ===== Main Method (UC4 Demo Only) =====
    public static void main(String[] args) {

        // Yard ↔ Feet
        System.out.println("1 yard == 3 feet → " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(3.0, LengthUnit.FEET)));

        // Yard ↔ Inches
        System.out.println("1 yard == 36 inches → " +
                new Quantity(1.0, LengthUnit.YARD)
                        .equals(new Quantity(36.0, LengthUnit.INCH)));

        // Yard ↔ Yard
        System.out.println("2 yard == 2 yard → " +
                new Quantity(2.0, LengthUnit.YARD)
                        .equals(new Quantity(2.0, LengthUnit.YARD)));

        // cm ↔ cm
        System.out.println("2 cm == 2 cm → " +
                new Quantity(2.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(2.0, LengthUnit.CENTIMETER)));

        // cm ↔ inch
        System.out.println("1 cm == 0.393701 inch → " +
                new Quantity(1.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(0.393701, LengthUnit.INCH)));

        // Non-equal case
        System.out.println("1 cm == 1 foot → " +
                new Quantity(1.0, LengthUnit.CENTIMETER)
                        .equals(new Quantity(1.0, LengthUnit.FEET)));
    }
}