package ms_rcpsp_lib.src.msrcpsp.validation;


import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Task;

import java.util.LinkedList;
import java.util.List;

/**
 * Validates a schedule by appearance of conflicts between tasks assigned to
 * given resource. If any resource has more than one task assigned in
 * overlapping periods, the conflict is noticed.
 */
public class ConflictValidator extends BaseValidator {

    private List<Conflict> conflictsCollection;

    public ConflictValidator() {
        conflictsCollection = new LinkedList<>();
    }

    /**
     * Checks whether a resource exists, which is assigned to multiple tasks
     * in the same period of time.
     *
     * @param schedule schedule to validate
     * @return ValidationResult, additionally stores error message in an array
     */
    @Override
    public ValidationResult validate(Schedule schedule) {
        errorMessages.clear();
        conflictsCollection.clear();
        String errorMessage;
        Task[] tasks = schedule.getTasks();
        for (Task firstTask : tasks) {
            for (Task secondTask : tasks) {

                if (firstTask.getId() != secondTask.getId() &&
                        firstTask.getResourceId() == secondTask.getResourceId() &&
                        firstTask.getStart() <= secondTask.getStart() &&
                        (firstTask.getStart() + firstTask.getDuration()) > secondTask.getStart()) {
                    Conflict conflict = new Conflict(firstTask.getResourceId(), firstTask.getId(), secondTask.getId());
                    if (!conflictsCollection.contains(conflict)) {
                        conflictsCollection.add(conflict);
                    }
                }

            }
        }

        if (conflictsCollection.size() != 0) {
            errorMessage = buildErrorMessage(schedule);
            errorMessages.add(errorMessage);
            return ValidationResult.FAILURE;
        }
        return ValidationResult.SUCCESS;
    }

    private String buildErrorMessage(Schedule schedule) {
        String errorMessage = "Conflicts detected! \n";
        for (Conflict c : conflictsCollection) {
            errorMessage += "Resource id: " + c.resourceId
                    + ", Task id: " + c.firstTaskId
                    + ", Start time: " + schedule.getTask(c.firstTaskId).getStart()
                    + ", Finish time: "
                    + (schedule.getTask(c.firstTaskId).getStart() + schedule.getTask(c.firstTaskId).getDuration())
                    + ", task id: " + c.secondTaskId
                    + ", start time: " + schedule.getTask(c.secondTaskId).getStart()
                    + ", finish time: "
                    + (schedule.getTask(c.secondTaskId).getStart() + schedule.getTask(c.secondTaskId).getDuration())
                    + "\n";
        }
        return errorMessage;
    }

    private class Conflict {

        private int resourceId;
        private int firstTaskId;
        private int secondTaskId;

        private Conflict(int resourceId, int firstTaskId, int secondTaskId) {
            this.resourceId = resourceId;
            this.firstTaskId = firstTaskId;
            this.secondTaskId = secondTaskId;
        }

    }

}
