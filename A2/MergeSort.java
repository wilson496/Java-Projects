/* MergeSort.java
   CSC 225 - Spring 2016
   Assignment 2 - Template for Merge Sort (Linked List version)
      
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java MergeSort
	
   To conveniently test the algorithm with a large input, create a 
   text file containing space-separated integer values and run the program with
	java MergeSort file.txt
   where file.txt is replaced by the name of the text file.

   NOTE: For large input files, the depth of recursion may cause the Java
   runtime environment to run out of stack space. To remedy this, run java
   with the extra parameter "-Xss64m" (which increases the stack size to 64
   megabytes). For example: java -Xss64m MergeSort input_data.txt
   
   B. Bird & M. Simpson - 03/22/2015
   
   
   MergeSort algorithm implementation by Cameron Wilson 
   
   Februrary 13th, 2016
   
   
   Assignment written to create a purely recursive implementation of MergeSort. The algorithm was done iteratively then converted to recursive. 
   
   
   Credit is due to B. Bird and M. Simpson for the test class and the subordinate functions. 
   

*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

//Do not change the name of the MergeSort class
public class MergeSort{
	/* MergeSort(head)
		Given a reference to the head of a list, sort the contents of the list 
		with merge sort and return a reference to the sorted list. Your 
		implementation may create a new list or modify the input list.
		
		To achieve full marks, you may not use any iterative loop constructs
		(including for loops, while	loops or do-while loops); all iteration
		must be accomplished with recursion. 
		
		You may add additional functions (or classes) if needed, but the entire program must be
		contained in this file. 

		Do not change the function signature (name/parameters).
	*/
	public static ListNode MergeSort(ListNode head){
		if (head == null || head.next == null){
			return head;
		}
		ListNode one = head;
		ListNode two = head.next;
		


		//Initial spot for while loop iteration, replaced by function call to function()
		two = function(two, head);
	
		two = head.next;
		head.next = null;
		return merge(MergeSort(one), MergeSort(two));
		
		
		

	}
	
	
	//function replaces the while loop that was in MergeSort with a call that returns the head node and a new node that will be assigned to two
	
	public static ListNode function(ListNode two, ListNode head){
		
		if (two != null && two.next !=null){
			head = head.next;
			two = two.next.next;
			return function(two, head);
		}
		
		return null; 
	}
	
	
	 public static ListNode merge(ListNode a, ListNode b){ 
       
  	    ListNode temp = new ListNode(0); 
        ListNode head = temp;
        ListNode c = head; 
    	
		return merge2(temp, head, a, b, c);
		
    } 
	

	//Additional function created to add recursion to the merge portion of the MergeSort implementation. 
	public static ListNode merge2(ListNode temp, ListNode head, ListNode a, ListNode b, ListNode c){
		
		
		if (a != null && b != null){
				
				if(a.value <= b.value){
					c.next = a;
					c = a;
					a = a.next;
				
				} else {
					c.next = b;
					c = b;
					b = b.next;
				}
				
				return merge2(temp, head, a, b, c);
				
				
		}
		
		
		c.next = (a == null) ? b : a; 
        return head.next; 
	
	}
	
	
	
	/* ListNode class */

	public static class ListNode{
		int value;
		ListNode next;
		public ListNode(int value){
			this.value = value;
			this.next = null;
		}
		public ListNode(int value, ListNode next){
			this.value = value;
			this.next = next;
		}
	}
	
	/* Testing code */
	
	/* listLength(node)
	   Compute the length of the list starting at the provided node.
	*/
	public static int listLength(ListNode node){
		if (node == null)
			return 0;
		return 1 + listLength(node.next);
	}
	
	/* testSorted(node)
	   Returns true if all elements of the list starting at the provided node
	   are in ascending order.
	*/
	public static boolean testSorted(ListNode node){
		//An empty list is always considered sorted.
		if (node == null)
			return true;
		//A list with one element is always considered sorted.
		if (node.next == null)
			return true;
		//Test whether the first element is greater than the second element
		if (node.value > node.next.value){
			//If so, the list is not sorted.
			return false;
		}else{
			//Otherwise, test whether the remaining elements are sorted and
			//return the result.
			return testSorted(node.next);
		}
	}
	
	/* printList(node)
	   Print all list elements starting at the provided node.
	*/
	public static void printList(ListNode node){
		if (node == null)
			System.out.println();
		else{
			System.out.print(node.value + " ");
			printList(node.next);
		}
	}
	
	/* readInput(inputScanner)
	   Read integer values from the provided scanner into a linked list.
	   Each recursive call reads one value, and recursion ends when the user
	   enters a negative value or the input file ends.
	*/
	public static ListNode readInput(Scanner inputScanner){
		//If no input is left, return an empty list (i.e. null)
		if (!inputScanner.hasNextInt())
			return null;
		int v = inputScanner.nextInt();
		//If the user entered a negative value, return an empty list.
		if (v < 0)
			return null;
		//If the user entered a valid input value, create a list node for it.
		ListNode node = new ListNode(v);
		//Scan for more values recursively, and set the returned list of values
		//to follow the node we just created.
		
		
		node.next = readInput(inputScanner);
		
		//Return the created list.
		return node;
	}

	/* main()
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		ListNode inputListHead = readInput(s);
		
		int inputLength = listLength(inputListHead);
		System.out.printf("Read %d values.\n",inputLength);
		
		
		long startTime = System.currentTimeMillis();
		
		ListNode sortedListHead = MergeSort(inputListHead);
		
		
		long endTime = System.currentTimeMillis();
		
		double totalTimeSeconds = (endTime-startTime)/1000.0;
		
		//Compute the length of the output list
		int sortedListLength = listLength(sortedListHead);
		
		//Don't print out the values if there are more than 100 of them
		
		
		if (sortedListLength <= 100){
			System.out.println("Sorted values:");
			printList(sortedListHead);
		}
		
		
		
		//Check whether the sort was successful
		boolean isSorted = testSorted(sortedListHead);
		
		System.out.printf("List %s sorted.\n",isSorted? "is":"is not");
		System.out.printf("Total Time (seconds): %.2f\n",totalTimeSeconds);
	}
}
