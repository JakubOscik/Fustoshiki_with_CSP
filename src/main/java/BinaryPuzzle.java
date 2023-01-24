import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BinaryPuzzle {
    private final List<List<String>> board;
    public final List<String> domain = new ArrayList<>(List.of("0", "1"));
    public final Set<String> solutions;

    public BinaryPuzzle(List<List<String>> board) {
        this.board = board;
        this.solutions = new HashSet<>();
    }

    public void doo(List<List<String>> grid, int i, int j){
        Solver s = new Solver(this.board, this.solutions, this.domain);
        Constraints c = new Constraints("binary");
        s.backtrack(grid, i, j, c);

    }
}
