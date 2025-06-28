package gameUi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class GameUi extends JFrame {
	private static final long serialVersionUID = 1L;
	private String playerChoice;
	private JButton cutButton, paperButton, rockButton;
	private int playerScore = 0, botScore = 0;

	public GameUi() {
		setTitle("Rock paper scissors");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		ImageIcon icon = new ImageIcon("src/resources/icon.png");
		setIconImage(icon.getImage());
		ImageIcon backgroundImage = new ImageIcon("src/resources/background.jpg");
		backgroundImage = new ImageIcon(backgroundImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH));
		setContentPane(new JLabel(backgroundImage));
		setLayout(new BorderLayout());
		JLabel titleLabel = new JLabel("Rock Paper Scissors", JLabel.CENTER);
		titleLabel.setFont(titleLabel.getFont().deriveFont(48f));
		titleLabel.setForeground(Color.orange);
		add(titleLabel, BorderLayout.NORTH);
		JPanel botOutputPanel = new JPanel(new BorderLayout());
		botOutputPanel.setOpaque(false);
		JLabel botOutputLabel = new JLabel("Bot Output", JLabel.CENTER);
		botOutputLabel.setFont(botOutputLabel.getFont().deriveFont(24f));
		botOutputLabel.setForeground(Color.white);
		botOutputPanel.add(botOutputLabel, BorderLayout.NORTH);
		JLabel botOutputImage = new JLabel(new ImageIcon("src/resources/dauhoi.png"));
		// auto resize the image
		botOutputImage.setIcon(new ImageIcon(
				((ImageIcon) botOutputImage.getIcon()).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
		botOutputImage.setVerticalAlignment(JLabel.NORTH);
		botOutputPanel.add(botOutputImage, BorderLayout.CENTER);
		JLabel score = new JLabel("Player: 0 - Bot: 0", JLabel.CENTER);
		score.setFont(score.getFont().deriveFont(24f));
		score.setForeground(Color.black);
		botOutputPanel.add(score, BorderLayout.SOUTH);
		JPanel playerPanel = new JPanel(new BorderLayout());
		playerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		playerPanel.setOpaque(false);
		JLabel playerLabel = new JLabel("Choose one of them", JLabel.CENTER);
		playerLabel.setFont(playerLabel.getFont().deriveFont(24f));
		playerLabel.setForeground(Color.white);
		playerPanel.add(playerLabel, BorderLayout.NORTH);
		JPanel playerChoicePanel = new JPanel();
		ImageIcon cut = new ImageIcon("src/resources/cut.png");
		// auto resize the image
		cut = new ImageIcon(cut.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		ImageIcon paper = new ImageIcon("src/resources/paper.png");
		// auto resize the image
		paper = new ImageIcon(paper.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		ImageIcon rock = new ImageIcon("src/resources/rock.png");
		// auto resize the image
		rock = new ImageIcon(rock.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		playerChoicePanel.setOpaque(false);
		cutButton = new JButton(cut);
		cutButton.setOpaque(false);
		cutButton.setContentAreaFilled(false);
		cutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playerChoice = "cut";
				paperButton.setEnabled(false);
				rockButton.setEnabled(false);
				playSound("src/resources/button.wav");
			}
		});
		paperButton = new JButton(paper);
		paperButton.setOpaque(false);
		paperButton.setContentAreaFilled(false);
		paperButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playerChoice = "paper";
				cutButton.setEnabled(false);
				rockButton.setEnabled(false);
				playSound("src/resources/button.wav");
			}
		});
		rockButton = new JButton(rock);
		rockButton.setOpaque(false);
		rockButton.setContentAreaFilled(false);
		rockButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playerChoice = "rock";
				cutButton.setEnabled(false);
				paperButton.setEnabled(false);
				playSound("src/resources/button.wav");
			}
		});
		playerChoicePanel.add(cutButton);
		playerChoicePanel.add(paperButton);
		playerChoicePanel.add(rockButton);
		playerPanel.add(playerChoicePanel, BorderLayout.CENTER);
		JPanel thaotac = new JPanel();
		thaotac.setOpaque(false);
		JButton playButton = new JButton("Play");
		playButton.setPreferredSize(new Dimension(100, 50));
		playButton.setBackground(Color.green);
		playButton.setForeground(Color.white);
		playButton.setFocusPainted(false);
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (playerChoice == null) {
					playSound("src/resources/error.wav");
					JOptionPane.showMessageDialog(GameUi.this, "Please choose one of them", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					String[] choices = { "cut", "paper", "rock" };
					String botChoice = choices[(int) (Math.random() * choices.length)];
					botOutputImage.setIcon(new ImageIcon("src/resources/" + botChoice + ".png"));
					botOutputImage.setIcon(new ImageIcon(((ImageIcon) botOutputImage.getIcon()).getImage()
							.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
					if (playerChoice.equals(botChoice)) {
						playSound("src/resources/draw.wav");
						JOptionPane.showMessageDialog(GameUi.this, "Draw!", "Result",
								JOptionPane.INFORMATION_MESSAGE);
						cutButton.setEnabled(true);
						paperButton.setEnabled(true);
						rockButton.setEnabled(true);
						playerChoice = null;
					} else if ((playerChoice.equals("cut") && botChoice.equals("paper"))
							|| (playerChoice.equals("paper") && botChoice.equals("rock"))
							|| (playerChoice.equals("rock") && botChoice.equals("cut"))) {
						playSound("src/resources/win.wav");
						JOptionPane.showMessageDialog(GameUi.this, "You win!", "Result",
								JOptionPane.INFORMATION_MESSAGE);
						cutButton.setEnabled(true);
						paperButton.setEnabled(true);
						rockButton.setEnabled(true);
						playerChoice = null;
						playerScore++;
						score.setText("Player: " + playerScore + " - Bot: " + botScore);
					} else {
						playSound("src/resources/lose.wav");
						JOptionPane.showMessageDialog(GameUi.this, "You lose!", "Result",
								JOptionPane.INFORMATION_MESSAGE);
						cutButton.setEnabled(true);
						paperButton.setEnabled(true);
						rockButton.setEnabled(true);
						playerChoice = null;
						botScore++;
						score.setText("Player: " + playerScore + " - Bot: " + botScore);
					}
				}
			}
		});
		JButton reChoice = new JButton("Re-choose");
		reChoice.setPreferredSize(new Dimension(100, 50));
		reChoice.setBackground(Color.blue);
		reChoice.setForeground(Color.white);
		reChoice.setFocusPainted(false);
		reChoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("src/resources/button.wav");
				playerChoice = null;
				cutButton.setEnabled(true);
				paperButton.setEnabled(true);
				rockButton.setEnabled(true);
				botOutputImage.setIcon(new ImageIcon("src/resources/dauhoi.png"));
				botOutputImage.setIcon(new ImageIcon(((ImageIcon) botOutputImage.getIcon()).getImage()
						.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
			}
		});
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				playSound("src/resources/button.wav");
				playerChoice = null;
				cutButton.setEnabled(true);
				paperButton.setEnabled(true);
				rockButton.setEnabled(true);
				botOutputImage.setIcon(new ImageIcon("src/resources/dauhoi.png"));
				botOutputImage.setIcon(new ImageIcon(((ImageIcon) botOutputImage.getIcon()).getImage()
						.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
				playerScore = 0;
				botScore = 0;
				score.setText("Player: 0 - Bot: 0");
			}
		});
		resetButton.setPreferredSize(new Dimension(100, 50));
		resetButton.setBackground(Color.red);
		resetButton.setForeground(Color.white);
		resetButton.setFocusPainted(false);
		thaotac.add(playButton);
		thaotac.add(reChoice);
		thaotac.add(resetButton);
		playerPanel.add(thaotac, BorderLayout.SOUTH);
		add(botOutputPanel, BorderLayout.CENTER);
		add(playerPanel, BorderLayout.SOUTH);

	}

	private void playSound(String soundFilePath) {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					File soundFile = new File(soundFilePath);
					if (!soundFile.exists()) {
						System.err.println("Sound file not found: " + soundFilePath);
						return null;
					}

					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
					Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
					clip.addLineListener(event -> {
					    if (event.getType() == LineEvent.Type.STOP) {
					        clip.close();
					    }
					});
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
					ex.printStackTrace();
					System.err.println("Error playing sound: " + ex.getMessage());
				}
				return null;
			}

			@Override
			protected void done() {
			}
		}.execute();
	}
}
