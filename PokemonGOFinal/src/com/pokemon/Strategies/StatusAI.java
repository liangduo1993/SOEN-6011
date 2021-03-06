package com.pokemon.Strategies;

import com.pokemon.Card.Card;
import com.pokemon.Card.Energy;
import com.pokemon.Card.Pokemon;
import com.pokemon.Card.Trainer;
import com.pokemon.Enums.CardCategory;
import com.pokemon.Enums.CardType;
import com.pokemon.Main.Enemy;
import com.pokemon.Main.GameInterface;
import com.pokemon.Main.ObjectHandler;
import com.pokemon.Main.Player;

public class StatusAI implements Strategy {
	private Enemy enemy;
	private Player player;
	private boolean hasHandBasic;
	private boolean hasHandStageone;
	private boolean hasEnergy;

	public StatusAI() {
		enemy = ObjectHandler.getEnemy();
		player = ObjectHandler.getPlayer();
		hasHandBasic = false;
		hasHandStageone = false;
		hasEnergy = false;
	}

	@Override
	public void turn() {
		if (GameInterface.turn == 1) {
			checkHandBasic();
			while (!checkHandBasic()) {
				System.out.println("----");
				enemy.getDeck().addAll(enemy.getHand());
				enemy.getHand().clear();
				enemy.shuffleDeck();
			}
			enemy.setPoke(getHandBasic());
			enemy.getHand().remove(getHandBasic());
		}
		if (GameInterface.turn > 1) {
			enemy.getHand().add(enemy.drawOneCard());
			checkHandBasic();
			Trainer t = null;
			while((t = getHandTrainer()) != null){
				t.getAbility().turn("player");
				enemy.getGraveyard().add(t);
				enemy.getHand().remove(t);
			}
			

			if (enemy.getPoke() == null && enemy.getBench().size() > 0) {
				enemy.setPoke(enemy.getBench().get(0));
				enemy.getBench().remove(0);
			}else if(enemy.getPoke() == null && enemy.getBench().size() == 0){
				checkHandBasic();
				enemy.setPoke(getHandBasic());
				enemy.getHand().remove(getHandBasic());
			}

			if (hasHandBasic && enemy.getBench().size() < 5) {
				enemy.getBench().add(getHandBasic());
				enemy.getHand().remove(getHandBasic());
			}

			while (checkHandEnergy()) {
				boolean flag = false;
				if (!flag && enemy.getPoke() != null && enemy.getPoke().getEnergys().size() < 4) {
					enemy.getPoke().addEnergy(getHandEnergy());
					enemy.getHand().remove(getHandEnergy());
					flag = true;
				}
				if (!flag && enemy.getBench().size() > 0 && enemy.getBench().get(0).getEnergys().size() < 3) {
					enemy.getBench().get(0).addEnergy(getHandEnergy());
					enemy.getHand().remove(getHandEnergy());
					flag = true;
				}
				if (!flag && enemy.getBench().size() > 1 && enemy.getBench().get(1).getEnergys().size() < 3) {
					enemy.getBench().get(1).addEnergy(getHandEnergy());
					enemy.getHand().remove(getHandEnergy());
					flag = true;
				}
				if (!flag && enemy.getBench().size() > 2 && enemy.getBench().get(2).getEnergys().size() < 3) {
					enemy.getBench().get(2).addEnergy(getHandEnergy());
					enemy.getHand().remove(getHandEnergy());
					flag = true;
				}
				if (!flag && enemy.getBench().size() > 3 && enemy.getBench().get(3).getEnergys().size() < 3) {
					enemy.getBench().get(3).addEnergy(getHandEnergy());
					enemy.getHand().remove(getHandEnergy());
					flag = true;
				}
				if (!flag && enemy.getBench().size() > 4 && enemy.getBench().get(4).getEnergys().size() < 3) {
					enemy.getBench().get(4).addEnergy(getHandEnergy());
					enemy.getHand().remove(getHandEnergy());
					flag = true;
				}
				if (!flag)
					break;
			}

			if (enemy.getPoke() != null && player.getPoke() != null && enemy.getPoke().isAttackable()) {
				Pokemon p = enemy.getPoke();
				if (enemy.getPoke().attackPlayer(2) || enemy.getPoke().attackPlayer(1)) {
					player.checkKnockout();
					enemy.checkKnockout();
				}
			}

		}
		System.out.println("Enemy has finished his turn!");
		GameInterface.turn++;
		GameInterface.playerTurn = true;
	}

	public boolean checkHandBasic() {
		for (Card c : enemy.getHand()) {
			if (c.getCardCategory() == CardCategory.Basic)
				hasHandBasic = true;
		}
		return hasHandBasic;
	}

	public Pokemon getHandBasic() {
		for (Card c : enemy.getHand()) {
			if (c.getCardCategory() == CardCategory.Basic) {
				hasHandBasic = false;
				return (Pokemon) c;
			}
		}
		return null;
	}

	public void checkHandStageone() {
		for (Card c : enemy.getHand()) {
			if (c.getCardCategory() == CardCategory.StageOne)
				hasHandStageone = true;
		}
	}

	public Pokemon getHandStageone() {
		for (Card c : enemy.getHand()) {
			if (c.getCardCategory() == CardCategory.StageOne) {
				hasHandStageone = false;
				return (Pokemon) c;
			}
		}
		return null;
	}

	public boolean checkHandEnergy() {
		hasEnergy = false;
		for (Card c : enemy.getHand()) {
			if (c.getCardType() == CardType.Engergy)
				hasEnergy = true;
		}
		return hasEnergy;
	}

	public Energy getHandEnergy() {
		for (Card c : enemy.getHand()) {
			if (c.getCardType() == CardType.Engergy) {
				hasEnergy = false;
				return (Energy) c;
			}
		}
		return null;
	}
	
	public Trainer getHandTrainer(){
		for (Card c : enemy.getHand()) {
			if (c.getCardType() == CardType.Trainer) {
				return (Trainer) c;
			}
		}
		return null;
	}

}
