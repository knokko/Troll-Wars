/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package nl.knokko.designer.dialogue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nl.knokko.designer.dialogue.ChoisePartBuilder.Choise;
import nl.knokko.story.dialogue.ChoiseDialogueText;
import nl.knokko.story.dialogue.DialogueText;
import nl.knokko.story.dialogue.Portraits;
import nl.knokko.story.dialogue.SimpleDialogueText;
import nl.knokko.texture.ImageTexture;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitInputStream;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.bits.BitOutputStream;
import nl.knokko.util.color.ColorSpecial;
import nl.knokko.util.resources.Resources;

public class DialogueDesigner {
	
	static final StringTyper NAME_TYPER = new StringTyper("Nameless"){

		@Override
		void set(String value) {
			name = value;
		}

		@Override
		String get() {
			return name;
		}
		
		@Override
		boolean isAllowed(char c){
			return isValidNameChar(c);
		}
		
		@Override
		void done(){
			super.done();
			save(name);
		}
		
		@Override
		boolean isValid(String name){
			return super.isValid(name) && !name.isEmpty() && !name.equals("Nameless");
		}
	};
	
	static String name;
	static DialogueBuilder dialogue;
	static Panel panel;
	static Frame frame;
	
	static StringTyper stringTyper;
	static FontSelector fontSelector;
	static SpecialColorSelector specialColorSelector;
	static PortraitSelector portraitSelector;
	static DialogueLinker dialogueLinker;
	
	static int viewX;
	static int viewY;

	public static void main(String[] args) {
		new File("dialogues").mkdir();
		panel = new Panel();
		frame = new Frame();
	}
	
	static void load(String name){
		try {
			DialogueDesigner.name = name;
			BitInput input = new BitInputStream(new FileInputStream(new File("dialogues/" + name + ".dalb")));
			dialogue = new DialogueBuilder(input);
			input.terminate();
		} catch(IOException ex){
			System.out.println("Can't find dialogue (" + ex.getMessage() + "); creating new one");
		}
	}
	
	static void save(String name){
		try {
			BitOutput output = new BitOutputStream(new FileOutputStream(new File("dialogues/" + name + ".dalb")));
			dialogue.save(output);
			//output.save(new File("dialogues/" + name + ".dalb"));
			output.terminate();
		} catch(IOException ex){
			System.out.println("Failed to save dialogue " + name + ":");
			ex.printStackTrace();
		}
	}
	
	static void backUp(){
		/*
		if(dialogue != null && dialogue.parts.size() > 0){
			BitBuffer data = new BitBuffer();
			dialogue.save(data);
			try {
				data.save(new File("dialogues/back-up " + System.currentTimeMillis() + ".dalb"));
			} catch (IOException e) {
				System.out.println("Couldn't save back-up:");
				e.printStackTrace();
			}
		}
		*/
	}
	
	static void setDialogue(DialogueBuilder dialogue, String name){
		DialogueDesigner.name = name;
		DialogueDesigner.dialogue = dialogue;
		viewX = 0;
		viewY = 0;
	}
	
	static void removePart(PartBuilder part){
		int index = part.index;
		for(PartBuilder pb : dialogue.parts){
			if(pb instanceof SimplePartBuilder){
				SimplePartBuilder spb = (SimplePartBuilder) pb;
				if(spb.nextIndex == index)
					spb.nextIndex = -1;
				else if(spb.nextIndex > index)
					spb.nextIndex--;
			}
			if(pb instanceof ChoisePartBuilder){
				ChoisePartBuilder cpb = (ChoisePartBuilder) pb;
				for(Choise choise : cpb.choises){
					if(choise.nextIndex == index)
						choise.nextIndex = -1;
					else if(choise.nextIndex > index)
						choise.nextIndex--;
				}
			}
		}
		dialogue.parts.remove(index);
		panel.repaint();
	}
	
	static void setStart(PartBuilder part){
		PartBuilder previous = dialogue.parts.get(0);
		dialogue.parts.set(part.index, previous);
		dialogue.parts.set(0, part);
		for(PartBuilder pb : dialogue.parts){
			if(pb instanceof SimplePartBuilder){
				SimplePartBuilder spb = (SimplePartBuilder) pb;
				if(spb.nextIndex == 0)
					spb.nextIndex = part.index;
				else if(spb.nextIndex == part.index)
					spb.nextIndex = 0;
			}
			if(pb instanceof ChoisePartBuilder){
				ChoisePartBuilder cpb = (ChoisePartBuilder) pb;
				for(Choise choise : cpb.choises){
					if(choise.nextIndex == 0)
						choise.nextIndex = part.index;
					else if(choise.nextIndex == part.index)
						choise.nextIndex = 0;
				}
			}
		}
		previous.index = part.index;
		part.index = 0;
		panel.repaint();//mark this part as the start part
	}
	
	static boolean selectingName(){
		return stringTyper == NAME_TYPER;
	}
	
	static void startSelectingName(){
		stringTyper = NAME_TYPER;
	}
	
	static boolean isValidMethodChar(char c){
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_';
	}
	
