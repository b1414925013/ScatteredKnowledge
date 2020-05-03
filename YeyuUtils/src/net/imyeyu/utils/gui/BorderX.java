package net.imyeyu.utils.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import net.imyeyu.utils.interfaces.GUIX;

public class BorderX {
	
	public static final Border EMPTY = Border.EMPTY;
	public static final BorderStrokeStyle NONE = BorderStrokeStyle.NONE;
	public static final BorderStrokeStyle SOLID = BorderStrokeStyle.SOLID;
	public static final BorderStrokeStyle DASHED = BorderStrokeStyle.DASHED;
	public static final BorderStrokeStyle DOTTED = BorderStrokeStyle.DOTTED;
	
	private Paint color;
	private BorderStrokeStyle style;
	private CornerRadii radius = CornerRadii.EMPTY;
	private int width;
	private Insets insets = new Insets(0);

	public BorderX(String color, BorderStrokeStyle style, int width) {
		this.color = Paint.valueOf(color);
		this.style = style;
		this.width = width;
	}

	public BorderX(String color, BorderStrokeStyle style, double radius, int width) {
		this.color = Paint.valueOf(color);
		this.style = style;
		this.width = width;
		this.radius = new CornerRadii(radius);
	}
	
	public void setRadius(double width, boolean asPercent) {
		radius = new CornerRadii(width, asPercent);
	}
	
	public void setRadius(double topLeft, double topRight, double bottomRight, double bottomLeft, boolean asPercent) {
		radius = new CornerRadii(topLeft, topRight, bottomRight, bottomLeft, asPercent);
	}
	
	public void setInsets(double width) {
		insets = new Insets(width);
	}
	
	public void setInsets(double top, double right, double bottom, double left) {
		insets = new Insets(top, right, bottom, left);
	}
	
	public Border def() {
		BorderStroke stroke = new BorderStroke(
			color, color, color, color,
			style, style, style, style,
			radius,
			new BorderWidths(width),
			insets
		);
		return new Border(stroke);
	}
	
	public Border top() {
		BorderStroke stroke = new BorderStroke(
			color, GUIX.TRANSPARENT, GUIX.TRANSPARENT, GUIX.TRANSPARENT,
			style, NONE, NONE, NONE,
			radius,
			new BorderWidths(width, 0, 0, 0),
			insets
		);
		return new Border(stroke);
	}
	
	public Border right() {
		BorderStroke stroke = new BorderStroke(
			GUIX.TRANSPARENT, color, GUIX.TRANSPARENT, GUIX.TRANSPARENT,
			NONE, style, NONE, NONE,
			radius,
			new BorderWidths(0, width, 0, 0),
			insets
		);
		return new Border(stroke);
	}
	
	public Border bottom() {
		BorderStroke stroke = new BorderStroke(
			GUIX.TRANSPARENT, GUIX.TRANSPARENT, color, GUIX.TRANSPARENT,
			NONE, NONE, style, NONE,
			radius,
			new BorderWidths(0, 0, width, 0),
			insets
		);
		return new Border(stroke);
	}
	
	public Border left() {
		BorderStroke stroke = new BorderStroke(
			GUIX.TRANSPARENT, GUIX.TRANSPARENT, GUIX.TRANSPARENT, color,
			NONE, NONE, NONE, style,
			radius,
			new BorderWidths(0, 0, 0, width),
			insets
		);
		return new Border(stroke);
	}
	
	public Border vertical() {
		BorderStroke stroke = new BorderStroke(
			color, GUIX.TRANSPARENT, color, GUIX.TRANSPARENT,
			style, NONE, style, NONE,
			radius,
			new BorderWidths(width, 0, width, 0),
			insets
		);
		return new Border(stroke);
	}
	
	public Border horizontal() {
		BorderStroke stroke = new BorderStroke(
			GUIX.TRANSPARENT, color, GUIX.TRANSPARENT, color,
			NONE, style, NONE, style,
			radius,
			new BorderWidths(0, width, 0, width),
			insets
		);
		return new Border(stroke);
	}
	
	public Border exLeft() {
		BorderStroke stroke = new BorderStroke(
			color, color, color, GUIX.TRANSPARENT,
			style, style, style, NONE,
			radius,
			new BorderWidths(width, width, width, 0),
			insets
		);
		return new Border(stroke);
	}
	
	public Border exRight() {
		BorderStroke stroke = new BorderStroke(
			color, GUIX.TRANSPARENT, color, color,
			style, NONE, style, style,
			radius,
			new BorderWidths(width, 0, width, width),
			insets
		);
		return new Border(stroke);
	}
	
	public Border exBottom() {
		BorderStroke stroke = new BorderStroke(
			color, color, GUIX.TRANSPARENT, color,
			style, style, NONE, style,
			radius,
			new BorderWidths(width, width, 0, width),
			insets
		);
		return new Border(stroke);
	}
}