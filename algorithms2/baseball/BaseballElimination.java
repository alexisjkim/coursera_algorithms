package algorithms2.baseball;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

    private FlowNetwork network;
    private Node[] teams;
    private int team_count;

    private class Node {
        private String team;
        private int wins;
        private int losses;
        private int left; // number of games left
        private int[] remaining; // how many games are left against each team
    }

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null) throw new IllegalArgumentException();

        In input = new In(filename);
        team_count = input.readInt();

        network = new FlowNetwork(team_count);
        teams = new Node[team_count];

        for (int i = 0; i < team_count; i++) {
            Node current = new Node();
            current.team = input.readString();
            current.wins = input.readInt();
            current.losses = input.readInt();
            current.left = input.readInt();
            current.remaining = new int[team_count];
            for (int j = 0; j < team_count; j++) {
                int games = input.readInt();
                if (i != j) {
                    current.remaining[j] = games;
                    network.addEdge(new FlowEdge(i, j, games));
                }
            }
            teams[i] = current;
            input.readLine();
        }
        input.close();
    }

    // number of teams
    public int numberOfTeams() {
        return team_count;
    }

    public Iterable<String> teams() { // all teams
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < team_count; i++) {
            stack.push(teams[i].team);
        }
        return stack;
    }

    public int wins(String team) { // number of wins for given team
        if (team == null) throw new IllegalArgumentException();
        int index = find_index(team);
        return teams[index].wins;
    }

    public int losses(String team) { // number of losses for given team
        if (team == null) throw new IllegalArgumentException();
        int index = find_index(team);
        return teams[index].losses;
    }

    public int remaining(String team) {  // number of remaining games for given team
        if (team == null) throw new IllegalArgumentException();
        int index = find_index(team);
        return teams[index].left;
    }

    public int against(String team1, String team2) { // number of remaining games between team1 and team2
        if (team1 == null || team2 == null) throw new IllegalArgumentException();

        int index1 = find_index(team1);
        int index2 = find_index(team2);

        return teams[index1].remaining[index2];
    }

    public boolean isEliminated(String team) {  // is given team eliminated?
        if (team == null) throw new IllegalArgumentException();

        int index = find_index(team);
        int max_wins = teams[index].wins + teams[index].left;

        for (int i = 0; i<team_count; i++) {
            if (teams[i].wins > max_wins) return true; // trivial elimination
        }

        double wanted_max = 0;
        for (int i = 0; i < team_count-1; i++) { // connect team nodes to t
            wanted_max += max_wins - teams[i].wins;
        }

        if (team.equals("Montreal")) System.out.println("hi");

        FlowNetwork net = make_network(team);
        FordFulkerson a = new FordFulkerson(net, net.V() - 1, index);

        Iterable<FlowEdge> x = net.adj(net.V() - 1);

        for (FlowEdge i : x) {
            if (i.flow() < i.capacity()) return true;
        }
        return false;

    }

    public Iterable<String> certificateOfElimination(String team) { // subset R of teams that eliminates given team; null if not eliminated
        if (team == null) throw new IllegalArgumentException();
        if (!isEliminated(team)) return null;
        Stack<String> stack = new Stack<>();

        int index = find_index(team);
        int max_wins = teams[index].wins + teams[index].left;
        for (int i = 0; i<team_count; i++) {
            if (teams[i].wins > max_wins) stack.push(teams[i].team);
        }
        if (!stack.isEmpty()) return stack;

        FlowNetwork net = make_network(team);
        FordFulkerson a = new FordFulkerson(net, net.V()-1, find_index(team));

        for (int i = 0; i < team_count; i++) {
            if (a.inCut(i)) stack.push(teams[i].team);
        }

        return stack;
    }

    private int find_index(String team) {
        for (int i = 0; i < team_count; i++) {
            if (teams[i].team.equals(team)) return i;
        }
        throw new IllegalArgumentException();
    }

    private FlowNetwork make_network(String team) {
        int index = find_index(team);
        int max_wins = teams[index].wins + teams[index].left;
        int[][] lst = new int[(team_count) * (team_count - 1) / 2][2];
        int current = 0;

        for (int i = 0; i < team_count; i++) {
            for (int j = i+1; j < team_count; j++) {
                lst[current][0] = i;
                lst[current][1] = j;
                current++;
            }
        }

        int s = team_count + lst.length;
        FlowNetwork net = new FlowNetwork(team_count + lst.length + 1); // team_count = t, team_count - 1 = s
        for (int i = 0; i < team_count; i++) { // connect team nodes to t
            if (i != index) net.addEdge(new FlowEdge(i, index, max_wins - teams[i].wins));
        }

        for (int i = 0; i < lst.length; i++) {
            if (lst[i][0] != index && lst[i][1] != index) {
                net.addEdge(new FlowEdge(s, i + team_count, teams[lst[i][0]].remaining[lst[i][1]])); // connecting s to game nodes
                net.addEdge(new FlowEdge(i + team_count, lst[i][0],
                                         Double.POSITIVE_INFINITY)); // connecting game nodes to team nodes
                net.addEdge(new FlowEdge(i + team_count, lst[i][1],
                                         Double.POSITIVE_INFINITY)); // connecting game nodes to team nodes
            }
        }
        return net;
    }

    public static void main(String[] args) {
        String file = "teams5.txt";
        BaseballElimination division = new BaseballElimination(file);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }

    }
}