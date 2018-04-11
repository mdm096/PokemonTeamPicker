package main_algorithm;

public class Type
{
   private String type;
   // key: {nor, fir, wat, ele, gra, ice, fig, poi, gro, fly, psy, bug, roc, gho, dra, dar, ste, fai}
   // offensive type chart
   private final int[] NOR_ATT = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
   private final int[] FIR_ATT = {0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,1,0};
   private final int[] WAT_ATT = {0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0};
   private final int[] ELE_ATT = {0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0};
   private final int[] GRA_ATT = {0,0,1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0};
   private final int[] ICE_ATT = {0,0,0,0,1,0,0,0,1,1,0,0,0,0,1,0,0,0};
   private final int[] FIG_ATT = {1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,1,1,0};
   private final int[] POI_ATT = {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1};
   private final int[] GRO_ATT = {0,1,0,1,0,0,0,1,0,0,0,0,1,0,0,0,1,0};
   private final int[] FLY_ATT = {0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0};
   private final int[] PSY_ATT = {0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0};
   private final int[] BUG_ATT = {0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0,0};
   private final int[] ROC_ATT = {0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0};
   private final int[] GHO_ATT = {0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0};
   private final int[] DRA_ATT = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0};
   private final int[] DAR_ATT = {0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0};
   private final int[] STE_ATT = {0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1};
   private final int[] FAI_ATT = {0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,0,0};

   // defensive type chart
   private final int[] NOR_DEF = {0,0,0,0,0,0,-1,0,0,0,0,0,0,2,0,0,0,0};
   private final int[] FIR_DEF = {0,1,-1,0,1,1,0,0,-1,0,0,1,-1,0,0,0,1,1};
   private final int[] WAT_DEF = {0,1,1,-1,-1,1,0,0,0,0,0,0,0,0,0,0,1,0};
   private final int[] ELE_DEF = {0,0,0,1,0,0,0,0,-1,1,0,0,0,0,0,0,1,0};
   private final int[] GRA_DEF = {0,-1,1,1,1,-1,0,-1,1,-1,0,-1,0,0,0,0,0,0};
   private final int[] ICE_DEF = {0,-1,0,0,0,1,-1,0,0,0,0,0,-1,0,0,0,-1,0};
   private final int[] FIG_DEF = {0,0,0,0,0,0,0,0,0,-1,-1,1,1,0,0,1,0,-1};
   private final int[] POI_DEF = {0,0,0,0,1,0,1,1,-1,0,-1,1,0,0,0,0,0,1};
   private final int[] GRO_DEF = {0,0,-1,2,-1,-1,0,1,0,0,0,0,1,0,0,0,0,0};
   private final int[] FLY_DEF = {0,0,0,-1,1,-1,1,0,2,0,0,1,-1,0,0,0,0,0};
   private final int[] PSY_DEF = {0,0,0,0,0,0,1,0,0,0,1,-1,0,-1,0,-1,0,0};
   private final int[] BUG_DEF = {0,-1,0,0,1,0,1,0,1,-1,0,0,-1,0,0,0,0,0};
   private final int[] ROC_DEF = {1,1,-1,0,-1,0,-1,1,-1,1,0,0,0,0,0,0,-1,0};
   private final int[] GHO_DEF = {2,0,0,0,0,0,2,1,0,0,0,1,0,-1,0,-1,0,0};
   private final int[] DRA_DEF = {0,1,1,1,1,-1,0,0,0,0,0,0,0,0,-1,0,0,-1};
   private final int[] DAR_DEF = {0,0,0,0,0,0,-1,0,0,0,2,-1,0,1,0,1,0,-1};
   private final int[] STE_DEF = {1,-1,0,0,1,1,-1,2,-1,1,1,1,1,0,1,0,1,1};
   private final int[] FAI_DEF = {0,0,0,0,0,0,1,-1,0,0,0,1,0,0,2,1,-1,0};
   
   private final int[] NA = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
   
   public Type(String name)
   {
      type = name;
   }
   
   public String toString()
   {
      return type;
   }
   
   public int[] getAttackPlusMinus()
   {
      int[] plusMinus = {0};
      switch(type) {
         case("Normal"):
            plusMinus = NOR_ATT;
            break;
         case("Fire"):
            plusMinus = FIR_ATT;
            break;
         case("Water"):
            plusMinus = WAT_ATT;
            break;
         case("Electric"):
            plusMinus = ELE_ATT;
            break;
         case("Grass"):
            plusMinus = GRA_ATT;
            break;
         case("Ice"):
            plusMinus = ICE_ATT;
            break;
         case("Fighting"):
            plusMinus = FIG_ATT;
            break;
         case("Poison"):
            plusMinus = POI_ATT;
            break;
         case("Ground"):
            plusMinus = GRO_ATT;
            break;
         case("Flying"):
            plusMinus = FLY_ATT;
            break;
         case("Psychic"):
            plusMinus = PSY_ATT;
            break;
         case("Bug"):
            plusMinus = BUG_ATT;
            break;
         case("Rock"):
            plusMinus = ROC_ATT;
            break;
         case("Ghost"):
            plusMinus = GHO_ATT;
            break;
         case("Dragon"):
            plusMinus = DRA_ATT;
            break;
         case("Dark"):
            plusMinus = DAR_ATT;
            break;
         case("Steel"):
            plusMinus = STE_ATT;
            break;
         case("Fairy"):
            plusMinus = FAI_ATT;
            break;
         case("NA"):
            plusMinus = NA;
            break;
      }
      return plusMinus;
   }
   
   public int[] getDefensePlusMinus()
   {
      int[] plusMinus = {0};
      switch(type) {
         case("Normal"):
            plusMinus = NOR_DEF;
            break;
         case("Fire"):
            plusMinus = FIR_DEF;
            break;
         case("Water"):
            plusMinus = WAT_DEF;
            break;
         case("Electric"):
            plusMinus = ELE_DEF;
            break;
         case("Grass"):
            plusMinus = GRA_DEF;
            break;
         case("Ice"):
            plusMinus = ICE_DEF;
            break;
         case("Fighting"):
            plusMinus = FIG_DEF;
            break;
         case("Poison"):
            plusMinus = POI_DEF;
            break;
         case("Ground"):
            plusMinus = GRO_DEF;
            break;
         case("Flying"):
            plusMinus = FLY_DEF;
            break;
         case("Psychic"):
            plusMinus = PSY_DEF;
            break;
         case("Bug"):
            plusMinus = BUG_DEF;
            break;
         case("Rock"):
            plusMinus = ROC_DEF;
            break;
         case("Ghost"):
            plusMinus = GHO_DEF;
            break;
         case("Dragon"):
            plusMinus = DRA_DEF;
            break;
         case("Dark"):
            plusMinus = DAR_DEF;
            break;
         case("Steel"):
            plusMinus = STE_DEF;
            break;
         case("Fairy"):
            plusMinus = FAI_DEF;
            break;
         case("NA"):
            plusMinus = NA;
            break;
      }
      return plusMinus;
   }
}
