package ms_rcpsp_lib.src.msrcpsp.evaluation;


import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;

/**
 * Weighted evaluator for cost and duration.
 */
public class WeightedEvaluator extends BaseEvaluator {

    private double evalRate;

    /**
     * Constructor. Sets evaluation rate to given value.
     *
     * @param schedule schedule to validate
     * @param evalRate evaluation rate linked to duration, 1 - evaluation rate
     *                 is used with cost
     */
    public WeightedEvaluator(Schedule schedule, double evalRate) {
        super(schedule);
        this.evalRate = evalRate;
    }

    @Override
    public double evaluate() {
        if (evalRate > 1 || evalRate < 0) {
            throw new IllegalArgumentException(
                    "Cannot provide the evalRate smaller than 0 or bigger than 1!");
        }
        double durationPart = getDuration() / (double) getMaxDuration();
        double costPart = getCost() / (double) getMaxCost();
        return durationPart * evalRate + costPart * (1 - evalRate);
    }

    @Override
    public BaseEvaluator getCopy(Schedule schedule) {
        return new WeightedEvaluator(schedule, evalRate);
    }

    @Override
    public EvaluatorType getType() {
        return EvaluatorType.WEIGHTED_EVALUATOR;
    }

    public double getEvalRate() {
        return evalRate;
    }

    public void setEvalRate(double evalRate) {
        this.evalRate = evalRate;
    }

}
