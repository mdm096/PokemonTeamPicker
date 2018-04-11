package main_algorithm;
import java.util.ArrayList;


public class Pokemon
{
   private String name;
   private Type type1;
   private Type type2;
   private int basePower;
   
   public Pokemon(String name, String type1, String type2, int basePower)
   {
      this.name = name;
      this.type1 = new Type(type1);
      this.type2 = new Type(type2);
      this.basePower = basePower;
   }
   
   public String getName() {
      return name;
   }
   
   public int getPower() {
      return basePower;
   }
   
   public ArrayList<String> getTypes() {
      ArrayList<String> types = new ArrayList<String>();
      types.add(type1.toString());
      types.add(type2.toString());
      return types;
   }
   
   public ArrayList<String> getStrengths(boolean attackFlag)
   {
      ArrayList<String> covered = new ArrayList<String>();
      
      int thisType;
      int[] type1Coverage;
      int[] type2Coverage;
      
      // return attacking coverage if flag param, else return defense coverage
      if(attackFlag)
      {
         type1Coverage = type1.getAttackPlusMinus();
         type2Coverage = type2.getAttackPlusMinus();
      }
      else
      {
         type1Coverage = type1.getDefensePlusMinus();
         type2Coverage = type2.getDefensePlusMinus();
      }
         
      String[] typeNames = {"Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting",
            "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"};
      
      for(int i=0; i<18; i++)
      {
         try{
            thisType = type1Coverage[i] + type2Coverage[i];
            if(thisType>0)
               covered.add(typeNames[i]);
         }
         catch(ArrayIndexOutOfBoundsException e)
         {
            System.out.println("Bad at " + name);
         }
      }
      
      return covered;
   }
   
   public String toString()
   {
      return "Name: " + name + ", Types: " + type1 + " and " + type2 + ", Base power: " + basePower;
   }
}
