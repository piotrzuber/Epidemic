package SimulationElements.Agents;

import IO.in.Data;
import SimulationElements.Meetings.Meeting;
import SimulationElements.Meetings.MeetingComparator;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Agent {
    private static final MeetingComparator COMP = new MeetingComparator();

    private int id;
    protected Agent[] friends;
    private State state;
    private boolean alive;
    protected Queue<Meeting> meetingQueue;

    public Agent(int agentsNumber, int id) {
        this.friends = new Agent[agentsNumber];
        Arrays.fill(this.friends, null);
        this.state = State.HEALTHY;
        this.alive = true;
        this.id = id;
        this.meetingQueue = new PriorityQueue<>(10, COMP);
    }

    public int getId() {
        return this.id;
    }

    public Queue<Meeting> getMeetingQueue() {
        return this.meetingQueue;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean getAlive() {
        return this.alive;
    }

    public Agent[] getFriends() {
        return this.friends;
    }

    public void addFriend(Agent agent) {
        this.friends[agent.getId() - 1] = agent;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getType() {
        return "zwykły";
    }

    public boolean setUpMeeting(Data data, int day) {
        Agent companion = null;

        double probability;
        int friendsNumber = 0;

        probability = data.getRand().nextDouble();
        if (probability > data.getProbMeeting() / 2)
            return false;

        for (Agent agent : this.friends) {
            if (agent != null && agent.getAlive())
                friendsNumber++;
        }

        if (friendsNumber == 0)
            return false;

        int no = data.getRand().nextInt(friendsNumber) + 1;
        int counter = 0;
        Agent[] friends1 = this.friends;
        for (int i = 0; i < friends1.length; i++) {
            Agent agent = friends1[i];
            if (agent != null && agent.getAlive())
                counter++;

            if (counter == no && this != agent) {
                companion = agent;
                break;
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
