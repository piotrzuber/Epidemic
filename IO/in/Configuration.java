package IO.in;

import Errors.InvalidFileTypeError;
import Errors.InvalidValueError;
import Errors.NoSuchFileError;
import Errors.NoSuchValueError;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Configuration {
    private Properties defaultConf;
    public Properties simulationConf;

    private void checkSeedValueCorrectness(Properties props) {
        String value = props.getProperty("seed");
        if (value != null) {
            try {Long.parseLong(value);
            } catch (NumberFormatException ex) {
                InvalidValueError err = new InvalidValueError(value, "seed");
                err.throwError();
            }
        }
    }

    private void checkProbValueCorrectness(Properties props, String key) {
        String value = props.getProperty(key);
        double valueDouble;
        if (value != null) {
            try {
                valueDouble = Double.parseDouble(value);
                if (valueDouble < 0 || valueDouble >= 1) {
                    InvalidValueError err = new InvalidValueError(value, key);
                    err.throwError();
                }
            } catch (NumberFormatException ex) {
                InvalidValueError err = new InvalidValueError(value, key);
                err.throwError();
            }
        }
    }

    private void checkAgentNumberCorrectness(Properties props) {
        String value = props.getProperty("liczbaAgentów");
        int valueInt;
        if (value != null) {
            try {
                valueInt = Integer.parseUnsignedInt(value);
                if (valueInt < 1 || valueInt > 1000000) {
                    InvalidValueError err = new InvalidValueError(value, "liczbaAgentów");
                    err.throwError();
                }
            } catch (NumberFormatException ex) {
                InvalidValueError err = new InvalidValueError(value, "liczbaAgentów");
                err.throwError();
            }
        }
    }

    private void checkDaysNumberCorrectness(Properties props) {
        String value = props.getProperty("liczbaDni");
        int valueInt;
        if (value != null) {
            try {
                valueInt = Integer.parseUnsignedInt(value);
                if (valueInt < 1 || valueInt > 1000) {
                    InvalidValueError err = new InvalidValueError(value, "liczbaDni");
                    err.throwError();
                }
            } catch (NumberFormatException ex) {
                InvalidValueError err = new InvalidValueError(value, "liczbaDni");
                err.throwError();
            }
        }
    }

    private void checkAvgFriendsCorrectness(Properties props) {
        String value = props.getProperty("śrZnajomych");
        int valueInt;
        if (value != null) {
            try {
                valueInt = Integer.parseUnsignedInt(value);
                if (valueInt >= Integer.parseInt(props.getProperty("liczbaAgentów"))) {
                    InvalidValueError err = new InvalidValueError(value, "śrZnajomych");
                    err.throwError();
                }
            } catch (NumberFormatException ex) {
                InvalidValueError err = new InvalidValueError(value, "śrZnajomych");
                err.throwError();
            }
        }
    }

    private void checkCorrectness(Properties props) {
        checkSeedValueCorrectness(props);
        checkAgentNumberCorrectness(props);
        checkProbValueCorrectness(props, "prawdTowarzyski");
        checkProbValueCorrectness(props, "prawdSpotkania");
        checkProbValueCorrectness(props, "prawdZarażenia");
        checkProbValueCorrectness(props, "prawdWyzdrowienia");
        checkProbValueCorrectness(props, "śmiertelność");
        checkDaysNumberCorrectness(props);
        checkAvgFriendsCorrectness(props);
    }

    private Properties takeDefaultConf() {
        Properties defaultConf = new Properties();
        try (FileInputStream stream = new FileInputStream("default.properties");
             Reader reader = Channels.newReader(stream.getChannel(), StandardCharsets.UTF_8.name())) {
            defaultConf.load(reader);
        } catch (MalformedInputException ex) {
            InvalidFileTypeError err = new InvalidFileTypeError("default.properties");
            err.throwError();
        } catch (FileNotFoundException ex) {
            NoSuchFileError err = new NoSuchFileError("default.properties");
            err.throwError();
        } catch (IOException ex) {
            System.out.println("Błąd odczytu pliku default.properties");
            System.exit(1);
        }

        checkCorrectness(defaultConf);
        return defaultConf;
    }

    private Properties takeSimulationConf(Properties defaultConf) {
        Properties simulationConf = new Properties(defaultConf);
        try (FileInputStream stream = new FileInputStream("simulation-conf.xml")) {
            try {
                simulationConf.loadFromXML(stream);
            } catch (MalformedInputException ex) {
                InvalidFileTypeError err = new InvalidFileTypeError("simulation-conf.xml");
                err.throwError();
            }
        } catch (FileNotFoundException ex) {
            NoSuchFileError err = new NoSuchFileError("simulation-conf.xml");
            err.throwError();
        } catch (IOException ex) {
            System.out.println("Błąd odczytu pliku simulation-conf.xml");
            System.exit(1);
        }

        checkCorrectness(simulationConf);
        return simulationConf;
    }

    private void checkNull(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null) {
            NoSuchValueError err = new NoSuchValueError(key);
            err.throwError();
        }
    }

    private void checkCompleteness(Properties props) {
        checkNull(props, "seed");
        checkNull(props, "liczbaAgentów");
        checkNull(props, "prawdTowarzyski");
        checkNull(props, "prawdSpotkania");
        checkNull(props, "prawdZarażenia");
        checkNull(props, "prawdWyzdrowienia");
        checkNull(props, "śmiertelność");
        checkNull(props, "liczbaDni");
        checkNull(props, "śrZnajomych");
        checkNull(props, "plikZRaportem");
    }

    public Configuration() {
        this.defaultConf = takeDefaultConf();
        this.simulationConf = takeSimulationConf(this.defaultConf);
        checkCompleteness(this.simulationConf);
        checkCorrectness(this.defaultConf);
        checkCorrectness(this.simulationConf);
    }
}
