package crawella;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import crawella.command.CommandArgument;
import crawella.command.ValidCommand;
import crawella.exception.InvalidCommandArgumentException;

public class ApplicationHelper {
	
	private static Map<String, Set<String>> parsedArgs = new HashMap<String, Set<String>>();

	private static boolean isCmdArg(String arg) {
		return arg.startsWith("-");
	}

	private static Set<CommandArgument> getCmdArgs(Set<CommandArgument> cmdArgs, String[] args, int index) throws InvalidCommandArgumentException {
		if (cmdArgs == null) {
			cmdArgs = new HashSet<CommandArgument>();
		}
		int nextIndex = index;
		String currentCmd = null;
		Set<String> currentCmdArgs = new HashSet<String>();
		for (int i = index; i < args.length; i++) {
			String arg = args[i];
			if (isCmdArg(arg)) {
				if (ValidCommand.isValidCmdName(arg)) {
					currentCmd = arg;
				} else {
					throw new InvalidCommandArgumentException(arg + " is not a valid command argument.");
				}
			} else if (currentCmd == null) {
				throw new InvalidCommandArgumentException(arg + " not a proper command argument.  Did you forget '-'?");
			} else {
				
				boolean onLastArg = i + 1 == args.length;
				boolean nextIsCmdArg = onLastArg ? false : isCmdArg(args[i + 1]);

				currentCmdArgs.add(arg);

				if (onLastArg || isCmdArg(args[i + 1])) {
					nextIndex = i + 1;
					cmdArgs.add(new CommandArgument(currentCmd, currentCmdArgs));
				}

				if (nextIsCmdArg) {
					cmdArgs = getCmdArgs(cmdArgs, args, nextIndex);
					break;
				}
			}
		}
		return cmdArgs;
	}

	public static void parseArgs(String[] args) throws InvalidCommandArgumentException {
		parseArgs(getCmdArgs(null, args, 0));
	}

	private static void parseArgs(Set<CommandArgument> cmdArgs) {
		Set<String> validArgs = new HashSet<String>();
		
		for (ValidCommand vCmd : ValidCommand.values()) {
			validArgs.add(vCmd.getCmdName());
		}

		for (CommandArgument cmdArg : cmdArgs) {
			parsedArgs.put(cmdArg.getName(), cmdArg.getArguments());
			validArgs.remove(cmdArg.getName());
		}

		for (String leftOverValidArg : validArgs) {
			parsedArgs.put(leftOverValidArg, null);
		}
	}

	public static Set<String> getArguments(String cmdName) {
		return parsedArgs.get(cmdName);
	}

	public static int getDepthArg() {
		Set<String> depthArgs = getArguments(ValidCommand.DEPTH.getCmdName());
		return depthArgs != null ? Integer.parseInt(depthArgs.iterator().next()) : 1;
	}
}
