String pattern(int n) {
        return Stream.iterate(1, i -> i + 1).limit(n)
            .map(x -> Stream.iterate(x, t -> t >= 1, i -> i - 1)
                .map(s -> s.toString())
                .reduce((f,s) -> f + s).get())
            .collect(Collectors.joining("\n","",(n == 0) ? "" : "\n"));
}
