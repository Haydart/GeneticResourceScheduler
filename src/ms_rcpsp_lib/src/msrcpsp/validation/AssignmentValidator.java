package ms_rcpsp_lib.src.msrcpsp.validation;


import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Task;

/**
 * Checks if all tasks have at least one resource assigned.
 */
public class AssignmentValidator extends BaseValidator {

    /**
     * Check if all tasks have at least one resource assigned.
     *
     * @param schedule schedule to validate
     * @return ValidationResult, additionally stores error message in an array
     */
    @Override
    public ValidationResult validate(Schedule schedule) {
        errorMessages.clear();
        String errorMessage = "";
        for (Task t : schedule.getTasks()) {
            if (t.getResourceId() == -1) {
                errorMessage += t.getId() + ", ";
            }
        }
        if (errorMessage.isEmpty()) {
            return ValidationResult.SUCCESS;
        }
        errorMessages.add("Assignment constraint violated by tasks: " + errorMessage);
        return ValidationResult.FAILURE;
    }

}
