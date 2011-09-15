package com.tmarki.comicmaker;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


public class TextObject extends ImageObject {
	private int textSize = 40;
	private int textWidth = 0;
	private int color = Color.BLACK;
	private int typeface = 0;
	private String text;
	private boolean bold = false;
	private boolean italic = false;
	public TextObject (int xx, int yy, int ts, int col, int tf, String ss) {
		mPosition.x = xx;
		mPosition.y = yy;
		text = ss;
		textSize = ts;
		color = col;
		typeface = tf;
		regenerateBitmap ();
	}
	public TextObject (int xx, int yy, int ts, int col, int tf, String ss, boolean bld, boolean itlic) {
		mPosition.x = xx;
		mPosition.y = yy;
		text = ss;
		textSize = ts;
		color = col;
		typeface = tf;
		bold = bld;
		italic = itlic;
		regenerateBitmap ();
	}
	public TextObject(TextObject other) {
		mPosition.x = other.mPosition.x;
		mPosition.y = other.mPosition.y;
		text = other.text;
		textSize = other.textSize;
		color = other.color;
		typeface = other.typeface;
		bold = other.bold;
		italic = other.italic;
		regenerateBitmap ();
	}
	public void regenerateBitmap () {
		Paint p = new Paint();
		p.setTextSize(textSize);
		p.setTypeface(getTypefaceObj());
    	p.setColor(color);
		p.setStyle(Paint.Style.FILL);
		String lines[] = text.split("\n");
		textWidth = 0;
		for (String s : lines) {
			int tmp = (int)p.measureText(s);
			if (tmp > textWidth)
				textWidth = tmp;
		}
		if (textWidth < 1)
			textWidth = 1;
		if (content != null)
			content.recycle();
		content = Bitmap.createBitmap(textWidth, textSize * (lines.length + 1), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas (content);
		c.drawARGB(0, 0, 0, 0);
		p.setAntiAlias(true);
		int i = 1;
		for (String s : lines)
		{
			c.drawText(s, 0, i * textSize, p);
			i++;
		}
		Drawable d =new BitmapDrawable(content);
		d.setBounds(0, 0, textWidth, textSize * (lines.length + 1));
	}
	public int getTextSize() {
		return textSize;
	}
	public void setTextSize(int textSize) {
		this.textSize = textSize;
		content = null;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getTypeface() {
		return typeface;
	}
	public void setTypeface(int typeface) {
		this.typeface = typeface;
		content = null;
	}
	public boolean isBold() {
		return bold;
	}
	public void setBold(boolean bold) {
		this.bold = bold;
		content = null;
	}
	public boolean isItalic() {
		return italic;
	}
	public void setItalic(boolean italic) {
		this.italic = italic;
		content = null;
	}
	public int getX() {
		return mPosition.x;
	}
	public void setX(int x) {
		this.mPosition.x = x;
	}
	public int getY() {
		return mPosition.y;
	}
	public void setY(int y) {
		this.mPosition.y = y;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		content = null;
	}
	public Typeface getTypefaceObj () {
		Typeface tmptf = Typeface.DEFAULT;
		if (typeface == 0)
			tmptf = Typeface.MONOSPACE;
		if (typeface == 1)
			tmptf = Typeface.SANS_SERIF;
		else if (typeface == 2)
			tmptf = Typeface.SERIF;
		if (bold && !italic)
			tmptf = Typeface.create(tmptf, Typeface.BOLD);
		if (italic && !bold)
			tmptf = Typeface.create(tmptf, Typeface.ITALIC);
		if (italic && bold)
			tmptf = Typeface.create(tmptf, Typeface.BOLD_ITALIC);
		return tmptf;
	}
	static public Typeface getTypefaceObj (int ft, boolean bol, boolean ita) {
		Typeface tmptf = Typeface.DEFAULT;
		if (ft == 0)
			tmptf = Typeface.MONOSPACE;
		if (ft == 1)
			tmptf = Typeface.SANS_SERIF;
		else if (ft == 2)
			tmptf = Typeface.SERIF;
		if (bol && !ita)
			tmptf = Typeface.create(tmptf, Typeface.BOLD);
		if (ita && !bol)
			tmptf = Typeface.create(tmptf, Typeface.ITALIC);
		if (ita && bol)
			tmptf = Typeface.create(tmptf, Typeface.BOLD_ITALIC);
		return tmptf;
	}
	
/*	@Override
	public void setScale(float Scale) {
		if ((mScale - Scale) > 0 && textSize > 10)
			textSize -= 2;
		else if((mScale - Scale) < 0 && textSize < 500) 
			textSize += 2;
		content = null;
	}*/

	public static String[] getTypefaceNames () {
		String[] tmp = new String[3];
		tmp[0] = "Monospace";
		tmp[1] = "Sans";
		tmp[2] = "Serif";
		return tmp;
	}
	
	@Override
	public void draw (Canvas c) {
		if (content == null)
			regenerateBitmap();
		super.draw(c);
	}
}

