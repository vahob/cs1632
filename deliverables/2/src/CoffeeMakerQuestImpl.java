import java.util.*;

enum Item {
	NONE,
	COFFEE,
	CREAM,
	SUGAR
}

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {
	
	Player player;
	Room curRoom;
	ArrayList<Room> rooms;
	boolean isOver;
	int curIndex;

	CoffeeMakerQuestImpl() {
		// TODO
		player = null;
		rooms = new ArrayList<>();
		isOver = false;
		curRoom = null;
		curIndex = -1;
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		// TODO
		return isOver;
	}

	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		// TODO
		player = p;
	}
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		// TODO
		if(room == null) return false;
		if(!rooms.isEmpty()) return false;
		return rooms.add(room);
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the northern-most room
	 * @param northDoor string to label the south door of room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {
		// TODO
		if (room == null) return false;
		if(northDoor == null || southDoor == null) return false;
		if(rooms.isEmpty()) return false; 
		for(Room r : rooms)
		{
			if(r.getAdjective().equals(room.getAdjective()) || r.getFurnishing().equals(room.getFurnishing()))
				return false;
		}
		rooms.get(rooms.size()-1).setNorthDoor(northDoor);
		room.setSouthDoor(southDoor);
		return rooms.add(room);
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() {
		// TODO
		return curRoom;
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		// TODO
		if(room == null) return false;
		for(Room r : rooms)
		{
			curIndex++;
			if(r.getAdjective().equals(room.getAdjective()) && r.getFurnishing().equals(room.getFurnishing())) {
				curRoom = room;
				return true;
			}				
		}		
		return false;
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() {
		// TODO
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make
     * sure you use Player.getInventoryString() whenever you need to display
     * the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
		// TODO
		char c = cmd.toLowerCase().charAt(0);
		switch (c) {
		case 'n':
		
			if(curIndex < rooms.size()-1)
			{
				this.setCurrentRoom(rooms.get(curIndex+1));
				return "";
			}
			break;
		
		
		case 's':
		
			if(curIndex < rooms.size()-1)
			{
				this.setCurrentRoom(rooms.get(0));
				return "A door in that direction does not exist.\n";
			}
			break;
		
		
		case 'i':
		
			return player.getInventoryString();
		case 'l':
		
			Item i = curRoom.getItem();
			player.addItem(i);
			String ret = "There might be something here...\nYou found some ";
			String item = "";
			
			switch(i) {
			case COFFEE:
				item = "caffeinated coffee!\n";
				break;
			case CREAM:
				item = "creamy cream!\n";
				break;
			case SUGAR:
				item = "sweet sugar!\n";
				break;
			case NONE:
				break;
			}
			return ret+item;
		case 'd':
		
			String r = player.getInventoryString();
			this.isOver = true;
			if(player.checkCoffee() && player.checkCream() && player.checkSugar())
			{				
				return r + "\nYou drink the beverage and are ready to study!\nYou win!\n";
			}
			else
			{
				if(player.checkCoffee() || player.checkCream() || player.checkSugar())
				{
					return r+"\n"+drinkWithPartialInventory();
				}
				else
				{
					return r + "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";
				}
			}
		default:
			return "What?";
		
		}
		return "";
	}
	
	// private method
	private String drinkWithPartialInventory() {
		// TODO Auto-generated method stub
		if(player.checkCoffee() && player.checkCream() && !player.checkSugar())
			return "Without sugar, the coffee is too bitter. You cannot study.\nYou lose!";
		if(player.checkCream() && player.checkSugar() && !player.checkCoffee())
			return "You drink the sweetened cream, but without caffeine you cannot study.\nYou lose!";
		if(player.checkCoffee() && player.checkSugar() && !player.checkCream())
			return "Without cream, you get an ulcer and cannot study.\nYou lose!";
		if(player.checkCream() && !player.checkCoffee() && !player.checkSugar())
			return "You drink the cream, but without caffeine, you cannot study.\nYou lose!";
		if(player.checkCoffee() && !player.checkCream() && !player.checkSugar())
			return "Without cream, you get an ulcer and cannot study.\nYou lose!";
		if(player.checkSugar() && !player.checkCoffee() && !player.checkCream())
			return "You eat the sugar, but without caffeine, you cannot study.\nYou lose!";
			
		return "";
	}
	
}
