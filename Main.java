import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
/**
 * @author Amirhossein Alibakhshi (id: 9731096)
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //in each step of this loop we can create and test an accepter:
        while (true){
            System.out.println("CHOOSE ONE OF THIS OPTIONS:");
            System.out.println(" A - USE A DEFAULT NFA");
            System.out.println(" B - CREATE YOUR OWN NFA");
            System.out.println(" C - CREATE NFA FROM A TEXT FILE");
            System.out.println(" Q - EXIT THE PROGRAM");
            String command = sc.nextLine();
            if (command.equals("Q") || command.equals("q")){
                System.out.println("END OF THE PROGRAM...");
                break;
            }else if (!command.equals("b") && !command.equals("B") && !command.equals("A") && !command.equals("a") && !command.equals("C") && !command.equals("c")){
                System.out.println("PLEASE ENTER A VALID INPUT!");
            }else{
                // we are going to have an accepter by user's command:
                Accepter accepter;
                // to choose how this NFA is going to be:
                if (command.equals("b") || command.equals("B")){
                    System.out.println("CREATING YOUR OWN NFA:");
                    accepter = new Accepter();
                }else if (command.equals("A") || command.equals("a")){
                    System.out.println("USING A DEFAULT NFA:");
                    accepter = new Accepter(true);
                }else{
                    System.out.println("USING A NFA DESCRIBED IN A TEXT FILE:");
                    System.out.println("CHOOSE ONE OF THIS OPTIONS:");
                    System.out.println(" A - THE TEXT FILE NAME IS \"NFA_Input_2.txt\"");
                    System.out.println(" B - THE TEXT FILE HAS ANOTHER NAME");
                    String cmdForTextFileName = sc.nextLine();
                    String filePath;
                    while(true){
                        if (cmdForTextFileName.toLowerCase().equals("a")){
                            filePath = "NFA_Input_2";
                            break;
                        }else if (cmdForTextFileName.toLowerCase().equals("b")){
                            System.out.println("ENTER THE FILE'S NAME (NO \".txt\")");
                            filePath = sc.nextLine().trim();
                            break;
                        }else{
                            System.out.println("PLEASE ENTER A VALID OPTION!");
                        }
                    }
                    try {
                        accepter = new Accepter(".\\"+ filePath +".txt");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("FAILED TO READ THE FILE\nPLEASE TRY AGAIN!");
                        continue;
                    }
                }
                //showing the NFA status:
                accepter.getAcceptorsStatus();


                // converting the NFA to DFA
                if (accepter.areInputsValid()) {
                    DFAfromNFA dfAfromNFA = new DFAfromNFA(accepter);
                    System.out.println("\nOUTPUT OF THE DFA:");
                    System.out.println(dfAfromNFA);
                    System.out.println("");
                    String commandToSaveOrNot;
                    boolean saveIt;
                    while(true){
                        System.out.println("DO YOU LIKE TO SAVE THIS DFA AS A TEXT FILE?");
                        System.out.println(" A - YES\n B - NO");
                        commandToSaveOrNot = sc.nextLine();
                        if (commandToSaveOrNot.toLowerCase().equals("a")){
                            saveIt = true;
                            break;
                        }else if (commandToSaveOrNot.toLowerCase().equals("b")){
                            saveIt = false;
                            break;
                        }else{
                            System.out.println("PLEASE ENTER A VALID OPTION!");
                        }
                    }
                    if (saveIt){
                        //writing in a text file:
                        System.out.println("WRITING THE NEW DFA'S DESCRIPTION ON A TEXT FILE:");
                        System.out.println("CHOOSE ONE OF THIS OPTIONS:");
                        System.out.println(" A - WRITE ON \"NFA_Input_2.txt\"");
                        System.out.println(" B - WRITE ON A  TEXT FILE WITH ANOTHER NAME");
                        String cmdForTextFileName = sc.nextLine();
                        String filePath;
                        while(true){
                            if (cmdForTextFileName.toLowerCase().equals("a")){
                                filePath = "DFA_Output_2";
                                break;
                            }else if (cmdForTextFileName.toLowerCase().equals("b")){
                                System.out.println("ENTER THE FILE'S NAME (NO \".txt\")");
                                filePath = sc.nextLine().trim();
                                break;
                            }else{
                                System.out.println("PLEASE ENTER A VALID OPTION!");
                            }
                        }
                        try {
                            dfAfromNFA.writeDFAOnFile(".\\"+ filePath +".txt");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("FAILED TO READ THE FILE\nPLEASE TRY AGAIN!");
                            continue;
                        }
                    }
                }// if data is valid
            }
        }
    }
}
