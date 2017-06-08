package com.pokemon.Abilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.pokemon.Main.ObjectHandler;

public class condAbility extends Abilities {
	private Abilities condAbility, elseAbility;
	private String cond;

	public condAbility(String line) {
		super();
		this.condAbility = null;
		this.elseAbility = null;

		if (line.substring(line.indexOf(":") + 1).equals("flip")) {

			String condA = line.substring(line.indexOf(":") + 1);
			this.cond = line.substring(0, line.indexOf(":"));
			if (!line.contains("else")) {
				String abilityName = condA.substring(0, condA.indexOf(":"));
				String abilityLine = condA.substring(condA.indexOf(":") + 1);

				Object o = null;
				Constructor<?> con = null;
				Class<?> classType = null;
				try {
					classType = Class.forName("com.pokemon.Abilities." + abilityName + "Ability");
					con = classType.getConstructor(String.class);
					o = con.newInstance(abilityLine);
					this.condAbility = (Abilities) o;

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}

			} else {

				String[] abilities = condA.split(":else:");
				String condAbilityName = abilities[0].substring(0, abilities[0].indexOf(":"));
				String condAbilityLine = abilities[0].substring(abilities[0].indexOf(":") + 1);

				Object o = null;
				Constructor<?> con = null;
				Class<?> classType = null;
				try {
					classType = Class.forName("com.pokemon.Abilities." + condAbilityName + "Ability");
					con = classType.getConstructor(String.class);
					o = con.newInstance(condAbilityLine);
					this.condAbility = (Abilities) o;

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}

				String elseAbilityName = abilities[1].substring(0, abilities[1].indexOf(":"));
				String elseAbilityLine = abilities[1].substring(abilities[1].indexOf(":") + 1);

				o = null;
				con = null;
				classType = null;
				try {
					classType = Class.forName("com.pokemon.Abilities." + elseAbilityName + "Ability");
					con = classType.getConstructor(String.class);
					o = con.newInstance(elseAbilityLine);
					this.elseAbility = (Abilities) o;

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}

		}

		if (line.contains("healed")) {
			String[] ss = line.split(":");
			this.cond = ss[0].concat(":").concat(ss[1]).concat(":").concat(ss[2]);
			String condA = line.substring(cond.length() + 1);
		
			if (!line.contains("else")) {
				String abilityName = condA.substring(0, condA.indexOf(":"));
				String abilityLine = condA.substring(condA.indexOf(":") + 1);

				Object o = null;
				Constructor<?> con = null;
				Class<?> classType = null;
				try {
					classType = Class.forName("com.pokemon.Abilities." + abilityName + "Ability");
					con = classType.getConstructor(String.class);
					o = con.newInstance(abilityLine);
					this.condAbility = (Abilities) o;

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}

			}
			
			
			
			
			
		}

	}

	public void turn(String target) {
		if (checkCond(target))
			this.condAbility.turn(target);
		else if (this.elseAbility != null) {
			this.elseAbility.turn(target);
		}
		return;
	}

	public boolean checkCond(String target) {
		if (this.cond.equals("flip")) {
			return Math.random() * 2 > 1;
		}

		if (this.cond.contains("healed")) {
			String[] conds = cond.split(":");
			switch (conds[2]) {
			case "your-active":
				if("enemy".equals(target) && ObjectHandler.getPlayer().getPoke().isHealed()){
					return true;
				}
				if("player".equals(target) && ObjectHandler.getEnemy().getPoke().isHealed()){
					return true;
				}
				break;

			default:
				break;
			}
			
		}

		return false;
	}

}
