import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
//MMN14-2022A-DS-DOR DAVID BENAYOUN-7/1/22
public class MergeableHeap {

		private static int myChoice;																					//menu decision - kept to whole program to keep the correct rules of the linkedlists
		private static int flag = -1;																					//flag to determine which heap is instructed
		private static int listCount;																					//amount heaps created
		private static int unions;																						//amount of unions
		private static int min;																							//minimum node
		private static Scanner read = new Scanner(System.in);															//user inputs
		private static ArrayList<LinkedList<Integer>> arrayList = new ArrayList<LinkedList<Integer>>();					//helper ArrayList in order to store and access multiple linkedlists
		private static LinkedList<Integer> inserted = new LinkedList<Integer>();										//helper LinkedList to determine if a node already exists in joint linkedlists
		//program instructions
		public static void main(String[] args) {
			Run();
		}
		//program driver - 3 linkedlists settings and an option to exit the program
		static void Run() {
			System.out.println("Welcome to mergeableheap using linkedlists, please choose linkedlists properties: *"
					+ "\n1 - Sorted linkedlists 								  *"
					+ "\n2 - Unsorted linkedlists 							  *\n"
					+ "3 - Joint linkedlists								  *"
					+"\n0 - Exit                               						  *"
					+"\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
			myChoice = read.nextInt();  //read user integer input
			//verify the number is in range
			if(myChoice>0&&myChoice<4) {
				Continue();
			}
			//termination
			else if(myChoice==0) Exit();
			//wrong number entered, program continues until the user is entering a number in the range
			else {
				//loop
				while(myChoice<0||myChoice>3) {
					System.out.println("Please enter a number between 0 to 3");
					myChoice = read.nextInt();
				}
				//user decided to exit
				if(myChoice==0) Exit();
				//user made his decision
				if(myChoice>0&&myChoice<4) {
					Continue();
				}
				
			}
		}
		//repeating functions menu with string verification
		static void Continue() {
			System.out.println("Please enter a command: MakeHeap, Insert, Minimum, ExtractMin, Union, PrintHeap, Exit");
			String command = read.next();
			//if callers to the desired function
			if(command.equals("MakeHeap")){
				MakeHeap();
			}
			if(command.equals("Insert")) {
				Insert();
			}
			if(command.equals("Minimum")) {
				Minimum();
			}
			if(command.equals("ExtractMin")) {
				ExtractMin();
			}
			if(command.equals("Union")) {
				Union();
			}
			if(command.equals("PrintHeap")) {
				PrintHeap();
			}
			if(command.equals("Exit")) {
				Exit();
			}
			//if wrong string input has been entered, return mistype and go back to functions menu
			else {
				System.out.println("# Theres a mistype #");
				Continue();
			}
		}
		//program termination 
		static void Exit(){
			System.out.println("Thanks for using me, goodbye!");
		}
		//creates a new integer linkedlist nested inside an ArrayList
		static void MakeHeap() {
			flag = flag+1; //add a flag
			listCount = listCount+1; //count amount of lists that has been created
			arrayList.add(new LinkedList<Integer>());	
			Continue();
		}
		//inserts a node while keeping the rules (sorted/unsorted/joint)
		static void Insert() {
			//verify if theres an existing heap
			if(flag>-1) {
				//sorted linkedlists - makes sure list stays sorted after insertion
				if(myChoice==1) {
					int x = read.nextInt();
					arrayList.get(flag).add(x);
					Collections.sort(arrayList.get(flag));
					Continue();
				}
				//unsorted linkedlists - normal insertion without any rules applied
				if(myChoice==2) {
					int y = read.nextInt();
					arrayList.get(flag).add(y);
					Continue();
				}
				//joint linkedlists - makes sure that the node hasnt been inserted before by storing all inserted nodes in a separate linkedlist
				if(myChoice==3) {
					int z = read.nextInt();
					//checks if the node exists inside inserted (helper linkedlist) - in case the node already exist user is asked for a different number
					if(inserted.contains(z)==true) {
						System.out.println("Node already exists, please enter a different number:");
						Insert();
					}
					inserted.add(z);
					arrayList.get(flag).add(z);
					Continue();
				}
			}
			//if a heap hasnt been created yet print a string and return to functions menu
			System.out.println("MakeHeap in order to insert");
			Continue();
		}
		
		//prints the smallest node in the current list
		static void Minimum() {
			//verify that theres an existing heap
			if(flag==-1) {
				System.out.println("You must MakeHeap before searching for minimum");
				Continue();
			}
			//if the heap is empty print a string a return to functions menu
			if(arrayList.get(flag).isEmpty()) {
				System.out.println("Heap is empty");
				Continue();
			}
			//in case the user works on sorted linkedlist and union function hasnt been used yet - return the first element in the linkedlist since a sorting has been done in Insert() function
			if(myChoice==1&&unions==0) {
				min = arrayList.get(flag).element();
				System.out.println("The smallest node is: "+min);	
				Continue();
			}
			//algorithm that finds the smallest node by looping the linkedlist using ListIterator and reutrns a string with smallest node
			min = arrayList.get(flag).element();
			for (ListIterator<Integer> listloop = arrayList.get(flag).listIterator(); listloop.hasNext();) {
				int current = listloop.next();	
				if(current<min) {
					min = current;
				}
			}
			System.out.println("The smallest node is: "+min);	
			Continue();
		}	
		//using minimum algorithm the function find the minimum and deletes it at the end
		static void ExtractMin() {
			//verify that theres an existing heap
			if(flag==-1) {
				System.out.println("You must MakeHeap before extracting a minimum");
				Continue();
			}
			//if the heap is empty print a string a return to functions menu
			if(arrayList.get(flag).isEmpty()) {
				System.out.println("Heap is empty");
				Continue();
			}
			//minimum algorithm
			min = arrayList.get(flag).element();
			for (ListIterator<Integer> listloop = arrayList.get(flag).listIterator(); listloop.hasNext();) {
				int current = listloop.next();	
				if(current<min) {
					min = current;
				}
			}
			//delete and print
			int delete = arrayList.get(flag).indexOf(min);
			arrayList.get(flag).remove(delete);
			System.out.println(min+" Has been deleted");
			Continue();
		}
		//building a new heap if theres more than 2 heaps created and adds nodes while keeping the old order inside each heap as it was before (first merged heap last node is pointing to the first node of the second merged heap and so on..) 
		static void Union() {
			if(flag>0){
				flag = flag+1;
				listCount = listCount+1;
				arrayList.add(new LinkedList<Integer>());	
				while(unions<listCount-1) {
					for (ListIterator<Integer> unionloop = arrayList.get(unions).listIterator(); unionloop.hasNext();) {
						int currentu = unionloop.next();
						arrayList.get(flag).add(currentu);		
					}
					unions = unions+1;
				}
			}
			else System.out.println("You must create at least 2 heaps in order to use this function");
			Continue();
		}
		//prints the current heap
		static void PrintHeap(){
			if(flag==-1) {
				System.out.println("Heap hasnt been created, please MakeHeap");
				return;
			}
			System.out.println(arrayList.get(flag).toString());
			Continue();
		}

	}