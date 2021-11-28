package crawella;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import crawella.command.CommandArgument;
import crawella.command.ValidCommand;
import crawella.exception.InvalidCommandArgumentException;

public class ApplicationHelperTest {
	
	@Test
	public void testParseWronglyFormattedCommandArgument() {
		final String args[] = {"depth", "1"};

		Exception exception = assertThrows(InvalidCommandArgumentException.class, () -> {
			ApplicationHelper.parseArgs(args);
		});

		System.out.println(exception.getMessage());

		assertTrue(exception.getMessage().contains(" not a proper command argument.  Did you forget '-'?"));
	}

	@Test
	public void testParseInvalidCommandArgument() {
		final String args[] = {"-dept", "1"};

		Exception exception = assertThrows(InvalidCommandArgumentException.class, () -> {
			ApplicationHelper.parseArgs(args);
		});

		System.out.println(exception.getMessage());

		assertTrue(exception.getMessage().contains(" is not a valid command argument."));	
	}

	@Test
	public void testSuccessfullyParseMultipleArgs() {
		final String args[] = {
			ValidCommand.DEPTH.getCmdName(), "1",
			ValidCommand.TEST.getCmdName(), "woo", "testing"
		};

		try {
			ApplicationHelper.parseArgs(args);

			int depth = ApplicationHelper.getDepthArg();
			assertEquals(depth, 1);

			Set<String> testArgs = ApplicationHelper.getArguments(ValidCommand.TEST.getCmdName());
			assertTrue(testArgs.size() == 2);

		} catch (InvalidCommandArgumentException e) {
			e.printStackTrace();
			fail("Should not have thrown an InvalidCommandArgumentException here");
		}

	}

}
