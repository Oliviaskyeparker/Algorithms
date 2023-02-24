package package1;

import java.util.ArrayList;

/**
 * A Matching represents a candidate solution to the stable matching problem. A Matching may or may
 * not be stable.
 */
public class Matching {
    /**
     * Number of companies.
     */
    private Integer m;

    /**
     * Number of interns.
     */
    private Integer n;

    /**
     * A list containing each company's preference list.
     */
    private ArrayList<ArrayList<Integer>> company_preference;

    /**
     * A list containing each intern's preference list.
     */
    private ArrayList<ArrayList<Integer>> intern_preference;

    /**
     * Number of positions available in each company.
     */
    private ArrayList<Integer> company_positions;

    /**
     * Matching information representing the index of company a intern is matched to, -1 if not
     * matched.
     *
     * <p>An empty matching is represented by a null value for this field.
     */
    private ArrayList<Integer> intern_matching;

    public Matching(
            Integer m,
            Integer n,
            ArrayList<ArrayList<Integer>> company_preference,
            ArrayList<ArrayList<Integer>> intern_preference,
            ArrayList<Integer> company_positions) {
        this.m = m;
        this.n = n;
        this.company_preference = company_preference;
        this.intern_preference = intern_preference;
        this.company_positions = company_positions;
        this.intern_matching = null;
    }

    public Matching(
            Integer m,
            Integer n,
            ArrayList<ArrayList<Integer>> company_preference,
            ArrayList<ArrayList<Integer>> intern_preference,
            ArrayList<Integer> company_positions,
            ArrayList<Integer> intern_matching) {
        this.m = m;
        this.n = n;
        this.company_preference = company_preference;
        this.intern_preference = intern_preference;
        this.company_positions = company_positions;
        this.intern_matching = intern_matching;
    }

    /**
     * Constructs a solution to the stable matching problem, given the problem as a Matching. Take a
     * Matching which represents the problem data with no solution, and a intern_matching which
     * solves the problem given in data.
     *
     * @param data              The given problem to solve.
     * @param intern_matching The solution to the problem.
     */
    public Matching(Matching data, ArrayList<Integer> intern_matching) {
        this(
                data.m,
                data.n,
                data.company_preference,
                data.intern_preference,
                data.company_positions,
                intern_matching);
    }

    /**
     * Creates a Matching from data which includes an empty solution.
     *
     * @param data The Matching containing the problem to solve.
     */
    public Matching(Matching data) {
        this(
                data.m,
                data.n,
                data.company_preference,
                data.intern_preference,
                data.company_positions,
                new ArrayList<Integer>(0));
    }

    public void setInternMatching(ArrayList<Integer> intern_matching) {
        this.intern_matching = intern_matching;
    }

    public Integer getCompanyCount() {
        return m;
    }

    public Integer getInternCount() {
        return n;
    }

    public ArrayList<ArrayList<Integer>> getCompanyPreference() {
        return company_preference;
    }

    public ArrayList<ArrayList<Integer>> getInternPreference() {
        return intern_preference;
    }

    public ArrayList<Integer> getCompanyPositions() {
        return company_positions;
    }

    public ArrayList<Integer> getInternMatching() {
        return intern_matching;
    }

    public int totalCompanyPositions() {
        int positions = 0;
        for (int i = 0; i < m; i++) {
            positions += company_positions.get(i);
        }
        return positions;
    }

    public String getInputSizeString() {
        return String.format("m=%d n=%d\n", m, n);
    }

    public String getSolutionString() {
        if (intern_matching == null) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < intern_matching.size(); i++) {
            String str = String.format("Intern %d Company %d", i, intern_matching.get(i));
            s.append(str);
            if (i != intern_matching.size() - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    public String toString() {
        return getInputSizeString() + getSolutionString();
    }
}