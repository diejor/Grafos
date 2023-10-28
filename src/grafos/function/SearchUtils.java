package grafos.function;

import java.util.function.*;

import graphs.AbstractGraph;
import grafos.edges.AbstractEdge;

public class SearchUtils<V, E extends AbstractEdge<V>, L> {
    public final Function<E, L> labeler;
    public final BiFunction<L, L, Boolean> labelerCmp;
    public final BinaryOperator<L> labelerAcc;
    public final Function<E, L> heuristic;
    public final TriConsumer<AbstractGraph<V, E>, V, V> onVisit;

    public SearchUtils() {
        this.labeler = v1 -> null;
        this.labelerCmp = (l1, l2) -> false;
        this.labelerAcc = (l1, l2) -> null;
        this.heuristic = v -> null;
        this.onVisit = (g, v1, v2) -> {
        };
    }

    public SearchUtils(
            Function<E, L> labeler,
            BiFunction<L, L, Boolean> labelerCmp,
            BinaryOperator<L> labelerAcc,
            Function<E, L> heuristic,
            TriConsumer<AbstractGraph<V, E>, V, V> onVisit) {
        this.labeler = labeler;
        this.labelerCmp = labelerCmp;
        this.labelerAcc = labelerAcc;
        this.heuristic = heuristic;
        this.onVisit = onVisit;
    }
}