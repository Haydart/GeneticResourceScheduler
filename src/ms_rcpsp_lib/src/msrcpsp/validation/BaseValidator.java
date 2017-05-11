package ms_rcpsp_lib.src.msrcpsp.validation;

import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base for all validators. Contains an array of error messages.
 */
abstract public class BaseValidator {

    protected List<String> errorMessages;

    public BaseValidator() {
        errorMessages = new ArrayList<>();
    }

    /**
     * Validates given schedule.
     *
     * @param schedule schedule to validate
     * @return error message, empty if schedule is valid
     */
    abstract public ValidationResult validate(Schedule schedule);

    public List<String> getErrorMessages() {
        return errorMessages;
    }

}
