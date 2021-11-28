package crawella.command;

import java.util.HashMap;
import java.util.Map;

public enum ValidCommand {
	DEPTH("-depth", 1),
	TEST("-test", 2);

	private String 		cmdName;
	private int				numArgs;

	private static Map<String, String> nameValidationMap = new HashMap<String, String>();

	private ValidCommand(String cmdName, int numArgs) {
		this.cmdName 			= cmdName;
		this.numArgs 			= numArgs;
	}

	static {
		for (ValidCommand vCmd : values()) {
			nameValidationMap.put(vCmd.cmdName, vCmd.cmdName);
		}
	}

	public static boolean isValidCmdName(String cmdName) {
		return nameValidationMap.containsKey(cmdName);
	}

	public String getCmdName() {
		return cmdName;
	}

	public int getNumArgs() {
		return numArgs;
	}
}
