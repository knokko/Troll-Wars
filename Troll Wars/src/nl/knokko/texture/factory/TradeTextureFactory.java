/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
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
package nl.knokko.texture.factory;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import nl.knokko.inventory.trading.ItemStack;
import nl.knokko.inventory.trading.TradeOffer;
import nl.knokko.texture.SizedTexture;

public class TradeTextureFactory {
	
	private static final java.awt.Color TRADE_OFFER_COLOR = new java.awt.Color(200, 150, 150);
	private static final Font TRADE_OFFER_FONT = new Font("TimesRoman", Font.ITALIC, 40);

	private static final BufferedImage TRADE_ARROW = createTradeArrow();
	
	private static BufferedImage createTradeArrow(){
		BufferedImage image = new BufferedImage(100, 64, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(java.awt.Color.YELLOW);
		int w = 67;
		g.fillRect(0, 20, w, 26);
		for(int i = 0; i < image.getHeight(); i++){
			g.drawLine(w, i, w + image.getHeight() / 2 - Math.abs(image.getHeight() / 2 - i), i);
		}
		g.dispose();
		return image;
	}
	
	public static SizedTexture creatureTradeOffersTexture(TradeOffer[] offers){
		if(offers.length == 0){
			BufferedImage image = new BufferedImage(600, 64, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(TRADE_OFFER_COLOR);
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
			g.setColor(java.awt.Color.BLACK);
			g.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
			g.setFont(TRADE_OFFER_FONT);
			g.drawString("There are no available trade offers.", 1, 40);
			g.dispose();
			return new SizedTexture(MyTextureLoader.loadTexture(image, false), image.getWidth(), image.getHeight());
		}
		BufferedImage image = new BufferedImage(600, 64 * offers.length, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(TRADE_OFFER_COLOR);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.setColor(java.awt.Color.BLACK);
		g.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
		g.setFont(TRADE_OFFER_FONT);
		for(int i = 0; i < offers.length; i++){
			g.drawImage(TRADE_ARROW, 300, i * 64, null);
			g.drawLine(0, i * 64, image.getWidth(), i * 64);
			TradeOffer to = offers[i];
			ItemStack[] give = to.getItemsToGive();
			ItemStack[] get = to.getItemsToGet();
			for(int j = 0; j < give.length && j < 4; j++){
				g.drawString(give[j].getAmount() + "", 5 + j * 100, i * 64 + 40);
				g.drawImage(give[j].getItem().getTexture().getImage(), 50 + j * 100, i * 64, 64, 64, null);
			}
			for(int j = 0; j < get.length && j < 4; j++){
				g.drawString(get[j].getAmount() + "", 305 + TRADE_ARROW.getWidth() + j * 100, i * 64 + 40);
				g.drawImage(get[j].getItem().getTexture().getImage(), 336 + TRADE_ARROW.getWidth() + j * 100, i * 64, 64, 64, null);
			}
		}
		g.dispose();
		return new SizedTexture(MyTextureLoader.loadTexture(image, false), image.getWidth(), image.getHeight());
	}
	
	public static SizedTexture createTradeOfferTexture(TradeOffer offer){
		BufferedImage image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new java.awt.Color(0, 150, 150));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.setColor(new java.awt.Color(0, 50, 50));
		g.fillRect(0, 0, image.getWidth() / 10, image.getHeight());
		g.fillRect(0, 0, image.getWidth(), image.getHeight() / 10);
		g.fillRect(0, image.getHeight() / 10 * 9, image.getWidth(), image.getHeight() / 10);
		g.fillRect(image.getWidth() / 10 * 9, 0, image.getWidth() / 10, image.getHeight());
		g.setFont(TRADE_OFFER_FONT);
		g.setColor(java.awt.Color.BLACK);
		g.drawString("Trade Offer", 190, 90);
		g.drawString("You are going to pay:", 75, 150);
		g.drawString("You are going to receive:", 75, 250);
		ItemStack[] give = offer.getItemsToGive();
		for(int j = 0; j < give.length && j < 4; j++){
			g.drawString(give[j].getAmount() + "", 75 + j * 100, 155 + 40);
			g.drawImage(give[j].getItem().getTexture().getImage(), 105 + j * 100, 155, 64, 64, null);
		}
		ItemStack[] get = offer.getItemsToGet();
		for(int j = 0; j < get.length && j < 4; j++){
			g.drawString(get[j].getAmount() + "", 75 + j * 100, 255 + 40);
			g.drawImage(get[j].getItem().getTexture().getImage(), 105 + j * 100, 255, 64, 64, null);
		}
		g.setColor(java.awt.Color.GREEN);
		g.fillRect(image.getWidth() / 6, image.getHeight() / 10 * 7, image.getWidth() / 4, image.getHeight() / 20 * 3);
		g.setColor(java.awt.Color.RED);
		g.fillRect(image.getWidth() * 7 / 12, image.getHeight() / 10 * 7, image.getWidth() / 4, image.getHeight() / 20 * 3);
		g.setColor(java.awt.Color.BLACK);
		g.drawRect(image.getWidth() / 6, image.getHeight() / 10 * 7, image.getWidth() / 4, image.getHeight() / 20 * 3);
		g.drawRect(image.getWidth() * 7 / 12, image.getHeight() / 10 * 7, image.getWidth() / 4, image.getHeight() / 20 * 3);
		g.drawString("Buy", image.getWidth() / 6 + image.getWidth() / 13, image.getHeight() / 10 * 8);
		g.drawString("Cancel", image.getWidth() * 7 / 12 + image.getWidth() / 30, image.getHeight() / 10 * 8);
		return new SizedTexture(MyTextureLoader.loadTexture(image, false), image.getWidth(), image.getHeight());
	}
}