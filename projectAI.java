package guiProject;

import java.util.Random;

import javax.swing.JButton;

public class projectAI {

	private projectFrame frame;
	private JButton[] arr;

	private int movesMade;

	public projectAI(projectFrame _frame) {
		this.frame = _frame;
		movesMade = 0;
	}

	public int makeRandomMove(JButton[] _arr) {
		int[] arr = new int[(9 - movesMade)];
		Random generator = new Random();
		for (int i = 0, j = 0; i < _arr.length; i++) {
			if (_arr[i].getText().equals(" ")) {
				arr[j] = i;
				j++;
			}
		}
		int index = generator.nextInt(arr.length);
		movesMade += 2;
		//System.out.println(arr[index]);
		return arr[index];
	}

	public int makeEducatedMove(JButton[] _arr, boolean isX) {

		// strategy: change win condition for computer to win, if there is none do a
		// random move

		String player = isX ? "X" : "O";
		String computer = isX ? "O" : "X";
		int winConValPlayer = checkPlayerWinCondition(_arr, player);
		int winConValComputer = checkAIWinCondition(_arr,computer);
		if (winConValComputer != -1) {
			//System.out.println("Found a Computer win Condition");
			return winConValComputer;
		}
		if (winConValPlayer != -1) {
			//System.out.println("Found a Player win Condition");
			return winConValPlayer;
		}
		return makeRandomMove(_arr);
	}

	private int checkPlayerWinCondition(JButton[] _arr, String player) {
		int[][] winPatterns = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // horizontal
				{ 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // vertical
				{ 0, 4, 8 }, { 2, 4, 6 } // diagonal
		};

		for (int[] pattern : winPatterns) {
			int count = 0;
			int emptyIndex = -1;

			for (int i : pattern) {
				if (_arr[i].getText().equals(player)) {
					count++;
				} else if (_arr[i].getText().equals(" ")) {
					emptyIndex = i;
				}
			}

			if (count == 2 && emptyIndex != -1) {
				movesMade += 2;
				return emptyIndex;
			}
		}

		return -1;

	}
	private int checkAIWinCondition(JButton[] _arr, String computer) {
		int[][] winPatterns = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // horizontal
				{ 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // vertical
				{ 0, 4, 8 }, { 2, 4, 6 } // diagonal
		};

		for (int[] pattern : winPatterns) {
			int count = 0;
			int emptyIndex = -1;

			for (int i : pattern) {
				if (_arr[i].getText().equals(computer)) {
					count++;
				} else if (_arr[i].getText().equals(" ")) {
					emptyIndex = i;
				}
			}

			if (count == 2 && emptyIndex != -1) {
				movesMade += 2;
				return emptyIndex;
			}
		}

		return -1;

	}

}
