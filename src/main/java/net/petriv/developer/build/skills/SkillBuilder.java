package main.java.net.petriv.developer.build.skills;

import main.java.net.petriv.developer.model.Skill;

public abstract class SkillBuilder {
    Skill skill;

    public void createSkill() {
        skill = new Skill();
    }

    abstract void buildId();

    abstract void buildName();


    Skill getSkill() {
        return skill;
    }
}

