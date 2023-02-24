package package1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {
	static class Node{
		int companynum;
		int numpositions;
		int preferindex;
		
	}
	ArrayList <Integer> finallist= new ArrayList<Integer>();
	ArrayList <Integer> finallist2= new ArrayList<Integer>();

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {//check every company's list of preferred interns until their matched intern to see if the intern prefers them
    	for(int i=0; i<problem.getCompanyCount(); i++) {
    		for(int j=0; j<problem.getCompanyPositions().get(i); j++) {
    			int index=0;
    			int flag=0;
    			while(index<problem.getInternCount()&&flag==0) {//finds index of current match on company preference list
    				int preferredintern=problem.getCompanyPreference().get(i).get(index);
    				for(int k=0; k<problem.getInternCount(); k++) {
    					if(problem.getInternMatching().indexOf(i)==preferredintern) {
    						flag=1;
    					}
    					
    				}
    				index++;
    			}
    			int index2=0;
    			//iterates through every preferred match above the current match to check for stability
    			while(index2<index) {//checks if each company intern match is unstable, or if a preferred intern is unmatched
    				int interntocheck=problem.getCompanyPreference().get(i).get(index2);
    				if(problem.getInternMatching().get(interntocheck)==-1) {
    					return false;
    				}
    				if(Unstable(problem, interntocheck, i, problem.getInternMatching().get(interntocheck))==true) {
    					return false;
    				}
    				index2++;
    			}
    			
    			
    		}
    		
    	}
    	
        

        return true;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_companyoptimal(Matching problem) {
    	int numcompanies=problem.getCompanyCount();
    	int availableinterns=problem.getInternCount();
    	int availablepositions=problem.totalCompanyPositions();
    	
    	LinkedList<Node> companyarray = new LinkedList<Node>();
    	
    	int finishedcompanies=0;
    	
    	
    	for(int i=0; i<availableinterns; i++) {
    		
    		finallist.add(-1);
    	}
    	
    	for(int i=0; i<numcompanies; i++) {//initializes company linked list with each number of positions available and current index
    		Node company= new Node();
    		company.companynum=i;
    		company.numpositions=problem.getCompanyPositions().get(i);
    		company.preferindex=0;
    		companyarray.add(company);
    	}
    	int i=0;
    	while(availablepositions>0) {
    		Node present= companyarray.get(i);
    		
    		while(present.numpositions==0&&availablepositions>0) {//iterates to next node if company is full& repeats list at end
    			i++;
    			i=i%numcompanies;
    			present=companyarray.get(i);
    		}
    		
    		while(present.numpositions>0) {//continues to iterate until every company is full
    			int interncheck=problem.getCompanyPreference().get(present.companynum).get(present.preferindex);
    			present.preferindex=present.preferindex+1;
    			int currentmatch=finallist.get(interncheck);
    			
    			if(currentmatch==-1) {//puts intern into spot if currently unmatched
    				finallist.set(interncheck, present.companynum);
    				present.numpositions=present.numpositions-1;
    				availablepositions--;
    			}
    			if(currentmatch!=-1&&currentmatch!=present.companynum) {//increments numpositions of old company, puts new match in place and decrements its numpositions
    				if(Unstable(problem, interncheck, present.companynum, currentmatch)==true) {
    					Node editNode=companyarray.get(currentmatch);
    					editNode.numpositions++;
    					companyarray.remove(currentmatch);
    					companyarray.add(currentmatch, editNode);
    					finallist.set(interncheck, present.companynum);
        				present.numpositions=present.numpositions-1;
    					
    				}
    				
    			}
    			
    		}
    		
    	}
    	
    	
    	
        /* TODO implement this function */
problem.setInternMatching(finallist);
        return problem;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_internoptimal(Matching problem) {
    	int numcompanies=problem.getCompanyCount();
    	int availableinterns=problem.getInternCount();
    	int availableposition=problem.totalCompanyPositions();
    	
    	
    	LinkedList<Node> companyarray = new LinkedList<Node>();
    	
    	
    	
    	for(int i=0; i<availableinterns; i++) {
    		
    		finallist2.add(-1);
    	}
    	
    	for(int i=0; i<numcompanies; i++) {//initializes company linked list with each number of positions available and current index
    		Node company= new Node();
    		company.companynum=i;
    		company.numpositions=problem.getCompanyPositions().get(i);
    		company.preferindex=0;
    		companyarray.add(company);
    	}
    	
    	int intern=0;
    	int flag2=0;//tracks if you went through the entire list
    	int flag3=0;//tracks if a match was made
       while(availableposition>0||flag2==0) {
    	   while(flag2==0&&finallist2.get(intern)!=-1) {//check with TA
    		   intern++;
    		   if(intern==availableinterns) {
    			   flag2=1;
    			   if(flag3==1) {
    		   intern=intern%availableinterns;
    		   flag2=0;
    		   flag3=0;}
    			   }//brings back to beginning of list
    	   }
    	   if(availableposition>0||flag2==0) { 
    	   int matched=0;
    	   int index=0;
    	 while(matched==0&&index!=numcompanies) {
    		 int company=problem.getInternPreference().get(intern).get(index);
    			 int flag=0;
    			 
    				 
    				 if(companyarray.get(company).numpositions>0) {
    					 companyarray.get(company).numpositions--;
    					 finallist2.set(intern, company); 
    					 flag=1;
    					 matched=1;
    					 availableposition--;
    				 }
    				 int k=0;
    				 int g=0;
    				 while(k<problem.getCompanyPositions().get(company)&&flag==0) {
    			 
    					 while(finallist2.get(g)!=company) {
    						 g++;
    					 }
    				 
    				 if(Unstable2(problem, company, intern, g )==true) {
    					 flag=1;
    					 int oldintern=g;
    					 finallist2.set(intern, company);
    					 finallist2.set(oldintern,-1);
    					 matched=1;
    					 flag3=1;
    					 
    				 }
    				 g++;
    				 k++;
    				 }
    			 
    			 
    		 
    		index++; 
    	 }
    	 intern++;
    	 if(intern==availableinterns) {
			   flag2=1;
			   if(flag3==1) {
		   intern=intern%availableinterns;
		   flag2=0;
		   flag3=0;}
			   }
       }
    	  
    	  
    	 }  
    	   
    	   
       
problem.setInternMatching(finallist2);
        return problem;
    }
    
    
    /**
     * returns true if unstable, false if stable
    
     * 
     * */
   public boolean Unstable(Matching problem, int intern, int company, int currentmatch) {
	   
	   int i=0;
	   int currentind=problem.getInternPreference().get(intern).indexOf(currentmatch);
	   int proposedind=problem.getInternPreference().get(intern).indexOf(company);
	   
	   if(proposedind<currentind) {
		   return true;
	   }
	   return false;
		   
	   }
	   
    

public boolean Unstable2(Matching problem, int company, int proposedintern, int currentintern) {
	   
	   int i=0;
	   int currentind=problem.getCompanyPreference().get(company).indexOf(currentintern);
	   int proposedind=problem.getCompanyPreference().get(company).indexOf(proposedintern);
	   
	   if(proposedind<currentind) {
		   return true;
	   }
	   return false;
		   
	   }
	   
} 
    

