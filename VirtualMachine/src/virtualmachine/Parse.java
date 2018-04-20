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
         Traslate();
   }
     public void Traslate()
     {
        String[] values;
        String[] valuesinitial= new String[3];
        valuesinitial[0] = "call";
        valuesinitial[1] = "Sys.init";
        valuesinitial[2] = "0";
        resultado.add("@256\n" +
                         "D=A\n" +
                         "@SP\n" +
                         "M=D\n");
        resultado.add(WriteFunctionCalling(valuesinitial));
        for (int i = 0; i < listRead.size(); i++) {
            values=listRead.get(i).split(" ");
            String command=values[0];
            if((command.equals("add"))|(command.equals("sub"))|(command.equals("neg"))|(command.equals("eq"))|(command.equals("gt"))|(command.equals("lt"))|(command.equals("and"))|(command.equals("or"))|(command.equals("not"))){ 
            resultado.add(writeArithmetic(values));
            }
            else if((command.equals("push"))|(command.equals("pop"))){
                resultado.add(WritePushPop(values));
            }
            else if((command.equals("label"))|(command.equals("goto"))|(command.equals("if-goto"))){
                resultado.add(WriteProgramFlow(values));
            }
            else if((command.equals("function"))|(command.equals("call"))|(command.equals("return"))){
                resultado.add(WriteFunctionCalling(values));
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
     public String WritePushPop(String[] values){
         String intrucction="";
         if((values[0].contains("push"))){
             int index=Integer.parseInt(values[2]);
             switch(values[1]){                
                    case "static":int auxstatic=index+16;
                        intrucction=pushprocees(Integer.toString(auxstatic),index,"pointer") ;
                     break;
                    case "this": intrucction=pushprocees("THIS",index,"") ;
                     break; 
                    case "local":intrucction=pushprocees("LCL",index,"");
                     break; 
                    case "argument": intrucction=pushprocees("ARG",index,"");
                     break;
                     case "that":  intrucction=pushprocees("THAT",index,"");
                     break; 
                     case "constant": intrucction="@" + index + "\n" + "D=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
                     break;
                     case "pointer":
                         String aux="THAT";
                         if(index==0){
                             aux="THIS";
                         }
                         intrucction=pushprocees(aux,index,"pointer") ;
                     break; 
                     case "temp": intrucction=pushprocees("R5",index+5,"") ;
                     break; 

                }
         }
         else if((values[0].equals("pop"))){
             int index=Integer.parseInt(values[2]);
             switch(values[1]){
                    case "static":int auxstatic=index+16;
                        intrucction=popProcees(Integer.toString(auxstatic) ,index,"pointer") ;
                     break;
                    case "this": intrucction=popProcees("THIS",index,"") ;
                     break; 
                    case "local":intrucction=popProcees("LCL",index,"");
                     break; 
                    case "argument": intrucction=popProcees("ARG",index,"");
                     break;
                     case "that":  intrucction=popProcees("THAT",index,"");
                     break; 
                     case "constant": 
                     break;
                     case "pointer":
                         String aux="THAT";
                         if(index==0){
                             aux="THIS";
                         }
                         intrucction=popProcees(aux,index,"pointer") ;
                     break; 
                     case "temp": intrucction=popProcees("R5",index+5,"") ;
                     break; 
                }
         }
         else{
             
         }
         return intrucction;
     }
     public String WriteProgramFlow(String[] values){
         String intrucction="";
         switch(values[0]){
                    case "label": intrucction="(" + values[1] +")\n" ;
                     break;
                    case "goto": intrucction= "@" + values[1] +"\n0;JMP\n";
                     break; 
                    case "if-goto": intrucction="@SP\n"+"AM=M-1\n" +"D=M\n" +"A=A-1\n"+ "@" + values[1] +"\nD;JNE\n";
                     break;                 
                    default: 
                     break;
                }
         return intrucction;
     }
     public String WriteFunctionCalling(String[] values){
         String intrucction="";
         switch(values[0]){
                    case "function": intrucction="(" + values[1] +")\n";
                        for (int i = 0; i < Integer.parseInt(values[2]); i++) {
                            intrucction+="@" + 0 + "\n" + "D=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
                        }
                     break;
                    case "call": 
                        String  returnAddress="returnAddress"+countlabels;
                        countlabels++;
                        intrucction+= "@" + returnAddress + "\n" +
                "D=A\n"+
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n";// saves the return address
                        intrucction+= pushprocees("LCL",0,"pointer");// saves the LCL of f
                        intrucction+= pushprocees("ARG",0,"pointer");// saves the ARG of f
                        intrucction+= pushprocees("THIS",0,"pointer");// saves the THIS of f
                        intrucction+= pushprocees("THAT",0,"pointer"); // saves the THAT of f
                        int auxsuma=Integer.parseInt(values[2])+5;
                        intrucction+= "@SP\n" +// repositions SP for g
                        "D=M\n" +
                        "@"+(auxsuma)+"\n" +
                        "D=D-A\n" +
                        "@ARG\n" +
                        "M=D\n" +
                        // repositions LCL for g        
                        "@SP\n" +
                        "D=M\n" +
                        "@LCL\n" +
                        "M=D\n" +
                        "@" + values[1] + "\n" +// transfers control to g
                        "0;JMP\n" +
                        "(" + returnAddress + ")\n";//the generated symbol
                        
                     break; 
                    case "return": intrucction+="@LCL\n" +
                                                "D=M\n" +
                                                "@R11\n" +
                                                "M=D\n" +
                                                "@5\n" +
                                                "A=D-A\n" +
                                                "D=M\n" +
                                                "@R12\n" +
                                                "M=D\n" +              
                                                "@ARG\n"+
                                                "D=M\n"+
                                                "@0\n"+
                                                "D=D+A\n"+
                                                "@R13\n"+
                                                "M=D\n"+
                                                "@SP\n"+
                                                "AM=M-1\n"+
                                                "D=M\n"+
                                                "@R13\n"+
                                                "A=M\n"+
                                                "M=D\n"+
                                                "@ARG\n" +
                                                "D=M\n" +
                                                "@SP\n" +
                                                "M=D+1\n" +
                                                "@R11\n" +
                                                "M=M-1\n" +
                                                "A=M\n" +
                                                "D=M\n" +
                                                "@" + "THAT" + "\n" +
                                                "M=D\n"+
                                                "@R11\n" +
                                                "M=M-1\n" +
                                                "A=M\n" +
                                                "D=M\n" +
                                                "@" + "THIS" + "\n" +
                                                "M=D\n"+
                                                "@R11\n" +
                                                "M=M-1\n" +
                                                "A=M\n" +
                                                "D=M\n" +
                                                "@" + "ARG" + "\n" +
                                                "M=D\n"+
                                                "@R11\n" +
                                                "M=M-1\n" +
                                                "A=M\n" +
                                                "D=M\n" +
                                                "@" + "LCL" + "\n" +
                                                "M=D\n"+
                                                "@R12\n" +
                                                "A=M\n" +
                                                "0;JMP\n";
                     break;                 
                    default: 
                     break;
                }
         return intrucction;
     }
     public String pushprocees(String segment, int index,String pointer){

        String aux="";
        String procees="";
        if(pointer.equals("pointer")){
            
        }
        else{
            aux="@" + index + "\n" + "A=D+A\nD=M\n";
        }

        procees= "@" + segment + "\n" +
                "D=M\n"+
                aux +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n";
        return procees;

    }
     public String popProcees(String segment, int index, String pointer){

        
        String aux="D=A\n";
        String procees="";
        if(pointer.equals("pointer")){
            
        }
        else{
            aux="D=M\n@" + index + "\nD=D+A\n";
        }

        procees= "@" + segment + "\n" +
                aux +
                "@R13\n" +
                "M=D\n" +
                "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@R13\n" +
                "A=M\n" +
                "M=D\n";;
        return procees;

    }
    
}
