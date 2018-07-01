package SimulationElements.Agents;

import IO.in.Data;
import SimulationElements.Meetings.Meeting;

public class AgentSocial extends Agent {

    public AgentSocial(int agentsNumber, int id) {
        super(agentsNumber, id);
    }

    @Override
    public String getType() {
        return "towarzyski";
    }

    @Override
    public boolean setUpMeeting(Data data, int day) {
        Agent companion = null;

        if (this.getState() == State.DISEASED)
            return super.setUpMeeting(data, day);

        double probability;
        int friendsNumber = 0;

        probability = data.getRand().nextDouble();
        if (probability > data.getProbMeeting())
            return false;

        for (Agent agent : this.friends) {
            if (agent != null && agent.getAlive()) {
                friendsNumber++;
                for (Agent agent2 : agent.friends) {
                    if (agent2 != null && agent2.getAlive()) {
                        friendsNumber++;
                    }
                }
            }
        }

        if (friendsNumber == 0)
            return false;

        int no = data.getRand().nextInt(friendsNumber) + 1;
        int counter = 0;
        Agent[] friends1 = this.friends;

        companionSeeking:
        for (int i = 0; i < friends1.length; i++) {
            Agent agent = friends1[i];
            if (agent != null && agent.getAlive()) {
                counter++;

                if (counter == no && this != agent) {
                    companion = agent;
                    break;
                }
                Agent[] friends2 = agent.friends;
                for (int i1 = 0; i1 < friends2.length; i1++) {
                    Agent agent2 = friends2[i1];
                    if (agent2 != null && agent2.getAlive()) {
                        counter++;

                        if (counter == no && this != agent2) {
                            companion = agent2;
                            break companionSeeking;
                        }
                    }
                }
            }
        }

        int daysRemaining = data.getDays() - day;

        if (companion != null) {
            Meeting meeting = new Meeting(day + data.getRand().nextInt(daysRemaining) + 1, this, companion);
            this.meetingQueue.offer(meeting);
            companion.meetingQueue.offer(meeting);
            return true;
        }

        return false;
    }
}
