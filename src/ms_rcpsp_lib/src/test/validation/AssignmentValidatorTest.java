package ms_rcpsp_lib.src.test.validation;

import ms_rcpsp_lib.src.msrcpsp.io.MSRCPSPIO;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Task;
import ms_rcpsp_lib.src.msrcpsp.validation.AssignmentValidator;
import ms_rcpsp_lib.src.msrcpsp.validation.BaseValidator;
import ms_rcpsp_lib.src.msrcpsp.validation.ValidationResult;
import org.junit.Test;

import static org.junit.Assert.*;


public class AssignmentValidatorTest {

  @Test
  public void testValidate() {
    MSRCPSPIO reader = new MSRCPSPIO();
    Schedule schedule = reader.readDefinition("assets/test/10_7_10_7.def");
    assertNotNull("Schedule was not readDefinition correctly", schedule);

    BaseValidator validator = new AssignmentValidator();
    assertEquals("Assignment constraint should be violated", ValidationResult.FAILURE, validator.validate(schedule));

    for (Task task : schedule.getTasks()) {
      schedule.assign(task, schedule.getResource(1));
    }

    assertEquals("Assignment constraint should not be violated",
        ValidationResult.SUCCESS, validator.validate(schedule));

  }

}