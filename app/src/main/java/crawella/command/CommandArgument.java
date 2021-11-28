package crawella.command;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CommandArgument {
	String cmdName = "";
	Set<String> arguments = new HashSet<String>();

	public CommandArgument(String name, Collection<String> args) {
		cmdName = name;
		arguments.addAll(args);
	}

	public String getName() {
		return cmdName;
	}

	public Set<String> getArguments() {
		return arguments;
	}
}
