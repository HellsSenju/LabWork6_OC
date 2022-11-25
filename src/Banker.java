import java.util.Random;

public class Banker {
    int n = 5; //кол во процессов
    int m = 3; //кол во типов ресурсов

    int[] available = new int[] {3, 3, 2}; //вектор доступных ресурсов m
    int[][] max = new int[][] //макс потребности процессов в ресурсах [n][m]
    {
        { 7, 5, 3 }, //P0
        { 3, 2, 2 }, //P1
        { 9, 0, 2 }, //P2
        { 2, 2, 2 }, //P3
        { 4, 3, 3 }  //P4
    };
    int[][] allocation = new int[][] //фактическое выделение ресурсов системой [n][m]
    {
        { 0, 1, 0 }, //P0
        { 2, 0, 0 }, //P1
        { 3, 0, 2 }, //P2
        { 2, 1, 1 }, //P3
        { 0, 0, 2 }  //P4
    };
    int[][] need = new int[n][m]; //оставшиеся потребности процессов в ресурсах

/*    int n; //кол во процессов
    int m; //кол во типов ресурсов

    int[] available; //вектор доступных ресурсов
    int[][] max; //макс потребности процессов в ресурсах [n][m]
    int[][] allocation; //фактическое выделение ресурсов системой [n][m]

    int[][] need ; //оставшиеся потребности процессов в ресурсах*/

/*    public Banker(){
        Random rnd = new Random();
        n = rnd.nextInt(3, 6);
        m = rnd.nextInt(2,4);

        need = new int[n][m];

        available = new int[m];
        for(int i = 0; i < m; i++) available[i] = rnd.nextInt(2,5);

        max = new int[n][m];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                max[i][j] = rnd.nextInt(2,10);

        allocation = new int[n][m];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                allocation[i][j] = rnd.nextInt(0,6);
        Print();
        FillNeed();
    }*/

    public void Do(){
        Print();
        FillNeed();
        String solution = "";

        boolean[] finish = new boolean[n]; //завершаемость процессоров при данном состоянии системы
        for(boolean i : finish) i = false;

        boolean flag;

        int[] work = available; //пробные выделения ресурсов

        //i = n
        //j = m
        int kol = n;
        while(kol > 0){
            for(int i = 0; i < n; i++){
                flag = true;
                for(int j = 0; j < m; j++){
                    if (finish[i] | need[i][j] > work[j]) {
                        flag = false;
                        break;
                    }
                }

                if(flag) //если условие выполняется для всей строки
                {
                    solution += "-> " + "P" + i;
                    for(int j = 0; j < m; j++)
                        work[j] = work[j] + allocation[i][j]; // высвобождаем ресурсы строки
                    finish[i] = true;
                    break;
                }
            }
            kol--;
        }

        flag = true;
        for(int i = 0; i < n; i++)
            if (!finish[i]) {
                flag = false;
                break;
            }

        if(flag) // если во всех ячейках finish - true - система безопасна
        {
            System.out.println("Cистема в безопасном состоянии");
            System.out.println(solution);
        }
        else System.out.println("Cистема в небезопасном состоянии");
    }

    public void FillNeed(){
        System.out.println("need: ");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                need[i][j] = max[i][j] - allocation[i][j];
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void Print(){
        System.out.println("n: " + n);
        System.out.println("M: " + m);

        System.out.println("available: ");
        for(int i = 0; i < m; i++) System.out.print(available[i] + " ");
        System.out.println();

        System.out.println("max: ");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++)
                System.out.print(max[i][j] + " ");
            System.out.println();
        }

        System.out.println("allocation: ");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++)
                System.out.print(allocation[i][j] + " ");
            System.out.println();
        }
    }
}
