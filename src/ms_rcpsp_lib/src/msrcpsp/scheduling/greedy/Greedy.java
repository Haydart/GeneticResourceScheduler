package ms_rcpsp_lib.src.msrcpsp.scheduling.greedy;


import ms_rcpsp_lib.src.msrcpsp.scheduling.Resource;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Task;

import java.util.Arrays;
import java.util.List;

/**
 * Completes a schedule in a greedy manner. Useful for when
 * representation of a problem is described either
 * by order of tasks or by assignments.
 * <p>
 * Designed to work with <code>assign()</code> methods
 * from <code>Schedule</code> class.
 */
public class Greedy {


    private boolean[] hasSuccessors;

    public Greedy() {

    }

    public Greedy(boolean[] hasSuccesors) {
        setHasSuccessors(hasSuccesors);
    }

    /**
     * Builds a schedule by assigning the cheapest resource to
     * each task and then setting an earliest available time
     * for it.
     *
     * @param schedule schedule to complete
     * @return complete schedule
     */
    public Schedule build(Schedule schedule) {
        for (Task task : schedule.getTasks()) {
            build(schedule, task);
        }

        return schedule;
    }

    /**
     * Sets timestamp of a task and assigns it to
     * a resource
     *
     * @param schedule schedule to complete
     * @param task     task to process
     * @return schedule with task assigned to resource
     * and timestamp
     */
    private Schedule build(Schedule schedule, Task task) {
        List<Resource> capableResources = schedule.getCapableResources(task);
        Resource candidateResource = schedule.findCheapestResource(capableResources);
        task.setResourceId(candidateResource.getId());
        int earliestTime = schedule.getEarliestTime(task);
        task.setStart(Math.max(candidateResource.getFinish(), earliestTime));
        candidateResource.setFinish(task.getStart() + task.getDuration());
        return schedule;
    }

    /**
     * Creates task / resource assignments, while leaving
     * order of tasks in place (timestamps might be moved a little bit
     * if no resources are available). Assumes that all start times
     * are set and predecessor constraint is not violated.
     *
     * @param schedule schedule to complete
     * @return complete schedule
     */
    public Schedule buildAssignments(Schedule schedule) {
        Arrays.sort(schedule.getTasks());
        for (Task task : schedule.getTasks()) {
            buildAssignments(schedule, task);
        }
        return schedule;
    }

    /**
     * Finds the cheapest resource available for the task and the point
     * of its start and assigns it. If there are none, assigns the first
     * available one and moves the start time.
     *
     * @param schedule schedule with the <code>task</code>
     * @param task     task to modify
     * @return assigned resource
     */
    public Resource buildAssignments(Schedule schedule, Task task) {
        task.setStart(schedule.getEarliestTime(task));
        List<Resource> capableResources = schedule.getCapableResources(task, task.getStart());
        Resource candidateResource = schedule.findCheapestResource(capableResources);
        if (null != candidateResource) {
            task.setResourceId(candidateResource.getId());
            candidateResource.setFinish(task.getStart() + task.getDuration());
        } else {
            capableResources = schedule.getCapableResources(task);
            candidateResource = schedule.findFirstFreeResource(capableResources);
            task.setResourceId(candidateResource.getId());
            task.setStart(candidateResource.getFinish());
            candidateResource.setFinish(task.getStart() + task.getDuration());
        }
        return candidateResource;
    }

    /**
     * Determines order of tasks by setting their start
     * and finish. Does not change task / resource assignment.
     * Assumes that all assignments are set. Uses knowledge about
     * successors of each task to first place the tasks with successors
     * and then rest of the tasks.
     *
     * @param schedule schedule to build
     * @return built schedule
     */
    public Schedule buildTimestamps(Schedule schedule) {
        Resource[] resources = schedule.getResources();
        for (Resource r : resources) {
            r.setFinish(0);
        }
        Task[] tasks = schedule.getTasks();
        Resource res;
        int start;
        // Assign tasks with relation requirements
        for (int i = 0; i < tasks.length; ++i) {
            if (hasSuccessors[i]) {
                res = resources[tasks[i].getResourceId() - 1];
                start = Math.max(schedule.getEarliestTime(tasks[i]),
                        res.getFinish());
                tasks[i].setStart(start);
                res.setFinish(start + tasks[i].getDuration());
            }
        }
        // Assign rest of the tasks
        for (int i = 0; i < tasks.length; ++i) {
            if (!hasSuccessors[i]) {
                res = resources[tasks[i].getResourceId() - 1];
                start = Math.max(schedule.getEarliestTime(tasks[i]),
                        res.getFinish());
                tasks[i].setStart(start);
                res.setFinish(start + tasks[i].getDuration());
            }
        }
        return schedule;
    }

    public boolean[] getHasSuccessors() {
        return hasSuccessors;
    }

    public void setHasSuccessors(boolean[] hasSuccessors) {
        this.hasSuccessors = hasSuccessors;
    }

}
