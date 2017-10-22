package main.java.net.petriv.developer.build.skills;

import main.java.net.petriv.developer.model.Skill;

public class DirectorSkill {
        SkillBuilder builder;

        public void setBuilder(SkillBuilder builder) {
            this.builder = builder;
        }

        public Skill buildSkill() {
           builder.createSkill();
           builder.buildId();
           builder.buildName();

            Skill skill = builder.getSkill();
            return skill;
        }
    }

