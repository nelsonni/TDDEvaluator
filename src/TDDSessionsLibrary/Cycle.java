package TDDSessionsLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/14/14.
 */
public class Cycle {

    protected List<Phase> phases;

    public Cycle () {
        phases = new ArrayList<>();
    }

    public Cycle(Phase first, Phase second) {
        phases = new ArrayList<>();
        phases.add(first);
        phases.add(second);
    }

    public Cycle(Phase first, Phase second, Phase third) {
        phases = new ArrayList<>();
        phases.add(first);
        phases.add(second);
        phases.add(third);
    }

    /***
     * Returns the positional offset value (order number) of the first event that the first phase within this cycle
     * refer to as the starting event of that phase.
     *
     * @return  the lowest index value of the Event objects referenced by Phase objects contained within this Cycle.
     */
    public int start() {
        if (phases.isEmpty()) {
            return 0;
        }
        return phases.get(0).start;
    }

    public int end() {
        if (phases.isEmpty()) {
            return 0;
        }
        return phases.get(phases.size()-1).end;
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Cycle c = (Cycle) obj;
        return this.phases.equals(c.phases);
    }

    @Override
    public String toString() {
        StringBuilder cycleStr = new StringBuilder();

        for (Phase p : phases) {
            cycleStr.append(p.toString());
        }

        return cycleStr.toString();
    }

    public int size() {
        return phases.size();
    }

    public Phase get(int index) {
        return phases.get(index);
    }

    public boolean add(Phase phase) {
        return phases.add(phase);
    }

    public boolean add(String jsonString) {
        return phases.add(new Phase(jsonString));
    }

    protected static List<Phase> parsePhaseList(List<String> phaseFileContent) {
        List<Phase> phasesList = new ArrayList<>();

        for (String contentLine : phaseFileContent) {
            phasesList.add(new Phase(contentLine));
        }

        return phasesList;
    }

}
