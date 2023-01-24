import org.checkerframework.checker.units.qual.C;
import org.javatuples.Pair;

import java.util.*;

public class FutoshikiPuzzle {
    private final List<List<String>> board;
    public final List<String> domain;
    //private final Integer dimension;
    //private final ArrayList<String> demandingDigits = new ArrayList<>();
    public final HashSet<String> solutions;

    public FutoshikiPuzzle(List<List<String>> board, List<List<String>> constraints) {
        this.board = board;
        this.domain = new ArrayList<>();
        for(int i = 1; i <= board.size() / 2 + 1; i++) {
            this.domain.add(String.valueOf(i));
        }
        this.solutions = new HashSet<>();
    }

    public FutoshikiPuzzle(List<List<String>> board) {
        this.board = board;
        this.domain = new ArrayList<>();
        for(int i = 1; i <= board.size() / 2 + 1; i++) {
            this.domain.add(String.valueOf(i));
        }
        this.solutions = new HashSet<>();
    }
    public void doo(List<List<String>> grid, int i, int j){
        Solver s = new Solver(this.board, this.solutions, this.domain);
//        System.out.println(s.rep(this.board));
        Constraints c = new Constraints("Futoshiki");
        s.backtrack(grid, i, j, c);

    }

    public void forw(List<List<String>> grid, int i, int j){
        Solver s = new Solver(this.board, this.solutions, this.domain);
        Constraints c = new Constraints("Futoshiki");
        s.forward(grid, i, j, c, this.domain);
    }
}
