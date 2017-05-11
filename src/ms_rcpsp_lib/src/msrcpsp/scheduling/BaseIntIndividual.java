package ms_rcpsp_lib.src.msrcpsp.scheduling;


import ms_rcpsp_lib.src.msrcpsp.evaluation.BaseEvaluator;

/**
 * Single individual in a population used.
 * Stores information about schedule and evaluation properties
 * along with double typed genes.
 */
public class BaseIntIndividual extends BaseIndividual {

    private int[] genes;

    public BaseIntIndividual(Schedule schedule, int genes[], BaseEvaluator evaluator) {
        super(schedule, evaluator);
        this.genes = new int[schedule.getTasks().length];
        System.arraycopy(genes, 0, this.genes, 0, genes.length);
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

}
