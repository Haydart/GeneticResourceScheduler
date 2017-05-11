package ms_rcpsp_lib.src.msrcpsp.evaluation;


import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;

/**
 * Single objective evaluator for cost.
 */
public class CostEvaluator extends BaseEvaluator {

    public CostEvaluator(Schedule schedule) {
        super(schedule);
    }

    /**
     * Cost of the schedule.
     *
     * @return cost of the schedule
     */
    @Override
    public double evaluate() {
        return super.getCost();
    }

    @Override
    public BaseEvaluator getCopy(Schedule schedule) {
        return new CostEvaluator(schedule);
    }

    @Override
    public EvaluatorType getType() {
        return EvaluatorType.COST_EVALUATOR;
    }

}
