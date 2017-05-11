package genetic;

import ms_rcpsp_lib.src.msrcpsp.io.MSRCPSPIO;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;

import java.io.IOException;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class to help with understanding of the library.
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String definitionFileCatalog = "src/ms_rcpsp_lib/assets/def_small/";
    private static final String definitionFile = "200_20_55_9.def";
    private static final String writeFile = "src/ms_rcpsp_lib/solutions_small/10_3_5_3.sol";

    public static void main(String[] args) {

        MSRCPSPIO reader = new MSRCPSPIO();
        Schedule schedule = reader.readDefinition(definitionFileCatalog + definitionFile);
        if (schedule == null) {
            LOGGER.log(Level.WARNING, "Could not read the Definition " + definitionFile);
        }

        GeneticAlgorithmManager geneticAlgorithmManager = new GeneticAlgorithmManager(schedule);
        geneticAlgorithmManager.run();

        ChartManager chartManager = new ChartManager();
        chartManager.displayChart(
                geneticAlgorithmManager.getBestSpecimenData(),
                geneticAlgorithmManager.getAverageSpecimenData(),
                geneticAlgorithmManager.getWorstSpecimenData(),
                definitionFile,
                "Tournament"
        );

        try {
            reader.write(schedule, writeFile);
        } catch (IOException e) {
            System.out.print("Writing to a file failed");
        }
    }
}
