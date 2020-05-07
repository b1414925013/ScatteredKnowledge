package net.imyeyu.desktopnote.util;

import java.io.File;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import net.imyeyu.desktopnote.Entrance;
import net.imyeyu.utils.YeyuUtils;

public class BackupService extends Service<Boolean> {
	
	private File file = new File("DesktopNote.data");
	private File backup = new File("DesktopNote.backup");
	
	public BackupService() {
		if (!backup.exists()) backup();
	}

	protected Task<Boolean> createTask() {
		return new Task<Boolean>() {
			protected Boolean call() throws Exception {
				while (true) {
					Thread.sleep(Entrance.BACKUP_TIME * 1000 * 60);
					backup();
				}
			}
		};
	}
	
	private void backup() {
		YeyuUtils.file().stringToFile(backup, YeyuUtils.file().fileToString(file, "UTF-8"));
	}
}