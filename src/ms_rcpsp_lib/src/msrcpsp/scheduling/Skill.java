package ms_rcpsp_lib.src.msrcpsp.scheduling;

/**
 * Defines skill existing in a project. Skill is an element linking resource and
 * task. Only a resource with equal or higher skill level than required for a task
 * can be assigned to that task.
 */
public class Skill {

    private String type;
    private int level;

    public Skill(String type, int level) {
        this.type = type;
        this.level = level;
    }

    public Skill() {
        this("", 0);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String toString() {
        return type + " : " + level;
    }

    /**
     * Compare two skills.
     *
     * @param s skill to compare to
     * @return true if this skill is equal to skill s
     */
    @Override
    public boolean equals(Object s) {
        if (!(s instanceof Skill)) {
            return false;
        }
        Skill skill = (Skill) s;
        return skill.type.equals(type) && skill.level == level;
    }

}
