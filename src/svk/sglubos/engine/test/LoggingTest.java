package svk.sglubos.engine.test;

import svk.sglubos.engine.utils.log.Logger;
import svk.sglubos.engine.utils.log.LoggingUtilities;
import svk.sglubos.engine.utils.log.basic.BasicLog;

public class LoggingTest {
	private BasicLog local = new BasicLog("local.log", false);
	private BasicLog global = (BasicLog) Logger.addGlobalLog("globlog", new BasicLog("global.log", true));
	
	LoggingTest() {
		Logger.setMasterLog(new BasicLog(LoggingUtilities.getTime() + ".log", false));
		Logger.log("bash");
		
		local.log("1","2\n","3");
		local.close();
		local.log("a");
		
		global.log("david");
		global.close();
		
		Logger.close();
	}
	
	public static void main(String[] args) {
		new LoggingTest();
	}
}
