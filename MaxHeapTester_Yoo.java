import java.lang.Comparable;
import java.util.ArrayList;


//Reflection: The program works to my knowledge, the print method is designed to neatly print out the heap for comparable objects of any viable toString length, but if there's a big difference in toString lengths between the smallest and biggest toString length values, the spacing will seem off because the element will be printed towards the left with a lot of filler spaces making up for the difference in toString lenghts.
//Note that due to Canvas changing the name of the submitted file if file has been submitted previously, you may need to change the file's name to match the tester class's name
/**
   Tester class which tests the MaxHeap class
*/
public class MaxHeapTester_Yoo{
   public static void main(String[] args){
      MaxHeap h = new MaxHeap<Integer>();
      
      //Testing add method
      System.out.println("Adding elements to heap\n");
      
      h.add(65);
      h.add(43);
      h.add(35);
      h.add(22);
      h.add(41);
      h.add(10);
      h.add(17);
      h.add(16);
      h.add(14);
      h.add(36);
      h.add(38);
      h.add(4);
      h.add(6);
      h.add(7);
      h.add(400003);
      h.print();
            
      //Testing remove root method
      System.out.println("\nRemoving root until heap is empty");
      while (!h.isEmpty()){
         System.out.println("Removed root: " + h.removeRoot());
         h.print();
         System.out.println();
      }
      
      System.out.println("\nPrinting empty tree: ");
      h.print();
   }
}



/**
   Stores Comparable elements in a MaxHeap order
*/
class MaxHeap<E extends Comparable>{
   private ArrayList<E> heap;
   
   /**
      Constructs a new empty MaxHeap
   */
   public MaxHeap(){
      heap = new ArrayList<E>();
      heap.add(null);
   }
   
   /**
      Constructs a new MaxHeap with the given element
   */
   public MaxHeap(E obj){
      heap = new ArrayList<E>();
      heap.add(null);
      heap.add(obj);
   }
   
   /**
      Adds an element to the heap if applicable
   */
   public void add(E obj){
      heap.add(obj);
      
      int index = heap.size() - 1;
      while (index > 1){
         E temp = heap.get(index/2);
         if (obj.compareTo(temp) > 0){
            heap.set(index/2, obj);
            heap.set(index, temp);
            index /= 2;
         }
         else break;
      }
   }
   
   /**
      Prints out the heap elements in the shape of a tree
   */
   public void print(){
      int max = 1;
      int count = 0;
      int height = getHeight();
      int level = 1;
      
      int maxLength = 0;
      for (int i = 1; i < heap.size(); i++) if (heap.get(i).toString().length() > maxLength) maxLength = heap.get(i).toString().length();
      String spaces = "";
      for (int i = 0; i < maxLength; i++) spaces += " ";
      
      for (E obj: heap){
         if (obj == null) continue;
         
         String fillerSpaces = spaces.substring(0, spaces.length() - obj.toString().length());
         
         for (int i = 0; i < Math.pow(2, (height - level)) - 1; i++) System.out.print(spaces);
         System.out.print(obj.toString());
         for (int i = 0; i < Math.pow(2, (height - level)) - 1; i++) System.out.print(spaces);
         System.out.print(spaces);
         System.out.print(fillerSpaces);
         count++;
         if (count == max){
            System.out.println();
            max *= 2;
            count = 0;
            level++;
         }
      }
      System.out.println();
   }
   
   /**
      Helper method which returns the height of the heap
   */
   private int getHeight(){
      int height = 0;
      int count = 0;
      
      while (count < heap.size() - 1){
         count += Math.pow(2, height);
         height++;
      }
      
      return height;
   }
   
   /**
      Removes the root from the heap, reorganizes the heap, and returns the root (returns null if empty)
   */
   public E removeRoot(){
      if (heap.isEmpty()) return null;
      E ret = heap.get(1);
      if (heap.size() == 2){
         heap.remove(1);
         return ret;
      }
      heap.set(1, heap.remove(heap.size() - 1));
      int index = 1;
      
      while (true){
         E left = (index*2 < heap.size()) ? heap.get(index*2) : null;
         E right = (index*2 + 1 < heap.size()) ? heap.get(index*2 + 1) : null;
         if (left == null && right == null) break;
         if (left != null && right == null){
            if (heap.get(index).compareTo(left) < 0){
               E temp = heap.get(index);
               heap.set(index, left);
               heap.set(index*2, temp);
               index *= 2;
            }
            else break;
         }
         if (left != null && right != null){
            if (left.compareTo(right) > 0){
               if (heap.get(index).compareTo(left) < 0){
                  E temp = heap.get(index);
                  heap.set(index, left);
                  heap.set(index*2, temp);
                  index *= 2;
               }
               else break;
            }
            else{
               if (heap.get(index).compareTo(right) < 0){
                  E temp = heap.get(index);
                  heap.set(index, right);
                  heap.set(index*2 + 1, temp);
                  index = index*2 + 1;
               }
            }
         }
      }
      
      return ret;
   }
   
   /**
      Returns whether the heap is empty or not
   */
   public boolean isEmpty(){
      return heap.size() == 1;
   }
}