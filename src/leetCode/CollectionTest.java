package leetCode;
import java.util.*;

public class CollectionTest {

	public static void testTreeSet() {
		List<String> aList = new ArrayList<>();
		aList.add("a");
		aList.add("b");
		
		List<String> bList = new ArrayList<>();
		bList.add("a");
		bList.add("b");
		
		/*TreeSet<List<String> > aTreeSet = new TreeSet<>(); 
		aTreeSet.add(aList);
		aTreeSet.add(bList);
		
		if(aList instanceof Comparable) {
			System.out.println("comparable");
		}
		
		Collections.sort(aList);*/
		
		HashSet<List<String> > hashListSet = new HashSet<>();
		hashListSet.add(aList);
		hashListSet.add(bList);
		System.out.println("size:" + hashListSet.size());
		
		//hashCode may be equal even for different strings
		TreeSet<List<String>> treeListSet = new TreeSet<>(new Comparator<List<String>>() {
			public int compare(List<String> o1, List<String> o2) {
				return o1.hashCode() - o2.hashCode();
			}
		});
		treeListSet.add(aList);
		treeListSet.add(bList);
		System.out.println("size:" + treeListSet.size());
		
		if(aList.equals(bList)) {
			System.out.println("equal");
		}
	}
	
	public static void testPriorityQueue() {
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		queue.add(1);
		queue.add(5);
		queue.add(4);
		queue.add(3);
		queue.poll();
		
		System.out.println(queue);
		
		queue = new PriorityQueue<>(10, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return b-a;
			}
		});
		queue.add(1);
		queue.add(5);
		queue.add(4);
		queue.add(3);
		queue.poll();
		
		System.out.println(queue);
	}
	
	public static void testMap() {
		//testTreeSet();
		//testPriorityQueue();
		
		HashMap<Integer, String> aMap = new HashMap<>();
		HashMap<Integer, String> bMap = new HashMap<>();
		aMap.put(1, "asde");
		bMap.put(1, "asde");
		System.out.println(aMap.equals(bMap));
	}
	
	public static void testSort() {
		ArrayList<Integer> l = new ArrayList<>();
		l.add(1);
		l.add(4);
		l.add(-3);
		l.add(-5);
		
		Collections.sort(l, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				System.out.println("a:"+a+",b:"+b);
				return 0;
			}
		});
		
		//Collections.sort(l);
	    System.out.println(l);
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		testSort();

	}
	
}
