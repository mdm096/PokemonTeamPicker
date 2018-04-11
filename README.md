# PokemonTeamPicker
Use a genetic algorithm to create optimal Pokemon teams with user-defined criteria.

Currently, this project is a barebones genetic algorithm that does the following:

  1. Reads in data from text file describing each Pokemon
    - Name (String), two types (String, NA if no second type), and base power (int)
    
  2. Randomly generates a population of Pokemon teams
  
  3. Assigns each Team a fitness score
    - Based on hardcoded values as well as user-defined preferences:
      + Two modes of operation are available: Type or Power Mode
        + If 'Type Mode' is selected, fitness penalties for type coverage gaps are doubled
        + If 'Power Mode' is selected, fitness penalties for type coverage gaps are weaker
      + User can specify a particular type to build around
        + If a type is specified, a set penalty is applied for each Pokemon that does not have the chosen type
    
  4. Uses several standard Genetic Algorithm processes to evolve the best team discoverable within a set number of generations
    - Genetic Algorithm methods applied include:
      + Roulette selection
      + Uniform crossover
      + Point mutation
      
Note: This project currently has no frontent, only a directory and an index page as placeholders for an eventual frontend to come. The plan is to use standard HTML, CSS, and JavaScript and call the Java algorithm code by creating a JSP file that will be requested by an AJAX call from the JavaScript.
