package guiProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class projectFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel masterPanel;
	private JLabel topLabel;
	private JButton selectX;
	private JButton selectO;
	private JButton[] grid;

	private boolean isX;
	private boolean needsClear;

	private projectAI playerAI;

	public projectFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		needsClear = false;

		playerAI = new projectAI(this);

		masterPanel = new projectPanel();
		masterPanel.setLayout(new BorderLayout());
		projectPanel gridPanel = new projectPanel();
		gridPanel.setLayout(new GridLayout(3, 3));

		projectPanel topPanel = new projectPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		projectPanel bottomPanel = new projectPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		grid = new JButton[9];
		for (int i = 0; i < grid.length; i++) {
			grid[i] = new JButton(" ");

			gridPanel.add(grid[i]);
			grid[i].setBackground(new Color(245,222,179));//new Color(72, 209, 204));
			grid[i].addActionListener(this);
			grid[i].setActionCommand(String.valueOf(i));
			grid[i].setFont(new Font("Arial", Font.BOLD, 20));
			grid[i].setForeground(Color.BLACK);
		}

		topLabel = new JLabel("Welcome to TikTakToe! Start Game or select player:");
		topLabel.setFont(new Font("Arial", Font.BOLD, 20));
		topPanel.add(topLabel);

		selectX = new JButton("X");
		selectX.addActionListener(this);
		selectX.setActionCommand("X");
		selectX.setBackground(Color.WHITE);
		selectO = new JButton("O");
		selectO.setActionCommand("O");
		selectO.addActionListener(this);
		selectO.setBackground(Color.WHITE);
		topPanel.add(selectX);
		topPanel.add(selectO);

		masterPanel.add(gridPanel, BorderLayout.CENTER);
		masterPanel.add(topPanel, BorderLayout.NORTH);
		masterPanel.add(bottomPanel, BorderLayout.SOUTH);

		this.setContentPane(masterPanel);
		this.setPreferredSize(new Dimension(900, 900));
		this.setTitle("TicTakToe");
		this.pack();
		
		//remove text box focus
		masterPanel.requestFocusInWindow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		String actionCommand = src.getActionCommand();

		switch (actionCommand) {
		case "X":
			isX = true;
			selectX.setEnabled(false);
			selectO.setEnabled(false);
			if (needsClear) {
				clearGrid();
			}
			break;
		case "O":
			isX = false;
			selectX.setEnabled(false);
			selectO.setEnabled(false);
			if (needsClear) {
				clearGrid();
			}
			break;
		default:
			int gridIndex = Integer.parseInt(actionCommand);
			String buttonText = isX ? "X" : "O";
			grid[gridIndex].setText(buttonText);
			checkWinStatus();
			grid[gridIndex].setEnabled(false);

			// computer move
			if (!(gridFull())) {
				int moveIndex = playerAI.makeEducatedMove(grid, isX); // random AI
				String aiChar = isX ? "O" : "X";
				grid[moveIndex].setText(aiChar);
				checkWinStatus();
				grid[moveIndex].setEnabled(false);
			} else {
				checkWinStatus();
			}

		}
	}

	private boolean gridFull() {
		int count = grid.length;
		for (JButton button : grid) {
			if (!(button.getText().equals(" ")))
				count--;
		}
		if (count == 0) {
			return true;
		}
		return false;
	}

	private void gameEnd() {
		needsClear = true;
		selectX.setEnabled(true);
		selectO.setEnabled(true);
	}

	private void clearGrid() {
		for (int i = 0; i < grid.length; i++) {
			grid[i].setText(" ");
			grid[i].setEnabled(true);
			checkWinStatus();

		}
		playerAI = new projectAI(this);
		needsClear = false;
		topLabel.setText("Good Luck!");
	}

	private void checkWinStatus() {
		
		// Check rows
		for (int i = 0; i <= 6; i += 3) {
			if (!grid[i].getText().equals(" ") && grid[i].getText().equals(grid[i + 1].getText())
					&& grid[i].getText().equals(grid[i + 2].getText())) {
				topLabel.setText("Game Over, "+grid[i].getText()+" Won by a Row! Choose Player to Restart: ");
				gameEnd();
				return;
			}
		}
		// Check columns
		for (int i = 0; i <= 2; i++) {
			if (!grid[i].getText().equals(" ") && grid[i].getText().equals(grid[i + 3].getText())
					&& grid[i].getText().equals(grid[i + 6].getText())) {
				topLabel.setText("Game Over, "+grid[i].getText()+" Won by a Column! Choose Player to Restart: ");
				gameEnd();
				return;
			}
		}
		// Check diagonals
		if (!grid[0].getText().equals(" ") && grid[0].getText().equals(grid[4].getText())
				&& grid[0].getText().equals(grid[8].getText())) {
			topLabel.setText("Game Over, "+grid[0].getText()+" Won by Right Diagonal! Choose Player to Restart: ");
			gameEnd();
			return;
		}
		if (!grid[2].getText().equals(" ") && grid[2].getText().equals(grid[4].getText())
				&& grid[2].getText().equals(grid[6].getText())) {
			topLabel.setText("Game Over, "+grid[2].getText()+" Won by Left Diagonal! Choose Player to Restart: ");
			gameEnd();
			return;
		}
		// Check if grid is full (draw)
		if (gridFull()) {
			topLabel.setText("It's a draw! Choose Player to Restart: ");
			needsClear = true;
			selectX.setEnabled(true);
			selectO.setEnabled(true);

		}

	}
	

}
