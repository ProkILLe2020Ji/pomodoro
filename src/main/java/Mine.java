import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Mine {
    static boolean isTest = false;
    static boolean add = false;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Добро пожаловать в Pomodoro. Напишите пожалуйста команду ('-help' для помощи).");
        String[] cmd = new Scanner(System.in).nextLine().split(" ");
        int workMin = 1;
        int breakMin = 1;
        int count = 1;
        int sizePrint = 60;
        int factor = 1;
        boolean isCallHelp = false;
        for (int i=0; i < cmd.length; i++) {
            switch (cmd[i]) {
                case "-help" -> {
                    printHelpMsg();
                    isCallHelp = true;
                }
                case "-w" -> workMin = Integer.parseInt(cmd[++i]);
                case "-b" -> breakMin = Integer.parseInt(cmd[++i]);
                case "-count" -> count = Integer.parseInt(cmd[++i]);
                case "-t" -> isTest = true;
                case "-m" -> factor = Integer.parseInt(cmd[++i]);
            }
        }
        if (!isCallHelp) {
            System.out.printf("Работаем %d min, " + "отдыхаем %d min, кол-во подходов %d.\n", workMin, breakMin, count);
            if (factor > 1){
                System.out.printf("Время работы будет увеличиваться в " + factor +" раз(а) с каждым подходом.\n");
            }else {
                System.out.println("Время работы будет сохранено для каждого подхода.\n");
            }
            long startTime = System.currentTimeMillis();
            for (int i = 1; i <= count; i++) {
                timer(workMin, breakMin, sizePrint);
                    workMin *= factor;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Pomodoro таймер истек. Время работы: " + (endTime - startTime)/(1000 * 60) + " минут.");
            for (int i=0; i < cmd.length; i++) {
                switch (cmd[i]) {
                    case "-help" -> {
                        printHelpMsg();
                        isCallHelp = true;
                    }
                    case "-w" -> workMin = Integer.parseInt(cmd[++i]);
                    case "-b" -> breakMin = Integer.parseInt(cmd[++i]);
                    case "-count" -> count = Integer.parseInt(cmd[++i]);
                    case "-t" -> isTest = true;
                    case "-a" -> add = true;
                }
            }
        }
    }
    private static void timer(int workTime, int breakTime, int sizeProgressBar) throws InterruptedException {
        printProgress("Время работать: ", workTime, sizeProgressBar);

        printProgress("Время отдыхать: ", breakTime, sizeProgressBar);
    }
    private static void printProgress(String process, int time, int size) throws InterruptedException {
        int length;
        int rep;
        length = 60* time / size;
        rep = 60* time /length;
        int stretchb = size /(3* time);
        for(int i=1; i <= rep; i++){
            double x = i;
            x = 1.0/3.0 *x;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            double w = time *stretchb;
            double percent = (x/w) *1000;
            x /=stretchb;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            percent = Math.round(percent);
            percent /= 10;
            System.out.print(process + percent+"% " + (" ").repeat(5 - (String.valueOf(percent).length())) +"[" + ("#").repeat(i) + ("-").repeat(rep - i)+"]    ( " + x +"min / " + time +"min )"+  "\r");
            if(!isTest){
                TimeUnit.SECONDS.sleep(length);
            }
        }
        System.out.println();
    }
    private static void printHelpMsg() {
        System.out.println(
                "\n\nPomodoro - сделай свое время более эффективным.\n");
        System.out.println(
                "-w <time>: время работы, сколько хочешь работать.\n");
        System.out.println(
                "-b <time>: время отдыха, сколько хочешь отдыхать.\n");
        System.out.println(
                "-count <count>: количество повторений.\n");
        System.out.println(
                "-m <factor time>: увеличение времени работы с каждым повторомю.\n");
        System.out.println(
                "-t <time skip>: пропустить время работы таймера.\n");
        System.out.println("Напишите пожалуйста команду.");
        String[] cmd = new Scanner(System.in).nextLine().split(" ");
    }
}
