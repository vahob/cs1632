import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Formatter;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Code by @author Wonsun Ahn
 * 
 * <p>
 * BeanCounterLogic: The bean counter, also known as a quincunx or the Galton
 * box, is a device for statistics experiments named after English scientist Sir
 * Francis Galton. It consists of an upright board with evenly spaced nails (or
 * pegs) in a triangular form. Each bean takes a random path and falls into a
 * slot.
 *
 * <p>
 * Beans are dropped from the opening of the board. Every time a bean hits a
 * nail, it has a 50% chance of falling to the left or to the right. The piles
 * of beans are accumulated in the slots at the bottom of the board.
 * 
 * <p>
 * This class implements the core logic of the machine. The MainPanel uses the
 * state inside BeanCounterLogic to display on the screen.
 * 
 * <p>
 * Note that BeanCounterLogic uses a logical coordinate system to store the
 * positions of in-flight beans.For example, for a 4-slot machine: (0, 0) (0, 1)
 * (1, 1) (0, 2) (1, 2) (2, 2) (0, 3) (1, 3) (2, 3) (3, 3) [Slot0] [Slot1]
 * [Slot2] [Slot3]
 */

public class BeanCounterLogicImpl implements BeanCounterLogic {
	// TODO: Add member methods and variables as needed

	private ArrayList<Bean> inFlightBeans;
	private ArrayList<Bean> remainingBeans;
	private ArrayList<ArrayList<Bean>> slotBean;
	private int slotCount;

	/**
	 * Constructor - creates the bean counter logic object that implements the core
	 * logic with the provided number of slots.
	 * 
	 * @param slotCount the number of slots in the machine
	 */
	BeanCounterLogicImpl(int slotCount) {
		// TODO: Implement
		remainingBeans = new ArrayList<Bean>(slotCount);
		inFlightBeans = new ArrayList<Bean>(slotCount);
		slotBean = new ArrayList<>();
		for (int i = 0; i < slotCount; i++) {
			ArrayList<Bean> e = new ArrayList<>();
			slotBean.add(e);
			inFlightBeans.add(null);

		}
		this.slotCount = slotCount;

	}

	/**
	 * Returns the number of slots the machine was initialized with.
	 * 
	 * @return number of slots
	 */
	public int getSlotCount() {
		// TODO: Implement
		return this.slotCount;
	}

	/**
	 * Returns the number of beans remaining that are waiting to get inserted.
	 * 
	 * @return number of beans remaining
	 */
	public int getRemainingBeanCount() {
		// TODO: Implement
		// System.out.println("remainig size: " + remainingBeans.size());
		return remainingBeans.size();
	}

	/**
	 * Returns the x-coordinate for the in-flight bean at the provided y-coordinate.
	 * 
	 * @param yPos the y-coordinate in which to look for the in-flight bean
	 * @return the x-coordinate of the in-flight bean; if no bean in y-coordinate,
	 *         return NO_BEAN_IN_YPOS
	 */
	public int getInFlightBeanXPos(int yPos) {
		// TODO: Implement
		if (inFlightBeans.get(yPos) != null)
			return inFlightBeans.get(yPos).getXPos();
		else
			return NO_BEAN_IN_YPOS;
	}

	/**
	 * Returns the number of beans in the ith slot.
	 * 
	 * @param i index of slot
	 * @return number of beans in slot
	 */
	public int getSlotBeanCount(int i) {
		// TODO: Implement
		if (slotBean.isEmpty())
			return 0;
		if (slotBean.get(i) == null)
			return 0;
		return slotBean.get(i).size();
	}

	/**
	 * Calculates the average slot number of all the beans in slots.
	 * 
	 * @return Average slot number of all the beans in slots.
	 */
	public double getAverageSlotBeanCount() {
		// TODO: Implement
		int sumOfSlotCounts = 0;
		int sumOfAllSlotNums = 0;
		for (int i = 0; i < slotBean.size(); i++) {
			int count = getSlotBeanCount(i);
			sumOfAllSlotNums += (i * count);
			sumOfSlotCounts += count;
		}
		if (sumOfSlotCounts > 0)
			return sumOfAllSlotNums / (double) sumOfSlotCounts;
		return 0;
	}

