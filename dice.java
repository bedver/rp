import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class dice {

    public static void main(String[] args){

        Hero victim = new Hero();

        System.out.println("Введите навык атакующего.");
        Scanner a = new Scanner(System.in);
        int aSkill = a.nextInt();

        System.out.println("Введите коэффициент характеристик атакующего.");
        Scanner h = new Scanner(System.in);
        int aStat = h.nextInt();

        System.out.println("Введите количество атак.");
        Scanner j = new Scanner(System.in);
        int aCount = j.nextInt();

        System.out.println("Введите урон оружия атакующего.");
        Scanner b = new Scanner(System.in);
        int aDamage = b.nextInt();

        System.out.println("Введите количество единиц здоровья обороняющегося.");
        Scanner f = new Scanner(System.in);
        victim.dHP = f.nextInt();

        System.out.println("Введите навык Уклонения или Парирования обороняющегося.");
        Scanner c = new Scanner(System.in);
        victim.dSkil = c.nextInt();

        System.out.println("Введите соответствующий коэффициент характеристик.");
        Scanner k = new Scanner(System.in);
        victim.dStat = k.nextInt();

        System.out.println("Введите показатель защиты обороняющегося.");
        Scanner d = new Scanner(System.in);
        victim.dDefence = d.nextInt();

        System.out.println("Введите навык брони обороняющегося.");
        Scanner l = new Scanner(System.in);
        victim.dSkill = l.nextInt();

        System.out.println("Введите прочность брони обороняющегося.");
        Scanner e = new Scanner(System.in);
        victim.dDurability = e.nextInt();

        System.out.println("Введите навык Сопротивления Урону обороняющегося.");
        Scanner g = new Scanner(System.in);
        victim.dResist = g.nextInt();

        System.out.println("Введите коэффициент характеристик Сопротивления Урону обороняющегося.");
        Scanner i = new Scanner(System.in);
        victim.dStatt = i.nextInt();

        //////////////////////////////////////////////////////////////////////////

        for (int x = 0; x < aCount; x++) {                                                                              //цикл кол-ва атак
            if (Hit(victim.dSkil, victim.dStat, aSkill, aStat)) {                                                       //проверка попадания

                if (victim.dDurability > 0) {                                                                           //проверяем, жива ли броня

                    int dmg = calcDmg(aDamage, victim.dDefence);                                                        //считаем факт.урон (минус защита)

                    if (Pierce(victim.dDefence, victim.dSkill, aDamage, aSkill)) {                                      //проверка пробития брони
                        victim.dDurability = victim.dDurability - 2;                                                    //и потом снижение качества брони
                        System.out.println("You break through armor! It has " + victim.dDurability + " durability left.");

                        if (Resist(victim.dResist, victim.dStatt, aSkill, aDamage)) {                                   //проверка сопрот.урону, если провал
                            victim.dHP = Success(victim.dHP, dmg);                                                      //считаем урон
                            System.out.println("Damage Resistance check success! You deal " + (dmg / 2) + " dmg.");
                            isAlive(victim.dHP);
                        } else {                                                                                        //если провал,
                            victim.dHP = Fail(victim.dHP, dmg);                                    //считаем урон
                            System.out.println("Damage Resistance check fail! You deal " + dmg + " dmg.");
                            isAlive(victim.dHP);
                        }
                    } else {
                        victim.dDurability = victim.dDurability - 1;
                        System.out.println("You didn't break through armor! It has " + victim.dDurability + " durability left.");
                    }
                } else {                                                                                                //если брони нет, то

                    if (Resist(victim.dResist, victim.dStatt, aSkill, aDamage)) {                                       //проверка сопрот.урону, если провал
                        victim.dHP = Success(victim.dHP, aDamage);                                                      //считаем урон
                        System.out.println("Damage Resistance check success! You deal " + (aDamage / 2) + " dmg.");
                        isAlive(victim.dHP);
                    } else {                                                                                            //если провал,
                        victim.dHP = Fail(victim.dHP, aDamage);                                                         //считаем урон
                        System.out.println("Damage Resistance check fail! You deal " + aDamage + " dmg.");
                        isAlive(victim.dHP);
                    }

                }
                if (victim.dHP <= 0) {
                    break;
                }
            }
            else {System.out.println("You missed!");}
        }
    }


    private static void isAlive (int HP) {
        if (HP <= 0) {
            System.out.println("Enemy is dead!");
        } else {
            System.out.println("Enemy has " + HP + " HP");
        }
    }

    private static boolean Hit(int dSkill, int dStat, int aSkill, int aStat) {
        Random random1 = new Random();
        int x = (dSkill * dStat) / (aSkill * aStat);
        return ((50 - 22 * Math.log(x)) >= (random1.nextInt(100) + 1));
    }

    private static boolean Pierce(int dDef, int dSkill, int aDmg, int aSkill) {
        Random random2 = new Random();
        int x = (dDef * dSkill) / (aDmg * aSkill);
        return ((50 - 22 * Math.log(x)) >= (random2.nextInt(100) + 1));
    }

    private static boolean Resist (int dRes, int dStat, int aSkill, int aDmg) {
        Random random3 = new Random();
        int x = (dRes * dStat)/(aSkill * aDmg);
        return ((50 - 22 * Math.log(x)) >= (random3.nextInt(100) + 1));
    }

    private static int Success (int hp, int dmg) {
        hp = (hp - (dmg/2));
        return hp;
    }

    private static int Fail (int hp, int dmg) {
        hp = (hp - dmg);
        return hp;
    }

    private static int calcDmg(int dmg, int def) {
        if (def > 0) {
            return (dmg - def);
        } else {
            return dmg;
        }
    }

}
