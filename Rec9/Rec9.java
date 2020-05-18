import java.util.concurrent.CompletableFuture;

class A {
    private final int x;
    
    A() {
        this(0);
    }

    private A(int x) {
        this.x = x;
    }

    void sleep() {
        System.out.println(Thread.currentThread().getName() + " " + x);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }
    A incr() {
        sleep();
        return new A(this.x + 1);
    }

    A decr() {
        sleep();
        if (x < 0) {
            throw new IllegalStateException();
        }
        return new A(this.x - 1);
    }

    static CompletableFuture<A> foo(A a) {
        return CompletableFuture.supplyAsync(() -> a.incr().decr());
    }

    static CompletableFuture<A> foo2(A a) {
        return CompletableFuture.supplyAsync(() -> a.incr()).thenApply(x -> x.decr());
    }

    static CompletableFuture<A> foo3(A a) {
        return CompletableFuture.supplyAsync(() -> a.incr()).thenApplyAsync(x -> x.decr());
    }

    static CompletableFuture<A> bar(A a) {
        return CompletableFuture.supplyAsync(() -> a.incr());
    }

    static CompletableFuture<A> baz(A a, int x) {
        if (x == 0) {
            return CompletableFuture.completedFuture(new A()); 
        } else {
            return CompletableFuture.supplyAsync(() -> a.incr().decr()); 
        }
    }

    static void methodCall() {
        //(a)
        CompletableFuture<A> a = foo(new A());
        System.out.println(a.join());

        //(b)
        CompletableFuture<A> b = foo(new A()).thenCompose(x -> bar(x));
        System.out.println(b.join());

        //(c)
        CompletableFuture<A> c = baz(new A(), 1);
        System.out.println(c.join());

        //(d)
        CompletableFuture<Void> all = CompletableFuture.allOf(
            foo(new A()),
            bar(new A()),
            baz(new A(),1)
        );
        all.join();
        System.out.println("done!");

        //(e)
        CompletableFuture<A> exc = CompletableFuture.supplyAsync(() -> new A().decr().decr())
                                                    .handle((res, ex) -> {
                                                        if (res == null) {
                                                            System.out.println("ERROR: " + ex);
                                                            return new A();
                                                        } else {
                                                            return res;
                                                        }
                                                    });
        System.out.println(exc.join());
    }

    public String toString() {
        return "" + x;
    }
}

