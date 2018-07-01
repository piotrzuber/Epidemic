package IO.out;

import IO.in.Data;
import SimulationElements.Agents.Agent;
import SimulationElements.Agents.State;
import SimulationElements.Simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Output {
    private File report;

    public Output(Data data) {

        this.report = new File(data.getReportFile());
    }

    public void writeProperties(Data data) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.report);
            writer.write("# twoje wyniki powinny zawierać te komentarze\n");
            writer.write("seed=" + data.getSeed() + "\n");
            writer.write("liczbaAgentów=" + data.getAgentsNumber() + "\n");
            writer.write("prawdTowarzyski=" + data.getProbSocial() + "\n");
            writer.write("prawdSpotkania=" + data.getProbMeeting() + "\n");
            writer.write("prawdZarażenia=" + data.getProbInfection() + "\n");
            writer.write("prawdWyzdrowienia=" + data.getProbCure() + "\n");
            writer.write("śmiertelność=" + data.getProbDecease() + "\n");
            writer.write("liczbaDni=" + data.getDays() + "\n");
            writer.write("śrZnajomych=" + data.getAvgFriends() + "\n");
        } catch (IOException ex) {
            System.out.println("Błąd zapisu raportu do pliku.");
            System.exit(1);
        } finally {
            flushAccess(writer);
        }
    }

    public void writeStart(Simulator sim) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.report, true);
            writer.write("# agenci jako: id typ lub id* typ dla chorego\n");
            for (Agent agent : sim.getAgents()) {
                if (agent.getState() == State.DISEASED) {
                    writer.write(agent.getId() + "* " + agent.getType() + "\n");
                }
                else {
                    writer.write(agent.getId() + " " + agent.getType() + "\n");
                }
            }
        } catch (IOException ex) {
            System.out.println("Błąd zapisu raportu do pliku.");
            System.exit(1);
        } finally {
            flushAccess(writer);
        }
    }

    public void writeGraph(Simulator sim) {
        FileWriter writer = null;
        StringBuilder outputLine;
        try {
            writer = new FileWriter(this.report, true);
            writer.write("# graf\n");
            for (Agent agent1 : sim.getAgents()) {
                outputLine = new StringBuilder(Integer.toString(agent1.getId()));
                for (Agent agent2 : agent1.getFriends()) {
                    if (agent2 != null) {
                        outputLine.append(" ").append(agent2.getId());
                    }
                }
                writer.write(outputLine.toString() + "\n");
            }
        } catch (IOException ex) {
            System.out.println("Błąd zapisu raportu do pliku.");
            System.exit(1);
        } finally {
            flushAccess(writer);
        }
    }

    public void writeDailyResult(Simulator sim) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.report, true);
            writer.write(sim.getHealthy() + " " + sim.getDiseased() + " " + sim.getImmune() + "\n");
        } catch (IOException ex) {
            System.out.println("Błąd zapisu raportu do pliku.");
            System.exit(1);
        } finally {
            flushAccess(writer);
        }
    }

    public void writeString(String str) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.report, true);
            writer.write(str);
        } catch (IOException ex) {
            System.out.println("Błąd zapisu raportu do pliku.");
            System.exit(1);
        } finally {
            flushAccess(writer);
        }
    }

    private void flushAccess(FileWriter writer) {
        if (writer != null) {
            try {
                writer.flush();
            } catch (IOException ex) {
                System.out.println("Błąd zamknięcia dostępu do pliku raportu.");
                System.exit(1);
            }
        }
    }
}
