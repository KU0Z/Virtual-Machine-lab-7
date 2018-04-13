/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualmachine;

import java.util.LinkedList;

/**
 *
 * @author keviu
 */
public class Parse {
    
    LinkedList<String> listRead= new LinkedList<String>();
    LinkedList<String> resultado= new LinkedList<String>();
    int countlabels=0;
     public Parse(LinkedList<String> alist){
         listRead=alist;
   }
     public void Traslate()
     {
         String[] values;
        for (int i = 0; i < listRead.size(); i++) {
            values=listRead.get(i).split(" ");
            if((values[0]=="add")|(values[0]=="sub")|(values[0]=="neg")|(values[0]=="eq")|(values[0]=="gt")|(values[0]=="lt")|(values[0]=="and")|(values[0]=="or")|(values[0]=="not")){            
            }
            else if((values[0]=="push")|(values[0]=="pop")){

            }

        }
     }
     public String writeArithmetic(String[] values){
         String intrucction="";
         switch(values[0]){
                    case "add": intrucction="@SP\n"+"AM=M-1\n" +"D=M\n" +"A=A-1\n"+ "M=M+D\n" ;
                     break;
                    case "sub": intrucction="@SP\n"+"AM=M-1\n" +"D=M\n" +"A=A-1\n"+ "M=M-D\n";
                     break; 
                    case "neg": intrucction="D=0\n@SP\nA=M-1\nM=D-M\n";
                     break;
                    case "eq": intrucction="@SP\n" +
                                "AM=M-1\n" +
                                "D=M\n" +
                                "A=A-1\n" +
                                "D=M-D\n" +
                                "@FALSE" + countlabels + "\n" +
                                "D;" + "JEQ" + "\n" +
                                "@SP\n" +
                                "A=M-1\n" +
                                "M=-1\n" +
                                "@CONTINUE" + countlabels + "\n" +
                                "0;JMP\n" +
                                "(FALSE" + countlabels + ")\n" +
                                "@SP\n" +
                                "A=M-1\n" +
                                "M=0\n" +
                                "(CONTINUE" + countlabels + ")\n";
                                countlabels++;
                     break;
                    case "gt":  intrucction="@SP\n" +
                                "AM=M-1\n" +
                                "D=M\n" +
                                "A=A-1\n" +
                                "D=M-D\n" +
                                "@FALSE" + countlabels + "\n" +
                                "D;" + "JGT" + "\n" +
                                "@SP\n" +
                                "A=M-1\n" +
                                "M=-1\n" +
                                "@CONTINUE" + countlabels + "\n" +
                                "0;JMP\n" +
                                "(FALSE" + countlabels + ")\n" +
                                "@SP\n" +
                                "A=M-1\n" +
                                "M=0\n" +
                                "(CONTINUE" + countlabels + ")\n";
                                countlabels++;
                     break; 
                    case "lt": intrucction="@SP\n" +
                                "AM=M-1\n" +
                                "D=M\n" +
                                "A=A-1\n" +
                                "D=M-D\n" +
                                "@FALSE" + countlabels + "\n" +
                                "D;" + "JLT" + "\n" +
                                "@SP\n" +
                                "A=M-1\n" +
                                "M=-1\n" +
                                "@CONTINUE" + countlabels + "\n" +
                                "0;JMP\n" +
                                "(FALSE" + countlabels + ")\n" +
                                "@SP\n" +
                                "A=M-1\n" +
                                "M=0\n" +
                                "(CONTINUE" + countlabels + ")\n";
                                countlabels++;
                     break;
                    case "and": intrucction="@SP\n"+"AM=M-1\n" +"D=M\n" +"A=A-1\n"+ "M=M&D\n" ;
                     break;
                     case "or":  intrucction="@SP\n"+"AM=M-1\n" +"D=M\n" +"A=A-1\n"+ "M=M|D\n" ;
                     break; 
                     case "not": intrucction="@SP\nA=M-1\nM=!M\n" ;
                     break; 
                    default: 
                     break;
                }
         return intrucction;
     }
    
}
