package ms_rcpsp_lib.src.msrcpsp.scheduling;


import ms_rcpsp_lib.src.msrcpsp.evaluation.BaseEvaluator;

import java.util.List;

/**
 * Single individual in a population used.
 * Stores information about schedule and evaluation properties.
 */
public class BaseIndividual implements Comparable<BaseIndividual> {

    protected Schedule schedule;
    protected double evalValue;
    protected int duration;
    protected double cost;
    protected double normalDuration;
    protected double normalCost;

    public BaseIndividual(Schedule schedule, BaseEvaluator evaluator) {
        this.schedule = new Schedule(schedule);
        this.schedule.setEvaluator(evaluator.getCopy(this.schedule));
        this.setEvalValue(-1);
    }

    /**
     * Sets duration and cost calculated by the evaluator
     */
    public void setDurationAndCost() {
        this.duration = schedule.getEvaluator().getDuration();
        this.cost = schedule.getEvaluator().getCost();
    }

    /**
     * Sets normalized duration and cost calculated by the evaluator
     */
    public void setNormalDurationAndCost() {
        BaseEvaluator evaluator = schedule.getEvaluator();
        // TODO: - get min cost / duration ?
        this.normalDuration = evaluator.getDuration() / (double) evaluator.getMaxDuration();
        this.normalCost = evaluator.getCost() / (double) evaluator.getMaxCost();
    }

    /**
     * Determines whether this individual dominates given
     * individual. It means all its objectives value are not
     * worse.
     *
     * @param individual individual to compare
     * @return true if this individual dominates the
     * individual in the parameter
     */
    public boolean dominates(BaseIndividual individual) {
        return this.getCost() <= individual.getCost() &&
                this.getDuration() <= individual.getDuration();
    }

    /**
     * Determines whether this individual dominates every
     * individual in a given population.
     *
     * @param population population to check
     * @return true if this individual dominates every
     * individual in a given population
     */
    public boolean dominates(List<? extends BaseIndividual> population) {
        for (BaseIndividual individual : population) {
            if (!this.dominates(individual)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether given individual dominates this individual.
     * It means all its objectives value are not worse.
     *
     * @param individual individual to compare
     * @return true if given individual dominates this individual
     */
    public boolean isDominated(BaseIndividual individual) {
        return individual.dominates(this);
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public double getEvalValue() {
        return evalValue;
    }

    public void setEvalValue(double evalValue) {
        this.evalValue = evalValue;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getNormalDuration() {
        return normalDuration;
    }

    public void setNormalDuration(double normalDuration) {
        this.normalDuration = normalDuration;
    }

    public double getNormalCost() {
        return normalCost;
    }

    public void setNormalCost(double normalCost) {
        this.normalCost = normalCost;
    }

    @Override
    public int compareTo(BaseIndividual o) {
        if (this.getNormalDuration() == o.getNormalDuration()) {
            return Double.compare(this.getNormalCost(), o.getNormalCost());
        }
        return Double.compare(this.getNormalDuration(), o.getNormalDuration());
    }
}
