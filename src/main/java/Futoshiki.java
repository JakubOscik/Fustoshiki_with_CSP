import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Futoshiki {
    public final List<List<String>> board;
    public final Set<String> solutions;
    public final List<String> simpleDomain;
    public final HashMap<Pair<Integer, Integer>, List<String>> domain;
    public final List<List<String>> horizontal;
    public final List<List<String>> vertical;
    public final HashMap<Pair<Integer, Integer>, Boolean> visited;
    public final List<String> defaultDomain;
    public int runs = 0;

    public Futoshiki(List<List<String>> board, List<List<String>> constraints) {
        this.defaultDomain = new ArrayList<>(board.size() / 2 + 1);
        for(int i = 1; i < board.size() / 2 + 2; i++) {
            this.defaultDomain.add(String.valueOf(i));
        }
        this.simpleDomain = new ArrayList<>();
        for(int i = 1; i <= board.size() / 2 + 1; i++) {
            this.simpleDomain.add(String.valueOf(i));
        }
        this.board = board;
        this.solutions = new HashSet<>();
        this.domain = new HashMap<>();
        visited = new HashMap<>((board.size() / 2 + 1) * (board.size() / 2 + 1));
        for(int i = 0; i < board.size(); i++){
            for(int j = 0; j < board.size(); j++){
                Pair<Integer, Integer> p = new Pair<>(i, j);
                this.domain.put(p, defaultDomain);
            }
        }
        horizontal = new ArrayList<>(board.size()/2 + 1);
        vertical = new ArrayList<>(board.size()/2 + 1);
        split(constraints);
        getStartingDomains();
    }

    public void doo(List<List<String>> grid, int i, int j){
        Solver s = new Solver(this.board, this.solutions, this.simpleDomain);
//        System.out.println(s.rep(this.board));
        Constraints c = new Constraints("Futoshiki");
        s.backtrack(grid, i, j, c);

    }

    private List<String> extractRows(List<List<String>> grid) {
        List<String> rows = new ArrayList<>(board.size()/2 + 1);
        for(int i = 0; i < grid.size(); i++) {
            StringBuilder row = new StringBuilder();
            if(i % 2 == 0) {
                for(int j = 0; j < grid.size(); j++) {
                    if(!grid.get(i).get(j).equals("-") && !grid.get(i).get(j).equals(">")
                            && !grid.get(i).get(j).equals("<"))
                        row.append(grid.get(i).get(j));
                }
                rows.add(row.toString());
            }
        }
        return rows;
    }

    public void getStartingDomains() {
        int rowNum = 0;
        List<Pair<Integer, Integer>> constans = new ArrayList<>();
        for(String row: extractRows(board)) {
            for(int j = 0; j < row.length(); j++) {
                char actual = row.charAt(j);
                if(actual == 'x') {
                    List<String> x = new ArrayList<>(defaultDomain);
                    this.domain.put(Pair.with(rowNum, j), x);
                    this.visited.put(Pair.with(rowNum, j), false);
                }
                else {
                    List<String> x = new ArrayList<>();
                    x.add(String.valueOf(actual));
                    this.domain.put(Pair.with(rowNum, j), x);
                    constans.add(Pair.with(rowNum, j));
                    this.visited.put(Pair.with(rowNum, j), true);
                }
            }
            rowNum++;
        }
        constans.forEach(c -> reduceDomain(domain, domain.get(c).get(0), c.getValue0(), c.getValue1()));
    }

    public void split(List<List<String>> constraints){
        for(int i = 0; i < constraints.size(); i++){
            if(i % 2 == 0){
                horizontal.add(constraints.get(i).stream()
                        .filter(c->c.equals("-") || c.equals(">") || c.equals("<")).collect(Collectors.toList()));
            }
            else vertical.add(constraints.get(i));
        }
    }

    public void reduceDomain(HashMap<Pair<Integer, Integer>, List<String>> domain, String value, int row, int col){
        for(int i = 0; i < board.size() / 2 + 1; i++) {
            if(i != row) domain.get(Pair.with(i, col)).remove(value);
            if(i != col) domain.get(Pair.with(row, i)).remove(value);
        }
    }

    public boolean isValid(HashMap<Pair<Integer, Integer>, List<String>> domain){
        return domain.values().stream().noneMatch(l -> l.size() == 0);
    }

    public void horizontalConstraintsCheck(HashMap<Pair<Integer, Integer>, List<String>> domain){
        for(int i = 0; i < horizontal.size(); i++){
            for(int j = 0; j < horizontal.get(i).size(); j++){
                String constraint = horizontal.get(i).get(j);
                if(constraint.equals(">")) {
                    List<String> greaterDomain = domain.get(Pair.with(i,j));
                    List<String> smallerDomain = domain.get(Pair.with(i,j+1));
                    if(greaterDomain.size() == 1){
                        domain.replace(Pair.with(i, j+1),
                                smallerDomain.stream().filter(v -> Integer.parseInt(v) < Integer.parseInt(greaterDomain.get(0)))
                                        .collect(Collectors.toList()));
                    }
                    if(smallerDomain.size() == 1){
                        domain.replace(Pair.with(i, j),
                                greaterDomain.stream().filter(v -> Integer.parseInt(v) > Integer.parseInt(smallerDomain.get(0)))
                                        .collect(Collectors.toList()));
                    }
                }
                else if(constraint.equals("<")) {
                    List<String> greaterDomain = domain.get(Pair.with(i,j+1));
                    List<String> smallerDomain = domain.get(Pair.with(i,j));
                    if(greaterDomain.size() == 1){
                        domain.replace(Pair.with(i, j),
                                smallerDomain.stream().filter(v -> Integer.parseInt(v) < Integer.parseInt(greaterDomain.get(0)))
                                        .collect(Collectors.toList()));
                    }
                    if(smallerDomain.size() == 1){
                        domain.replace(Pair.with(i, j+1),
                                greaterDomain.stream().filter(v -> Integer.parseInt(v) > Integer.parseInt(smallerDomain.get(0)))
                                        .collect(Collectors.toList()));
                    }
                }
            }
        }
    }

    public void verticalConstraintsCheck(HashMap<Pair<Integer, Integer>, List<String>> domain){
        for(int i = 0; i < vertical.size(); i++){
            for(int j = 0; j < vertical.get(i).size(); j++){
                String constraint = vertical.get(i).get(j);
                if(constraint.equals(">")) {
                    List<String> greaterDomain = domain.get(Pair.with(i,j));
                    List<String> smallerDomain = domain.get(Pair.with(i+1,j));
                    if(greaterDomain.size() == 1){
                        domain.replace(Pair.with(i+1, j),
                                smallerDomain.stream().filter(v -> Integer.parseInt(v) < Integer.parseInt(greaterDomain.get(0)))
                                        .collect(Collectors.toList()));
                    }
                    if(smallerDomain.size() == 1){
                        domain.replace(Pair.with(i, j),
                                greaterDomain.stream().filter(v -> Integer.parseInt(v) > Integer.parseInt(smallerDomain.get(0)))
                                        .collect(Collectors.toList()));
                    }
                }
                else if(constraint.equals("<")) {
                    List<String> greaterDomain = domain.get(Pair.with(i+1,j));
                    List<String> smallerDomain = domain.get(Pair.with(i,j));
                    if(greaterDomain.size() == 1){
                        domain.replace(Pair.with(i, j),
                                smallerDomain.stream().filter(v -> Integer.parseInt(v) < Integer.parseInt(greaterDomain.get(0)))
                                        .collect(Collectors.toList()));
                    }
                    if(smallerDomain.size() == 1){
                        domain.replace(Pair.with(i+1, j),
                                greaterDomain.stream().filter(v -> Integer.parseInt(v) > Integer.parseInt(smallerDomain.get(0)))
                                        .collect(Collectors.toList()));
                    }
                }
            }
        }
    }

    public void changeDomain(HashMap<Pair<Integer, Integer>, List<String>> domain, int i, int j){
        reduceDomain(domain, domain.get(Pair.with(i, j)).get(0), i, j);
        horizontalConstraintsCheck(domain);
        verticalConstraintsCheck(domain);
    }

    public void forward(HashMap<Pair<Integer, Integer>, List<String>> domain, HashMap<Pair<Integer, Integer>, Boolean> vis,
                        int i, int j, int heur) {
        runs++;
        if(!isValid(domain)) {
            return;
        }
        if(i == board.size() / 2 + 1) {
            if(isValid(domain)){
                if(!solutions.contains(domainsToString(domain))) {
                    solutions.add(domainsToString(domain));
                    System.out.println(domainsToString(domain));
                }
            }
            return;
        }
        for(String s: domain.get(Pair.with(i, j))) {
            HashMap<Pair<Integer, Integer>, List<String>> temp = new HashMap<>(domain.size());
            domain.keySet().forEach(v -> temp.put(v, new ArrayList<>(domain.get(v))));
            List<String> val = new ArrayList<>();
            val.add(s);
            temp.replace(Pair.with(i, j), val);
            changeDomain(temp, i, j);
            if(heur == 1) {
                if (j == board.size() / 2) forward(temp, null,i + 1, 0, 1);
                else forward(temp, null, i, j + 1, 1);
            }
            else {
                HashMap<Pair<Integer, Integer>, Boolean> tempVisited = new HashMap<>(vis.size());
                vis.keySet().forEach(k -> tempVisited.put(k, visited.get(k)));
                var sorted = temp.keySet().stream()
                        .filter(pair -> !tempVisited.get(pair)).min(Comparator.comparingInt(o -> temp.get(o).size()));
                forward(temp, tempVisited, sorted.get().getValue0(), sorted.get().getValue1(), 2);
            }
        }
    }

    public void forwardCheck(HashMap<Pair<Integer, Integer>, List<String>> domains,
                             HashMap<Pair<Integer, Integer>, Boolean> visited, int i, int j) {
        runs++;
        if(!isValid(domains)) { // checking whether there is not duplicated values (in row or column)
            return;
        }

        HashMap<Pair<Integer, Integer>, Boolean> tempVisited = new HashMap<>(visited.size());
        visited.keySet().forEach(k -> tempVisited.put(k, visited.get(k)));

        for(String d: domains.get(Pair.with(i, j))) {
            HashMap<Pair<Integer, Integer>, List<String>> tempDoms = new HashMap<>(domains.size());
            domains.keySet().forEach(k -> tempDoms.put(k, new ArrayList<>(domains.get(k))));

            List<String> x = new ArrayList<>();x.add(d);
            tempDoms.replace(Pair.with(i, j), x);
            tempVisited.replace(Pair.with(i, j), true);
            changeDomain(tempDoms, i, j);
            var sorted = tempDoms.keySet().stream()
                    .filter(pair -> !tempVisited.get(pair)).min(Comparator.comparingInt(o -> tempDoms.get(o).size()));
            if(sorted.isEmpty()) {
                if(isValid(domains)) {
                    var domainRepresentation = domainsToString(domains);
                    solutions.add(domainRepresentation);
                    System.out.println(domainRepresentation);
                }
                return;
            }

            forwardCheck(tempDoms, tempVisited, sorted.get().getValue0(), sorted.get().getValue1());
        }
    }

    public Pair<Integer, Integer> getShortestDomain(HashMap<Pair<Integer, Integer>, List<String>> dom) {
        Iterator <Pair<Integer, Integer>> it = dom.keySet().iterator();
        int max = 7;
        int i = 0;
        int j = 0;
        while(it.hasNext())
        {
            Pair<Integer, Integer> key= it.next();
            System.out.println("Roll no.: "+key+"     name: "+dom.get(key));
            if(dom.get(key).size() < max && dom.get(key).size() > 1) {
                max = dom.get(key).size();
                i = key.getValue0();
                j = key.getValue1();
            }
        }
        return new Pair<>(i, j);
    }

    private String domainsToString(HashMap<Pair<Integer, Integer>, List<String>> domains) {
        int row = 0;
        int col = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < board.size(); i++) {
            for(int j = 0; j < board.get(i).size(); j++) {
                stringBuilder.append(StringUtils.rightPad(!board.get(i).get(j).equals("-") && !board.get(i).get(j).equals(">")
                        && !board.get(i).get(j).equals("<")
                        ? domains.get(Pair.with(row, col)).get(0) : board.get(i).get(j), 1));
                if(!board.get(i).get(j).equals("-") && !board.get(i).get(j).equals(">") && !board.get(i).get(j).equals("<"))
                    col++;
            }
            col = 0;
            if(i % 2 == 0)
                row++;
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
