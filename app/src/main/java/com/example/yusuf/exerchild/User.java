package com.example.yusuf.exerchild;

public class User implements Comparable<User>{

        private String username;
        private String password;
        private int age;
        private String city;
        private String gender;
        public int scoreMat,scoreIng;
        public  double average_score;


    public User(String username, String password, int age, String city, String gender,
        int scoreMat,int scoreIng) {
            this.username = username;
            this.password = password;
            this.age = age;
            this.city = city;
            this.gender = gender;
            this.scoreMat = scoreMat;
            this.scoreIng = scoreIng;
        }

    public User(String username, int age, String city, String gender) {
            this.username = username;
            this.age = age;
            this.city = city;
            this.gender = gender;
        }



    public User(String username, String password, int age, String city, String gender) {
            this.username = username;
            this.password = password;
            this.age = age;
            this.city = city;
            this.gender = gender;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getScoreMat() {
            return scoreMat;
        }

        public void setScoreMat(int scoreMat) {
            this.scoreMat = scoreMat;
        }

        public int getScoreIng() {
            return scoreIng;
        }

        public void setScoreIng(int scoreIng) {
            this.scoreIng = scoreIng;
        }

        public double getAverage_score() {
            return average_score;
        }

        public void setAverage_score(double average_score) {
            this.average_score = average_score;
        }

        public String toString(){
            return "username:"+username+" password:"+password+" age:"+age+" city:"+city+" gender:"+gender+
                    " matematik:"+scoreMat+" ingilizce:"+scoreIng+
                    " average score:"+average_score;
        }

        public String toStringScore(int score, String ders, int tarih){
            return "username:"+username+" age:"+age+" city:"+city+" gender:"+gender+" "+ders+":"+score+" tarih:"+tarih ;
        }

        public String toStringScore( String mat,int matscr,String ingilizce,int ingscr, int tarih){
            return "username:"+username+" age:"+age+" city:"+city+" gender:"+gender+" "
                    +mat+":"+matscr+" "
                    +ingilizce+":"+ingscr
                    +" tarih:"+tarih ;
        }

        @Override
        public int compareTo( User o) {
            return Double.compare(this.average_score,o.average_score );
        }
}
