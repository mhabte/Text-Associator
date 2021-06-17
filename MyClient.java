//Used to test methods of TextAssociator.java
public class MyClient {
	public static void main(String[] args){
		
		TextAssociator sc = new TextAssociator();
		System.out.println("Expected result = true");
		System.out.println("  Actual result = " + sc.addNewWord("happy"));
		System.out.println();
		
		System.out.println("Expected result = false");
		System.out.println("  Actual result = " + sc.addNewWord("happy"));
		System.out.println();
		
		sc.addAssociation("happy", "estatic");
		sc.addAssociation("happy", "thrilled");
		sc.addAssociation("happy", "joyous");
		
		sc.addNewWord("angry");
		sc.addAssociation("angry", "pissed off");
		sc.addAssociation("angry", "tilted");
		sc.addAssociation("angry", "ragey");
		sc.addAssociation("angry", "furious");
		
		sc.addNewWord("bad");
		sc.addAssociation("bad", "horrible");
		sc.addAssociation("bad", "terrifying");
		sc.addAssociation("bad", "tepid");
		
		sc.addNewWord("hard");
		sc.addAssociation("hard", "difficult");
		sc.addAssociation("hard", "challenging");
		
		sc.addNewWord("really");
		sc.addAssociation("really", "absolutely");
		
		sc.addNewWord("good");
		sc.addAssociation("good", "marvelous");
		
		sc.addNewWord("very");
		sc.addAssociation("very", "bona fide");
		
		sc.addNewWord("smart");
		sc.addAssociation("smart", "brainy");
		
//		EXPECTED RESULT
//		Current number of elements : 8
//		Current table size: 100
//			in table index, 35: bad: [terrifying, horrible, tepid]
//			in table index, 35: good: [marvelous]
//			in table index, 61: angry: [tilted, pissed off, ragey, furious]
//			in table index, 65: hard: [difficult, challenging]
//			in table index, 65: smart: [brainy]
//			in table index, 74: very: [bona fide]
//			in table index, 80: happy: [estatic, thrilled, joyous]
//			in table index, 85: really: [absolutely]
		
		System.out.println("LOOK AT COMMENTED OUT SECTION OF CODE FOR EXPECTED RESULT");
		System.out.println("ACTUAL RESULT");
		sc.prettyPrint();
		System.out.println();
		
		System.out.println("Expected result = bad: [terrifying, horrible, tepid]");
		System.out.print("  Actual result = ");
		sc.printWord("bad");
		System.out.println();
		
		System.out.println("Expected result = true");
		System.out.println("  Actual result = " + sc.removeAssociation("smart", "brainy"));
		System.out.println();
		
		System.out.println("Expected result = false");
		System.out.println("  Actual result = " + sc.removeAssociation("smart", "brainy"));
		System.out.println();
		
		System.out.println("Expected result = smart: []");
		System.out.print("  Actual result = ");
		sc.printWord("smart");
		System.out.println();
		
		System.out.println("Expected result = true");
		System.out.println("  Actual result = " + sc.remove("smart"));
		System.out.println();
		
		System.out.println("Expected result = false");
		System.out.println("  Actual result = " + sc.remove("smart"));
		System.out.println();
		
		System.out.println("Expected result = Word doesn't exist in the current directory.");
		System.out.print("  Actual result = ");
		sc.printWord("smart");
		System.out.println();	
	}
}
