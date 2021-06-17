import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/* 
 * TextAssociator represents a collection of associations between words.
 */
public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private int size;
	
	/* INNER CLASS
	 * Represents a separate chain in your implementation of your hashing
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/* Creates an empty WordInfoSeparateChain without any WordInfo
		 */
		public WordInfoSeparateChain() {
			chain = new ArrayList<WordInfo>();
		}
		
		/* Adds a WordInfo object to the SeparateChain
		 * Returns true if the WordInfo was successfully added, false otherwise
		 */
		private boolean add(WordInfo wi) {
			if(!chain.contains(wi)){
				chain.add(wi);
				return true;
			}else{
				return false;
			}
		}
		
		/* Removes the given WordInfo object from the separate chain
		 * Returns true if the WordInfo was successfully removed, false otherwise
		 */
		private boolean remove(WordInfo wi) {
			if(chain.contains(wi)){
				chain.remove(wi);
				return true;
			}else{
				return false;
			}
		}
		
		// Returns the size of this separate chain
		private int size() {
			return chain.size();
		}
		
		// Returns the String representation of this separate chain
		public String toString() {
			return chain.toString();
		}
		
		// Returns the list of WordInfo objects in this chain
		private List<WordInfo> getElements() {
			return chain;
		}
	}
	
	
	/* Creates a new TextAssociator without any associations 
	 */
	public TextAssociator() {
		this.size = 0;
		this.table = new WordInfoSeparateChain[100];
	}
	
	//passes in a word and uses that word to return the proper slot an object
	//should be placed in the form of an integer
	private int hashValue(String word){
		return new WordInfo(word).hashCode() % this.table.length;
	}
	
	/* Adds a word with no associations to the TextAssociator 
	 * Returns False if this word is already contained in your TextAssociator ,
	 * Returns True if this word is successfully added
	 */
	public boolean addNewWord(String word) {
		if((double) this.size / this.table.length > 0.85){
			rehash();
		}
		
		int index = hashValue(word);
		WordInfo wordObject = new WordInfo(word);
		if(this.table[index] == null){
			this.table[index] = new WordInfoSeparateChain();
		}
		if(!this.table[index].add(wordObject)) {
			return false;
		}
		this.size++;	
		return true;
	}
	
	//This method creates a structure with more space and reassigns the values 
	//from the old structure into the proper positions in the new structure
	private void rehash(){
		int index;
		WordInfoSeparateChain[] temp = this.table;
		this.table = new WordInfoSeparateChain[temp.length * 2];
		
		for(int i = 0; i < temp.length; i++){
			if(temp[i] != null){
				for(int j = 0; j < temp[i].getElements().size(); j++){
					index = hashValue(temp[i].getElements().get(j).getWord());
					if(this.table[index] == null){
						this.table[index] = new WordInfoSeparateChain();
					}
					this.table[index].add(temp[i].getElements().get(j));
				}
			}
		}
	}
	
	//requires an index and a WordInfo object to be passed
	//returns true or false whether or not a particular word object doesn't exists
	private boolean tableContains(int index, WordInfo wordObject){
		return this.table[index].getElements().contains(wordObject);
	}
	
	
	/* Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the TextAssociator or if 
	 * the association between the two words already exists
	 */
	public boolean addAssociation(String word, String association) {
		WordInfo wordObject = new WordInfo(word);
		int index = hashValue(word);
		
		if(this.table[index] == null || !tableContains(index, wordObject)){
			return false;
		}
		
		int wordIndex = this.table[index].getElements().indexOf(wordObject);
		
		if(this.table[index].getElements().get(wordIndex).getAssociations().contains(association)) {
			return false;
		}else {
			this.table[index].getElements().get(wordIndex).addAssociation(association);
			return true;
		}
	}
	
	
	/* Remove the given word from the TextAssociator, returns false if word 
	 * was not contained, returns true if the word was successfully removed.
	 * Note that only a source word can be removed by this method, not an association.
	 */
	public boolean remove(String word) {
		int index = hashValue(word);
		WordInfo wordObject = new WordInfo(word);
		
		if(this.table[index] == null || !tableContains(index, wordObject)){
			return false;
		}
		
		return this.table[index].remove(wordObject);
	}
	
	//Given a word and an association, removes the association from the given word
	//and returns true if successfully removed, returns false if association doesn't exist with
	//the word
	public boolean removeAssociation(String word, String association) {
		int index = hashValue(word);
		WordInfo wordObject = new WordInfo(word);
		Set<String> s = new HashSet<>();
		s = getAssociations(word);
		
		if(s == null || !s.contains(association)){
			return false;
		}
		
		//removes association now that we confirmed its existence
		this.table[index].getElements().get(this.table[index].getElements()
				.indexOf(wordObject)).removeAssociation(association);
		return true;
	}
	
	
	/* Returns a set of all the words associated with the given String  
	 * Returns null if the given String does not exist in the TextAssociator
	 */
	public Set<String> getAssociations(String word) {
		int index = hashValue(word);
		WordInfo wordObject = new WordInfo(word);
		
		if(this.table[index] == null){
			return null;
		}
		
		for(int i = 0; i < this.table[index].size(); i++){
			if(this.table[index].getElements().get(i).equals(wordObject)){
				return this.table[index].getElements().get(i).getAssociations();
			}
		}
		
		return null;
	}
	
	
	/* Prints the current associations between words being stored
	 * to System.out
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		//Walk through every possible index in the table
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				//For each separate chain, grab each individual WordInfo
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
	
	//Used only for testing in the MyClient file 
	public void printWord(String word) {
		WordInfo wordObject = new WordInfo(word);
		int index = hashValue(word);
		
		if(table[index] != null && tableContains(index, wordObject)) {
			System.out.println(this.table[index].getElements().get(this.table[index].getElements()
					.indexOf(wordObject)));
		}else {
			System.out.println("Word doesn't exist in the current directory.");
		}
	}
}
