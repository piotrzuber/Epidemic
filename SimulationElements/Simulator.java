package SimulationElements;

import IO.in.Data;
import IO.out.Output;
import SimulationElements.Agents.Agent;
import SimulationElements.Agents.AgentSocial;
import SimulationElements.Meetings.Meeting;
import SimulationElements.Agents.State;

public class Simulator {
    private Agent[] agents;
    private int healthy;
    private int diseased;
    private int immune;

    public Simulator(Data data) {
        this.agents = initAgents(data);
        this.healthy = this.agents.length - 1;
        this.diseased = 1;
        this.immune = 0;
    }

    public Agent[] getAgents() {
        return this.agents;
    }

    public int getHealthy() {
        return this.healthy;
    }

    public int getDiseased() {
        return this.diseased;
    }

    public int getImmune() {
        return this.immune;
    }

    private Agent[] initAgents(Data data) {
        Agent[] agents = new Agent[data.getAgentsNumber()];

        for (int i = 0; i < agents.length; i++) {
            double probability = data.getRand().nextDouble();

            if (probability <= data.getProbSocial()) {
                agents[i] = new AgentSocial(agents.length, i + 1);
            }
            else {
                agents[i] = new Agent(agents.length, i + 1);
            }
        }

        int infectedId = data.getRand().nextInt(agents.length - 1) + 1;
        agents[infectedId].setState(State.DISEASED);

        return agents;
    }

    private void drawGraph(Data data) {
        int goal = (data.getAgentsNumber() * data.getAvgFriends()) / 2;

        int idx1;
        int idx2;
        int strangersNumber;
        int counter;

        while (goal > 0) {
            strangersNumber = 0;
            counter = 0;
            idx1 = data.getRand().nextInt(data.getAgentsNumber());
            for (Agent agent : this.agents[idx1].getFriends()) {
                if (agent == null) {
                    strangersNumber++;
                }
            }
            if (strangersNumber == 0) {
                continue;
            }

            idx2 = data.getRand().nextInt(strangersNumber);

            if (idx1 == idx2)
                continue;

            for (int i = 0; i < this.agents[idx1].getFriends().length; i++) {
                Agent agent = this.agents[idx1].getFriends()[i];
                if (agent == null) {
                    if (counter == idx2 && i != idx1) {
                        this.agents[idx1].addFriend(this.agents[i]);
                        this.agents[i].addFriend(this.agents[idx1]);
                        goal--;
                        break;
                    }
                    counter++;
                }
            }
        }
    }

    public void runSimulation(Data data) {
        this.drawGraph(data);
        int day = 1;
        boolean willOfMeeting;
        Agent agent;
        double probability;
        Meeting queueHead;
        Output out = new Output(data);

        out.writeProperties(data);
        out.writeString("\n");
        out.writeStart(this);
        out.writeString("\n");
        out.writeGraph(this);
        out.writeString("\n");
        out.writeString("# liczność w kolejnych dniach\n");
        out.writeDailyResult(this);

        while (day <= data.getDays()) {
            for (int i = 0; i < this.agents.length; i++) {
                agent = this.agents[i];
                if (!agent.getAlive())
                    continue;

                if (agent.getState() == State.DISEASED) {
                    probability = data.getRand().nextDouble();
                    if (probability <= data.getProbDecease()) {
                        agent.setAlive(false);
                        this.diseased--;

                        while ((queueHead = agent.getMeetingQueue().poll()) != null) {
                            queueHead.delete();
                        }
                        continue;
                    }
                    else {
                        probability = data.getRand().nextDouble();
                        if (probability <= data.getProbCure()) {
                            agent.setState(State.IMMUNE);
                            this.immune++;
                            this.diseased--;
                        }
                    }
                }

                if (day != data.getDays()) {
                    do {
                        willOfMeeting = agent.setUpMeeting(data, day);
                    } while (willOfMeeting);
                }

                queueHead = agent.getMeetingQueue().peek();
                while ((queueHead != null) && (queueHead.getDay() == day)) {
                    if (queueHead.run(data)) {
                        this.diseased++;
                        this.healthy--;
                    }
                    queueHead.delete();
                    queueHead = agent.getMeetingQueue().peek();
                }
            }

            out.writeDailyResult(this);
            day++;
        }
    }
}

