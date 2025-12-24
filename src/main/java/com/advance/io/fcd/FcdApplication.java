package com.advance.io.fcd;

import com.advance.io.fcd.exceptions.OutOfCardsException;
import com.advance.io.fcd.models.VideoResult;
import com.advance.io.fcd.services.impl.GameService;
import com.advance.io.fcd.utils.ConsoleUtil;
import com.advance.io.fcd.utils.VideoGenerationUtil;
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

	@Autowired
	VideoGenerationUtil videoGenerationUtil;

	/**
	 * This is the main method which makes use of run method.
	 * @param args Optional! If set, the first parameter is the player's name or --video-gen to generate videos.
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
		// Check if video generation mode
		if (args.length > 0 && "--video-gen".equals(args[0])) {
			runVideoGenerator(args);
		} else {
			runPokerGame(args);
		}
	}

	/**
	 * Run the video generator.
	 *
	 * @param args Command line arguments
	 */
	private void runVideoGenerator(String... args) {
		if (args.length < 2) {
			printVideoGenUsage();
			System.exit(1);
			return;
		}

		String imagePath = args[1];
		String outputPath = args.length > 2 ? args[2] : null;
		int duration = args.length > 3 ? Integer.parseInt(args[3]) : 5;
		int frameRate = args.length > 4 ? Integer.parseInt(args[4]) : 30;

		log.info("=== Video Generator ===");
		log.info("Image: " + imagePath);
		log.info("Duration: " + duration + " seconds");
		log.info("Frame Rate: " + frameRate + " fps");
		if (outputPath != null) {
			log.info("Output: " + outputPath);
		}

		VideoResult result = videoGenerationUtil.generateVideo(imagePath, outputPath, duration, frameRate);

		if (result.isSuccess()) {
			System.out.println("\n✓ SUCCESS: " + result.getMessage());
			System.out.println("Video saved to: " + result.getOutputPath());
			System.exit(0);
		} else {
			System.err.println("\n✗ FAILED: " + result.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Run the poker game.
	 *
	 * @param args Command line arguments
	 * @throws Exception Exception
	 */
	private void runPokerGame(String... args) throws Exception {
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
	 * Print usage for video generation.
	 */
	private void printVideoGenUsage() {
		System.out.println("Video Generator - Generate videos from static images");
		System.out.println("\nUsage:");
		System.out.println("  java -jar fcd-0.0.1-SNAPSHOT.jar --video-gen <imagePath> [outputPath] [duration] [frameRate]");
		System.out.println("\nArguments:");
		System.out.println("  imagePath   : Path to the input image file (required)");
		System.out.println("  outputPath  : Path where the video will be saved (optional)");
		System.out.println("  duration    : Duration of the video in seconds (optional, default: 5)");
		System.out.println("  frameRate   : Frame rate of the video (optional, default: 30)");
		System.out.println("\nExample:");
		System.out.println("  java -jar fcd-0.0.1-SNAPSHOT.jar --video-gen /path/to/image.jpg");
		System.out.println("  java -jar fcd-0.0.1-SNAPSHOT.jar --video-gen /path/to/image.jpg /path/to/output.mp4 10 24");
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
