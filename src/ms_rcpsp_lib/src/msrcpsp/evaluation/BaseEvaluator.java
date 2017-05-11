package ms_rcpsp_lib.src.msrcpsp.evaluation;


import ms_rcpsp_lib.src.msrcpsp.scheduling.Resource;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Task;

/**
 * Abstract evaluator class. All evaluators inherit from it.
 * Has cost, duration functions along with abstract <code>evaluate()</code>
 */
abstract public class BaseEvaluator {

    private Schedule schedule;

    public BaseEvaluator(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Abstract <code>evaluate()</code>. Each evaluator
     * must define a body for this function.
     *
     * @return evaluation value
     */
    abstract public double evaluate();

    /**
     * Creates a copy of this evaluator with a new schedule
     *
     * @param schedule schedule to use
     * @return new evaluator of the same type, but
     * with the new schedule
     */
    abstract public BaseEvaluator getCopy(Schedule schedule);

    /**
     * Allows to differentiate evaluators
     *
     * @return type from <code>EvaluatorType</code>
     */
    abstract public EvaluatorType getType();

    /**
     * Gets total duration of the project, which is the latest finish
     * date of all resources.
     *
     * @return total duration of the project
     */
    public int getDuration() {
        int result = 0;
        Resource[] resources = schedule.getResources();
        for (Resource r : resources) {
            if (r.getFinish() > result) {
                result = r.getFinish();
            }
        }
        return result;
    }

    /**
     * Gets total cost of the project, which is the sum of all resources' salary
     * times the duration of the tasks they work on.
     *
     * @return total cost of the project
     */
    public double getCost() {
        double cost = 0;
        Task[] tasks = schedule.getTasks();
        for (Task t : tasks) {
            if (t.getResourceId() != -1) {
                cost += schedule.getResource(t.getResourceId()).getSalary()
                        * t.getDuration();

            }

        }
        return cost;
    }

    /**
     * Sums duration of all task of the schedule.
     *
     * @return maximum possible duration of the schedule
     */
    public int getMaxDuration() {
        int duration = 0;
        for (Task t : schedule.getTasks()) {
            duration += t.getDuration();
        }
        return duration;
    }

    /**
     * Sums cost of all tasks of the schedule.
     *
     * @return maximum possible cost of the schedule
     */
    public int getMaxCost() {
        Resource[] resources = schedule.getResources();
        Resource expRes = resources[0];
        for (Resource r : resources) {
            if (r.getSalary() > expRes.getSalary()) {
                expRes = r;
            }
        }
        int maxCost = 0;
        for (Task t : schedule.getTasks()) {
            maxCost += (t.getDuration() * expRes.getSalary());
        }
        return maxCost;
    }

    /**
     * Calculates normalized duration by dividing duration by max duration.
     *
     * @return normalized duration of the schedule
     */
    public double getDurationNormalized() {
        return (double) getDuration() / (double) getMaxDuration();
    }

    /**
     * Calculates normalized cost by dividing cost by max cost.
     *
     * @return normalized cost of the schedule
     */
    public double getCostNormalized() {
        return getCost() / (double) getMaxCost();
    }

}
