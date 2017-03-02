package server.utility;

import java.util.TreeMap;

public class CommandRunner {
	public static TreeMap<String, Command> commands = new TreeMap<String, Command>(String.CASE_INSENSITIVE_ORDER);
	
	public static void registerCommand(Command command)
	{
		commands.put(command.name, command);
	}
	public static String getAvailableCommandsString()
	{
		String result="---ALL AVAILABLE COMMANDS---\n";
		for(String key : commands.keySet())
		{
			result+=commands.get(key).name.toUpperCase() + (commands.get(key).description.equals("")?"":":   ") + commands.get(key).description + "\n";
		}
		return result;
	}
	public static void processInput(String input){
    	String[] inputComponents = input.trim().split(" ");
    	if(inputComponents.length==0)
    		return;
    	String command = inputComponents[0];
    	if(!commands.containsKey(command))
    	{
    		System.out.println("ERROR: Could not find command "+command);
    		return;
    	}
    	String[] args = new String[inputComponents.length-1];
    	for(int i=1;i<inputComponents.length;i++)
    	{
    		args[i-1]=inputComponents[i];
    	}
    	try{
    		commands.get(command).run(args);
    	}catch(Exception ex){
    		System.out.println("Failed to run command");
    		ex.printStackTrace();
		}
    }
}