	/**
	 * Removes the lower half of all beans currently in slots, keeping only the
	 * upper half. If there are an odd number of beans, remove (N-1)/2 beans, where
	 * N is the number of beans. So, if there are 3 beans, 1 will be removed and 2
	 * will be remaining.
	 */
	public void upperHalf() {
		// TODO: Implement
		int sumOfSlotCounts = getSumOfSlotCount()-1;
		int slot = 0;
		int beanNumber = getSlotBeanCount(slot)-1;
		int deleted = 0;
		//System.out.println("sumOfCounts: " + sumOfSlotCounts);
		while(deleted < Math.floor(sumOfSlotCounts / 2) ){
			/*if (slotBean.get(slot).isEmpty()) {
				slot++;
				beanNumber = 0;
				System.out.println("slot empty: slot: " + slot + " beanN: " + beanNumber);
			}*/
			
			//System.out.println("i: " + deleted + " count: " + getSlotBeanCount(slot)+" beanNumber: " + beanNumber);
			if(beanNumber < getSlotBeanCount(slot) && beanNumber >= 0 && getSlotBeanCount(slot) > 0 ) {
				//System.out.println("slot not empty: slot: " + slot + " beanN: " + beanNumber);
				slotBean.get(slot).remove(beanNumber--);
				deleted++;
			}
			else
			{
				//System.out.println("slot empty: slot: " + slot + " beanN: " + beanNumber);
				if(slot < getSlotCount()-1) slot++;
				if(getSlotBeanCount(slot) > 0) beanNumber = getSlotBeanCount(slot)-1;
				else beanNumber = 0;
				
			}
			
		}

	}

	public int getSumOfSlotCount() {
		int sumOfSlotCounts = 0;
		for (int i = 0; i < slotBean.size(); i++) {
			int count = getSlotBeanCount(i);
			sumOfSlotCounts += count;
		}
		return sumOfSlotCounts;
	}

	/**
	 * Removes the upper half of all beans currently in slots, keeping only the
	 * lower half. If there are an odd number of beans, remove (N-1)/2 beans, where
	 * N is the number of beans. So, if there are 3 beans, 1 will be removed and 2
	 * will be remaining.
	 */
	public void lowerHalf() {
		// TODO: Implement
		int sumOfSlotCounts = getSumOfSlotCount()-1;
		if(sumOfSlotCounts < 0) return;
		//if(getSlotCount() == 1 && getSumOfSlotCount() == 1) return;
		int slot = getSlotCount()-1;
		int beanNumber = getSlotBeanCount(slot)-1;
		int deleted=0;
		//System.out.println("sumOfCounts: " + sumOfSlotCounts);
		while(deleted < Math.floor(sumOfSlotCounts / 2) ){
			/*if (slotBean.get(slot).isEmpty()) {
				slot++;
				beanNumber = 0;
				System.out.println("slot empty: slot: " + slot + " beanN: " + beanNumber);
			}*/
			
			//System.out.println("i: " + deleted + " count: " + getSlotBeanCount(slot)+" beanNumber: " + beanNumber);
			if(beanNumber < getSlotBeanCount(slot) && beanNumber >= 0 && getSlotBeanCount(slot) > 0 ) {
				//System.out.println("slot not empty: slot: " + slot + " beanN: " + beanNumber);
				slotBean.get(slot).remove(beanNumber--);
				deleted++;
			}
			else
			{
				//System.out.println("slot empty: slot: " + slot + " beanN: " + beanNumber);
				if(slot >= 0) slot--;
				if(getSlotBeanCount(slot) > 0) beanNumber = getSlotBeanCount(slot)-1;
				else beanNumber = 0;
				
			}
			
		}
	}

	/**
	 * A hard reset. Initializes the machine with the passed beans. The machine
	 * starts with one bean at the top.
	 * 
	 * @param beans array of beans to add to the machine
	 */
	public void reset(Bean[] beans) {
		// TODO: Implement
		remainingBeans.clear();
		inFlightBeans.clear();
		this.slotBean.clear();
		for (int i = 0; i < getSlotCount(); i++) {
			ArrayList<Bean> e = new ArrayList<>();
			slotBean.add(e);
			inFlightBeans.add(null);
		}
		if (beans == null)
			return;

		this.remainingBeans.addAll(Arrays.asList(beans));
		//System.out.println("reset: ");
		insertBean();
		if (inFlightBeans.get(0) != null)
			inFlightBeans.get(0).reset();

	}

