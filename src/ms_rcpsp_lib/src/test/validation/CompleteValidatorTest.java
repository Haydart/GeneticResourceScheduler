package ms_rcpsp_lib.src.test.validation;

import ms_rcpsp_lib.src.msrcpsp.io.MSRCPSPIO;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import ms_rcpsp_lib.src.msrcpsp.validation.BaseValidator;
import ms_rcpsp_lib.src.msrcpsp.validation.CompleteValidator;
import ms_rcpsp_lib.src.msrcpsp.validation.ValidationResult;
import org.junit.Test;

import static org.junit.Assert.*;


public class CompleteValidatorTest {

  @Test
  public void testValidate() {
    MSRCPSPIO reader = new MSRCPSPIO();

    Schedule schedule = reader.readDefinition("assets/test/10_7_10_7.def");
    assertNotNull("Schedule was not readDefinition correctly", schedule);

    BaseValidator validator = new CompleteValidator();
    assertEquals("Validation should result in a failure", ValidationResult.FAILURE,
        validator.validate(schedule));
    assertEquals("There should be 4 error messages", 4, validator.getErrorMessages().size());

    schedule.assign(schedule.getTask(1), schedule.getResource(7), 1);
    schedule.assign(schedule.getTask(5), schedule.getResource(1), 1);
    schedule.assign(schedule.getTask(6), schedule.getResource(3), 1);
    schedule.assign(schedule.getTask(2), schedule.getResource(4), 1);
    schedule.assign(schedule.getTask(3), schedule.getResource(2), 1);

    schedule.assign(schedule.getTask(9), schedule.getResource(2), 23);
    schedule.assign(schedule.getTask(4), schedule.getResource(5), 41);
    schedule.assign(schedule.getTask(7), schedule.getResource(7), 64);

    schedule.assign(schedule.getTask(8), schedule.getResource(3), 82);
    schedule.assign(schedule.getTask(10), schedule.getResource(6), 82);

    assertEquals("Validation should result in a success", ValidationResult.SUCCESS,
        validator.validate(schedule));
    assertEquals("There should be no error messages", 0, validator.getErrorMessages().size());
  }

}