package ms_rcpsp_lib.src.test.scheduling.schedule_builders;

import ms_rcpsp_lib.src.msrcpsp.io.MSRCPSPIO;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import ms_rcpsp_lib.src.msrcpsp.scheduling.greedy.Greedy;
import ms_rcpsp_lib.src.msrcpsp.validation.BaseValidator;
import ms_rcpsp_lib.src.msrcpsp.validation.CompleteValidator;
import ms_rcpsp_lib.src.msrcpsp.validation.ValidationResult;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduleBuilderTest {

  @Test
  public void testBuildAssignments() {
    MSRCPSPIO reader = new MSRCPSPIO();
    Schedule schedule = reader.readDefinition("assets/test/10_7_10_7.def");
    assertNotNull("Schedule was not readDefinition correctly", schedule);

    schedule.assign(schedule.getTask(2), 1);
    schedule.assign(schedule.getTask(1), 2);
    schedule.assign(schedule.getTask(3), 3);
    schedule.assign(schedule.getTask(5), 4);
    schedule.assign(schedule.getTask(6), 5);

    schedule.assign(schedule.getTask(9), 6);
    schedule.assign(schedule.getTask(8), 7);
    schedule.assign(schedule.getTask(4), 8);
    schedule.assign(schedule.getTask(7), 9);
    schedule.assign(schedule.getTask(10), 10);

    Greedy scheduleBuilder = new Greedy();
    BaseValidator validator = new CompleteValidator();

    scheduleBuilder.buildAssignments(schedule);
    assertEquals("Schedule should be valid", ValidationResult.SUCCESS, validator.validate(schedule));
  }

  @Test
  public void testBuildTimestamp() {
    MSRCPSPIO reader = new MSRCPSPIO();
    Schedule schedule = reader.readDefinition("assets/test/10_7_10_7.def");
    assertNotNull("Schedule was not readDefinition correctly", schedule);

    schedule.assign(schedule.getTask(1), schedule.getResource(5));
    schedule.assign(schedule.getTask(2), schedule.getResource(3));
    schedule.assign(schedule.getTask(3), schedule.getResource(1));
    schedule.assign(schedule.getTask(4), schedule.getResource(7));
    schedule.assign(schedule.getTask(5), schedule.getResource(2));

    schedule.assign(schedule.getTask(6), schedule.getResource(4));
    schedule.assign(schedule.getTask(7), schedule.getResource(5));
    schedule.assign(schedule.getTask(8), schedule.getResource(3));
    schedule.assign(schedule.getTask(9), schedule.getResource(2));
    schedule.assign(schedule.getTask(10), schedule.getResource(1));

    Greedy scheduleBuilder = new Greedy();
    BaseValidator validator = new CompleteValidator();

    scheduleBuilder.buildTimestamps(schedule);
    assertEquals("Schedule should be valid", ValidationResult.SUCCESS, validator.validate(schedule));
  }

}