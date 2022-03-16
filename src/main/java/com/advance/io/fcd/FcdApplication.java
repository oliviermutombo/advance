package com.advance.io.fcd;

import com.advance.io.fcd.exceptions.OutOfCardsException;
import com.advance.io.fcd.services.impl.GameService;
import com.advance.io.fcd.utils.ConsoleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

/**
 * <h1>Five Card Draw Game</h1>
 * This variant of the five card game (fcd) has been implemented as part of an assessment
 * by advance and provides the following functionality:
 * <ul>
 * <li>Simulate shuffling a standard deck of 52 cards.</li>
 * <li>Deal a single hand of 5 cards to the player.</li>
 * <li>Evaluate the player’s hand, informing them of the highest ranked poker hand that
 * matches their hand of 5 cards.</li>
 * </ul>
 *
 * <b>Note:</b>
 * <ul>
 * <li>This is a single-user game designed to allow extensibility to a
 * server side application in future versions.</li>
 * <li>Few of the algorithms made use of external resources but they've been adjusted to fit
 * advance.io requirements.</li>
 * </ul>
 *
 * For more information, visit www.advance.io
 *
 * @author  Olivier Mutombo
 * @version 1.0
 * @since   2022-03-14
 */
@SpringBootApplication
@Profile("!test")
public class FcdApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(FcdApplication.class);

	@Autowired
	GameService gameService;

	@Autowired
	ConsoleUtil consoleUtil;

	/**
	 * This is the main method which makes use of run method.
	 * @param args Optional! If set, the first parameter is the player's name.
	 */
	public static void main(String[] args) {
		SpringApplication.run(FcdApplication.class, args);
	}

	/**
	 * Custom implementation of the run method in CommandLineRunner interface.
	 *
	 * @param args Arguments from calling java main method.
	 * @throws Exception Exception
	 */
	@Override
	public void run(String... args) throws Exception {

		String name = "";
		if (args.length == 0) {
			name = consoleUtil.answerToPrompt("Please enter your name:");
		} else {
			name = args[0];
		}
		consoleUtil.info("Hi " + name);
		startNewGame();
	}

	/**
	 * This method starts a new game.
	 * You're given choices to either play just a couple of rounds or play until the deck is empty.
	 * You can stop the game at anytime.
	 *
	 * @throws IOException IOException
	 */
	private void startNewGame() throws IOException {
		boolean newGame = true;
		boolean dealMore = true;
		do {
			try {
				if (newGame) {
					consoleUtil.info("Sit back and relax while I shuffle the deck...");
				}
				consoleUtil.play(gameService.play(newGame));
				newGame = false;
				dealMore = consoleUtil.agreedToPrompt("Deal more cards? (Y/N)");
				if (!dealMore) {
					consoleUtil.exitGame(0);
				}
			} catch(OutOfCardsException ex) {
				log.warn(ex.getMessage());
				if(consoleUtil.agreedToPrompt("The deck is out of cards!\nStart a new game? (Y/N)")) {
					startNewGame();
				} else {
					consoleUtil.exitGame(0);
				}
			} catch (Exception ex) {
				log.error(ex.getMessage());
				consoleUtil.exitGame(1);
			}
		}while(!newGame || dealMore);
	}
}
