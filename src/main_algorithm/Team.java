package main_algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class Team
{
   private Pokemon[] teamArray;
   private int numPokemon;
   private int fitness = 0;
   
   public Team(int size)
   {
      teamArray = new Pokemon[size];
      numPokemon = 0;
   }
   
   public boolean addPokemon(Pokemon newPokemon)
   {
      if(numPokemon < teamArray.length)
      {
         teamArray[numPokemon] = newPokemon;
         numPokemon++;
         return true;
      }
      
      return false;
   }
   
   public Pokemon getPokemon(int index)
   {
      return teamArray[index];
   }
   
   public void replacePokemon(Pokemon newPokemon, int index)
   {
      teamArray[index] = newPokemon;
   }
   
   public int getTotalPower()
   {
      int total = 0;
      for(Pokemon pokemon : teamArray)
      {
         total += pokemon.getPower();
      }
      return total;
   }
   
   public void setFitness(int newFitness)
   {
      fitness = newFitness;
   }
   
   public int getFitness()
   {
      return fitness;
   }
   
   public ArrayList<String> getTeamTypeCoverage(boolean attackFlag)
   {
      ArrayList<String> teamCoverage = new ArrayList<String>();
      
      for(Pokemon pokemon : teamArray)
      {
         ArrayList<String> pokemonCoverage = pokemon.getStrengths(attackFlag);
         for(String type : pokemonCoverage)
         {
            if(!teamCoverage.contains(type))
               teamCoverage.add(type);
         }
      }
      
      return teamCoverage;
   }
   
   public ArrayList<String> getTeamTypeGaps(boolean attackFlag)
   {
      ArrayList<String> allTypes = new ArrayList<String>(Arrays
            .asList("Fire", "Grass", "Ice", "Fighting", "Psychic", "Steel", "Fairy", "Electric", "Poison", "Rock", "Normal", "Ground",
                  "Flying", "Bug", "Water", "Dragon", "Dark", "Ghost"));
      
      ArrayList<String> gaps = new ArrayList<String>();
      ArrayList<String> covered = getTeamTypeCoverage(attackFlag);
      
      for(String type : allTypes)
      {
         if(!covered.contains(type))
            gaps.add(type);
      }
      
      if(gaps.size() == 0)
         gaps.add("None");
      
      return gaps;
   }
   
   public String simpleToString()
   {
      String total = "";
      
      for(int i = 0; i < teamArray.length; i++)
      {
         total += teamArray[i].getName() + ", ";
      }
      
      return total + "Fitness: " + fitness;
   }
   
   public String toString()
   {
      String total = "Team Summary\n";
      
      for(int i = 0; i < teamArray.length; i++)
      {
         total += teamArray[i].toString() + "\n";
      }
      
      total += "\nTeam offensively weak against: ";
      
      for(String type : getTeamTypeGaps(true))
      {
         total += type + ", ";
      }
      
      total += "\nTeam defensively weak against: ";
      
      for(String type : getTeamTypeGaps(false))
      {
         total += type + ", ";
      }
      
      total += "\nOverall fitness score: " + fitness;
      
      return total + "\n";
   }
}
