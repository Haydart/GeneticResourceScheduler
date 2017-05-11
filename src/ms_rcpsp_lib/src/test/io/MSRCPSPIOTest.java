package ms_rcpsp_lib.src.test.io;

import ms_rcpsp_lib.src.msrcpsp.io.MSRCPSPIO;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Schedule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MSRCPSPIOTest {

  @Test
  public void testReadNoFile() {
    MSRCPSPIO reader = new MSRCPSPIO();
    Schedule schedule = reader.readDefinition("wrong_path");
    assertNull("Schedule was not null", schedule);
  }

  @Test
  public void testRead() {
    MSRCPSPIO reader = new MSRCPSPIO();
    Schedule schedule = reader.readDefinition("assets/test/10_7_10_7.def");
    assertNotNull("Schedule is null", schedule);

    assertEquals("Wrong number of tasks", 10, schedule.getTasks().length);
    assertEquals("Wrong number of resources", 7, schedule.getResources().length);

    assertEquals("Wrong salary of the last resource", schedule.getResource(7).getSalary(), 89.9, 0.0);
    assertEquals("Wrong last skill of last resource", schedule.getResource(7).getSkills()[3].getType(), "Q2");

    assertEquals("Wrong duration of the last task", schedule.getTask(10).getDuration(), 23);
    assertEquals("Wrong last predecessor of the 7th task", schedule.getTask(7).getPredecessors()[3], 4);
  }

}