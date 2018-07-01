package IO.in;

import java.util.Random;

public class Data {
    private long seed;
    private int agentsNumber;
    private double probSocial;
    private double probMeeting;
    private double probInfection;
    private double probCure;
    private double probDecease;
    private int days;
    private int avgFriends;
    private String reportFile;
    private Random rand;

    public Data(Configuration props) {
        this.seed = Long.parseLong(props.simulationConf.getProperty("seed"));
        this.agentsNumber = Integer.parseInt(props.simulationConf.getProperty("liczbaAgentów"));
        this.probSocial = Double.parseDouble(props.simulationConf.getProperty("prawdTowarzyski"));
        this.probMeeting = Double.parseDouble(props.simulationConf.getProperty("prawdSpotkania"));
        this.probInfection = Double.parseDouble(props.simulationConf.getProperty("prawdZarażenia"));
        this.probCure = Double.parseDouble(props.simulationConf.getProperty("prawdWyzdrowienia"));
        this.probDecease = Double.parseDouble(props.simulationConf.getProperty("śmiertelność"));
        this.days = Integer.parseInt(props.simulationConf.getProperty("liczbaDni"));
        this.avgFriends = Integer.parseInt(props.simulationConf.getProperty("śrZnajomych"));
        this.reportFile = props.simulationConf.getProperty("plikZRaportem");
        this.rand = new Random(Long.parseLong(props.simulationConf.getProperty("seed")));
    }

    public Random getRand() {

        return this.rand;
    }

    public long getSeed() {

        return this.seed;
    }

    public String getReportFile() {

        return this.reportFile;
    }

    public int getAvgFriends() {

        return this.avgFriends;
    }

    public int getDays() {

        return this.days;
    }

    public double getProbDecease() {

        return this.probDecease;
    }

    public double getProbCure() {

        return this.probCure;
    }

    public double getProbInfection() {

        return this.probInfection;
    }

    public double getProbMeeting() {

        return this.probMeeting;
    }

    public double getProbSocial() {

        return this.probSocial;
    }

    public int getAgentsNumber() {

        return this.agentsNumber;
    }
}