	/**
	 * Repeats the experiment by scooping up all beans in the slots and all beans
	 * in-flight and adding them into the pool of remaining beans. As in the
	 * beginning, the machine starts with one bean at the top.
	 */
	public void repeat() {
		// TODO: Implement
		//System.out.println("size remainingBeans: " + remainingBeans.size());
		//System.out.println("getallbeans: " + this.getSumOfSlotCount());
		//System.out.println("size of inflight: " + inFlightBeans.size());

		for (int i = 0; i < this.getSlotCount(); i++) {
			if (slotBean.isEmpty())
				break;
			for(int k=0; k < slotBean.get(i).size(); k++)
			{
				if(slotBean.get(i).get(k) != null)
					this.remainingBeans.add(slotBean.get(i).get(k));
			}
			//this.remainingBeans
					//.addAll(this.slotBean.get(i).stream().filter(x -> x != null).collect(Collectors.toList()));
			if(this.inFlightBeans.get(i) != null)
				this.remainingBeans.add(this.inFlightBeans.get(i));
		}
		inFlightBeans.clear();		
		this.slotBean.clear();
		for(int i=0; i < getSlotCount(); i++)
		{
			ArrayList<Bean> e = new ArrayList<>();
			slotBean.add(e);
			inFlightBeans.add(null);
		}
		insertBean();
		if(inFlightBeans.get(0) != null) inFlightBeans.get(0).reset();
	}

	/**
	 * Advances the machine one step. All the in-flight beans fall down one step to
	 * the next peg. A new bean is inserted into the top of the machine if there are
	 * beans remaining.
	 * 
	 * @return whether there has been any status change. If there is no change, that
	 *         means the machine is finished.
	 */
	public boolean advanceStep() {
		// TODO: Implement
		/*System.out.println("Before:");
		for(int i=0; i < getSlotCount(); i++)
		{
			if(inFlightBeans.get(i)==null)
				System.out.println(i+"=null");
			else
				System.out.println(i+" not null:" + inFlightBeans.get(i).getXPos());
		}*/
		
		boolean anyStatusChange=false;
		Bean prev=null; 
		Bean bean=inFlightBeans.get(0);
		
		for(int i=0; i < getSlotCount(); i++)
		{
			if(i < getSlotCount()-1)
			{
				//System.out.println("i < count-2");
				prev = bean;
				bean = inFlightBeans.get(i+1);
				if(prev!=null) prev.choose();
				inFlightBeans.set(i+1, prev);		
				anyStatusChange = true;
			}
			else
			{
				//System.out.println("i else: " + i);
				if(bean != null) {
					this.slotBean.get(bean.getXPos()).add(bean);
					anyStatusChange= true;
				}
			}
			
						
		}
		insertBean();
		if(inFlightBeans.get(0) != null) inFlightBeans.get(0).reset();
		//System.out.println("After:");
		/*for(int i=0; i < getSlotCount(); i++)
		{
			if(inFlightBeans.get(i)==null)
				System.out.println(i+"=null");
			else
				System.out.println(i+" not null:" + inFlightBeans.get(i).getXPos());
		}*/
		return anyStatusChange;

	}

	/* public boolean advanceStep() {
		// TODO: Implement
		System.out.println("Before:");
		for(int i=0; i < getSlotCount(); i++)
		{
			if(inFlightBeans.get(i)==null)
				System.out.println(i+"=null");
			else
				System.out.println(i+" not null:" + inFlightBeans.get(i).getXPos());
		}
		if(getSlotCount()==0) return false;
		boolean anyStatusChange=false;
		Bean prev = inFlightBeans.get(0);
		if(getSlotCount()==1) {
			insertBean();
			this.slotBean.get(0).add(prev);
			this.inFlightBeans.set(0,null);
			return false;
		}
		Bean bean = inFlightBeans.get(1);
		if(prev != null) prev.choose();
		inFlightBeans.set(1, prev);
		
		for(int i=1; i < getSlotCount(); i++)
		{
			if(bean == null && i < getSlotCount()-1)
			{
				//System.out.println("Bean is null");
				prev = bean;
				bean = inFlightBeans.get(i+1);
				if(i < getSlotCount() - 1)
					inFlightBeans.set(i+1, prev);
				continue;
			}	
			if(i < getSlotCount()-1)
			{
				//System.out.println("i < count-2");
				prev = bean;
				bean = inFlightBeans.get(i+1);
				prev.choose();
				inFlightBeans.set(i+1, prev);		
				anyStatusChange = true;
			}
			else
			{
				//System.out.println("i else: " + i);
				if(bean != null) {
					this.slotBean.get(bean.getXPos()).add(bean);
					anyStatusChange= true;
				}
			}
			
						
		}
		insertBean();
		if(inFlightBeans.get(0) != null) inFlightBeans.get(0).reset();
		/*System.out.println("After:");
		for(int i=0; i < getSlotCount(); i++)
		{
			if(inFlightBeans.get(i)==null)
				System.out.println(i+"=null");
			else
				System.out.println(i+" not null:" + inFlightBeans.get(i).getXPos());
		}
		return anyStatusChange;

	} */

