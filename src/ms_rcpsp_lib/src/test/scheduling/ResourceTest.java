package ms_rcpsp_lib.src.test.scheduling;

import ms_rcpsp_lib.src.msrcpsp.scheduling.Resource;
import ms_rcpsp_lib.src.msrcpsp.scheduling.Skill;
import org.junit.Test;

import static org.junit.Assert.*;


public class ResourceTest {

  @Test
  public void testToString() {
    Skill skills[] = {
        new Skill("Coding", 1)
    };
    Resource resource = new Resource(1, 10000, skills);
    String result = "1, 10000.0, Coding : 1 ";
    assertEquals("toString() returns a wrong string", result, resource.toString());
  }

  @Test
  @SuppressWarnings({"equals()", "EqualsBetweenInconvertibleTypes"})
  public void testEqualsWrongObject() {
    Resource firstresource = new Resource(0);
    String secondResource = "Second resource";

    assertFalse("Resource instance should not be equal to an object of different class", firstresource.equals(secondResource));
  }

  @Test
  public void testEquals() {
    Skill skills[] = {
        new Skill("Coding", 1)
    };
    Resource firstResource = new Resource(1, 10000, skills, 10);
    Resource secondResource = new Resource(2, 5000, null, 5);

    assertFalse("Resources should be different", firstResource.equals(secondResource));

    secondResource.setId(firstResource.getId());
    assertFalse("Resources should be different", firstResource.equals(secondResource));

    secondResource.setSalary(firstResource.getSalary());
    assertFalse("Resources should be different", firstResource.equals(secondResource));

    secondResource.setSkills(firstResource.getSkills());
    assertTrue("Resources should be equal", firstResource.equals(secondResource));

    secondResource.setFinish(firstResource.getFinish());
    assertTrue("Resources should be equal", firstResource.equals(secondResource));
  }

}