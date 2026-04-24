public class Main {

    // Inner class representing Feet measurement
    static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        // Override equals() method
        @Override
        public boolean equals(Object obj) {

            // Step 1: Same reference check (reflexive)
            if (this == obj) return true;

            // Step 2: Null check
            if (obj == null) return false;

            // Step 3: Type check
            if (getClass() != obj.getClass()) return false;

            // Step 4: Cast safely
            Feet other = (Feet) obj;

            // Step 5: Compare values using Double.compare()
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Main method to test
    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        Feet f3 = new Feet(2.0);

        System.out.println("1.0 ft vs 1.0 ft: " + f1.equals(f2)); // true
        System.out.println("1.0 ft vs 2.0 ft: " + f1.equals(f3)); // false
    }
}