	private void insertBean() {
		
		if(!remainingBeans.isEmpty()) {
			//System.out.println("remaining at zero " + this.remainingBeans.get(0).getXPos());
			this.inFlightBeans.set(0,this.remainingBeans.remove(0));}
		else
			this.inFlightBeans.set(0,null);
	}
	
	/**
	 * Number of spaces in between numbers when printing out the state of the machine.
	 * Make sure the number is odd (even numbers don't work as well).
	 */
	private int xspacing = 3;

	/**
	 * Calculates the number of spaces to indent for the given row of pegs.
	 * 
	 * @param yPos the y-position (or row number) of the pegs
	 * @return the number of spaces to indent
	 */
	private int getIndent(int yPos) {
		int rootIndent = (getSlotCount() - 1) * (xspacing + 1) / 2 + (xspacing + 1);
		return rootIndent - (xspacing + 1) / 2 * yPos;
	}

	/**
	 * Constructs a string representation of the bean count of all the slots.
	 * 
	 * @return a string with bean counts for each slot
	 */
	public String getSlotString() {
		StringBuilder bld = new StringBuilder();
		Formatter fmt = new Formatter(bld);
		String format = "%" + (xspacing + 1) + "d";
		for (int i = 0; i < getSlotCount(); i++) {
			fmt.format(format, getSlotBeanCount(i));
		}
		fmt.close();
		return bld.toString();
	}

	/**
	 * Constructs a string representation of the entire machine. If a peg has a bean
	 * above it, it is represented as a "1", otherwise it is represented as a "0".
	 * At the very bottom is attached the slots with the bean counts.
	 * 
	 * @return the string representation of the machine
	 */
	public String toString() {
		StringBuilder bld = new StringBuilder();
		Formatter fmt = new Formatter(bld);
		for (int yPos = 0; yPos < getSlotCount(); yPos++) {
			int xBeanPos = getInFlightBeanXPos(yPos);
			for (int xPos = 0; xPos <= yPos; xPos++) {
				int spacing = (xPos == 0) ? getIndent(yPos) : (xspacing + 1);
				String format = "%" + spacing + "d";
				if (xPos == xBeanPos) {
					fmt.format(format, 1);
				} else {
					fmt.format(format, 0);
				}
			}
			fmt.format("%n");
		}
		fmt.close();
		return bld.toString() + getSlotString();
	}

	/**
	 * Prints usage information.
	 */
	public static void showUsage() {
		System.out.println("Usage: java BeanCounterLogic slot_count bean_count <luck | skill> [debug]");
		System.out.println("Example: java BeanCounterLogic 10 400 luck");
		System.out.println("Example: java BeanCounterLogic 20 1000 skill debug");
	}
	
	/**
	 * Auxiliary main method. Runs the machine in text mode with no bells and
	 * whistles. It simply shows the slot bean count at the end.
	 * 
	 * @param args commandline arguments; see showUsage() for detailed information
	 */
	public static void main(String[] args) {
		boolean debug;
		boolean luck;
		int slotCount = 0;
		int beanCount = 0;

		if (args.length != 3 && args.length != 4) {
			showUsage();
			return;
		}

		try {
			slotCount = Integer.parseInt(args[0]);
			beanCount = Integer.parseInt(args[1]);
		} catch (NumberFormatException ne) {
			showUsage();
			return;
		}
		if (beanCount < 0) {
			showUsage();
			return;
		}

		if (args[2].equals("luck")) {
			luck = true;
		} else if (args[2].equals("skill")) {
			luck = false;
		} else {
			showUsage();
			return;
		}
		
		if (args.length == 4 && args[3].equals("debug")) {
			debug = true;
		} else {
			debug = false;
		}

		// Create the internal logic
		BeanCounterLogicImpl logic = new BeanCounterLogicImpl(slotCount);
		// Create the beans (in luck mode)
		BeanImpl[] beans = new BeanImpl[beanCount];
		for (int i = 0; i < beanCount; i++) {
			beans[i] = new BeanImpl(slotCount, luck, new Random());
		}
		// Initialize the logic with the beans
		logic.reset(beans);

		if (debug) {
			System.out.println(logic.toString());
		}

		// Perform the experiment
		while (true) {
			if (!logic.advanceStep()) {
				break;
			}
			if (debug) {
				System.out.println(logic.toString());
			}
		}
		// display experimental results
		System.out.println("Slot bean counts:");
		System.out.println(logic.getSlotString());
		//logic.upperHalf();
	}
}
