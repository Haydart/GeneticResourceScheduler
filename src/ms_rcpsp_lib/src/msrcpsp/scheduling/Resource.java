package ms_rcpsp_lib.src.msrcpsp.scheduling;

import java.util.Arrays;

/**
 * Defines resource in a project. Resource can be assigned to task if it has
 * required skill at no lower than required level. It is also defined by salary.
 * To make design easier, resource is also described by finish field - the time
 * when resource finished its last assigned task.
 */
public class Resource implements Cloneable
{

    private int id;
    private double salary;
    private Skill[] skills;
    private int finish;

    public Resource(int id, double salary, Skill[] skills, int finish) {
        this.id = id;
        this.salary = salary;
        this.skills = skills;
        this.finish = finish;
    }

    public Resource(double salary) {
        this(-1, salary, null, -1);
    }

    public Resource(int id, double salary, Skill[] skills) {
        this.id = id;
        this.salary = salary;
        this.skills = skills;
    }

    /**
     * Determines whether this resource has a skill.
     *
     * @param skill skill to check for
     * @return true if resource has a skill, false otherwise
     */
    public boolean hasSkill(Skill skill) {
        for (Skill s : skills) {
            if (s.getType().equals(skill.getType()) &&
                    s.getLevel() >= skill.getLevel()) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Skill[] getSkills() {
        return skills;
    }

    public void setSkills(Skill[] skills) {
        this.skills = skills;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String toString() {
        String res = "";
        for (Skill s : skills) {
            res += s + " ";
        }
        return id + ", " + salary + ", " + res;
    }

    /**
     * Compare two resources.
     *
     * @param r resource to compare to
     * @return true if this resource is equal to resource r
     */
    @Override
    public boolean equals(Object r) {
        if (!(r instanceof Resource)) {
            return false;
        }
        Resource resource = (Resource) r;
        return id == resource.id &&
                salary == resource.salary &&
                Arrays.equals(skills, resource.skills);
    }

}
