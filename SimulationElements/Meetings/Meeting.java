package SimulationElements.Meetings;

import IO.in.Data;
import SimulationElements.Agents.Agent;
import SimulationElements.Agents.State;

public class Meeting {
    protected int day;
    private Agent agent1;
    private Agent agent2;
    protected int ordinal;
    private static int counter;

    public Meeting(int day, Agent a1, Agent a2) {
        this.day = day;
        this.agent1 = a1;
        this.agent2 = a2;
        this.ordinal = counter;
        counter++;
    }

    public int getDay() {
        return this.day;
    }

    public boolean run(Data data) {
        double probability;

        if (this.agent1.getState() == State.DISEASED) {
            probability = data.getRand().nextDouble();
            if (probability <= data.getProbInfection() && agent2.getState() == State.HEALTHY) {
                this.agent2.setState(State.DISEASED);
                return true;
            }
        }
        else if (this.agent2.getState() == State.DISEASED) {
            probability = data.getRand().nextDouble();
            if (probability <= data.getProbInfection() && agent1.getState() == State.HEALTHY) {
                this.agent1.setState(State.DISEASED);
                return true;
            }
        }

        return false;
    }

    public void delete() {
        this.agent1.getMeetingQueue().remove(this);
        this.agent2.getMeetingQueue().remove(this);
    }
}
