package net.imyeyu.utils;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Shortcuter implements AWTEventListener {
	
	private static Shortcuter instance = new Shortcuter();
	private Map<String, ShortcutListener> listeners;
	private Set<Component> ignoredComponents;
	private boolean[] keys;

	private Shortcuter() {
		keys = new boolean[256];
		ignoredComponents = new HashSet<Component>();
		listeners = new HashMap<String, ShortcutListener>();
		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
	}

	public void eventDispatched(AWTEvent event) {
		if (event.getClass() == KeyEvent.class) {
			KeyEvent ke = (KeyEvent) event;
			if (ke.getID() == KeyEvent.KEY_PRESSED) {
				keys[ke.getKeyCode()] = true;
				String shortcut = searchShortcut();
				ShortcutListener l = listeners.get(shortcut);
				if (l != null && !isIgnored(event)) {
					l.handle();
				}
			} else if (ke.getID() == KeyEvent.KEY_RELEASED) {
				keys[ke.getKeyCode()] = false;
			}
		}
	}

	protected String searchShortcut() {
		StringBuilder shortcut = new StringBuilder();
		for (int i = 0; i < keys.length; ++i) {
			if (keys[i]) {
				shortcut.append(i).append(".");
			}
		}
		return shortcut.toString();
	}

	protected boolean isIgnored(AWTEvent event) {
		if (!(event.getSource() instanceof Component)) {
			return false;
		}
		boolean ignored = false;
		for (Component com = (Component) event.getSource(); com != null; com = com.getParent()) {
			if (ignoredComponents.contains(com)) {
				ignored = true;
				break;
			}
		}
		return ignored;
	}

	public static Shortcuter getInstance() {
		return instance;
	}

	public void removeShortcutListener(ShortcutListener l) {
		String tempKey = null;
		for (Map.Entry<String, ShortcutListener> e : listeners.entrySet()) {
			if (e.getValue().equals(l)) {
				tempKey = e.getKey();
			}
		}
		listeners.remove(tempKey);
	}

	public void addShortcutListener(ShortcutListener l, int... keys) {
		StringBuilder sb = new StringBuilder();
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; ++i) {
			if (0 < keys[i] && keys[i] < this.keys.length) {
				sb.append(keys[i]).append(".");
			} else {
				System.out.println("Key is not valid");
				return;
			}
		}
		String shortcut = sb.toString();
		if (listeners.containsKey(shortcut)) {
			System.out.println("The shourt cut is already used.");
		} else {
			listeners.put(shortcut, l);
		}
	}

	public void addIgnoredComponent(Component com) {
		ignoredComponents.add(com);
	}

	public void removeDiscardComponent(Component com) {
		ignoredComponents.remove(com);
	}

	public static interface ShortcutListener {
		void handle();
	}
}