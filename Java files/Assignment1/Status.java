public enum Status {
    ARRIVES {
       @Override
       public String asLowerCase() {
           return ARRIVES.toString().toLowerCase();
       }
    },
    SERVED {
        @Override
        public String asLowerCase() {
            return SERVED.toString().toLowerCase();
        }
    },
    LEAVES {
        @Override
        public String asLowerCase() {
            return LEAVES.toString().toLowerCase();
        }
    };

    public abstract String asLowerCase();
}
