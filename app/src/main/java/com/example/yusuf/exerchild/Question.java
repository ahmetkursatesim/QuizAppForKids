package com.example.yusuf.exerchild;

public class Question {
    public String questionName;
    public String question;
    public String answer;
    public String wrongAnswer_1;
    public String wrongAnswer_2;
    public String imageName1;
    boolean clikable=false;




    public Question(String qName, String quest, String ans, String wAns1, String wAns2){

        questionName= qName;
        question= quest;
        answer= ans;
        wrongAnswer_1= wAns1;
        wrongAnswer_2= wAns2;



    }


    public Question(String qName, String quest, String a, String wAwer1, String wAwer2, String im){

        questionName= qName;
        question= quest;
        answer= a;
        wrongAnswer_1= wAwer1;
        wrongAnswer_2= wAwer2;
        imageName1=im;



    }
}
