import IO.in.Configuration;
import IO.in.Data;
import SimulationElements.Simulator;

public class Symulacja {
    public static void main(String[] args) {
        Configuration properties = new Configuration();
        Data data = new Data(properties);
        Simulator simulator = new Simulator(data);

        simulator.runSimulation(data);
    }
}
