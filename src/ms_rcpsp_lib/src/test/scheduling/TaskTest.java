package ms_rcpsp_lib.src.test.scheduling;

import ms_rcpsp_lib.src.msrcpsp.scheduling.Skill;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Task;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {

  @Test
  public void testToString() {
    Skill skill = new Skill("Coding", 1);
    int[] predecessors = {5, 7};
    Task task = new Task(1, skill, 10, 0, predecessors, 1);

    String result = "1, duration: 10, start: 0, required skills: Coding : 1, predecessors: 5 7 ";
    assertEquals("toString() returns a wrong string", result, task.toString());
  }

  @Test
  @SuppressWarnings({"equals()", "EqualsBetweenInconvertibleTypes"})
  public void testEqualsWrongObject() {
    Skill skill = new Skill("Coding", 1);
    int[] predecessors = {5, 7};
    Task firstTask = new Task(1, skill, 10, predecessors);
    String secondTask = "Second Task";

    assertFalse("Task instance should not be equal to an object of different class", firstTask.equals(secondTask));
  }

  @Test
  public void testEquals() {
    Skill skill = new Skill("Coding", 1);
    int[] predecessors = {5, 7};
    Task firstTask = new Task(1, skill, 10, 0, predecessors, 1);
    Task secondTask = new Task(2, null, 5, 1, null, 2);

    assertFalse("Tasks should be different", firstTask.equals(secondTask));

    secondTask.setDuration(firstTask.getDuration());
    assertFalse("Tasks should be different", firstTask.equals(secondTask));

    secondTask.setId(firstTask.getId());
    assertFalse("Tasks should be different", firstTask.equals(secondTask));

    secondTask.setRequiredSkills(firstTask.getRequiredSkills());
    assertFalse("Tasks should be different", firstTask.equals(secondTask));

    secondTask.setPredecessors(firstTask.getPredecessors());
    assertTrue("Tasks should be equal", firstTask.equals(secondTask));

    secondTask.setStart(firstTask.getStart());
    assertTrue("Tasks should be equal", firstTask.equals(secondTask));

    secondTask.setResourceId(firstTask.getResourceId());
    assertTrue("Tasks should be equal", firstTask.equals(secondTask));
  }

  @Test(expected=IllegalArgumentException.class)
  public void testCompareToWrongObject() {
    Skill skill = new Skill("Coding", 1);
    int[] predecessors = {5, 7};
    Task firstTask = new Task(1, skill, 10, predecessors);
    String secondTask = "Second Task";

    firstTask.compareTo(secondTask);
  }

  @Test
  public void testCompareTo() {
    Skill skill = new Skill("Coding", 1);
    int[] predecessors = {5, 7};
    Task firstTask = new Task(1, skill, 10, 0, predecessors, 1);
    Task secondTask = new Task(2, null, 5,  1, null, 2);

    assertEquals("First task starts earlier", -1, firstTask.compareTo(secondTask));

    firstTask.setStart(1);
    assertEquals("Tasks start at the same time", 0, firstTask.compareTo(secondTask));

    firstTask.setStart(2);
    assertEquals("Second task starts earlier", 1, firstTask.compareTo(secondTask));
  }

}