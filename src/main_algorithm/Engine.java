package main_algorithm;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Engine
{
   private static int POP_SIZE = 10;
   private static int TEAM_SIZE = 6;
   private static int TOTAL_TYPES = 18;
   private static int POWER_ATTACK_PENALTY = 25;
   private static int POWER_DEFENSE_PENALTY = 50;
   private static int TYPE_ATTACK_PENALTY = 50;
   private static int TYPE_DEFENSE_PENALTY = 100;
   private static int TYPE_PREF_PENALTY = 250;
   private static int NUM_GENERATIONS = 10000;
   private static double MUTATION_RATE = 0.05;
   private static ArrayList<Pokemon> pokemonList;
   
   private boolean typeMode;
   private Type preferred;
   
   // Constructor - loads in data
   public Engine()
   {
      pokemonList = loadPokemonData();
      Scanner scan = new Scanner(System.in);
      typeMode = promptForMode(scan);
      preferred = new Type(promptForTypePreference(scan));
      scan.close();
   }
   
   // Ask user what type (if any) they would like to build around
   private String promptForTypePreference(Scanner scan)
   {
      boolean chooseType = false;
      System.out.println("The Pokemon Team Picker can also build around a particular type.");
      System.out.println("Would you like to pick a type to focus on? (Y/N)");
      String chooseTypeInput = scan.nextLine();
      String type = "NA";
      if(chooseTypeInput.equals("Y") || chooseTypeInput.equals("Yes"))
         chooseType = true;
      if(chooseType)
      {
         boolean success = false;
         String[] typeNames = {"Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting",
               "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"};
         while(!success)
         {
            System.out.println("Enter the name of the type you'd like your team to emphasize:");
            type = scan.next();
            for (String typeName : typeNames)
            {
               if(type.equals(typeName))
               {
                  type = typeName;
                  success = true;
                  break;
               }
            }
         }
      }
      return type;
   }
   
   // Ask user whether they prefer to optimize for type first or power first
   private boolean promptForMode(Scanner scan)
   {
      System.out.println("The Pokemon Team Picker can optimize for type coverage or overall power.");
      System.out.println("Please enter 'type' for type mode or 'power' for power mode:");
      boolean modeSelected = false;
      boolean choice = true;
      String input;
      
      while(!modeSelected)
      {
         input = scan.nextLine();
         if(input.equals("type"))
         {
            choice = true;
            modeSelected = true;
         }
         else if(input.equals("power"))
         {
            choice = false;
            modeSelected = true;
         }
         else
            System.out.println("Sorry, I didn't catch that. Enter 'type' or 'power' to choose a mode.");
      }
      return choice;
   }
   
   // Fitness function assigns value according to power and type coverage
   private int fitness(Team subject)
   {
      int powerTotal = subject.getTotalPower();
      int attackGaps = TOTAL_TYPES - subject.getTeamTypeCoverage(true).size();
      int defenseGaps = TOTAL_TYPES - subject.getTeamTypeCoverage(false).size();
      int fitness = 0;
      
      if(typeMode)
         fitness = powerTotal - (attackGaps * TYPE_ATTACK_PENALTY) - (defenseGaps * TYPE_DEFENSE_PENALTY);
      else
         fitness = powerTotal - (attackGaps * POWER_ATTACK_PENALTY) - (defenseGaps * POWER_DEFENSE_PENALTY);
      
      if(!preferred.toString().equals("NA"))
      {
         for(int i = 0; i < TEAM_SIZE; i++)
         {
            if(!subject.getPokemon(i).getTypes().contains(preferred.toString()))
               fitness -= TYPE_PREF_PENALTY;
         }
      }
      
      return fitness;
   }
   
   // Data load function - currently pulls from simple text file
   public static ArrayList<Pokemon> loadPokemonData()
   {
      ArrayList<Pokemon> data = new ArrayList<Pokemon>();
      try
      { 
         Scanner datascan = new Scanner(new File("src/pokemon data.txt"));
         while(datascan.hasNextLine())
         {
            data.add(new Pokemon(datascan.next(), datascan.next(), datascan.next(), datascan.nextInt()));
         }
         datascan.close();
         System.out.println("Data loaded successfully");
      }
      catch(FileNotFoundException e)
      { 
         System.out.println("data file not found.");
      }
      return data;
   }
   
   // Roulette selection - best Teams more likely to be chosen as parents
   public static Team selectParent(double[] wheel, Team[] population)
   {
      Team parent = new Team(TEAM_SIZE);
      
      Random random = new Random();
      double ball = random.nextDouble();
      boolean found = false;
      int spot = 0;
      
      while(!found)
      {
         if(ball <= wheel[spot])
         {
            parent = population[spot];
            found = true;
         }
         else
         {
            ball -= wheel[spot];
            spot++;
         }
      }
      
      return parent;
   }
   
   // Uniform crossover generates 2 children, given 2 parents and a basic mask
   public static Team uniformCrossover(Team parent1, Team parent2, boolean[] mask)
   {
      Team child = new Team(TEAM_SIZE);
      
      for(int i = 0; i < TEAM_SIZE; i++)
      {
         if(mask[i])
            child.addPokemon(parent1.getPokemon(i));
         else
            child.addPokemon(parent2.getPokemon(i));
      }
      
      return child;
   }
   
   // Randomizer function to build a new team
   public static Team newTeam()
   {
      Random rand = new Random();
      Team randomTeam = new Team(TEAM_SIZE);
      int pokemonIndex;
      
      for(int i = 0; i < TEAM_SIZE; i++)
      {
         pokemonIndex = rand.nextInt(pokemonList.size());
         randomTeam.addPokemon(pokemonList.get(pokemonIndex));
      }
      
      return randomTeam;
   }
   
   // Main algorithm code
   public static void main(String [] args)
   {
      // Initialize engine object and RNG
      Engine runSpace = new Engine();
      Random random = new Random();
      
      // Generate first population
      Team[] population = new Team[POP_SIZE];
      for(int i = 0; i < POP_SIZE; i++)
      {
         population[i] = newTeam();
         population[i].setFitness(runSpace.fitness(population[i]));
      }
      
      System.out.println("Initial population created.");
      
      // Generation variable declarations
      int totalFitness;
      int bestGen = 0;
      int singleFitness;
      int worst;
      boolean improved = false;
      Pokemon mutant;
      Team best = new Team(TEAM_SIZE);
      Team parent1 = new Team(TEAM_SIZE);
      Team parent2 = new Team(TEAM_SIZE);
      boolean[] mask = new boolean[TEAM_SIZE];
      double[] wheel = new double[POP_SIZE];
      Team[] children = new Team[POP_SIZE];
      
      // MAIN GENERATION LOOP
      for(int gen = 0; gen < NUM_GENERATIONS; gen++)
      {
         // prepare roulette selection wheel
         totalFitness = 0;
         wheel = new double[POP_SIZE];
         for(int i = 0; i < population.length; i++)
         {
            singleFitness = population[i].getFitness();
            totalFitness += singleFitness;
            wheel[i] = singleFitness;
         }
         for(int i = 0; i < POP_SIZE; i++)
            wheel[i] = (double)wheel[i]/(double)totalFitness;
         
         // prepare elitism - find best Team
         for(int i = 0; i < POP_SIZE; i++)
         {
            if(population[i].getFitness() > best.getFitness())
            {
               best = population[i];
               bestGen = gen;
               improved = true;
            }
         }
         
         //create children
         children = new Team[POP_SIZE];
         for(int child = 0; child < children.length; child += 2)
         {
            // pick parents
            parent1 = selectParent(wheel, population);
            parent2 = selectParent(wheel, population);
            
            // build mask and two children
            for(int i = 0; i < TEAM_SIZE; i++)
               mask[i] = random.nextBoolean();
            children[child] = uniformCrossover(parent1, parent2, mask);
            children[child + 1] = uniformCrossover(parent2, parent1, mask);
            
            // occasional mutation, checked separately for each child
            if(random.nextDouble() < MUTATION_RATE)
            {
               mutant = pokemonList.get(random.nextInt(pokemonList.size()));
               children[child].replacePokemon(mutant, random.nextInt(TEAM_SIZE));
            }
            if(random.nextDouble() < MUTATION_RATE)
            {
               mutant = pokemonList.get(random.nextInt(pokemonList.size()));
               children[child + 1].replacePokemon(mutant, random.nextInt(TEAM_SIZE));
            }
         }
         for(Team t : children)
            t.setFitness(runSpace.fitness(t));
         
         // Apply elitism to new generation
         worst = 0;
         for(int i = 0; i < POP_SIZE; i++)
         {
            if(population[i].getFitness() < population[worst].getFitness())
               worst = i;
         }
         population[worst] = best;
         
         // Clean up generation and prepare for next
         if(improved)
         {
            System.out.println(best.simpleToString() + " at gen " + gen);
            improved = false;
         }
         for(int i = 0; i < POP_SIZE; i++)
            population[i] = children[i];
      }
      System.out.println(best.toString());
      System.out.println("Best team found at generation: " + bestGen);
   }
}
