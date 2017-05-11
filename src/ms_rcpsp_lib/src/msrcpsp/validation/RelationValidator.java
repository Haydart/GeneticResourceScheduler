package ms_rcpsp_lib.src.msrcpsp.validation;


import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Task;

/**
 * Validates if solution violates precedence relations. Any task cannot be set
 * to start in the time earlier than all of its predecessors would be finished.
 */
public class RelationValidator extends BaseValidator {

    /**
     * Checks if relations constraint is satisfied.
     *
     * @param schedule schedule to validate
     * @return string with list of tasks ids that violate relation constraint
     * or null if schedule is valid
     */
    @Override
    public ValidationResult validate(Schedule schedule) {
        errorMessages.clear();
        String errorMessage = "";
        for (Task task : schedule.getTasks()) {
            if (task.getStart() < schedule.getEarliestTime(task)) {
                errorMessage += "Task id: " + task.getId() + ", start time: " + task.getStart()
                        + ", earliest possible time (including predecessors): "
                        + schedule.getEarliestTime(task) + "\n";
            }
        }
        if (errorMessage.isEmpty()) {
            return ValidationResult.SUCCESS;
        }
        errorMessages.add("Precedence relations constraint violated: \n" + errorMessage);
        return ValidationResult.FAILURE;
    }

}