	static boolean isValidNameChar(char character){
		return (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') || character == ' ';
	}
	
	private static final char ANNOYING_CHAR = "'".charAt(0);
	
	static boolean isValidDialogueChar(char c){
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == ' ' || c == '.' || c == '!' 
				|| c == '?' || c == ',' || c == '(' || c == ')' || c == ANNOYING_CHAR || c == ':';
	}
	
	static class Frame extends JFrame implements KeyListener, MouseListener, MouseWheelListener {

		private static final long serialVersionUID = 6461070450475831536L;
		
		boolean fileToolbar;
		boolean newToolbar;
		
		//boolean selectingName;
		
		boolean loadingFile;
		int loadingScroll;
		String[] loadableFiles;
		
		byte clickAction = CLICK_ACTION_DEFAULT;
		
		Frame(){
			setSize(1600,900);
			setVisible(true);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			addKeyListener(this);
			addMouseListener(this);
			addMouseWheelListener(this);
			add(panel);
		}
		
		@Override
		public void dispose(){
			backUp();
			super.dispose();
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			int x = event.getX() - 8;
			int y = event.getY() - 30;
			if(fontSelector != null){
				fontSelector.click(x, y);
				return;
			}
			if(y < OFFSET_Y){
				if(x > X_FILE && x < X_FILE + WIDTH_FILE){
					fileToolbar = !fileToolbar;
					panel.repaint();
				}
				if(dialogue != null){
					if(x > X_LEAVE && x < X_LEAVE + WIDTH_LEAVE){
						dialogue.canLeave = !dialogue.canLeave;
						panel.repaint();
					}
					if(x > X_NEWPART && x < X_NEWPART + WIDTH_NEW_PART){
						newToolbar = !newToolbar;
						panel.repaint();
					}
				}
			}
			else {
				if(fileToolbar && x > X_FILE && x < X_FILE + WIDTH_FILE && y <= OFFSET_Y + HEIGHT_FILE){
					if(dialogue == null){
						if(y > Y_FILE_NEW && y < Y_FILE_NEW + OFFSET_Y)
							setDialogue(new DialogueBuilder(), "Nameless");
						if(y > Y_FILE_LOAD && y < Y_FILE_LOAD + OFFSET_Y){
							loadingFile = true;
							loadableFiles = new File("dialogues").list(new FilenameFilter(){

								@Override
								public boolean accept(File dir, String name) {
									return name.endsWith(".dalb") && !name.startsWith("back-up");
								}
							});
							for(int i = 0; i < loadableFiles.length; i++)
								loadableFiles[i] = loadableFiles[i].substring(0, loadableFiles[i].length() - 5);
						}
					}
					else {
						if(y > Y_FILE_SAVE && y < Y_FILE_SAVE + OFFSET_Y){
							if(name.equals("Nameless"))
								startSelectingName();
							else
								save(name);
						}
						if(y > Y_FILE_CLOSE && y < Y_FILE_CLOSE + OFFSET_Y){
							backUp();
							setDialogue(null, null);
						}
						if(y > Y_FILE_CLEAR && y < Y_FILE_CLEAR + OFFSET_Y){
							backUp();
							setDialogue(new DialogueBuilder(), name);
						}
						if (y > Y_FILE_EXPORT && y < Y_FILE_EXPORT + OFFSET_Y) {
							new File("dialogues").mkdirs();
							try {
								BitOutput output = new BitOutputStream(new FileOutputStream(new File("dialogues/" + name + ".dal")));
								output.addBoolean(dialogue.canLeave);
								output.addInt(dialogue.parts.size());
								dialogue.portraits.save(output);
								for (PartBuilder part : dialogue.parts) {
									part.save(output, dialogue.portraits);
								}
								output.terminate();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					}
					fileToolbar = false;
					panel.repaint();
				}
				else if(newToolbar && x >= X_NEWPART && x <= X_NEWPART + WIDTH_NEW_PART && y <= HEIGHT_NEW){
					newToolbar = false;
					if(y <= Y_NEWPART_SIMPLE + OFFSET_Y){
						clickAction = CLICK_ACTION_SIMPLE_PART;
					}
					else if(y <= Y_NEWPART_CHOISE + OFFSET_Y){
						clickAction = CLICK_ACTION_CHOISE_PART;
					}
					panel.repaint();
				}
				else if(loadingFile){
					if(x >= getWidth() / 2 - LOAD_BUTTON_WIDTH / 2 && x <= getWidth() / 2 + LOAD_BUTTON_WIDTH / 2){
						int relY = y - loadingScroll - getHeight() / 2 + LOAD_BUTTON_HEIGHT / 2;
						int index = (int) (relY / (1.2 * LOAD_BUTTON_HEIGHT));
						int buffer = (int) (relY - 1.2 * LOAD_BUTTON_HEIGHT * index);
						if(buffer >= 0 && buffer <= LOAD_BUTTON_HEIGHT && index < loadableFiles.length){
							load(loadableFiles[index]);
							loadingFile = false;
							loadingScroll = 0;
							loadableFiles = null;
							panel.repaint();
						}
					}
				}
				else {
					if(dialogue != null){
						if(clickAction == CLICK_ACTION_SIMPLE_PART || clickAction == CLICK_ACTION_CHOISE_PART){
							int minX = viewX + x;
							int minY = viewY + y;
							for(PartBuilder part : dialogue.parts){
								if(Math.abs(minX - part.designerX) < DIALOGUE_WIDTH && Math.abs(minY - part.designerY) < DIALOGUE_HEIGHT){
									System.out.println("Did not create part because of intersection");
									return;
								}
							}
							if(clickAction == CLICK_ACTION_SIMPLE_PART)
								dialogue.parts.add(new SimplePartBuilder(dialogue.parts.size(), minX, minY));
							else
								dialogue.parts.add(new ChoisePartBuilder(dialogue.parts.size(), minX, minY));
							clickAction = CLICK_ACTION_DEFAULT;
							panel.repaint();
						}
						else if(clickAction == CLICK_ACTION_DEFAULT){
							int ax = viewX + x;
							int ay = viewY + y;
							stringTyper = null;
							int indexToRemove = -1;
							int index = 0;
							for(PartBuilder part : dialogue.parts){
								if(ax >= part.designerX && ax <= part.designerX + DIALOGUE_WIDTH && ay >= part.designerY && ay <= part.designerY + DIALOGUE_HEIGHT){
									if(event.isAltDown() && event.getButton() == MouseEvent.BUTTON2){
										indexToRemove = index;
										break;
									}
									if(dialogueLinker != null){
										dialogueLinker.link(part);
										dialogueLinker = null;
										panel.repaint();
										return;
									}
									if(part instanceof SimplePartBuilder)
										stringTyper = new SimpleDialogueTextTyper((SimplePartBuilder) part);
									if(part instanceof ChoisePartBuilder)
										stringTyper = new ChoiseDialogueTextTyper((ChoisePartBuilder) part, 0);
								}
								index++;
							}
							if(indexToRemove != -1)
								dialogue.parts.remove(indexToRemove);
							panel.repaint();
						}
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void keyTyped(KeyEvent event) {
			if(stringTyper != null)
				stringTyper.type(event);
			if(fontSelector != null)
				fontSelector.type(event);
		}

		@Override
		public void keyPressed(KeyEvent event) {
			if(stringTyper != null)
				stringTyper.press(event);
			if(fontSelector != null)
				fontSelector.press(event);
			if(specialColorSelector != null)
				specialColorSelector.press(event);
			if(portraitSelector != null)
				portraitSelector.press(event);
			int key = event.getKeyCode();
			if(key == KeyEvent.VK_ESCAPE){
				boolean repaint = true;
				if(fileToolbar)
					fileToolbar = false;
				else if(newToolbar)
					newToolbar = false;
				else if(loadingFile)
					loadingFile = false;
				else if(clickAction != CLICK_ACTION_DEFAULT)
					clickAction = CLICK_ACTION_DEFAULT;
				else if(dialogueLinker != null)
					dialogueLinker = null;
				else
					repaint = false;
				if(repaint)
					panel.repaint();
			}
			if(stringTyper != null || fontSelector != null || specialColorSelector != null || portraitSelector != null)
				return;
			if(key == KeyEvent.VK_UP){
				viewY -= SPEED;
				panel.repaint();
			}
			if(key == KeyEvent.VK_DOWN){
				viewY += SPEED;
				panel.repaint();
			}
			if(key == KeyEvent.VK_LEFT){
				viewX -= SPEED;
				panel.repaint();
			}
			if(key == KeyEvent.VK_RIGHT){
				viewX += SPEED;
				panel.repaint();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void mouseWheelMoved(MouseWheelEvent event) {
			if(loadingFile){
				loadingScroll += event.getUnitsToScroll() * 5;
				panel.repaint();
			}
		}
	}
	
	static class Panel extends JPanel {
		
		static final Color BORDER = new Color(80, 80, 80);
		static final Color BACKGROUND = new Color(130, 130, 130);
		
		static final Font FONT = new Font("TimesRoman", Font.PLAIN, 40);

		private static final long serialVersionUID = -1435245964868753590L;
		
		@Override
		public void paint(Graphics g){
			g.setFont(FONT);
			g.setColor(BACKGROUND);
			g.fillRect(0, OFFSET_Y, frame.getWidth(), frame.getHeight() - OFFSET_Y);
			if(dialogue != null){
				for(PartBuilder part : dialogue.parts){
					if(part.designerX <= viewX + frame.getWidth() && part.designerX + DIALOGUE_WIDTH >= viewX && part.designerY <= viewY + frame.getHeight() && part.designerY + DIALOGUE_HEIGHT >= viewY){
						if(part instanceof SimplePartBuilder){
							SimplePartBuilder simple = (SimplePartBuilder) part;
							BufferedImage image = Resources.createDialogueImage(simple.background, simple.portrait, simple.title.toSDT(), new DialogueText[]{new SimpleDialogueText(simple.text.text, simple.text.color, simple.text.font)}, null);
							g.drawImage(image, simple.designerX - viewX, simple.designerY - 500 - viewY, null);
							if(simple.functionName != null){
								g.setColor(java.awt.Color.BLUE);
								g.drawString("Call function " + simple.functionName, simple.designerX + 50 - viewX, simple.designerY + 340 - viewY);
							}
							else {
								g.setColor(java.awt.Color.BLACK);
								g.drawString("Doesn't call a function", simple.designerX + 50 - viewX, simple.designerY + 340 - viewY);
							}
							if(stringTyper instanceof SimpleDialogueTyper && ((SimpleDialogueTyper)stringTyper).part == simple){
								g.setColor(Color.RED);
								g.drawRect(simple.designerX - viewX, simple.designerY - viewY, DIALOGUE_WIDTH, 300);
							}
							if(dialogueLinker instanceof SimpleDialogueLinker && ((SimpleDialogueLinker) dialogueLinker).part == part){
								g.setColor(Color.PINK);
								g.drawRect(simple.designerX - viewX, simple.designerY - viewY, DIALOGUE_WIDTH, 300);
							}
						}
						if(part instanceof ChoisePartBuilder){
							ChoisePartBuilder choise = (ChoisePartBuilder) part;
							int[] textHeights = new int[choise.choises.size()];
							BufferedImage image = Resources.createDialogueImage(choise.background, choise.portrait, choise.title.toSDT(), choise.toDialogueTexts(), textHeights);
							for(int i = 0; i < textHeights.length; i++)
								textHeights[i] -= 500;
							g.drawImage(image, choise.designerX - viewX, choise.designerY - 500 - viewY, null);
							int fx = choise.designerX - viewX + DIALOGUE_WIDTH + 20;
							int i = 0;
							for(Choise c : choise.choises){
								String fc = "";
								if(c.function != null)
									fc += "Call function " + c.function;
								else
									fc += "No function";
								fc += " and ";
								if(c.condition != null)
									fc += "has condition " + c.condition;
								else
									fc += "no condition";
								g.setColor(Color.BLACK);
								g.drawString(fc, fx, choise.designerY - viewY + textHeights[i]);
								i++;
							}
							if(stringTyper instanceof ChoiseDialogueTyper && ((ChoiseDialogueTyper)stringTyper).part == choise){
								g.setColor(Color.RED);
								g.drawRect(choise.designerX - viewX, choise.designerY - viewY, DIALOGUE_WIDTH, 300);
							}
							if(dialogueLinker instanceof ChoiseDialogueLinker && ((ChoiseDialogueLinker) dialogueLinker).part == choise){
								g.setColor(Color.PINK);
								g.drawRect(choise.designerX - viewX, choise.designerY - viewY, DIALOGUE_WIDTH, 300);
							}
						}
						if(part == dialogue.parts.get(0)){
							//start part
							g.setColor(Color.GREEN);
							g.drawRect(part.designerX - viewX - 1, part.designerY - viewY - 1, DIALOGUE_WIDTH + 2, 302);
						}
					}
				}
				for(PartBuilder part : dialogue.parts){
					if(part instanceof SimplePartBuilder){
						SimplePartBuilder simple = (SimplePartBuilder) part;
						if(simple.nextIndex >= 0){
							g.setColor(java.awt.Color.RED);
							PartBuilder next = dialogue.parts.get(simple.nextIndex);
							g.drawLine(simple.designerX - viewX + DIALOGUE_WIDTH / 2, simple.designerY - viewY + DIALOGUE_HEIGHT / 2, next.designerX - viewX, next.designerY - viewY);
						}
					}
					if(part instanceof ChoisePartBuilder){
						ChoisePartBuilder choise = (ChoisePartBuilder) part;
						int[] textHeights = null;
						int i = 0;
						for(Choise c : choise.choises){
							if(c.nextIndex >= 0){
								g.setColor(java.awt.Color.RED);
								PartBuilder next = dialogue.parts.get(c.nextIndex);
								if(textHeights == null){
									textHeights = new int[choise.choises.size()];
									Resources.getTextHeights(choise.toDialogueTexts(), textHeights);
									for(int j = 0; j < textHeights.length; j++)
										textHeights[j] -= 500;
								}
								g.drawLine(choise.designerX - viewX + DIALOGUE_WIDTH / 2, choise.designerY - viewY + textHeights[i] - 10, next.designerX - viewX, next.designerY - viewY);
							}
							i++;
						}
					}
				}
			}
			g.setColor(BORDER);
			g.fillRect(0, 0, frame.getWidth(), OFFSET_Y);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, frame.getWidth(), OFFSET_Y);
			g.drawLine(X_FILE, 0, X_FILE, OFFSET_Y);
			g.drawLine(X_FILE + WIDTH_FILE, 0, X_FILE + WIDTH_FILE, OFFSET_Y);
			g.drawString("File", X_FILE + 20, 40);
			if(dialogue != null){
				g.drawLine(X_LEAVE, 0, X_LEAVE, OFFSET_Y);
				g.drawLine(X_LEAVE + WIDTH_LEAVE, 0, X_LEAVE + WIDTH_LEAVE, OFFSET_Y);
				g.drawLine(X_NEWPART, 0, X_NEWPART, OFFSET_Y);
				g.drawLine(X_NEWPART + WIDTH_NEW_PART, 0, X_NEWPART + WIDTH_NEW_PART, OFFSET_Y);
				g.drawLine(X_NAME, 0, X_NAME, OFFSET_Y);
				g.drawLine(X_NAME + WIDTH_NAME, 0, X_NAME + WIDTH_NAME, OFFSET_Y);
				g.drawString("Can leave?", X_LEAVE + 20, 40);
				g.drawString(dialogue.canLeave ? "Yes" : "No", X_LEAVE + 220, 40);
				g.drawString("New", X_NEWPART + 10, 40);
				g.drawString(name, X_NAME, 40);
				if(frame.newToolbar){
					g.setColor(BORDER);
					g.fillRect(X_NEWPART, OFFSET_Y, WIDTH_NEW_PART, 100);
					g.setColor(Color.BLACK);
					g.drawRect(X_NEWPART, OFFSET_Y, WIDTH_NEW_PART, 100);
					g.drawLine(X_NEWPART, Y_NEWPART_SIMPLE, X_NEWPART + WIDTH_NEW_PART, Y_NEWPART_SIMPLE);
					g.drawLine(X_NEWPART, Y_NEWPART_CHOISE, X_NEWPART + WIDTH_NEW_PART, Y_NEWPART_CHOISE);
					g.drawString("Simple part", X_NEWPART + 10, Y_NEWPART_SIMPLE + 40);
					g.drawString("Choise part", X_NEWPART + 10, Y_NEWPART_CHOISE + 40);
				}
			}
			if(frame.fileToolbar){
				g.setColor(BORDER);
				g.fillRect(X_FILE, OFFSET_Y, WIDTH_FILE, HEIGHT_FILE);
				g.setColor(Color.BLACK);
				g.drawRect(X_FILE, OFFSET_Y, WIDTH_FILE, HEIGHT_FILE);
				g.drawLine(X_FILE, Y_FILE_LOAD, X_FILE + WIDTH_FILE, Y_FILE_LOAD);
				g.drawLine(X_FILE, Y_FILE_SAVE, X_FILE + WIDTH_FILE, Y_FILE_SAVE);
				g.drawLine(X_FILE, Y_FILE_CLOSE, X_FILE + WIDTH_FILE, Y_FILE_CLOSE);
				g.drawLine(X_FILE, OFFSET_Y + 200, X_FILE + WIDTH_FILE, OFFSET_Y + 200);
				g.drawLine(X_FILE, OFFSET_Y + 250, X_FILE + WIDTH_FILE, OFFSET_Y + 250);
				if(dialogue == null){
					g.drawString("New", X_FILE + 15, Y_FILE_NEW + 40);
					if(new File("dialogues").list().length > 0)
						g.drawString("Load", X_FILE + 10, Y_FILE_LOAD + 40);
				}
				else {
					g.drawString("Save", X_FILE + 10, Y_FILE_SAVE + 40);
					g.drawString("Close", X_FILE + 5, Y_FILE_CLOSE + 40);
					g.setColor(Color.RED);
					g.drawString("Clear", X_FILE + 5, Y_FILE_CLEAR + 40);
					g.setColor(Color.BLACK);
					g.drawString("Export", X_FILE, Y_FILE_EXPORT + 40);
				}
			}
			if(selectingName()){
				g.setColor(SAVE_BORDER_COLOR);
				int mw = frame.getWidth() / 2;
				int mh = frame.getHeight() / 2;
				g.fillRect(mw - SAVE_BORDER_OFFSET_X - SAVE_BORDER_WIDTH, mh - SAVE_BORDER_OFFSET_Y - SAVE_BORDER_HEIGHT, SAVE_BORDER_WIDTH, 2 * (SAVE_BORDER_OFFSET_Y + SAVE_BORDER_HEIGHT));
				g.fillRect(mw + SAVE_BORDER_OFFSET_X, mh - SAVE_BORDER_OFFSET_Y - SAVE_BORDER_HEIGHT, SAVE_BORDER_WIDTH, 2 * (SAVE_BORDER_OFFSET_Y + SAVE_BORDER_HEIGHT));
				g.fillRect(mw - SAVE_BORDER_OFFSET_X - SAVE_BORDER_WIDTH, mh - SAVE_BORDER_OFFSET_Y - SAVE_BORDER_HEIGHT, 2 * (SAVE_BORDER_OFFSET_X + SAVE_BORDER_WIDTH), SAVE_BORDER_HEIGHT);
				g.fillRect(mw - SAVE_BORDER_OFFSET_X - SAVE_BORDER_WIDTH, mh + SAVE_BORDER_OFFSET_Y, 2 * (SAVE_BORDER_OFFSET_X + SAVE_BORDER_WIDTH), SAVE_BORDER_HEIGHT);
				g.setColor(SAVE_COLOR);
				g.fillRect(mw - SAVE_BORDER_OFFSET_X, mh - SAVE_BORDER_OFFSET_Y, 2 * SAVE_BORDER_OFFSET_X, 2 * SAVE_BORDER_OFFSET_Y);
				g.setFont(FONT);
				g.setColor(SAVE_NAME_COLOR);
				int nameWidth = (int) g.getFontMetrics().getStringBounds(name, g).getWidth();
				g.drawString(name, mw - nameWidth / 2, (int) (mh + 0.7 * SAVE_BORDER_OFFSET_Y));
			}
			if(frame.loadingFile){
				g.setColor(LOAD_BORDER_COLOR);
				int mw = frame.getWidth() / 2;
				int mh = frame.getHeight() / 2;
				g.fillRect(mw - LOAD_BORDER_OFFSET_X - LOAD_BORDER_WIDTH, mh - LOAD_BORDER_OFFSET_Y - LOAD_BORDER_HEIGHT, LOAD_BORDER_WIDTH, 2 * (LOAD_BORDER_OFFSET_Y + LOAD_BORDER_HEIGHT));
				g.fillRect(mw + LOAD_BORDER_OFFSET_X, mh - LOAD_BORDER_OFFSET_Y - LOAD_BORDER_HEIGHT, LOAD_BORDER_WIDTH, 2 * (LOAD_BORDER_OFFSET_Y + LOAD_BORDER_HEIGHT));
				g.fillRect(mw - LOAD_BORDER_OFFSET_X - LOAD_BORDER_WIDTH, mh - LOAD_BORDER_OFFSET_Y - LOAD_BORDER_HEIGHT, 2 * (LOAD_BORDER_OFFSET_X + LOAD_BORDER_WIDTH), LOAD_BORDER_HEIGHT);
				g.fillRect(mw - LOAD_BORDER_OFFSET_X - LOAD_BORDER_WIDTH, mh + LOAD_BORDER_OFFSET_Y, 2 * (LOAD_BORDER_OFFSET_X + LOAD_BORDER_WIDTH), LOAD_BORDER_HEIGHT);
				g.setColor(LOAD_COLOR);
				g.fillRect(mw - LOAD_BORDER_OFFSET_X, mh - LOAD_BORDER_OFFSET_Y, 2 * LOAD_BORDER_OFFSET_X, 2 * LOAD_BORDER_OFFSET_Y);
				g.setFont(FONT);
				for(int index = 0; index < frame.loadableFiles.length; index++){
					int y = (int) (mh + index * 1.2 * LOAD_BUTTON_HEIGHT + frame.loadingScroll);
					g.setColor(LOAD_BUTTON_COLOR);
					g.fillRect(mw - LOAD_BUTTON_WIDTH / 2, -LOAD_BUTTON_HEIGHT / 2 + y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT);
					g.setColor(Color.BLACK);
					g.drawRect(mw - LOAD_BUTTON_WIDTH / 2, -LOAD_BUTTON_HEIGHT / 2 + y, LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT);
					g.drawString(frame.loadableFiles[index], (int) (mw - LOAD_BUTTON_WIDTH * 0.45), (int) (LOAD_BUTTON_HEIGHT * 0.35 + y));
				}
			}
			if(fontSelector != null)
				fontSelector.paint(g);
			if(specialColorSelector != null)
				specialColorSelector.paint(g);
			if(portraitSelector != null)
				portraitSelector.paint(g);
		}
	}
	
	static class PortraitSelector {
		
		private static final Entry[] PORTRAITS;
		
		static {
			Field[] fields = Portraits.class.getFields();
			PORTRAITS = new Entry[fields.length];
			try {
				for(int index = 0; index < fields.length; index++)
					PORTRAITS[index] = new Entry(fields[index].getName(), (ImageTexture) fields[index].get(null));
			} catch(IllegalAccessException ex){
				throw new Error(ex);//Shouldn't happen because class.getFields() only returns public fields and Portraits.class only contains constants
			}
		}
		
		private int selectedIndex;
		private PartBuilder part;
		
		PortraitSelector(PartBuilder part){
			this.part = part;
			for(int index = 0; index < PORTRAITS.length; index++)
				if(PORTRAITS[index].portrait == part.portrait)
					selectedIndex = index;
		}
		
		public void paint(Graphics g){
			g.setColor(Color.GRAY);
			g.fillRect(200, 200, 600, 400);
			g.setColor(Color.BLACK);
			g.drawRect(200, 200, 600, 400);
			g.setFont(FontSelector.FONT);
			int y = 250;
			for(int index = selectedIndex - 2; index <= selectedIndex + 2; index++){
				if(index == selectedIndex)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.BLACK);
				if(index >= 0 && index < PORTRAITS.length)
					g.drawString(PORTRAITS[index].name, 220, y);
				y += 50;
			}
		}
		
		public void press(KeyEvent event){
			if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
				portraitSelector = null;
				panel.repaint();
			}
			if(event.getKeyCode() == KeyEvent.VK_ENTER){
				if(!dialogue.portraits.has(PORTRAITS[selectedIndex].name))
					dialogue.portraits.add(PORTRAITS[selectedIndex].name);
				part.portrait = PORTRAITS[selectedIndex].portrait;
				portraitSelector = null;
				panel.repaint();
			}
			if(event.getKeyCode() == KeyEvent.VK_UP && selectedIndex > 0)
				selectedIndex--;
			panel.repaint();
			if(event.getKeyCode() == KeyEvent.VK_DOWN && selectedIndex < PORTRAITS.length - 1){
				selectedIndex++;
				panel.repaint();
			}
		}
		
		static class Entry {
			
			final String name;
			final ImageTexture portrait;
			
			Entry(String name, ImageTexture portrait){
				this.name = name;
				this.portrait = portrait;
			}
		}
	}
	
	static abstract class DialogueLinker {
		
		abstract void link(PartBuilder target);
	}
	
	static class SimpleDialogueLinker extends DialogueLinker {
		
		private final SimplePartBuilder part;
		
		SimpleDialogueLinker(SimplePartBuilder part){
			this.part = part;
		}

		@Override
		void link(PartBuilder target) {
			if(target != part)
				part.nextIndex = target.index;
		}
	}
	
	static class ChoiseDialogueLinker extends DialogueLinker {
		
		private final Choise choise;
		private final ChoisePartBuilder part;
		
		ChoiseDialogueLinker(ChoisePartBuilder part, Choise choise){
			this.choise = choise;
			this.part = part;
		}

		@Override
		void link(PartBuilder target) {
			if(target != part)
				choise.nextIndex = target.index;
		}
	}
	
	static class SpecialColorSelector {
		
		private final FontSelector fontSelector;
		
		private int index;
		
		SpecialColorSelector(FontSelector fontSelector){
			this.fontSelector = fontSelector;
		}
		
		public void paint(Graphics g){
			g.setColor(fontSelector.background);
			g.fillRect(300, 200, 650, 280);
			g.setColor(Color.BLACK);
			g.drawRect(300, 200, 650, 280);
			String previous;
			if(index == 0)
				previous = null;
			else
				previous = ColorSpecial.get(index - 1).toString().substring(5);
			String current = ColorSpecial.get(index).toString().substring(5);
			String next;
			if(index + 1 < ColorSpecial.amount())
				next = ColorSpecial.get(index + 1).toString().substring(5);
			else
				next = null;
			g.drawRect(400, 295, 450, 90);
			g.setFont(FontSelector.FONT);
			
			if(previous != null)
				g.drawString(previous, 420, 260);
			g.setColor(Color.YELLOW);
			g.drawString(current, 420, 350);
			g.setColor(Color.BLACK);
			if(next != null)
				g.drawString(next, 420, 440);
		}
		
		public void press(KeyEvent event){
			if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
				DialogueDesigner.fontSelector = this.fontSelector;
				specialColorSelector = null;
				panel.repaint();
			}
			if(event.getKeyCode() == KeyEvent.VK_ENTER){
				fontSelector.set(new Font(fontSelector.fontName, fontSelector.style, fontSelector.size), ColorSpecial.get(index));
				specialColorSelector = null;
				panel.repaint();
			}
			if(event.getKeyCode() == KeyEvent.VK_UP && index > 0){
				index--;
				panel.repaint();
			}
			if(event.getKeyCode() == KeyEvent.VK_DOWN && index < ColorSpecial.amount() - 1){
				index++;
				panel.repaint();
			}
		}
	}
	
	static abstract class FontSelector {
		
		static final Font FONT = new Font("TimesRoman", 0, 30);
		
		static final byte STATE_NOTHING = 0;
		static final byte STATE_NAME = 1;
		static final byte STATE_STYLE = 2;
		static final byte STATE_SIZE = 3;
		static final byte STATE_RED = 4;
		static final byte STATE_GREEN = 5;
		static final byte STATE_BLUE = 6;
		static final byte STATE_ALPHA = 7;
		
		final Color background;
		
		String fontName;
		
		int style;
		int size;
		
		int red,green,blue,alpha;
		
		byte state = STATE_NOTHING;
		
		FontSelector(Color background, String fontName, int style, int size, int red, int green, int blue, int alpha){
			this.background = background;
			this.fontName = fontName;
			this.style = style;
			this.size = size;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.alpha = alpha;
		}
		
		FontSelector(Color background, Font current, nl.knokko.util.color.Color color){
			this(background, current.getName(), current.getStyle(), current.getSize(), color.getRedI(), color.getGreenI(), color.getBlueI(), color.getAlphaI());
		}
		
		FontSelector(Color background, Font current, int red, int green, int blue, int alpha){
			this(background, current.getName(), current.getStyle(), current.getSize(), red, green, blue, alpha);
		}
		
		FontSelector(Color background, String fontName, int style, int size, nl.knokko.util.color.Color color){
			this(background, fontName, style, size, color.getRedI(), color.getGreenI(), color.getBlueI(), color.getAlphaI());
		}
		
		void paint(Graphics g){
			g.setColor(background);
			g.fillRect(300, 200, 650, 280);
			g.setColor(Color.BLACK);
			g.drawRect(300, 200, 650, 280);
			g.setColor(new Color(red, green, blue, alpha));
			g.setFont(new Font(fontName, style, size));
			g.drawString("Font Name:" + fontName, 400, 250);
			c(g, STATE_NAME);
			g.drawRect(390, 210, 500, 50);
			g.setColor(Color.BLACK);
			g.setFont(FONT);
			c(g, STATE_STYLE);
			g.drawString("Style: " + style, 400, 350);
			g.drawRect(390, 310, 250, 50);
			c(g, STATE_SIZE);
			g.drawString("Size: " + size, 400, 400);
			g.drawRect(390, 360, 250, 50);
			c(g, STATE_RED);
			g.drawString("Red: " + red, 400, 450);
			g.drawRect(390, 410, 250, 50);
			c(g, STATE_GREEN);
			g.drawString("Green: " + green, 700, 350);
			g.drawRect(690, 310, 250, 50);
			c(g, STATE_BLUE);
			g.drawString("Blue: " + blue, 700, 400);
			g.drawRect(690, 360, 250, 50);
			c(g, STATE_ALPHA);
			g.drawString("Alpha: " + alpha, 700, 450);
			g.drawRect(690, 410, 250, 50);
		}
		
		void c(Graphics g, byte state){
			g.setColor(state == this.state ? Color.YELLOW : Color.BLACK);
		}
		
		void click(int x, int y){
			if(x >= 390 && x <= 890 && y >= 210 && y <= 260)
				setState(STATE_NAME);
			else if(x >= 390 && x <= 640){
				if(y >= 310 && y <= 360)
					setState(STATE_STYLE);
				else if(y >= 360 && y <= 410)
					setState(STATE_SIZE);
				else if(y >= 410 && y <= 460)
					setState(STATE_RED);
			}
			else if(x >= 690 && x <= 940){
				if(y >= 310 && y <= 360)
					setState(STATE_GREEN);
				else if(y >= 360 && y <= 410)
					setState(STATE_BLUE);
				else if(y >= 410 && y <= 460)
					setState(STATE_ALPHA);
			}
		}
		
		void press(KeyEvent event){
			if(event.isAltDown() && event.getKeyCode() == KeyEvent.VK_S){
				specialColorSelector = new SpecialColorSelector(this);
				panel.repaint();
			}
			if(state == STATE_NOTHING && (event.getKeyCode() == KeyEvent.VK_ENTER || event.getKeyCode() == KeyEvent.VK_ESCAPE)){
				set(new Font(fontName, style, size), new nl.knokko.util.color.ColorAlpha(red, green, blue, alpha));
				fontSelector = null;
				panel.repaint();
			}
		}
		
		void type(KeyEvent event){
			
		}
		
		void setState(byte state){
			this.state = state;
			if(state == STATE_NOTHING)
				stringTyper = null;
			if(state == STATE_NAME)
				stringTyper = new FontNameTyper();
			if(state == STATE_STYLE)
				stringTyper = new StyleTyper();
			if(state == STATE_SIZE)
				stringTyper = new SizeTyper();
			if(state == STATE_RED)
				stringTyper = new RedTyper();
			if(state == STATE_GREEN)
				stringTyper = new GreenTyper();
			if(state == STATE_BLUE)
				stringTyper = new BlueTyper();
			if(state == STATE_ALPHA)
				stringTyper = new AlphaTyper();
			panel.repaint();
		}
		
		abstract void set(Font font, nl.knokko.util.color.Color color);
		
		class FontNameTyper extends StringTyper {

			FontNameTyper() {
				super(fontName);
			}

			@Override
			void set(String value) {
				fontName = value;
			}

			@Override
			String get() {
				return fontName;
			}
			
			@Override
			void done(){
				state = STATE_NOTHING;
				super.done();
			}
		}
		
		abstract class FontIntTyper extends IntTyper {

			FontIntTyper(int oldValue, int minValue, int maxValue) {
				super(oldValue, minValue, maxValue);
			}
			
			@Override
			void done(){
				state = STATE_NOTHING;
				super.done();
			}
		}
		
		class StyleTyper extends FontIntTyper {

			StyleTyper() {
				super(style, 0, 3);
			}

			@Override
			void set(int value) {
				style = value;
			}

			@Override
			int value() {
				return style;
			}
		}
		
		class SizeTyper extends FontIntTyper {

			SizeTyper() {
				super(size, 1, 100);
			}

			@Override
			void set(int value) {
				size = value;
			}

			@Override
			int value() {
				return size;
			}
		}
		
		class RedTyper extends FontIntTyper {

			RedTyper() {
				super(red, 0, 255);
			}

			@Override
			void set(int value) {
				red = value;
			}

			@Override
			int value() {
				return red;
			}
		}
		
		class GreenTyper extends FontIntTyper {

			GreenTyper() {
				super(green, 0, 255);
			}

			@Override
			void set(int value) {
				green = value;
			}

			@Override
			int value() {
				return green;
			}
		}
		
		class BlueTyper extends FontIntTyper {

			BlueTyper() {
				super(blue, 0, 255);
			}

			@Override
			void set(int value) {
				blue = value;
			}

			@Override
			int value() {
				return blue;
			}
		}
		
		class AlphaTyper extends FontIntTyper {
			
			AlphaTyper(){
				super(alpha, 1, 255);
			}

			@Override
			void set(int value) {
				alpha = value;
			}

			@Override
			int value() {
				return alpha;
			}
		}
	}
	
	static abstract class StringTyper {
		
		private final String oldValue;
		
		StringTyper(String oldValue){
			this.oldValue = oldValue;
		}
		
		void type(KeyEvent event){
			char character = event.getKeyChar();
			if(isAllowed(character)){
				String result = get() + character;
				if(isValid(result)){
					set(get() + character);
					panel.repaint();
				}
			}
		}
		
		void press(KeyEvent event){
			int key = event.getKeyCode();
			boolean repaint = true;
			String get = get();
			if(get == null)
				get = "null";
			if(key == KeyEvent.VK_ESCAPE)
				reset();
			else if(key == KeyEvent.VK_ENTER && isValid(get))
				done();
			else if(key == KeyEvent.VK_BACK_SPACE && get.length() > 0)
				set(get.substring(0, get.length() - 1));
			else if(key == KeyEvent.VK_DELETE && get.length() > 0)
				set(get.substring(1));
			else
				repaint = false;
			if(repaint)
				panel.repaint();
		}
		
		void reset(){
			set(oldValue);
			done();
		}
		
		abstract void set(String value);
		
		abstract String get();
		
		void done(){
			stringTyper = null;
			String get = get();
			if(get.isEmpty() || get.equals("null"))
				set(null);
			panel.repaint();
		}
		
		boolean isAllowed(char c){
			return isValidDialogueChar(c);
		}
		
		boolean isValid(String value){
			for(int i = 0; i < value.length(); i++)
				if(!isAllowed(value.charAt(i)))
					return false;
			return true;
		}
	}
	
	static abstract class IntTyper extends StringTyper {
		
		final int minValue;
		final int maxValue;

		IntTyper(int oldValue, int minValue, int maxValue) {
			super(oldValue + "");
			this.minValue = minValue;
			this.maxValue = maxValue;
		}
		
		@Override
		boolean isValid(String value){
			if(value.isEmpty() && minValue <= 0 && maxValue >= 0)
				return true;
			try {
				int number = Integer.parseInt(value);
				return number >= minValue && number <= maxValue;
			} catch(NumberFormatException nfe){
				return false;
			}
		}
		
		@Override
		boolean isAllowed(char character){
			return character >= '0' && character <= '9';
		}
		
		@Override
		void set(String value){
			set(!value.isEmpty() ? Integer.parseInt(value) : 0);
		}
		
		@Override
		String get(){
			return value() + "";
		}
		
		abstract void set(int value);
		
		abstract int value();
	}
	
	static abstract class SimpleDialogueTyper extends StringTyper {
		
		final SimplePartBuilder part;

		SimpleDialogueTyper(SimplePartBuilder part, String old) {
			super(old);
			this.part = part;
		}
		
		@Override
		void press(KeyEvent event){
			if(event.getKeyCode() == KeyEvent.VK_LEFT)
				stringTyper = left();
			else if(event.getKeyCode() == KeyEvent.VK_RIGHT)
				stringTyper = right();
			else if(event.isAltDown() && event.getKeyCode() == KeyEvent.VK_F){
				FontSelector fs = font();
				if(fs != null){
					stringTyper = null;
					fontSelector = fs;
					panel.repaint();
				}
			}
			else if(event.isAltDown() && event.getKeyCode() == KeyEvent.VK_L){
				stringTyper = null;
				dialogueLinker = new SimpleDialogueLinker(part);
				panel.repaint();
			}
			else if(event.isAltDown() && event.getKeyCode() == KeyEvent.VK_P){
				stringTyper = null;
				portraitSelector = new PortraitSelector(part);
				panel.repaint();
			}
			else if(event.isAltDown() && event.getKeyCode() == KeyEvent.VK_S){
				setStart(part);
			}
			else if(event.getKeyCode() == KeyEvent.VK_DELETE){
				removePart(part);
				stringTyper = null;
			}
			else {
				super.press(event);
			}
		}
		
		abstract SimpleDialogueTyper left();
		
		abstract SimpleDialogueTyper right();
		
		abstract FontSelector font();
	}
	
	static class SimpleDialogueTextTyper extends SimpleDialogueTyper {

		SimpleDialogueTextTyper(SimplePartBuilder part) {
			super(part, part.text.text);
		}

		@Override
		void set(String value) {
			part.text.text = value;
		}

		@Override
		String get() {
			return part.text.text;
		}

		@Override
		SimpleDialogueTyper left() {
			return new SimpleDialogueFunctionTyper(part);
		}

		@Override
		SimpleDialogueTyper right() {
			return new SimpleDialogueTitleTyper(part);
		}

		@Override
		FontSelector font() {
			return new TextFontSelector(Panel.BACKGROUND, part.text);
		}
	}
	
	static class SimpleDialogueTitleTyper extends SimpleDialogueTyper {

		SimpleDialogueTitleTyper(SimplePartBuilder part) {
			super(part, part.title.text);
		}

		@Override
		SimpleDialogueTyper left() {
			return new SimpleDialogueTextTyper(part);
		}

		@Override
		SimpleDialogueTyper right() {
			return new SimpleDialogueFunctionTyper(part);
		}

		@Override
		FontSelector font() {
			return new TextFontSelector(Panel.BACKGROUND, part.title);
		}

		@Override
		void set(String value) {
			part.title.text = value;
		}

		@Override
		String get() {
			return part.title.text;
		}
	}
	
	static class SimpleDialogueFunctionTyper extends SimpleDialogueTyper {

		SimpleDialogueFunctionTyper(SimplePartBuilder part) {
			super(part, part.functionName);
		}

		@Override
		void set(String value) {
			part.functionName = value;
		}

		@Override
		String get() {
			return part.functionName;
		}
		
		@Override
		boolean isAllowed(char character){
			return isValidMethodChar(character);
		}

		@Override
		SimpleDialogueTyper left() {
			return new SimpleDialogueTitleTyper(part);
		}

		@Override
		SimpleDialogueTyper right() {
			return new SimpleDialogueTextTyper(part);
		}

		@Override
		FontSelector font() {
			return null;
		}
	}
	
	static abstract class ChoiseDialogueTyper extends StringTyper {
		
		final ChoisePartBuilder part;
		int index;
		
		ChoiseDialogueTyper(String old, ChoisePartBuilder part, int index){
			super(old);
			this.part = part;
			this.index = index;
		}
		
		@Override
		void press(KeyEvent event){
			if(event.getKeyCode() == KeyEvent.VK_UP){
				if(index > 0)
					stringTyper = constructor(part, index - 1);
			}
			else if(event.getKeyCode() == KeyEvent.VK_DOWN){
				if(index + 1 >= part.choises.size()){
					part.choises.add(new Choise(new SimplePartBuilder.Text("Enter new text...", ChoiseDialogueText.DEFAULT_COLOR, ChoiseDialogueText.DEFAULT_FONT), -1, null, null));
					panel.repaint();
				}
				stringTyper = constructor(part, index + 1);
			}
			else if(event.getKeyCode() == KeyEvent.VK_LEFT)
				stringTyper = left();
			else if(event.getKeyCode() == KeyEvent.VK_RIGHT)
				stringTyper = right();
			else if(event.isAltDown() && event.getKeyCode() == KeyEvent.VK_F){
				FontSelector fs = font();
				if(fs != null){
					stringTyper = null;
					fontSelector = fs;
					panel.repaint();
				}
			}
			else if(event.isAltDown() && event.getKeyCode() == KeyEvent.VK_L){
				stringTyper = null;
				dialogueLinker = new ChoiseDialogueLinker(part, part.choises.get(index));
				panel.repaint();
			}
			else if(event.isAltDown() && event.getKeyCode() == KeyEvent.VK_P){
				stringTyper = null;
				portraitSelector = new PortraitSelector(part);
				panel.repaint();
			}
			else if(event.getKeyCode() == KeyEvent.VK_DELETE){
				stringTyper = null;
				if(part.choises.size() > 1)
					part.choises.remove(index);
				else
					removePart(part);
				panel.repaint();
			}
			else {
				super.press(event);
			}
		}
		
		abstract ChoiseDialogueTyper left();
		
		abstract ChoiseDialogueTyper right();
		
		abstract FontSelector font();
		
		abstract StringTyper constructor(ChoisePartBuilder part, int index);
	}
	
	static class ChoiseDialogueTextTyper extends ChoiseDialogueTyper {

		ChoiseDialogueTextTyper(ChoisePartBuilder part, int index) {
			super(part.choises.get(index).text.text, part, index);
		}

		@Override
		void set(String value) {
			part.choises.get(index).text.text = value;
		}

		@Override
		String get() {
			return part.choises.get(index).text.text;
		}

		@Override
		ChoiseDialogueTyper left() {
			return new ChoiseDialogueConditionTyper(part, index);
		}

		@Override
		ChoiseDialogueTyper right() {
			return new ChoiseDialogueTitleTyper(part, index);
		}

		@Override
		FontSelector font() {
			return new ChoiseDialogueTextFont(part, index);
		}

		@Override
		StringTyper constructor(ChoisePartBuilder part, int index) {
			return new ChoiseDialogueTextTyper(part, index);
		}
	}
	
	static class ChoiseDialogueTitleTyper extends ChoiseDialogueTyper {

		ChoiseDialogueTitleTyper(ChoisePartBuilder part, int index) {
			super(part.title.text, part, index);
		}

		@Override
		ChoiseDialogueTyper left() {
			return new ChoiseDialogueTextTyper(part, index);
		}

		@Override
		ChoiseDialogueTyper right() {
			return new ChoiseDialogueFunctionTyper(part, index);
		}

		@Override
		void set(String value) {
			part.title.text = value;
		}

		@Override
		String get() {
			return part.title.text;
		}

		@Override
		FontSelector font() {
			return new ChoiseDialogueTitleFont(part);
		}

		@Override
		StringTyper constructor(ChoisePartBuilder part, int index) {
			return new ChoiseDialogueTitleTyper(part, index);
		}
	}
	
	static class ChoiseDialogueFunctionTyper extends ChoiseDialogueTyper {

		ChoiseDialogueFunctionTyper(ChoisePartBuilder part, int index) {
			super(part.choises.get(index).function, part, index);
		}

		@Override
		void set(String value) {
			part.choises.get(index).function = value;
		}

		@Override
		String get() {
			return part.choises.get(index).function;
		}
		
		@Override
		public boolean isAllowed(char character){
			return isValidMethodChar(character);
		}

		@Override
		ChoiseDialogueTyper left() {
			return new ChoiseDialogueTitleTyper(part, index);
		}

		@Override
		ChoiseDialogueTyper right() {
			return new ChoiseDialogueConditionTyper(part, index);
		}

		@Override
		FontSelector font() {
			return null;
		}

		@Override
		StringTyper constructor(ChoisePartBuilder part, int index) {
			return new ChoiseDialogueFunctionTyper(part, index);
		}
	}
	
	static class ChoiseDialogueConditionTyper extends ChoiseDialogueTyper {

		ChoiseDialogueConditionTyper(ChoisePartBuilder part, int index) {
			super(part.choises.get(index).condition, part, index);
		}

		@Override
		ChoiseDialogueTyper left() {
			return new ChoiseDialogueFunctionTyper(part, index);
		}

		@Override
		ChoiseDialogueTyper right() {
			return new ChoiseDialogueTextTyper(part, index);
		}

		@Override
		void set(String value) {
			part.choises.get(index).condition = value;
		}

		@Override
		String get() {
			return part.choises.get(index).condition;
		}

		@Override
		FontSelector font() {
			return null;
		}

		@Override
		StringTyper constructor(ChoisePartBuilder part, int index) {
			return new ChoiseDialogueConditionTyper(part, index);
		}
	}
	
	static class TextFontSelector extends FontSelector {
		
		final SimplePartBuilder.Text text;

		TextFontSelector(Color background, SimplePartBuilder.Text text) {
			super(background, text.font, text.color);
			this.text = text;
		}

		@Override
		void set(Font font, nl.knokko.util.color.Color color) {
			text.color = color;
			text.font = font;
		}
	}
	
	static class ChoiseDialogueTitleFont extends TextFontSelector {

		ChoiseDialogueTitleFont(ChoisePartBuilder part) {
			super(Panel.BACKGROUND, part.title);
		}
	}
	
	static class ChoiseDialogueTextFont extends TextFontSelector {

		ChoiseDialogueTextFont(ChoisePartBuilder part, int index) {
			super(Panel.BACKGROUND, part.choises.get(index).text);
		}
	}
	
	static final int X_FILE = 10;
	static final int WIDTH_FILE = 100;
	static final int X_LEAVE = 500;
	static final int WIDTH_LEAVE = 300;
	static final int X_NAME = 900;
	static final int WIDTH_NAME = 300;
	static final int X_NEWPART = 150;
	static final int WIDTH_NEW_PART = 200;
	
	static final int OFFSET_Y = 50;
	
	static final int Y_FILE_NEW = OFFSET_Y;
	static final int Y_FILE_LOAD = Y_FILE_NEW + OFFSET_Y;
	static final int Y_FILE_SAVE = Y_FILE_LOAD + OFFSET_Y;
	static final int Y_FILE_CLOSE = Y_FILE_SAVE + OFFSET_Y;
	static final int Y_FILE_CLEAR = Y_FILE_CLOSE + OFFSET_Y;
	static final int Y_FILE_EXPORT = Y_FILE_CLEAR + OFFSET_Y;
	static final int HEIGHT_FILE = 300;
	
	static final int Y_NEWPART_SIMPLE = OFFSET_Y;
	static final int Y_NEWPART_CHOISE = Y_NEWPART_SIMPLE + OFFSET_Y;
	static final int HEIGHT_NEW = Y_NEWPART_CHOISE + OFFSET_Y;
	
	static final Color SAVE_BORDER_COLOR = new Color(40, 40, 40);
	static final Color SAVE_COLOR = new Color(150, 150, 150);
	static final Color SAVE_NAME_COLOR = new Color(0, 0, 0);
	static final int SAVE_BORDER_WIDTH = 50;
	static final int SAVE_BORDER_OFFSET_X = 300;
	static final int SAVE_BORDER_HEIGHT = 50;
	static final int SAVE_BORDER_OFFSET_Y = 100;
	
	static final Color LOAD_BORDER_COLOR = new Color(40, 40, 40);
	static final Color LOAD_COLOR = new Color(150, 150, 150);
	static final Color LOAD_BUTTON_COLOR = new Color(100, 100, 100);
	static final int LOAD_BORDER_OFFSET_X = 300;
	static final int LOAD_BORDER_OFFSET_Y = 400;
	static final int LOAD_BORDER_WIDTH = 50;
	static final int LOAD_BORDER_HEIGHT = 30;
	static final int LOAD_BUTTON_WIDTH = LOAD_BORDER_OFFSET_X - 50;
	static final int LOAD_BUTTON_HEIGHT = 70;
	
	static final byte CLICK_ACTION_DEFAULT = 0;
	static final byte CLICK_ACTION_SIMPLE_PART = 1;
	static final byte CLICK_ACTION_CHOISE_PART = 2;
	
	static final byte PART_ACTION_UNDEFINED = -1;
	static final byte PART_ACTION_EDIT_TEXT = 0;
	static final byte PART_ACTION_EDIT_BACKGROUND = 1;
	static final byte PART_ACTION_EDIT_PORTRAIT = 2;
	static final byte PART_ACTION_SET_NEXT = 3;
	static final byte PART_ACTION_EDIT_TITLE = 4;
	
	static final int DIALOGUE_WIDTH = 800;
	static final int DIALOGUE_HEIGHT = 300;
	
	static final int SPEED = 30;
	
	static final Color TYPE_MARK_COLOR = Color.YELLOW;
}
