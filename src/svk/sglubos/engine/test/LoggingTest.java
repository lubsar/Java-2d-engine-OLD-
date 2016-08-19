package svk.sglubos.engine.test;

import java.io.FileNotFoundException;

import svk.sglubos.engine.utils.log.Logger;
import svk.sglubos.engine.utils.log.LoggingUtilities;
import svk.sglubos.engine.utils.log.basic.BasicLog;

public class LoggingTest {
	private BasicLog local;
	private BasicLog global;
	
	LoggingTest() throws FileNotFoundException {
		local = new BasicLog("id", false, "local.log");
		global = (BasicLog) Logger.addGlobalLog(new BasicLog("none", true, "global.log"));
		Logger.setMasterLog(new BasicLog("log", false,LoggingUtilities.getTime() + ".log"));
		
		Logger.log("bash");
		
		local.log("1","2\n","3");
		local.close();
		local.log("a");
		
		global.log("david");
		global.close();
		
		Logger.close();
	}
	
	public static void main(String[] args) {
		try {
			new LoggingTest();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
