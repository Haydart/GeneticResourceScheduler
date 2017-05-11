package ms_rcpsp_lib.src.test.scheduling;

import ms_rcpsp_lib.src.msrcpsp.scheduling.Skill;
import org.junit.Test;

import static org.junit.Assert.*;


public class SkillTest {

  @Test
  public void testToString() {
    Skill skill = new Skill("Coding", 1);
    assertEquals("toString() returns a wrong string", "Coding : 1", skill.toString());
  }

  @Test
  @SuppressWarnings({"equals()", "EqualsBetweenInconvertibleTypes"})
    public void testEqualsWrongObject() {
    Skill firstSkill = new Skill();
    String secondSkill = "Second skill";

    assertFalse("Skill instance should not be equal to an object of different class", firstSkill.equals(secondSkill));
  }

  @Test
  public void testEquals() {
    Skill firstSkill = new Skill("Coding", 1);
    Skill secondSkill = new Skill("ms_rcpsp_lib", 5);

    assertFalse("Skills should be different", firstSkill.equals(secondSkill));

    secondSkill.setType(firstSkill.getType());
    assertFalse("Skills should be different", firstSkill.equals(secondSkill));

    secondSkill.setLevel(firstSkill.getLevel());
    assertTrue("Skills should be equal", firstSkill.equals(secondSkill));
  }

}