package ms_rcpsp_lib.src.msrcpsp.evaluation;

import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;

/**
 * Single objective evaluator for duration.
 */
public class DurationEvaluator extends BaseEvaluator {

    public DurationEvaluator(Schedule schedule) {
        super(schedule);
    }

    /**
     * Duration of the schedule
     *
     * @return duration of the schedule
     */
    @Override
    public double evaluate() {
        return (double) super.getDuration();
    }

    @Override
    public BaseEvaluator getCopy(Schedule schedule) {
        return new DurationEvaluator(schedule);
    }

    @Override
    public EvaluatorType getType() {
        return EvaluatorType.DURATION_EVALUATOR;
    }

}
